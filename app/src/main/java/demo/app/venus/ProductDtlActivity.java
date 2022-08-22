package demo.app.venus;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import demo.app.venus.database.RoomDB;
import demo.app.venus.database.Products;

public class ProductDtlActivity extends AppCompatActivity {
    EditText name;
    EditText date;
    EditText ing;
    Button submit;
    RoomDB database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_dtl);
        name = findViewById(R.id.pname);
        date = findViewById(R.id.pexp);
        ing = findViewById(R.id.plist);
        submit = findViewById(R.id.submitbutton);
        database = RoomDB.getInstance(this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.mainDao().insert(new Products("haha","2022/1/2","{ww}",1));
                List<String> names = database.mainDao().getName("haha");
                name.setText(names.get(0));
            }
        });

    }
}