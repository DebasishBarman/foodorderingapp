package Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import Models.trackModel;
import debasishbarmandevoleper.com.miniproject.R;

public class trackAdapter extends FirestoreRecyclerAdapter<trackModel,trackAdapter.viewHolder> {



    public trackAdapter(@NonNull FirestoreRecyclerOptions<trackModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull trackModel model) {

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    class viewHolder extends RecyclerView.ViewHolder{

        TextView price,address,quantity,title;
        Button status;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.textView63);
            quantity=itemView.findViewById(R.id.textView65);
            price=itemView.findViewById(R.id.textView64);
            status=itemView.findViewById(R.id.button18);

        }
    }
}
