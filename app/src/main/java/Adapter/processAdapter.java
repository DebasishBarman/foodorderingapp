package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import Models.OrderModel;
import Models.finalOrderModel;
import Models.processOrderModel;
import debasishbarmandevoleper.com.miniproject.R;

public class processAdapter extends FirestoreRecyclerAdapter<finalOrderModel,processAdapter.myViewHolder> {

    public processAdapter(@NonNull FirestoreRecyclerOptions<finalOrderModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull finalOrderModel model) {
        holder.title.setText(model.getTitle());
        holder.price.setText(model.getTotal_price());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                   // Toast.makeText(holder.info.getContext(), "Processing", Toast.LENGTH_SHORT).show();
                    holder.info.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(compoundButton.isChecked()==true){

                                ///setting status to 3 ==processing;
                                DocumentSnapshot snapshot=getSnapshots().getSnapshot(holder.getAdapterPosition());
                                String id=snapshot.getId();
                                DocumentReference reference=holder.db.collection("PlacedOrder").document(id);
                                Map<String,Object> plcorder =new HashMap<>();
                                plcorder.put("status","3"); //for processing*/
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

                                ////
                                DocumentReference ref=holder.db.collection("DeliveryList").document();
                                Map<String,Object> map=new HashMap<>();
                                map.put("title",model.getTitle());
                                map.put("order_id",model.getOrder_id());
                                map.put("user_id",model.getUser_id());
                                map.put("admin_id",model.getAdmin_id());
                                map.put("address",model.getAddress());
                                map.put("quantity",model.getQuantity());
                                map.put("price",model.getTotal_price());
                                map.put("status","1");//1 for added to processed, 2 for your way,3 for delivered;
                                ref.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.info.getContext(), "Error try again!!", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                            else{
                                Toast.makeText(holder.info.getContext(), "Info failed", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                    return;
                }
            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.process_order,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView id,title,price,address;
        CheckBox checkBox;
        Button info;
        private FirebaseFirestore db;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.txtID);
            title=itemView.findViewById(R.id.textDishName);
            price=itemView.findViewById(R.id.textPrice);
            address=itemView.findViewById(R.id.textAddr);
            checkBox=itemView.findViewById(R.id.checkBox);
            info=itemView.findViewById(R.id.button11);
            db=FirebaseFirestore.getInstance();
        }
    }
}
