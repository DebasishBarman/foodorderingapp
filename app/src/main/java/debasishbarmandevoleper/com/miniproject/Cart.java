package debasishbarmandevoleper.com.miniproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import Adapter.CartAdapter;
import Models.cartModel;

public class Cart extends AppCompatActivity{

    FirebaseAuth auth;
    FirebaseFirestore db;
    Button save,totals;
    DocumentReference root;
    DocumentReference p;
    RecyclerView foodsList;
    CartAdapter adapter;
    String list;
    TextView item,tprice;
    EditText address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        foodsList=findViewById(R.id.items);
        address=findViewById(R.id.addr);
        save=findViewById(R.id.button7);
        item=findViewById(R.id.titem);
        tprice=findViewById(R.id.tprice);
        totals=findViewById(R.id.button9);

        foodsList.setLayoutManager(new LinearLayoutManager(this));
        foodsList.hasFixedSize();

        auth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        root=db.collection("cart").document();



        CollectionReference cref=db.collection("cart");
        Query query=cref.whereEqualTo("user_id",auth.getCurrentUser().getUid());

        FirestoreRecyclerOptions<cartModel> options=new FirestoreRecyclerOptions.Builder<cartModel>()
                .setQuery(query,cartModel.class)
                .build();


        adapter=new CartAdapter(options);
        foodsList.setAdapter(adapter);

        totals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count=adapter.getItemCount();
                //String name=adapter.getSnapshots().getSnapshot().
                if (count >= 0) {


                    Toast.makeText(Cart.this, "" + count, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(Cart.this, "" + foodsList.hasFixedSize(), Toast.LENGTH_SHORT).show();
                    int total_item = 0;
                    int total_price = 0;
                    for (int i = 0; i <count; i++) {
                        //Toast.makeText(Cart.this, "" + i, Toast.LENGTH_SHORT).show();
                        TextView[] qty = new TextView[count];//earlier 50
                        qty[i] = foodsList.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.inc);

                       // total item
                        int[] items = new int[count]; //earlier 50
                        items[i] = Integer.parseInt(qty[i].getText().toString()); ///single item
                        total_item = total_item + items[i];

                        //total price;
                        String price = adapter.getSnapshots().get(i).getPrice();
                        int[] prices = new int[count];  //earlier 50
                        prices[i] = Integer.parseInt(price); //single price of an item
                        total_price = total_price + (prices[i] * items[i]);
                    }
                    Toast.makeText(Cart.this, " Total item" + total_item, Toast.LENGTH_SHORT).show();
                    Toast.makeText(Cart.this, "final prices" + total_price, Toast.LENGTH_SHORT).show();

                    item.setText(String.valueOf(total_item));
                    String y=String.valueOf(total_price);
                    tprice.setText("Rs "+y+" /-");
                     ///remove brace
                }
            }
        });

        //new save test

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(address.getText().toString())) {
                    address.setError("Please add valid address");
                    return;
                } else {


                    int count = adapter.getItemCount();
                    if (count >= 0) {
                        int total_item = 0;
                        int total_price = 0;
                        for (int i = 0; i < count; i++) {


                            TextView[] qty = new TextView[50];
                            qty[i] = foodsList.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.inc);


                            //total item
                            int[] items = new int[50];
                            items[i] = Integer.parseInt(qty[i].getText().toString()); ///one quantity of recycler
                            total_item = total_item + items[i];

                            //total price;
                            String price = adapter.getSnapshots().get(i).getPrice();
                            int[] prices = new int[100];
                            prices[i] = Integer.parseInt(price); //single price of an item
                            total_price = total_price + (prices[i] * items[i]);
                            // Toast.makeText(this, "Total prices"+prices[i]*(items[i]), Toast.LENGTH_SHORT).show();


                            root = db.collection("PlacedOrder").document();
                            Map<String, Object> add = new HashMap<>();
                            add.put("user_id", adapter.getSnapshots().get(i).getUser_id());
                            add.put("order_id", adapter.getSnapshots().get(i).getOrder_id());
                            add.put("title", adapter.getSnapshots().get(i).getTitle());
                            add.put("price", String.valueOf(prices[i]));
                            add.put("total_price", String.valueOf(prices[i] * (items[i])));
                            add.put("quantity", qty[i].getText().toString());
                            add.put("ids", root.getId());
                            add.put("imgurl",adapter.getSnapshots().get(i).getImgUrl());
                            add.put("address", address.getText().toString().trim());
                            add.put("status","0");// 0 for initialization work not done
                            add.put("admin_id", adapter.getSnapshots().get(i).getAdmin_id());


                            root.set(add, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Cart.this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();
                                    // startActivity(new Intent(Cart.this,User_Profile.class));
                                    // finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Cart.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
                if (adapter.getItemCount() == 0) {
                    Toast.makeText(Cart.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
        startActivity(new Intent(Cart.this,User_Profile.class));
        finish();
    }
}