package demo.app.venus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import demo.app.venus.database.Database;
import demo.app.venus.database.Products;
/*DBclassproductList
* 可將setting的按鈕連至這個activity
* 應該不用動 拿掉floatingButton及更改xml(請記得id)即可
*
* 希望可以拿掉appbar 變成toolbar(只有在dtl相關頁面可以看見bar)
* product_list為card物件的xml要改一格一格請去那邊改
* activity_product_dtl為顯示內容的頁面(可以在appbar上新增edit按鈕)
* activity_product_dtl_edit為編輯內容的頁面(可以在appbar新增save按鈕並退出頁面)
* 程式邏輯請先不要更改(除了增加按鈕的方法)
* 之後你們做完我再繞回來做邏輯
* * */
public class TestDbActivity extends AppCompatActivity {
    FloatingActionButton fab_add;
    RecyclerView view;
    MyListAdapter myListAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<Products> arrayList = new ArrayList<>();
    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = Database.getInstance(this);
        setContentView(R.layout.activity_test_db);
        new Thread(new Runnable() {
            @Override
            public void run() {
                makeData();
                fab_add = findViewById(R.id.floatingButton);
                fab_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(TestDbActivity.this, ProductDtlEditActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }).start();
        view = findViewById(R.id.recycler);
        view.setLayoutManager(new GridLayoutManager(TestDbActivity.this, 2));
        view.addItemDecoration(new DividerItemDecoration(TestDbActivity.this, DividerItemDecoration.VERTICAL));
        myListAdapter = new MyListAdapter();
        view.setAdapter(myListAdapter);
        new Thread(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout = findViewById(R.id.refreshLayout);
                swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.yellow));
                swipeRefreshLayout.setOnRefreshListener(()-> {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            arrayList.clear();
                            makeData();
                            myListAdapter.notifyDataSetChanged();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }).start();
                });
            }
        }).start();
        //下拉刷新
    }
    private void makeData() {
        arrayList.addAll(database.mainDAO().getAll());
    }
    private class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
        class ViewHolder extends RecyclerView.ViewHolder{
            private TextView brandName,pName,pExp;
            private View mView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                brandName = itemView.findViewById(R.id.brandName);
                pName = itemView.findViewById(R.id.pname);
                pExp = itemView.findViewById(R.id.pExp);
                mView  = itemView;
            }
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.product_list,parent,false);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.brandName.setText(arrayList.get(position).getBrandName());
            holder.pName.setText(arrayList.get(position).getProductName());
            holder.pExp.setText(arrayList.get(position).getExpdate());

            holder.mView.setOnClickListener((v)->{
                Intent intent = new Intent(TestDbActivity.this,TestDbActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("product",arrayList.get(position));
                intent.putExtra("bundle_product",bundle);
                startActivity(intent);
            });
        }
        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }


}



