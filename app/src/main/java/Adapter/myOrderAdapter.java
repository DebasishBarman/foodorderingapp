package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import Models.OrderModel;
import Models.finalOrderModel;
import debasishbarmandevoleper.com.miniproject.R;

public class myOrderAdapter extends FirestoreRecyclerAdapter<finalOrderModel,myOrderAdapter.myOrderViewHolder> {


    public myOrderAdapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myOrderViewHolder holder, int position, @NonNull finalOrderModel model) {
        holder.name.setText(model.getTitle());
        holder.price.setText(model.getTotal_price());
        holder.id.setText(model.getOrder_id());
        holder.qty.setText(model.getQuantity());

       // Toast.makeText(holder.name.getContext(), ""+model.getAdmin_id(), Toast.LENGTH_SHORT).show();

        //accept order Button

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*DocumentReference doc=holder.fstore.collection("AcceptedOrder").document(); //Accepting the order and storing it in Accept
                Map<String,Object> acptOrd =new HashMap<>();
                acptOrd.put("order_id",model.getOrder_id());
                acptOrd.put("admin_id",model.getAdmin_id());
                acptOrd.put("user_id",model.getUser_id());
                acptOrd.put("food_name", model.getTitle());
                acptOrd.put("address",model.getAddress());
                acptOrd.put("doc_id",doc.getId());
                acptOrd.put("total_price",model.getTotal_price());
                acptOrd.put("status","1"); //for accepted
                //future update address;
                doc.set(acptOrd, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(holder.name.getContext(), "Starting to work on Current Order", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(holder.name.getContext(), "Order initialization failed", Toast.LENGTH_SHORT).show();
                    }
                });*/

                //getting is of a particular data
                DocumentSnapshot snapshot=getSnapshots().getSnapshot(holder.getAdapterPosition());
                String id=snapshot.getId();
                DocumentReference reference=holder.fstore.collection("PlacedOrder").document(id);
                Map<String,Object> plcorder =new HashMap<>();
                plcorder.put("status","1"); //for accepted*/
                reference.update(plcorder).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(holder.price.getContext(), "Successful.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(holder.price.getContext(), "Oops Error Occured.", Toast.LENGTH_SHORT).show();
                    }
                });



                ///delete after accepting order
               /* FirebaseFirestore.getInstance().collection("PlacedOrder")
                        .document(model.getIds()).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(holder.name.getContext(), "Success", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                              //  Toast.makeText(holder.name.getContext(), "Failed to Delete", Toast.LENGTH_SHORT).show();
                            }
                        });*/
            }
        });

        ///work here for reject Order
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //store in rejectedOrder table and display rejected to user from database;
                DocumentReference reject=holder.fstore.collection("RejectedOrder").document(); //rejecting the order and storing it in reject table
                Map<String,Object> acptOrd =new HashMap<>();
                acptOrd.put("order_id",model.getOrder_id());
                acptOrd.put("admin_id",model.getAdmin_id());
                acptOrd.put("user_id",model.getUser_id());
                acptOrd.put("food_name", model.getTitle());
                acptOrd.put("price",model.getTotal_price());
                acptOrd.put("status","0");
                //future update address;
                reject.set(acptOrd, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(holder.name.getContext(), "Rejected", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(holder.name.getContext(), "Order initialization failed", Toast.LENGTH_SHORT).show();
                    }
                });



                ///Rejected and setting Placed order status to 3 ==rejected
                DocumentSnapshot snapshot=getSnapshots().getSnapshot(holder.getAdapterPosition());
                String id=snapshot.getId();
                DocumentReference reference=holder.fstore.collection("PlacedOrder").document(id);
                Map<String,Object> plcorder =new HashMap<>();
                plcorder.put("status","3"); //for status 3 for rejected orders
                reference.update(plcorder).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(holder.price.getContext(), "Successful.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(holder.price.getContext(), "Oops Error Occured.", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    @NonNull
    @Override
    public myOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_row,parent,false);
        return new myOrderViewHolder(view);
    }

    class myOrderViewHolder extends RecyclerView.ViewHolder {

        TextView id,price,qty,name;
        Button accept,reject;
        FirebaseFirestore fstore;
        FirebaseAuth auth;
        public myOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            fstore=FirebaseFirestore.getInstance();
            auth=FirebaseAuth.getInstance();
            id=itemView.findViewById(R.id.orderID);
            price=itemView.findViewById(R.id.textView18);
            qty=itemView.findViewById(R.id.qtyOrder);
            name=itemView.findViewById(R.id.nameOder);
            accept=itemView.findViewById(R.id.button4);
            reject=itemView.findViewById(R.id.button5);
        }
    }
}
