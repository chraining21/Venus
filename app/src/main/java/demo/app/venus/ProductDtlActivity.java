package demo.app.venus;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import demo.app.venus.database.Database;
import demo.app.venus.database.Database;
import demo.app.venus.database.Products;

public class ProductDtlActivity extends AppCompatActivity {
    TextView bName;
    TextView pName;
    TextView exp;
    Database database;
    Bundle bundle = getIntent().getBundleExtra("bundle_product");
    Products p = (Products) bundle.getSerializable("product");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_dtl);
        bName = findViewById(R.id.bname);
        pName = findViewById(R.id.pname);
        exp = findViewById(R.id.pexp);
        database = Database.getInstance(this);
        bName.setText(p.getBrandName());
        pName.setText(p.getProductName());
        exp.setText(p.getExpdate());
    }
}