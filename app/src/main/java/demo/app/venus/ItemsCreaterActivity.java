package demo.app.venus;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import demo.app.venus.Models.Items;

public class ItemsCreaterActivity extends AppCompatActivity {
    EditText editText_title,editText_notes;
    ImageView imageView_save;
    Items items;
    boolean isOldNote = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_creater);


        imageView_save = findViewById(R.id.imageView_save);
        editText_title = findViewById(R.id.editText_title);
        editText_notes = findViewById(R.id.editText_notes);


        items = new Items();
        try{
            items = (Items) getIntent().getSerializableExtra("old_note");
            editText_title.setText(items.getTitle());
            editText_notes.setText(items.getNotes());
//            cat_group.setCategory(items.getCategory());

            isOldNote = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }




        imageView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editText_title.getText().toString();
                String description  = editText_notes.getText().toString();

                if(description.isEmpty()){
                    Toast.makeText(ItemsCreaterActivity.this,"請輸入完整資訊",Toast.LENGTH_SHORT).show();
                    return;
                }


                if(!isOldNote){
                    items = new Items();
                }


                items.setTitle(title);
                items.setNotes(description);
//               items.setDate(formatter.format(date));

                Intent intent  = new Intent();
                intent.putExtra("item",items);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });


    }
}