package demo.app.venus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import demo.app.venus.adapter.ProductListAdapter;
import demo.app.venus.database.RoomDB;
import demo.app.venus.models.Products;

public class TestDbActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProductListAdapter pListAdapter;
    List<Products> products = new ArrayList<>();
    RoomDB database;
    FloatingActionButton fab_add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_db);
        recyclerView = findViewById(R.id.recycler_home);
        fab_add = findViewById(R.id.floatingButton);
        database = RoomDB.getInstance(this);
        products = database.mainDao().getAll();

        updateRecycler(products);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestDbActivity.this, ProductDtlActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateRecycler(List<Products> products) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        pListAdapter = new ProductListAdapter(TestDbActivity.this,products,pClickListener);
        recyclerView.setAdapter(pListAdapter);
    }

    private final ProductsClickListener pClickListener = new ProductsClickListener() {
        @Override
        public void onClick(Products p) {
            Intent intent = new Intent(TestDbActivity.this, ProductDtlActivity.class);
            intent.putExtra("product",p);
            startActivity(intent);
        }

    };

}