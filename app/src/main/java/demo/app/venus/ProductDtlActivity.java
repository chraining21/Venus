package demo.app.venus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import demo.app.venus.database.Database;
import demo.app.venus.database.Ingredient;
import demo.app.venus.database.Products;

public class ProductDtlActivity extends AppCompatActivity {
    TextView bName,pName,exp, kind;
    Database database;
    Products p;
    DtlListAdapter myListAdapter;
    Button trash,edit;
    RecyclerView view;
    ArrayList<Integer> temp = new ArrayList<>();
    ArrayList<Integer> temp2 = new ArrayList<>();
    List<Ingredient> ingres ;
    String function="";
    public static ProductDtlActivity productDtlActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_dtl);
        p = (Products) getIntent().getSerializableExtra("result");
        setup();
        Gson gson=  new Gson();
        Type collectionType = new TypeToken<List<Ingredient>>() {}.getType();
        ingres = gson.fromJson(p.getIngreJson(), collectionType);
        int count = 0;
        for(Ingredient i :ingres){
            if(i.getTier().equals("icky")||i.getTier().equals("Icky")){
                temp2.add(count);
            }
            else if(i.getIrr().contains("3")||i.getIrr().contains("4")||i.getIrr().contains("5")){
                temp.add(count);
            }
            else if(i.getCom().contains("3")||i.getCom().contains("4")||i.getCom().contains("5")){
                temp.add(count);
            }
            count+=1;
        }
        view = findViewById(R.id.recycler_ingre);
        view.setLayoutManager(new LinearLayoutManager(ProductDtlActivity.this));
        myListAdapter = new DtlListAdapter();
        view.setAdapter(myListAdapter);
        database = Database.getInstance(this);
        productDtlActivity=this;

    }
    private void setup(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                bName = findViewById(R.id.bname);
                pName = findViewById(R.id.pname);
                exp = findViewById(R.id.pexp);
                trash = findViewById(R.id.trashbutton);
                edit = findViewById(R.id.editbutton);
                kind = findViewById(R.id.kind);
                bName.setText(p.getBrandName());
                pName.setText(p.getProductName());
                exp.setText(p.getExpdate());
                switch (p.getKind()){
                    case 0:
                        kind.setText("我的保養品");
                        break;
                    case 1:
                        kind.setText("蜜糖");
                        break;
                    case 2:
                        kind.setText("毒藥");
                        break;
                    case 3:
                        kind.setText("願望清單");
                        break;
                }
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ProductDtlActivity.this,ProductDtlEditActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("result",p);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
                trash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        database.mainDAO().delete(p);
                        finish();
                    }
                });
            }
        }).start();
    }
    private class DtlListAdapter extends RecyclerView.Adapter<DtlListAdapter.ViewHolder>{
        class ViewHolder extends RecyclerView.ViewHolder{
            private TextView name,func,ingrecom,ingreirr,text_com,text_irr;
            private CardView bg;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.ingrename);
                func = itemView.findViewById(R.id.ingrefunction);
                ingreirr = itemView.findViewById(R.id.num_com);
                ingrecom = itemView.findViewById(R.id.num_irr);
                text_com = itemView.findViewById(R.id.text_com);
                text_irr = itemView.findViewById(R.id.text_irr);
                bg = itemView.findViewById(R.id.ingreContainer);
            }
        }
        @NonNull
        @Override
        public DtlListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ingredient_list,parent,false);
            return new DtlListAdapter.ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull DtlListAdapter.ViewHolder holder, int position) {
            holder.name.setText(ingres.get(position).getIngreName());
            for(int i = 0; i<ingres.get(position).getFunc().size();i++){
                if(i<ingres.get(position).getFunc().size()-1){
                    function+=ingres.get(position).getFunc().get(i)+", ";
                }
                else{
                    function+=ingres.get(position).getFunc().get(i);
                }
            }
            holder.func.setText(function);
            function="";
            holder.ingreirr.setText(ingres.get(position).getIrr());
            holder.ingrecom.setText(ingres.get(position).getCom());
            for(int i:temp){
                if(position==i){
                    holder.text_irr.setTextColor(getResources().getColor(R.color.white));
                    holder.text_com.setTextColor(getResources().getColor(R.color.white));
                    holder.bg.setCardBackgroundColor(getResources().getColor(R.color.er));
                    holder.name.setTextColor(getResources().getColor(R.color.white));
                    holder.func.setTextColor(getResources().getColor(R.color.white));
                    holder.ingreirr.setTextColor(getResources().getColor(R.color.white));
                    holder.ingrecom.setTextColor(getResources().getColor(R.color.white));
                }
                else{
                    holder.text_irr.setTextColor(getResources().getColor(R.color.black));
                    holder.text_com.setTextColor(getResources().getColor(R.color.black));
                    holder.bg.setCardBackgroundColor(getResources().getColor(R.color.yellow));
                    holder.name.setTextColor(getResources().getColor(R.color.black));
                    holder.func.setTextColor(getResources().getColor(R.color.black));
                    holder.ingreirr.setTextColor(getResources().getColor(R.color.black));
                    holder.ingrecom.setTextColor(getResources().getColor(R.color.black));
                }
            }
            for(int i :temp2){
                if(position==i){
                    holder.text_irr.setTextColor(getResources().getColor(R.color.white));
                    holder.text_com.setTextColor(getResources().getColor(R.color.white));
                    holder.bg.setCardBackgroundColor(getResources().getColor(R.color.er2));
                    holder.name.setTextColor(getResources().getColor(R.color.white));
                    holder.func.setTextColor(getResources().getColor(R.color.white));
                    holder.ingreirr.setTextColor(getResources().getColor(R.color.white));
                    holder.ingrecom.setTextColor(getResources().getColor(R.color.white));
                }
                else{
                    holder.text_irr.setTextColor(getResources().getColor(R.color.black));
                    holder.text_com.setTextColor(getResources().getColor(R.color.black));
                    holder.bg.setCardBackgroundColor(getResources().getColor(R.color.yellow));
                    holder.name.setTextColor(getResources().getColor(R.color.black));
                    holder.func.setTextColor(getResources().getColor(R.color.black));
                    holder.ingreirr.setTextColor(getResources().getColor(R.color.black));
                    holder.ingrecom.setTextColor(getResources().getColor(R.color.black));
                }
            }

        }
        @Override
        public int getItemCount() {
            return ingres.size();
        }
    }
}