package com.devnovikov.keepapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoteActivity extends AppCompatActivity {


    @BindView(R.id.personImage)
    ImageView pImageView;
    @BindView(R.id.note_title)
    EditText pNameEt;
    @BindView(R.id.note_subtitle)
    EditText pAgeEt;
    @BindView(R.id.addButton)
    Button saveButton;
    @BindView(R.id.toolbar_note)
    Toolbar toolbar;

    ActionBar actionBar;


    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;

    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private static final int IMAGE_PICK_GALLERY_CODE = 103;

    private String[] cameraPermissions;
    private String[] storagePermissions;

    private Uri imageUri;

    private String id, title, subTitle, addTimeStamp, updateTimeStamp;
    private boolean editMode = false;
    private DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        //initiate database object in main fucntion
        dbHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        editMode = intent.getExtras().getBoolean("EDIT_MODE");

        if (editMode) {
            actionBar.setTitle("Update Note");
            saveButton.setVisibility(View.INVISIBLE);
            //saveButton.setText("Update");

            id = intent.getStringExtra("ID");
            title = intent.getStringExtra("TITLE");
            subTitle = intent.getStringExtra("SUBTITLE");
            imageUri = Uri.parse(intent.getStringExtra("IMAGE"));
            addTimeStamp = intent.getStringExtra("ADD_TIMESTAMP");
            updateTimeStamp = intent.getStringExtra("UPDATE_TIMESTAMP");

            pNameEt.setText(title);
            pAgeEt.setText(subTitle);

            if (imageUri.toString().equals(null)) {
                pImageView.setImageResource(R.drawable.ic_action_addphoto);
            } else {
                pImageView.setImageURI(imageUri);
            }

        } else {
            saveButton.setVisibility(View.VISIBLE);
            actionBar.setTitle("Add Information");
            saveButton.setText("Add Note");
        }

    }

    @OnClick(R.id.personImage)
    public void onPersonImageClicked() {
        imagePickDialog();
    }

    private void imagePickDialog() {
        String[] options = {"Camera", "Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select for image");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    //if 0 then open the camera and also check the permission of camera
                    if (!checkCameraPermission()) {
                        //if permission not granted then request for camera permission
                        requestCameraPermission();
                    } else {
                        pickFromCamera();
                    }
                } else if (which == 1) {
                    if (!checkStoragePermission()) {
                        requestStoragePermission();
                    } else {
                        pickFromStorage();
                    }
                }
            }
        });
        builder.create().show();
    }

    private void pickFromStorage() {
        //get image from gallery
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera() {
        //get image from camera
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Image title");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image description");

        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    @OnClick(R.id.addButton)
    public void onAddButtonClicked() {
        //when click on save button insert the data to db
        getData();


    }

    private void getData() {

        title = pNameEt.getText().toString().trim();
        subTitle = pAgeEt.getText().toString().trim();

        if (editMode) {
            String newUpdateTime = "" + System.currentTimeMillis();
            dbHelper.updateInfo(id, title, subTitle, "" + imageUri, addTimeStamp, newUpdateTime);
            Toast.makeText(this, "Update successfully!", Toast.LENGTH_SHORT).show();

        } else {
            String timeStamp = "" + System.currentTimeMillis();

            //long id = dbHelper.insertInfo(title, subTitlle, phone, ""+imageUri, timeStamp, timeStamp);
            dbHelper.insertInfo(title, subTitle, ""+imageUri, timeStamp, timeStamp);
            Toast.makeText(this, "Record added", Toast.LENGTH_SHORT).show();
        }


        //Toast.makeText(this, "Record added to id: " + id, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(NoteActivity.this, MainActivity.class));
    }

    @Override
    public void onBackPressed() {
        if (editMode) {
            getData();
        }
        super.onBackPressed();
    }

    private boolean checkStoragePermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED) &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults.length>0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && storageAccepted) {
                        pickFromCamera();
                    } else {
                        Toast.makeText(this, "Camera permission required!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case STORAGE_REQUEST_CODE:
                if (grantResults.length>0) {

                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted) {
                        pickFromStorage();
                    }
                    else {
                        Toast.makeText(this, "Storage permission required!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            } else if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);

                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();
                    imageUri = resultUri;
                    pImageView.setImageURI(resultUri);
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                    Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
