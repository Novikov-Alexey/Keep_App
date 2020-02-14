package com.devnovikov.keepapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

    private Context context;
    private ArrayList<Note> arrayList;
    DatabaseHelper databaseHelper;

    public Adapter(Context context, ArrayList<Note> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).
                inflate(R.layout.note_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        Note note = arrayList.get(position);
        //get for view
        final String id = note.getId();
        final String image = note.getImage();
        final String title = note.getTitle();
        final String subtitle = note.getSubTitle();
        final String addTimeStamp = note.getAddTimeStamp();
        final String updateTimeStamp = note.getUpdateTimeStamp();

        //set views
        holder.profileIv.setImageURI(Uri.parse(image));
        holder.title.setText(title);
        holder.subtitle.setText(subtitle);

        holder.editItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NoteActivity.class);
                intent.putExtra("ID", id);
                intent.putExtra("TITLE", title);
                intent.putExtra("SUBTITLE", subtitle);
                intent.putExtra("IMAGE", image);
                intent.putExtra("ADD_TIMESTAMP", addTimeStamp);
                intent.putExtra("UPDATE_TIMESTAMP", updateTimeStamp);
                intent.putExtra("EDIT_MODE", true);
                context.startActivity(intent);
            }
        });

        //when long press on item
        //show alert dialog for delete item
        holder.editItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(context, "hi", Toast.LENGTH_SHORT).show();
                deleteDialog(
                        ""+id
                );
                return false;
            }
        });
    }

    private void deleteDialog(final String id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        builder.setTitle("Delete");
        builder.setMessage("Are you want to delete?");
        builder.setCancelable(true);
        builder.setIcon(R.drawable.ic_delete_white_24px);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseHelper.deleteInfo(id);
                ((MainActivity)context).onResume();
                Toast.makeText(context, "Delete Successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.create().show();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        //BindView(R.id.profileIv) ImageView profileIv;
        //BindView(R.id.title) TextView title;
        //BindView(R.id.subTitlle) TextView subTitlle;
        //BindView(R.id.phone) TextView phone;
        ImageView profileIv;
        TextView title, subtitle;
        ConstraintLayout editItem;

        public Holder(@NonNull View itemView) {
            super(itemView);
            profileIv = itemView.findViewById(R.id.profileIv);
            title = itemView.findViewById(R.id.title);
            subtitle = itemView.findViewById(R.id.subTitlle);
            editItem = itemView.findViewById(R.id.editItem);
        }
    }
}
