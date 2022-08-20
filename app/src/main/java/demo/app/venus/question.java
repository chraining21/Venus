package demo.app.venus;

import static demo.app.venus.quiz.listOfQ;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class question extends AppCompatActivity {
    List<questionlist> allQuestionList;
    questionlist questionlist;
    int index = 0;
    TextView text_question,optiona,optionb,optionc,optiond;
    CardView card_optiona,card_optionb,card_optionc,card_optiond;
    int count = 0;
    LinearLayout nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Hooks();
        allQuestionList = listOfQ;
        Collections.shuffle(allQuestionList);
        questionlist = listOfQ.get(index);

        card_optiona.setBackgroundColor(getResources().getColor(R.color.yellow));
        card_optionb.setBackgroundColor(getResources().getColor(R.color.yellow));
        card_optionc.setBackgroundColor(getResources().getColor(R.color.yellow));
        card_optiond.setBackgroundColor(getResources().getColor(R.color.yellow));
        Selected();
        nextBtn.setClickable(false);

        setAllData();

    }

    private void setAllData() {
        text_question.setText(questionlist.getQuestion());

        optiona.setText(questionlist.getoA());
        optionb.setText(questionlist.getoB());
        optionc.setText(questionlist.getoC());
        optiond.setText(questionlist.getoD());

    }

    private void Hooks() {
        text_question = findViewById(R.id.text_question);
        optiona = findViewById(R.id.text_optiona);
        optionb = findViewById(R.id.text_optionb);
        optionc = findViewById(R.id.text_optionc);
        optiond = findViewById(R.id.text_optiond);

        card_optiona = findViewById(R.id.card_optiona);
        card_optionb = findViewById(R.id.card_optionb);
        card_optionc = findViewById(R.id.card_optionc);
        card_optiond = findViewById(R.id.card_optiond);

        nextBtn = findViewById(R.id.nextBtn);
    }

    public void Selected(){
        if(index<listOfQ.size()-1){
            enableButton();
        nextBtn.setOnClickListener(v -> {
            if(card_optiona.hasOnClickListeners()){
                count+=1;
                index++;
                questionlist = listOfQ.get(index);
                setAllData();
            }else if(card_optionb.hasOnClickListeners()){
                count+=2;
                index++;
                questionlist = listOfQ.get(index);
                setAllData();
            }else if(card_optionc.hasOnClickListeners()){
                count+=3;
                index++;
                questionlist = listOfQ.get(index);
                setAllData();
            }else{
                count+=4;
                index++;
                questionlist = listOfQ.get(index);
                setAllData();
            }
        });
        }
        else {
            result();
            resetColor();
        }
    }

    private void result() {
        Intent intent = new Intent(question.this,testresult.class);
        intent.putExtra("result",count);
        startActivity(intent);
    }

    public void enableButton(){
        card_optiona.setClickable(true);
        card_optionb.setClickable(true);
        card_optionc.setClickable(true);
        card_optiond.setClickable(true);
    }

//    public void disableButton(){
//        card_optiona.setClickable(false);
//        card_optionb.setClickable(false);
//        card_optionc.setClickable(false);
//        card_optiond.setClickable(false);
//    }

    public void resetColor(){
        card_optiona.setBackgroundColor(getResources().getColor(R.color.yellow));
        card_optionb.setBackgroundColor(getResources().getColor(R.color.yellow));
        card_optionc.setBackgroundColor(getResources().getColor(R.color.yellow));
        card_optiond.setBackgroundColor(getResources().getColor(R.color.yellow));

    }
    public void OptClick(View view){
        nextBtn.setClickable(true);
        if(card_optiona.hasOnClickListeners()){
            card_optiona.setBackgroundColor(getResources().getColor(R.color.green));
        }else if(card_optionb.hasOnClickListeners()){
            card_optionb.setBackgroundColor(getResources().getColor(R.color.green));
        }
        else if(card_optionc.hasOnClickListeners()){
            card_optionc.setBackgroundColor(getResources().getColor(R.color.green));
        }else {
            card_optiond.setBackgroundColor(getResources().getColor(R.color.green));
        }
//        disableButton();
    }
}
