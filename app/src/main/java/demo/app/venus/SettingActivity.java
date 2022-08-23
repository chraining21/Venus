package demo.app.venus;

//import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class SettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //觸發'產品清單'
        ((RelativeLayout)findViewById(R.id.listLayout)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                        startActivityForResult(new Intent(SettingActivity.this,TestDbActivity.class), 0);
            }
        });
        //觸發'分析成分'
        ((RelativeLayout)findViewById(R.id.scanLayout)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){

                startActivityForResult(new Intent(SettingActivity.this,uploadActivity.class), 0);

            }
        });
        //觸發'膚質測驗'
        ((RelativeLayout)findViewById(R.id.skinTestLayout)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                startActivityForResult(new Intent(SettingActivity.this,quiz.class), 0);
            }
        });

        //觸發'使用說明'
        ((RelativeLayout)findViewById(R.id.userGuideLayout)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                        startActivityForResult(new Intent(SettingActivity.this,UserGuideActivity.class), 0);
            }
        });

    }
}