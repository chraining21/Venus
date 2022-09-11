package demo.app.venus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;

import demo.app.venus.database.Database;
import demo.app.venus.database.Products;

public class TestDbActivity extends AppCompatActivity {
    RecyclerView view;
    MyListAdapter myListAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<Products> arrayList = new ArrayList<>();
    Database database;
    Spinner spinner;
    public static final String [] mData ={"我的保養品","蜜糖","毒藥"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = Database.getInstance(this);
        setContentView(R.layout.activity_test_db);
        makeData(0);
        view = findViewById(R.id.recycler);
        view.setLayoutManager(new GridLayoutManager(TestDbActivity.this, 2));
        myListAdapter = new MyListAdapter();
        view.setAdapter(myListAdapter);
        new Thread(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout = findViewById(R.id.refreshLayout);
                swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.yellow));
                swipeRefreshLayout.setOnRefreshListener(() -> {
                    arrayList.clear();
                    spinner.setSelection(0);
                    makeData(0);
                    myListAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                });
            }
        }).start();
        setup();
    }
    private void setup(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                spinner= findViewById(R.id.spinnerCat);
                Adapter mAdapter = new Adapter(TestDbActivity.this, Arrays.asList(mData));
                spinner.setAdapter(mAdapter);
                spinner.setSelection(0, false);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int pos, long id) {
                        if(pos == 0){
                            arrayList.clear();
                            makeData(pos);
                            myListAdapter.notifyDataSetChanged();
                        }
                        if(pos == 1){
                            arrayList.clear();
                            makeData(pos);
                            myListAdapter.notifyDataSetChanged();
                        }
                        if(pos == 2){
                            arrayList.clear();
                            makeData(pos);
                            myListAdapter.notifyDataSetChanged();
                        }

                    }
                    public void onNothingSelected(AdapterView<?> parent) {}
                });

            }
        }).start();
    }
    private void makeData(int kind) {
        if(kind == 0){
            arrayList.addAll(database.mainDAO().getNormal());
        }
        if(kind ==1){
            arrayList.addAll(database.mainDAO().getHoney());
        }
        if(kind ==2){
            arrayList.addAll(database.mainDAO().getToxic());
        }

    }
    private class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {
        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView brandName, pName, pExp;
            private View mView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                brandName = itemView.findViewById(R.id.brandName);
                pName = itemView.findViewById(R.id.pname);
                pExp = itemView.findViewById(R.id.pExp);
                mView = itemView;
            }
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.product_list, parent, false);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.brandName.setText(arrayList.get(position).getBrandName());
            holder.pName.setText(arrayList.get(position).getProductName());
            holder.pExp.setText(arrayList.get(position).getExpdate());
            holder.mView.setOnClickListener((v)->{
                Intent intent = new Intent(TestDbActivity.this,ProductDtlActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("result", arrayList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            });

        }
        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }
}




