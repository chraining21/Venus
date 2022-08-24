package demo.app.venus;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class testresult extends Activity {
    ImageView resultpng;
    TextView resulttext;
    TextView resultnum;

    int result;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testresult);

        result = getIntent().getIntExtra("result",0);


        resultpng = (ImageView)findViewById(R.id.resultpng);
        resulttext = (TextView)findViewById(R.id.resulttext);
        resultnum =  (TextView)findViewById(R.id.resultnum);

        resultnum.setText(result+"/25");

        if(result<=10){
            resulttext.setText("乾性膚質");
        } else if(result<=14){
            resulttext.setText("中性膚質");
        } else if(result<=20){
            resulttext.setText("混合性膚質");
        } else{
            resulttext.setText("油性膚質");
        }

        if(result<=10){
            resultpng.setImageDrawable(getResources().getDrawable(R.drawable.dry));
        } else if(result<=14){
            resultpng.setImageDrawable(getResources().getDrawable(R.drawable.neutral));
        } else if(result<=20){
            resultpng.setImageDrawable(getResources().getDrawable(R.drawable.combination));
        } else{
            resultpng.setImageDrawable(getResources().getDrawable(R.drawable.oily));
        }

        ((Button) findViewById(R.id.btn_testend)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                startActivityForResult(new Intent(testresult.this,SettingActivity.class),0);
            }
        });
    }
}