package debasishbarmandevoleper.com.miniproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import Adapter.horizontalAdapter;
import Models.randomModel;

public class allCategory extends AppCompatActivity {

    RecyclerView mainRview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_category);

        mainRview=findViewById(R.id.mainRec);
        horizontalAdapter adpter;
        ArrayList<randomModel> source;
        mainRview.setLayoutManager(new LinearLayoutManager(this));
        source=new ArrayList<>();
        source.add(new randomModel(R.drawable.iceream,"ice cream"));
        source.add(new randomModel(R.drawable.pizza,"Pizza"));
        source.add(new randomModel(R.drawable.ic_baseline_shopping_cart_24,"Pizza"));
        source.add(new randomModel(R.drawable.ic_baseline_shopping_cart_24,"Pizza"));
        source.add(new randomModel(R.drawable.ic_baseline_shopping_cart_24,"Pizza"));
        source.add(new randomModel(R.drawable.ic_baseline_shopping_cart_24,"Pizza"));
        source.add(new randomModel(R.drawable.ic_baseline_shopping_cart_24,"Pizza"));
        adpter=new horizontalAdapter(source,this);
        mainRview.setAdapter(adpter);
    }
}