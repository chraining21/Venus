package demo.app.venus;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import demo.app.venus.database.Database;
import demo.app.venus.database.Ingredient;
import demo.app.venus.database.Products;

public class ProductDtlEditActivity extends AppCompatActivity {
    EditText bname,pname,pexp;
    LinearLayout ingre,detail;
    Button submit;
    Database db;
    DtlListAdapter myListAdapter;
    RecyclerView view;
    List<Ingredient> ingres ;
    String data;
    Products p;
    String function="";
    Spinner spinner;
    int pkind=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_dtl_edit);

        new Thread(new Runnable() {
            @Override
            public void run() {
                bname = findViewById(R.id.edit_bname);
                pname = findViewById(R.id.edit_pname);
                pexp = findViewById(R.id.edit_pexp);
                spinner = findViewById(R.id.spinnerCat);
                submit = findViewById(R.id.submitbutton);
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Products p = new Products(bname.getText().toString(),pname.getText().toString(),pexp.getText().toString(),data,pkind);
                        db.mainDAO().insert(p);
                        if(ProductDtlActivity.productDtlActivity!=null){
                            ProductDtlActivity.productDtlActivity.finish();
                        }
                        finish();
                    }
                });
            }
        }).start();
        data = getIntent().getStringExtra("ingrejson");
        if(data!=null){
            Gson gson=  new Gson();
            Type collectionType = new TypeToken<List<Ingredient>>() {}.getType();
            ingres = gson.fromJson(data, collectionType);
        }
        p =  (Products) getIntent().getSerializableExtra("result");
        if(p!=null){
            bname.setText(p.getBrandName());
            pname.setText(p.getProductName());
            pexp.setText(p.getExpdate());
            Gson gson=  new Gson();
            Type collectionType = new TypeToken<List<Ingredient>>() {}.getType();
            ingres = gson.fromJson(p.getIngreJson(), collectionType);
        }
        db = Database.getInstance(this);
        view = findViewById(R.id.recycler_ingre);
        view.setLayoutManager(new LinearLayoutManager(ProductDtlEditActivity.this));
        myListAdapter = new DtlListAdapter();
        view.setAdapter(myListAdapter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.kind_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(spnOnItemSelected);

        new Thread(new Runnable() {
            @Override
            public void run() {
                data = getIntent().getStringExtra("ingrejson");
                Gson gson=  new Gson();
                Type collectionType = new TypeToken<List<Ingredient>>() {}.getType();
                ingres = gson.fromJson(data, collectionType);
            }
        }).start();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Products p = new Products(bname.getText().toString(),pname.getText().toString(),pexp.getText().toString(),data, 0);
                db.mainDAO().insert(p);
                if(ProductDtlActivity.productDtlActivity!=null){
                    ProductDtlActivity.productDtlActivity.finish();
                }
                finish();
            }
        });

    }
    private class DtlListAdapter extends RecyclerView.Adapter<DtlListAdapter.ViewHolder>{
        class ViewHolder extends RecyclerView.ViewHolder{
            private TextView name,func,ingrecom,ingreirr;
            private View mView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.ingrename);
                func = itemView.findViewById(R.id.ingrefunction);
                ingreirr = itemView.findViewById(R.id.num_com);
                ingrecom = itemView.findViewById(R.id.num_irr);
                ingre = itemView.findViewById(R.id.ingredient);
                detail = itemView.findViewById(R.id.details);
                mView  = itemView;
                ingre.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
            }
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ingredient_list,parent,false);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
            holder.ingreirr.setText((ingres.get(position).getIrr().equals(""))?"0":ingres.get(position).getIrr());
            holder.ingrecom.setText((ingres.get(position).getCom().equals(""))?"0":ingres.get(position).getCom());
            holder.mView.setOnClickListener((v)->{
                int c = (detail.getVisibility() == View.GONE)? View.VISIBLE : View.GONE;
                TransitionManager.beginDelayedTransition(ingre,new AutoTransition());
                detail.setVisibility(c);
            });
        }
        @Override
        public int getItemCount() {
            return ingres.size();
        }
    }

    private AdapterView.OnItemSelectedListener spnOnItemSelected
            = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            if(pos==0){
                pkind = 0;
            }
            if(pos==1){
                pkind = 1;
            }
            if(pos==2){
                pkind = 2;
            }
        }
        public void onNothingSelected(AdapterView<?> parent) {
            pkind = 0;
        }
    };
}