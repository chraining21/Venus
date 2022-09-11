package demo.app.venus;

import static demo.app.venus.TestDbActivity.mData;
import android.animation.LayoutTransition;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import demo.app.venus.database.Database;
import demo.app.venus.database.Ingredient;
import demo.app.venus.database.Products;

public class ProductDtlEditActivity extends AppCompatActivity {
    EditText bname,pname;
    LinearLayout ingre,detail;
    Button submit,date;
    Database db;
    DtlListAdapter myListAdapter;
    RecyclerView view;
    List<Ingredient> ingres ;
    String function="";
    Spinner spinner;
    int pkind=0;
    Boolean edit = false;
    Products sendp;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_dtl_edit);
        setup();
        db = Database.getInstance(this);
        if(getIntent().getSerializableExtra("result")!=null) {
            Products p =  (Products) getIntent().getSerializableExtra("result");
            System.out.print(p);
            bname.setText(p.getBrandName());
            pname.setText(p.getProductName());
            date.setText(p.getExpdate());
            Gson gson = new Gson();
            Type collectionType = new TypeToken<List<Ingredient>>() {
            }.getType();
            ingres = gson.fromJson(p .getIngreJson(), collectionType);
            edit = true;
            sendp = p;
        }
        if(getIntent().getStringExtra("ingrejson")!=null) {
            String data = getIntent().getStringExtra("ingrejson");
            Gson gson = new Gson();
            Type collectionType = new TypeToken<List<Ingredient>>() {
            }.getType();
            ingres = gson.fromJson(data, collectionType);
        }
        view = findViewById(R.id.recycler_ingre);
        view.setLayoutManager(new LinearLayoutManager(ProductDtlEditActivity.this));
        myListAdapter = new DtlListAdapter();
        view.setAdapter(myListAdapter);
    }
    private void setup(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                bname = findViewById(R.id.edit_bname);
                pname = findViewById(R.id.edit_pname);
                submit = findViewById(R.id.submitbutton);
                date = findViewById(R.id.datepicker);
                spinner = findViewById(R.id.spinnerCat);
                Adapter mAdapter = new Adapter(ProductDtlEditActivity.this, Arrays.asList(mData));
                spinner.setAdapter(mAdapter);
                spinner.setSelection(0, false);
                spinner.setOnItemSelectedListener(spnOnItemSelected);
                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                month+=1;
                                String dateTime = year+"-"+month+"-"+day;
                                date.setText(dateTime);
                            }

                        }, year, month, day).show();
                    }
                });
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String data = new Gson().toJson(ingres);
                        if(edit == true){
                            Products p = new Products(bname.getText().toString(),pname.getText().toString(),date.getText().toString(),data,pkind);
                            db.mainDAO().insert(p);
                            db.mainDAO().delete(sendp);
                            if(ProductDtlActivity.productDtlActivity!=null){
                                ProductDtlActivity.productDtlActivity.finish();
                            }
                            finish();
                        }
                        else{
                            Products p = new Products(bname.getText().toString(),pname.getText().toString(),date.getText().toString(),data,pkind);
                            db.mainDAO().insert(p);
                            if(UploadActivity.uploadActivity!=null){
                                UploadActivity.uploadActivity.finish();
                            }
                            finish();
                        }
                    }
                });
            }
        }).start();
    }
    private class DtlListAdapter extends RecyclerView.Adapter<DtlListAdapter.ViewHolder>{
        class ViewHolder extends RecyclerView.ViewHolder{
            private TextView name,func,ingrecom,ingreirr;
            private View mView;
            private CardView bg;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.ingrename);
                func = itemView.findViewById(R.id.ingrefunction);
                ingreirr = itemView.findViewById(R.id.num_com);
                ingrecom = itemView.findViewById(R.id.num_irr);
                ingre = itemView.findViewById(R.id.ingredient);
                detail = itemView.findViewById(R.id.details);
                bg = itemView.findViewById(R.id.ingreContainer);
                mView  = itemView;
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
            holder.ingreirr.setText((ingres.get(position).getIrr()==null||ingres.get(position).getIrr().equals(""))?"0":ingres.get(position).getIrr());
            holder.ingrecom.setText((ingres.get(position).getIrr()==null||ingres.get(position).getCom().equals(""))?"0":ingres.get(position).getCom());
            String temp1 = holder.ingreirr.getText().toString();
            String temp2 = holder.ingrecom.getText().toString();
            if(temp1.contains("3")||temp1.contains("4")||temp1.contains("5")){holder.bg.setCardBackgroundColor(getResources().getColor(R.color.er));}
            else{
                if(temp2.contains("3")||temp2.contains("4")||temp2.contains("5")){holder.bg.setCardBackgroundColor(getResources().getColor(R.color.er));}
            }
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
        public void onNothingSelected(AdapterView<?> parent) {}
    };

}