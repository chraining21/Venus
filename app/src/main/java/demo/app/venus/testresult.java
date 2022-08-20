package demo.app.venus;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
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

        resultpng = findViewById(R.id.resultpng);
        resulttext = findViewById(R.id.resulttext);
        resultnum = findViewById(R.id.resultnum);

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
            resultpng.setImageResource(Integer.parseInt("@drawable/dry"));
        } else if(result<=14){
            resultpng.setImageResource(Integer.parseInt("@drawable/neutral"));
        } else if(result<=20){
            resultpng.setImageResource(Integer.parseInt("@drawable/combination"));
        } else{
            resultpng.setImageResource(Integer.parseInt("@drawable/oily"));
        }

    }


}