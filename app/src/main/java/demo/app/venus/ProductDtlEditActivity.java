package demo.app.venus;

import static demo.app.venus.TestDbActivity.mData;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import demo.app.venus.database.Database;
import demo.app.venus.database.Ingredient;
import demo.app.venus.database.Products;

public class ProductDtlEditActivity extends AppCompatActivity {
    EditText bname,pname;
    Button submit,date;
    Database db;
    DtlListAdapter myListAdapter;
    PopListAdapter popAdapter;
    RecyclerView view,popitem;
    List<Ingredient> ingres;
    ArrayList<Ingredient> compares = new ArrayList<>();
    ArrayList<Integer> temp = new ArrayList<>();
    ArrayList<Integer> temp2 = new ArrayList<>();
    String function="";
    Spinner spinner;
    int pkind=0,danger = 0;
    Boolean edit = false;
    Products sendp;
    FloatingActionButton FAB;
    Dialog dialog;
    private PopupWindow popupWindow = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_dtl_edit);
        setup();
        bname = findViewById(R.id.edit_bname);
        pname = findViewById(R.id.edit_pname);
        date = findViewById(R.id.datepicker);
        db = Database.getInstance(this);
        if(getIntent().getSerializableExtra("result")!=null) {
            Products p =  (Products) getIntent().getSerializableExtra("result");
            bname.setText(p.getBrandName());
            pname.setText(p.getProductName());
            date.setText(p.getExpdate());
            Gson gson = new Gson();
            Type collectionType = new TypeToken<List<Ingredient>>() {
            }.getType();
            ingres = gson.fromJson(p.getIngreJson(), collectionType);
            sendp = p;
            edit = true;
        }
        if(getIntent().getStringExtra("ingrejson")!=null) {
            String data = getIntent().getStringExtra("ingrejson");
            Gson gson = new Gson();
            Type collectionType = new TypeToken<List<Ingredient>>() {
            }.getType();
            ingres = gson.fromJson(data, collectionType);
        }
        int count = 0;
        for(Ingredient i :ingres){
            if(i.getTier().equals("icky")||i.getTier().equals("Icky")){
                temp2.add(count);
                danger+=1;
            }
            else if(i.getIrr().contains("3")||i.getIrr().contains("4")||i.getIrr().contains("5")){
                temp.add(count);
                danger+=1;
            }
            else if(i.getCom().contains("3")||i.getCom().contains("4")||i.getCom().contains("5")){
                temp.add(count);
                danger+=1;
            }
            count+=1;
        }
        view = findViewById(R.id.recycler_ingre);
        view.setLayoutManager(new LinearLayoutManager(ProductDtlEditActivity.this));
        myListAdapter = new DtlListAdapter();
        view.setAdapter(myListAdapter);

        if(danger>0){
            Toast.makeText(this,"此產品含致痘度/刺激度高的成分",Toast.LENGTH_LONG).show();
        }
    }
    private void initPopupWindow() {
        View view = LayoutInflater.from(ProductDtlEditActivity.this).inflate(R.layout.popuplayout, null);
        popitem = view.findViewById(R.id.popitem);
        TextView popsubtitle = view.findViewById(R.id.popsubtitle);
        popsubtitle.setText("有"+compares.size()+"種成分與毒藥相同");
        popitem.setLayoutManager(new LinearLayoutManager(ProductDtlEditActivity.this));
        popAdapter = new PopListAdapter();
        popitem.setAdapter(popAdapter);
        popupWindow = new PopupWindow(view);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOnDismissListener(()->darkerBack(1f));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
    }
    private void darkerBack(Float bgColor) {
        WindowManager.LayoutParams lp = ProductDtlEditActivity.this.getWindow().getAttributes();
        lp.alpha = bgColor;
        ProductDtlEditActivity.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ProductDtlEditActivity.this.getWindow().setAttributes(lp);
    }
    private void setup(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                FAB = findViewById(R.id.FAB);
                submit = findViewById(R.id.submitbutton);
                spinner = findViewById(R.id.spinnerCat);
                Adapter mAdapter = new Adapter(ProductDtlEditActivity.this, Arrays.asList(mData));
                spinner.setAdapter(mAdapter);
                spinner.setSelection(0, false);
                spinner.setOnItemSelectedListener(spnOnItemSelected);
                FAB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) { dialog = new Dialog(ProductDtlEditActivity.this);
                        dialog.startDialog();
                        List<Products> temp = db.mainDAO().getToxic();
                        List<Ingredient> temp1;
                        Gson gson = new Gson();
                        Type collectionType = new TypeToken<List<Ingredient>>() {
                        }.getType();
                        for(Products p :temp) {
                            temp1 = gson.fromJson(p.getIngreJson(), collectionType);
                            for (Ingredient i : temp1) {
                                for (Ingredient j : ingres) {
                                    if (i.getIngreName().equals(j.getIngreName())&& !compares.contains(j)) {
                                        compares.add(j);
                                    }
                                }
                            }
                        }
                        initPopupWindow();

                        dialog.dismiss();
                        darkerBack(0.5f);
                        popupWindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL, 0, 0);
                    }
                });
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
                            if(UploadActivity.uploadActivity!=null) {
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
            private TextView name,func,ingrecom,ingreirr,text_com,text_irr;
            private CardView bg;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.ingrename);
                func = itemView.findViewById(R.id.ingrefunction);
                ingreirr = itemView.findViewById(R.id.num_com);
                ingrecom = itemView.findViewById(R.id.num_irr);
                bg = itemView.findViewById(R.id.ingreContainer);
                text_com = itemView.findViewById(R.id.text_com);
                text_irr = itemView.findViewById(R.id.text_irr);
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

        }
        @Override
        public int getItemCount() {
            return ingres.size();
        }
    }
    private class PopListAdapter extends RecyclerView.Adapter<PopListAdapter.ViewHolder>{
        class ViewHolder extends RecyclerView.ViewHolder{
            private TextView name,func,ingrecom,ingreirr,text_com,text_irr;
            private CardView bg;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.ingrename);
                func = itemView.findViewById(R.id.ingrefunction);
                ingreirr = itemView.findViewById(R.id.num_com);
                ingrecom = itemView.findViewById(R.id.num_irr);
                bg = itemView.findViewById(R.id.ingreContainer);
                text_com = itemView.findViewById(R.id.text_com);
                text_irr = itemView.findViewById(R.id.text_irr);
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
            holder.name.setText(compares.get(position).getIngreName());
            for(int i = 0; i<compares.get(position).getFunc().size();i++){
                if(i<compares.get(position).getFunc().size()-1){
                    function+=compares.get(position).getFunc().get(i)+", ";
                }
                else{
                    function+=compares.get(position).getFunc().get(i);
                }
            }
            holder.func.setText(function);
            function="";
            holder.ingreirr.setText(compares.get(position).getIrr());
            holder.ingrecom.setText(compares.get(position).getCom());
            if(compares.get(position).getTier().contains("Icky")||compares.get(position).getTier().contains("icky")){
                holder.text_irr.setTextColor(getResources().getColor(R.color.white));
                holder.text_com.setTextColor(getResources().getColor(R.color.white));
                holder.bg.setCardBackgroundColor(getResources().getColor(R.color.er2));
                holder.name.setTextColor(getResources().getColor(R.color.white));
                holder.func.setTextColor(getResources().getColor(R.color.white));
                holder.ingreirr.setTextColor(getResources().getColor(R.color.white));
                holder.ingrecom.setTextColor(getResources().getColor(R.color.white));
            }
            if(compares.get(position).getIrr().contains("3")||compares.get(position).getIrr().contains("4")||compares.get(position).getIrr().contains("5")){
                holder.text_irr.setTextColor(getResources().getColor(R.color.white));
                holder.text_com.setTextColor(getResources().getColor(R.color.white));
                holder.bg.setCardBackgroundColor(getResources().getColor(R.color.er));
                holder.name.setTextColor(getResources().getColor(R.color.white));
                holder.func.setTextColor(getResources().getColor(R.color.white));
                holder.ingreirr.setTextColor(getResources().getColor(R.color.white));
                holder.ingrecom.setTextColor(getResources().getColor(R.color.white));
            }
            else if(compares.get(position).getCom().contains("3")||compares.get(position).getCom().contains("4")||compares.get(position).getCom().contains("5")){
                holder.text_irr.setTextColor(getResources().getColor(R.color.white));
                holder.text_com.setTextColor(getResources().getColor(R.color.white));
                holder.bg.setCardBackgroundColor(getResources().getColor(R.color.er));
                holder.name.setTextColor(getResources().getColor(R.color.white));
                holder.func.setTextColor(getResources().getColor(R.color.white));
                holder.ingreirr.setTextColor(getResources().getColor(R.color.white));
                holder.ingrecom.setTextColor(getResources().getColor(R.color.white));
            }

        }
        @Override
        public int getItemCount() {
            return compares.size();
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
            if(pos==3){
                pkind = 3;
            }
        }
        public void onNothingSelected(AdapterView<?> parent) {}
    };

}
