package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import Models.UserOrders;
import de.hdodenhof.circleimageview.CircleImageView;
import debasishbarmandevoleper.com.miniproject.R;

public class userOrderAdapter extends FirestoreRecyclerAdapter<UserOrders,userOrderAdapter.myViewHolder> {

    public userOrderAdapter(@NonNull FirestoreRecyclerOptions<UserOrders> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull UserOrders model) {
        holder.name.setText(model.getTitle());
        holder.qty.setText(model.getQuantity());
        holder.price.setText(model.getTotal_price());
        holder.address.setText(model.getAddress());
        Glide.with(holder.name.getContext()).load(model.getImgurl()).into(holder.img);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_row,parent,false);
        return new myViewHolder(v);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        EditText name,price,qty,address;
        CircleImageView img;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.editTextTextPersonName4);
            price=itemView.findViewById(R.id.editTextNumber4);
            qty=itemView.findViewById(R.id.editTextNumber3);
            address=itemView.findViewById(R.id.editTextTextMultiLine4);
            img=itemView.findViewById(R.id.circleImageView);
        }

    }
}
