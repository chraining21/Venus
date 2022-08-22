package demo.app.venus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;


import demo.app.venus.Adapters.ItemsListAdapter;
import demo.app.venus.Database.RoomDB;
import demo.app.venus.Models.Items;

public class ListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ItemsListAdapter itemsListAdapter;
    List<Items> items = new ArrayList<>();
    RoomDB database;
//    FloatingActionButton fab_add;
    SearchView searchView_home;
    Items selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recycler_list);
        searchView_home = findViewById(R.id.searchView_list);

        database = RoomDB.getInstance(this);
        items = database.mainDAO().getAll();

        updateRecycler(items);


            //按下新增按鈕後跳轉至 輸入 頁面
//        ab_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ListActivity.this,ItemsCreaterActivity.class);
//                startActivityForResult(intent, 101);
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101){
            if(resultCode== Activity.RESULT_OK){
                Items new_notes = (Items) data.getSerializableExtra("item");
                database.mainDAO().insert(new_notes);
                items.clear();
                items.addAll(database.mainDAO().getAll());
                itemsListAdapter.notify();

            }
        }
        //requestCode==第51行 ===>> startActivityForResult(intent, 101);
        else if (requestCode==101){
            if(resultCode==Activity.RESULT_OK){
                Items new_notes = (Items) data.getSerializableExtra("note");
                database.mainDAO().update(new_notes.getID(), new_notes.getTitle(), new_notes.getNotes());
                items.clear();
                items.addAll(database.mainDAO().getAll());
                itemsListAdapter.notify();
            }
        }

    }


    private void updateRecycler(List<Items> items) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        itemsListAdapter = new ItemsListAdapter(ListActivity.this,items,itemsClickListener);
        recyclerView.setAdapter(itemsListAdapter);

    }

    private final ItemsClickListener itemsClickListener = new ItemsClickListener() {
        @Override
        public void onClick(Items items) {
            Intent intent = new Intent(ListActivity.this, ItemsCreaterActivity.class);
            intent.putExtra("old_note",items);
            startActivityForResult(intent, 102);
        }

        @Override
        public void onLongClick(Items notes, CardView cardView) {

        }

    };
}