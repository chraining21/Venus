package demo.app.venus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


import demo.app.venus.Adapters.ItemsListAdapter;
import demo.app.venus.Database.RoomDB;
import demo.app.venus.Models.Items;

public class ListActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    RecyclerView recyclerView;
    ItemsListAdapter itemsListAdapter;
    List<Items> items = new ArrayList<>();
    RoomDB database;
    FloatingActionButton fab_add;
    SearchView searchView_list;
    Items selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recycler_list);
        searchView_list = findViewById(R.id.searchView_list);
        fab_add = findViewById(R.id.fab_add);

        database = RoomDB.getInstance(this);
        items = database.mainDAO().getAll();

        updateRecycler(items);


            //按下新增按鈕後跳轉至 輸入 頁面
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this,ItemsCreaterActivity.class);
                startActivityForResult(intent,101);
            }
        });

        searchView_list.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }
    //過濾產品名稱
    private void filter(String newText) {
        List<Items> filteredList = new ArrayList<>();
        for(Items singleNote : items){
            if(singleNote.getTitle().toLowerCase().contains(newText.toLowerCase())
                    || singleNote.getNotes().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(singleNote);
            }
        }
        itemsListAdapter.filterList(filteredList);
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
                itemsListAdapter.notifyDataSetChanged();

            }
        }

        else if (requestCode==102){
            if(resultCode==Activity.RESULT_OK){
                Items new_notes = (Items) data.getSerializableExtra("item");
                database.mainDAO().update(new_notes.getID(), new_notes.getTitle(), new_notes.getNotes());
                items.clear();
                items.addAll(database.mainDAO().getAll());
                itemsListAdapter.notifyDataSetChanged();
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
        public void onLongClick(Items items, CardView cardView) {
            selectedItem = new Items();
            selectedItem =  items;
            showPopup(cardView);
        }
    };

    private void showPopup(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:
                database.mainDAO().delete(selectedItem);
                items.remove(selectedItem);
                itemsListAdapter.notifyDataSetChanged();
                Toast.makeText(ListActivity.this,"已刪除該產品資訊",Toast.LENGTH_SHORT);
                return  true;
            default:
                return  false;
        }
    }
}