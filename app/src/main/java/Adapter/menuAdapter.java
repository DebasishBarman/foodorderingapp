package Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.DialogPlusBuilder;
import com.orhanobut.dialogplus.ViewHolder;

import Models.menuModel;
import debasishbarmandevoleper.com.miniproject.My_Menus;
import debasishbarmandevoleper.com.miniproject.R;
import debasishbarmandevoleper.com.miniproject.updateMenu;

import static android.content.Intent.*;

public class menuAdapter extends FirestoreRecyclerAdapter<menuModel,menuAdapter.myViewHolder> {
    Context context;

    public menuAdapter(@NonNull FirestoreRecyclerOptions<menuModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull menuModel model) {
        holder.name.setText(model.getTitle());
        holder.price.setText(model.getPrice());
        Glide.with(holder.img.getContext()).load(model.getImgUrl()).into(holder.img);
        ////dialog opener
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentSnapshot snapshot=getSnapshots().getSnapshot(holder.getAdapterPosition());
                final  String id=snapshot.getId();
                Intent i=new Intent((Activity)holder.img.getContext(),updateMenu.class);
                i.putExtra("name",model.getTitle());
                i.putExtra("price",model.getPrice());
                i.putExtra("desc",model.getDescription());
                i.putExtra("imgurl",model.getImgUrl());
                i.putExtra("id",id);

                ((Activity)holder.img.getContext()).startActivity(i);
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.my_menu_rows,parent,false);
        return new myViewHolder(v);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView name,price;
        ImageView img;
        Button btn;
        private FirebaseAuth auth;
        private FirebaseFirestore firestore;
        private FirebaseStorage storage;
        private StorageReference storageReference;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.textView27);
            price=itemView.findViewById(R.id.textView32);
            img=itemView.findViewById(R.id.imageView7);
            btn=itemView.findViewById(R.id.button13);
            auth=FirebaseAuth.getInstance();
            firestore=FirebaseFirestore.getInstance();
            storage=FirebaseStorage.getInstance();



        }
    }

}
