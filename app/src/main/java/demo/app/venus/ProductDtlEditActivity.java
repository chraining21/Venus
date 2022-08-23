package demo.app.venus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import demo.app.venus.database.Database;
import demo.app.venus.database.Products;

public class ProductDtlEditActivity extends AppCompatActivity {
    EditText bName;
    EditText pName;
    EditText exp;
    Button submit;
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_dtl_edit);
        bName = findViewById(R.id.edit_bname);
        pName = findViewById(R.id.edit_pname);
        exp = findViewById(R.id.edit_pexp);
        submit = findViewById(R.id.submitbutton);
        db = Database.getInstance(this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.mainDAO().insert(new Products("hihi","haha","2022/1/2","{ww}",1));
                List<Products> results = db.mainDAO().getAll();
                bName.setText(results.get(0).getBrandName());
                pName.setText(results.get(0).getProductName());
                exp.setText(results.get(0).getExpdate());
            }
        });

    }
}