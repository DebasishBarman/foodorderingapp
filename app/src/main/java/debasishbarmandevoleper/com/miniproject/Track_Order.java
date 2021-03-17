package debasishbarmandevoleper.com.miniproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import Adapter.trackAdapter;
import Models.trackModel;

public class Track_Order extends AppCompatActivity {

    RecyclerView list;
    trackAdapter adapter;
    private FirebaseAuth auth=FirebaseAuth.getInstance();
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track__order);
        list=findViewById(R.id.orderRecview);
        list.setLayoutManager(new LinearLayoutManager(this));

        CollectionReference reference=db.collection("DeliveryList");
        Query query=reference.whereEqualTo("status",2).whereEqualTo("user_id",auth.getCurrentUser());
        FirestoreRecyclerOptions<trackModel> options=new FirestoreRecyclerOptions.Builder<trackModel>()
                .setQuery(query,trackModel.class)
                .build();

        adapter=new trackAdapter(options);
        list.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.stopListening();
    }
}