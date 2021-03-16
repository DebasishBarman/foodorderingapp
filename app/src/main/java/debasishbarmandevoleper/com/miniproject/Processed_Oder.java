package debasishbarmandevoleper.com.miniproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import Adapter.processAdapter;
import Models.finalOrderModel;

public class Processed_Oder extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private RecyclerView recyclerView;
    private DocumentReference documentReference;
    RecyclerView orderRecView;
    processAdapter adapter;///previous

    //current
   // myOrderAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.processed__oder);
        orderRecView=findViewById(R.id.processRecView);
        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

       // orderRecView.setLayoutManager(new LinearLayoutManager(Processed_Oder.this));
        orderRecView.setLayoutManager(new LinearLayoutManager(this));

        CollectionReference collectionReference=firestore.collection("PlacedOrder");
        Query query=collectionReference.whereEqualTo("admin_id",auth.getCurrentUser().getUid()).whereEqualTo("status","1");


        FirestoreRecyclerOptions<finalOrderModel> options=new FirestoreRecyclerOptions.Builder<finalOrderModel>()
                .setQuery(query,finalOrderModel.class)
                .build();

        adapter=new processAdapter(options);
        if(adapter.getItemCount()==0){
            Toast.makeText(this, "No Item", Toast.LENGTH_SHORT).show();
        }
        orderRecView.setAdapter(adapter);

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
        startActivity(new Intent(Processed_Oder.this,AdminProfile.class));
        finish();
    }
}