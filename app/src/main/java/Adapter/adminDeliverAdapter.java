package Adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import Models.delvModelAdmin;
import debasishbarmandevoleper.com.miniproject.R;

public class adminDeliverAdapter extends FirestoreRecyclerAdapter<delvModelAdmin,adminDeliverAdapter.viewHolder> {

    public adminDeliverAdapter(@NonNull FirestoreRecyclerOptions<delvModelAdmin> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull delvModelAdmin model) {
        holder.price.setText(model.getPrice());
        holder.dish.setText(model.getTitle());
        holder.phone.setEnabled(false);
        holder.address.setText(model.getAddress());
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        Task<DocumentSnapshot> data=firestore.collection("Users").document(model.getUser_id()).get()
                .addOnSuccessListener(documentSnapshot -> {
                    String phone=documentSnapshot.getString("userPhone");

                    holder.phone.setText(phone);
                });
        holder.deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firestore.collection("DeliveryList").document(getSnapshots().getSnapshot(position).getId())
                        .update("status","2")
                        .addOnSuccessListener(aVoid ->
                                Toast.makeText(holder.dish.getContext(), "Delivery Successfull",
                                        Toast.LENGTH_SHORT).show());
            }
        });
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone="tel:"+holder.phone.getText().toString();
                Toast.makeText(holder.dish.getContext(), ""+phone, Toast.LENGTH_SHORT).show();
                Intent i=new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse(phone.toString()));
                ((Activity)holder.dish.getContext()).startActivity(i);
            }
        });


    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.del_list_item_admin,parent,false);
        return new viewHolder(view);
    }

    class viewHolder extends RecyclerView.ViewHolder{
        TextView dish,price,address,phone;
        Button deliver,call;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            address=itemView.findViewById(R.id.editTextTextMultiLine5);
            price=itemView.findViewById(R.id.textView48);
            dish=itemView.findViewById(R.id.textView50);
            phone=itemView.findViewById(R.id.textView52);
            deliver=itemView.findViewById(R.id.button15);
            call=itemView.findViewById(R.id.button16);
        }

    }
}
