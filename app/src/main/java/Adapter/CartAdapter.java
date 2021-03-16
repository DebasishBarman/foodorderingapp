package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

import Models.cartModel;
import debasishbarmandevoleper.com.miniproject.R;

public class CartAdapter extends FirestoreRecyclerAdapter<cartModel,CartAdapter.myHolder> {

    private static int count;
    public CartAdapter(@NonNull FirestoreRecyclerOptions<cartModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myHolder holder, int position, @NonNull cartModel model) {
        Glide.with(holder.img.getContext()).load(model.getImgUrl()).into(holder.img);
        holder.title.setText(model.getTitle().toString());
        holder.price.setText(model.getPrice().toString());
       // holder.desc.setText(model.get().toString());
        holder.title.setText(model.getTitle().toString());
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.db.collection("cart").document(model.getOrder_id()).delete();
            }
        });

        holder.inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    int c;
                     c= ++holder.counts;
                holder.qty.setText(String.valueOf(c));
               // int items=c*Integer.parseInt(model.getPrice());
                //new code

            }
        });
        holder.dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.counts!=1){
                    int c= --holder.counts;
                    holder.qty.setText(String.valueOf(c));
                }

            }
        });

    }

    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_card,parent,false);
        return new myHolder(v);
    }

    class myHolder extends RecyclerView.ViewHolder {
        TextView price,title,desc,qty;
        Button inc,dec,remove;
        ImageView img;
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        private int counts=1;
        public myHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
            price=itemView.findViewById(R.id.price);
            qty=itemView.findViewById(R.id.inc);
            title=itemView.findViewById(R.id.textView25);
           // desc=itemView.findViewById(R.id.textView27);
            inc=itemView.findViewById(R.id.button6);
            remove=itemView.findViewById(R.id.button8);
            dec=itemView.findViewById(R.id.dec);
        }
    }
}
