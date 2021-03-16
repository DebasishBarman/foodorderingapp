package Adapter;

import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import Models.model;
import debasishbarmandevoleper.com.miniproject.R;

public class myAdapter extends FirestoreRecyclerAdapter<model,myAdapter.myViewHolder> {

    private RequestQueue myReq;
    ProgressDialog dialog;
    FirebaseFirestore db;
    FirebaseAuth auth;
    DocumentReference root;

    public myAdapter(@NonNull FirestoreRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder,final int position, @NonNull model model) {
        holder.title.setText(model.getTitle());
        holder.price.setText(model.getPrice());
        holder.desc.setText(model.getDescription());
        Glide.with(holder.img.getContext()).load(model.getImgUrl()).into(holder.img);

        holder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Query query=;

                db=FirebaseFirestore.getInstance();
                auth=FirebaseAuth.getInstance();
                root=db.collection("cart").document();///try
                Map<String ,Object> add=new HashMap<>();
                add.put("user_id",holder.firebaseAuth.getCurrentUser().getUid());//current user of app
                add.put("admin_id",model.getAdmin_id()); ///admin who posted the food
                add.put("title",model.getTitle());
                add.put("price",model.getPrice());
                add.put("imgUrl",model.getImgUrl());
                add.put("order_id",root.getId());
                root.set(add,SetOptions.merge());
                Toast.makeText(holder.img.getContext(), "Added to Cart", Toast.LENGTH_SHORT).show();
            }

        });

        //accessing food row Button
        /*holder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.buy_dialog))
                        .setExpanded(true,1300)
                        .create();

                FirebaseFirestore fstore=FirebaseFirestore.getInstance();

                DocumentReference dRefs=fstore.collection("tempOrder").document();
                //.collection("myOrder").document();//testing
                View view1=dialogPlus.getHolderView();
                ImageView imgView=view1.findViewById(R.id.imageView5);
                EditText qty=view1.findViewById(R.id.qty);
                TextView title=view1.findViewById(R.id.textView14);
                TextView price=view1.findViewById(R.id.textView15);
                Button buySingle=view1.findViewById(R.id.button3);
                Glide.with(imgView.getContext()).load(model.getImgUrl()).into(imgView);//problem might occur
                title.setText(model.getTitle());
                price.setText(model.getPrice());

                buySingle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog=new ProgressDialog(view1.getContext());
                        dialog.setMessage("Ordering...");
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();
                        Map<String,Object> order=new HashMap<>();

                        order.put("user_id",holder.firebaseAuth.getCurrentUser().getUid());//current user of app
                        order.put("admin_id",model.getAdmin_id()); ///admin who posted the food
                        order.put("name",model.getTitle());
                        order.put("price",model.getPrice());
                        order.put("quantity",qty.getText().toString().trim());
                        order.put("order_id",dRefs.getId());///get DOCUMENT ID OF CURRENT DOCUMENT ID

                        dRefs.set(order).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                dialog.dismiss();
                                Toast.makeText(view1.getContext(), "Order Complete", Toast.LENGTH_SHORT).show();
                                dialogPlus.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialog.dismiss();
                                Toast.makeText(view1.getContext(), "Failed to order", Toast.LENGTH_SHORT).show();
                                dialogPlus.dismiss();
                            }
                        })
                        ;


                        Toast.makeText(view1.getContext(), "Sending Order to Chef Wait...", Toast.LENGTH_SHORT).show();

                    }
                });

                dialogPlus.show();

            }
        });*/  //code here

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.food_row,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title,desc,price;
        Button buy;
        FirebaseAuth firebaseAuth;
            public myViewHolder(@NonNull View itemView) {
                super(itemView);
                firebaseAuth=FirebaseAuth.getInstance();
                img=itemView.findViewById(R.id.imageView3);
                title=itemView.findViewById(R.id.titleRow);
                price=itemView.findViewById(R.id.textView13);
                desc=itemView.findViewById(R.id.textView12);
                buy=itemView.findViewById(R.id.button2);
            }
        }
}
