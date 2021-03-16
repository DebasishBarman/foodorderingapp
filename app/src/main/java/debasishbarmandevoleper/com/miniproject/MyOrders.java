package debasishbarmandevoleper.com.miniproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import Adapter.userOrderAdapter;
import Models.UserOrders;

public class MyOrders extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private RecyclerView recyclerView;
    private DocumentReference documentReference;
    userOrderAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        recyclerView=findViewById(R.id.myOrderRecView);

        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CollectionReference reference=firestore.collection("PlacedOrder");
        Query query=reference.whereEqualTo("user_id",auth.getCurrentUser().getUid());

        FirestoreRecyclerOptions<UserOrders> options=new FirestoreRecyclerOptions.Builder<UserOrders>()
                .setQuery(query,UserOrders.class)
                .build();


        adapter=new userOrderAdapter(options);
        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(MyOrders.this,User_Profile.class));
        finish();
    }
}