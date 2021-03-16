package Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import Models.finalOrderModel;
import Models.model;
import Models.randomModel;
import de.hdodenhof.circleimageview.CircleImageView;
import debasishbarmandevoleper.com.miniproject.R;
import debasishbarmandevoleper.com.miniproject.User_Profile;
import debasishbarmandevoleper.com.miniproject.edit_user;
import debasishbarmandevoleper.com.miniproject.updateMenu;

public class horizontalAdapter extends RecyclerView.Adapter<horizontalAdapter.myView> {

   private ArrayList<randomModel> list;
   Context context;


   public horizontalAdapter(ArrayList<randomModel> list,Context context){
       this.list=list;
       this.context=context;
   }

    @NonNull
    @Override
    public myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
        return new myView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myView holder, int position) {
            String cate;
            randomModel model=list.get(position);
            holder.textView.setText(model.getText());
            Glide.with(context).load(model.getPic()).into(holder.imageView);
            cate= (String) holder.textView.getText().toString();
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(holder.textView.getContext(), ""+cate, Toast.LENGTH_SHORT).show();

                    SharedPreferences share=holder.textView.getContext().getSharedPreferences("name", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=share.edit();
                    editor.putString("category",cate);
                    editor.apply();
                    Intent i=new Intent((Activity)holder.textView.getContext(), User_Profile.class);
                    ((Activity)holder.textView.getContext()).startActivity(i);
                    ((Activity) holder.textView.getContext()).finish();
                }
            });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myView extends RecyclerView.ViewHolder{

        TextView textView;
        ImageView imageView;
        myAdapter adapter;
       // RecyclerView recyclerView;
        public myView(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.pizza);
            imageView=itemView.findViewById(R.id.imageView8);
           // recyclerView=itemView.findViewById(R.id.recView);
           // adapter=new myAdapter(options);
        }
    }
}
