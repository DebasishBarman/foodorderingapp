package debasishbarmandevoleper.com.miniproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import Adapter.adminDeliverAdapter;
import Models.delvModelAdmin;

public class DeliverItem extends AppCompatActivity{
    private RecyclerView list;
    private adminDeliverAdapter adapter;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_deliver_item);

        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        list=findViewById(R.id.delList);

        list.setLayoutManager(new LinearLayoutManager(this));



        CollectionReference collectionReference=firestore.collection("DeliveryList");
        Query query=collectionReference.whereEqualTo("admin_id",auth.getCurrentUser().getUid()).whereEqualTo("status","1"); //

        FirestoreRecyclerOptions<delvModelAdmin> options=new FirestoreRecyclerOptions.Builder<delvModelAdmin>()
                .setQuery(query,delvModelAdmin.class)
                .build();

        adapter =new adminDeliverAdapter(options);
        list.setAdapter(adapter);
       // adapter.startListening();
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
        startActivity(new Intent(DeliverItem.this,AdminProfile.class));
        finish();
    }
}