package debasishbarmandevoleper.com.miniproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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

import Adapter.menuAdapter;
import Models.finalOrderModel;
import Models.menuModel;

public class My_Menus extends AppCompatActivity {
    private menuAdapter adapter;
    private RecyclerView recyclerView;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    DocumentReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__menus_admin);
        recyclerView=findViewById(R.id.mymenurecview);
        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
       // DocumentReference documentReference=db.collection("AdminFoods").document();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CollectionReference collectionReference=firestore.collection("AdminFoods");
        Query query=collectionReference.whereEqualTo("admin_id",auth.getCurrentUser().getUid().trim());
        FirestoreRecyclerOptions<menuModel> options=new FirestoreRecyclerOptions.Builder<menuModel>()
                .setQuery(query,menuModel.class)
                .build();

        adapter=new menuAdapter(options);
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

}