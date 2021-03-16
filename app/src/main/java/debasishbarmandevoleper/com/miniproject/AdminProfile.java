package debasishbarmandevoleper.com.miniproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import Adapter.myOrderAdapter;
import Models.OrderModel;
import Models.finalOrderModel;

public class AdminProfile extends AppCompatActivity {

    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore;
    ActionBarDrawerToggle toggle_Admin;
    DocumentReference dref;
    DrawerLayout drawerLayout_admin;
    androidx.appcompat.widget.Toolbar toolbar_admin;
    RecyclerView recOrder;
    NavigationView navigationView_admin;
    myOrderAdapter orderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        recOrder=findViewById(R.id.recViewAdmin);
        drawerLayout_admin=findViewById(R.id.admin_drawer);
        toolbar_admin=findViewById(R.id.toolbar_admin);
        setSupportActionBar(toolbar_admin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView_admin=findViewById(R.id.navigation_admin);
        // toolbar_users=new ActionBarDrawerToggle(this,drawerLayout_users,R.string.open,R.string.close);
        toggle_Admin=new ActionBarDrawerToggle(this,drawerLayout_admin,R.string.open,R.string.close);
        drawerLayout_admin.addDrawerListener(toggle_Admin);
        toggle_Admin.syncState();

        recOrder.setLayoutManager(new LinearLayoutManager(this));

        //displayOrders();

        navigationView_admin.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.edit_profile_admin:
                        startActivity(new Intent(AdminProfile.this,editAdmin.class));
                        finish();
                        break;

                    case R.id.post:

                        startActivity(new Intent(AdminProfile.this,Post_Food_Admin.class));
                        finish();
                        break;

                    case R.id.mypost:
                        startActivity(new Intent(AdminProfile.this,My_Menus.class));
                        finish();
                        break;

                    case R.id.processed_order:

                         startActivity(new Intent(AdminProfile.this,Processed_Oder.class));
                         finish();
                         break;

                    case R.id.delivery:
                        startActivity(new Intent(AdminProfile.this,DeliverItem.class));
                        finish();
                        break;

                    case R.id.logout_admin:
                        try {
                            if (auth.getCurrentUser() != null) {
                                auth.signOut();
                               // Toast.makeText(User_Profile.this, "Logged Out", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(AdminProfile.this, home.class));
                                finish();
                                break;
                            }
                        } catch (Exception e) {

                            Toast.makeText(AdminProfile.this, "Something went out wrong", Toast.LENGTH_SHORT).show();

                        }
                }

                return true;

            }
        });

        firebaseFirestore=FirebaseFirestore.getInstance();

        CollectionReference colref=firebaseFirestore.collection("PlacedOrder");
        //Query query=firebaseFirestore.collection("Order");
        Query query=colref.whereEqualTo("admin_id",auth.getCurrentUser().getUid()).whereEqualTo("status","0");
        FirestoreRecyclerOptions<finalOrderModel> options=new FirestoreRecyclerOptions.Builder<finalOrderModel>()
                .setQuery(query,finalOrderModel.class)
                .build();


        ////trying new codes


        orderAdapter=new myOrderAdapter(options);
        if(orderAdapter.getItemCount()==0){
            Toast.makeText(this, "Currently no order request", Toast.LENGTH_SHORT).show();
        }
        recOrder.setAdapter(orderAdapter);

    }


    @Override
    protected void onStart() {
        super.onStart();
        orderAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        orderAdapter.stopListening();
    }

}
