package debasishbarmandevoleper.com.miniproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
////firebase ui import

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
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.stream.Stream;

import Adapter.horizontalAdapter;
import Adapter.myAdapter;
import Models.Pincode;
import Models.model;

public class User_Profile extends AppCompatActivity {

    //Checked as User
    RecyclerView recyclerView;
    FirebaseFirestore firestore,ex;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    ActionBarDrawerToggle toggle_Users;
    DrawerLayout drawerLayout_users;
    androidx.appcompat.widget.Toolbar toolbar_users;
    DocumentReference docref;
    NavigationView navigationView_users;
    myAdapter adapter;

    TextView textView;

    String ij;

    //
    horizontalAdapter adpter;
    ArrayList<String> source;


    //new code
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private DocumentReference refs=db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__profile);

       // pins=findViewById(R.id.inputs);
       // final String codes;
       // textView=findViewById(R.id.textView57);
       // cate=findViewById(R.id.cateRec);
        firestore=FirebaseFirestore.getInstance();
        recyclerView=findViewById(R.id.recView);
        docref=firestore.collection("Users").document(auth.getCurrentUser().getUid());
        drawerLayout_users=findViewById(R.id.user_drawer);
        toolbar_users=findViewById(R.id.toolbar_user);
        setSupportActionBar(toolbar_users);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView_users=findViewById(R.id.navigation_users);

        RecyclerView.LayoutManager categorylayoutManager;


        toggle_Users=new ActionBarDrawerToggle(this,drawerLayout_users,R.string.open,R.string.close);
        drawerLayout_users.addDrawerListener(toggle_Users);
        toggle_Users.syncState();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        navigationView_users.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.orders:
                        startActivity(new Intent(User_Profile.this,MyOrders.class));
                        finish();
                        break;
                    case R.id.edit_profile_users:
                        //moving to edit user data
                        startActivity(new Intent(User_Profile.this,edit_user.class));
                        Toast.makeText(User_Profile.this, "edit profile", Toast.LENGTH_LONG).show();
                        break;
                     //case
                    case R.id.track:
                        startActivity(new Intent(User_Profile.this,Track_Order.class));
                        Toast.makeText(User_Profile.this, "Track Orders", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.cart:
                        startActivity(new Intent(User_Profile.this,Cart.class));
                        finish();
                        break;
                    case R.id.logout:
                        try {
                            if (auth.getCurrentUser() != null) {
                                auth.signOut();
                                Toast.makeText(User_Profile.this, "Logged Out", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(User_Profile.this, home.class));
                                finish();
                                break;
                            }
                        } catch (Exception e) {
                            Toast.makeText(User_Profile.this, "Something went out wrong", Toast.LENGTH_SHORT).show();
                        }
                }
                return true;
            }
        });


       DocumentReference reference = firestore.collection("Users").document(auth.getCurrentUser().getUid());
        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                SharedPreferences prf=getApplicationContext().getSharedPreferences("name",MODE_PRIVATE);
                String cate=prf.getString("category","");
                Toast.makeText(User_Profile.this, ""+cate, Toast.LENGTH_SHORT).show();
                String s = documentSnapshot.getString("pincode");
                if (s == null) {
                    startActivity(new Intent(User_Profile.this, edit_user.class));
                   // Toast.makeText(User_Profile.this, "Enter your details first", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    CollectionReference cols = firestore.collection("AdminFoods");
                    Query query = cols.whereEqualTo("pincode", s).whereEqualTo("category",cate);//changes done
                    FirestoreRecyclerOptions<model> options = new FirestoreRecyclerOptions.Builder<model>()
                            .setQuery(query, model.class)
                            .build();
                    adapter = new myAdapter(options);
                    recyclerView.setAdapter(adapter);
                    adapter.startListening();
                }
            }
        });






       // recyclerView.setAdapter(adapter);




    }
    public void category_click(View v){
        TextView cats=v.findViewById(R.id.pizza);
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStart() {

        super.onStart();

        if(auth.getCurrentUser()==null){
            startActivity(new Intent(User_Profile.this,home.class));
            finish();
        }
        if(auth.getCurrentUser()!=null){


            /*refs.addSnapshotListener(User_Profile.this,new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(value.exists()&&value!=null){
                       pins.setText(value.getString("pincode"));
                             codes=pins.getText().toString();

                    }

                }
            });*/

           // adapter.startListening();


        }
    }

    @Override
    protected void onStop() {
        super.onStop();
//        adapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(User_Profile.this,allCategory.class));
        //logic
    }
}