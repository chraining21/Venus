package demo.app.venus;

import static demo.app.venus.quiz.listOfQ;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import demo.app.venus.R;
import demo.app.venus.questionlist;

public class question extends Activity {
    List<questionlist> allQuestionList;
    demo.app.venus.questionlist questionlist;
    int index = 0; //計問題循環次數
    TextView text_question,optiona,optionb,optionc,optiond; //問題.選項文字
    CardView card_optiona,card_optionb,card_optionc,card_optiond; //點選選項按鍵
    int count = 0; //計算分數
    LinearLayout nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question); //基本語法

        Hooks();
        allQuestionList = listOfQ;
        Collections.shuffle(allQuestionList);//混序
        questionlist = listOfQ.get(index);
        setAllData();
        resetColor();
        enableButton();
        Selected();
    }

    private void setAllData() { //取得list[index]的問題以及選項文字並更改原文字
        text_question.setText(questionlist.getQuestion());

        optiona.setText(questionlist.getoA());
        optionb.setText(questionlist.getoB());
        optionc.setText(questionlist.getoC());
        optiond.setText(questionlist.getoD());
    }

    private void Hooks() { //找到按鍵ID
        text_question = (TextView)findViewById(R.id.text_question);
        optiona = (TextView) findViewById(R.id.text_optiona);
        optionb = (TextView)findViewById(R.id.text_optionb);
        optionc = (TextView)findViewById(R.id.text_optionc);
        optiond = (TextView)findViewById(R.id.text_optiond);

        card_optiona = (CardView) findViewById(R.id.card_optiona);
        card_optionb = (CardView)findViewById(R.id.card_optionb);
        card_optionc = (CardView)findViewById(R.id.card_optionc);
        card_optiond = (CardView)findViewById(R.id.card_optiond);

        nextBtn = (LinearLayout) findViewById(R.id.nextBtn);
    }

    public void Selected(){
        if(card_optiona.hasOnClickListeners()){
            count+=1;
        }else if(card_optionb.hasOnClickListeners()){
            count+=2;
        }else if(card_optionc.hasOnClickListeners()){
            count+=3;
        }else{
            count+=4;
        }

        nextBtn.setOnClickListener(v -> {
            if(index<allQuestionList.size()-1){
                resetColor();
                enableButton();
                index+=1;
                questionlist = allQuestionList.get(index);
                setAllData();
            }
            else {
                result();
            }
        });
    }

    private void result() {
        Intent intent = new Intent(question.this,testresult.class);
        intent.putExtra("result",count);
        ((LinearLayout) findViewById(R.id.nextBtn)).setOnClickListener(v -> startActivityForResult(new Intent(question.this,testresult.class), 0));
    }

    public void enableButton(){ //讓按鍵可按
        card_optiona.setClickable(true);
        card_optionb.setClickable(true);
        card_optionc.setClickable(true);
        card_optiond.setClickable(true);
    }

    public void disableButton(){  //讓按鍵不可按
        card_optiona.setClickable(false);
        card_optionb.setClickable(false);
        card_optionc.setClickable(false);
        card_optiond.setClickable(false);
    }

    public void resetColor(){ //設置初始顏色
        card_optiona.setBackgroundColor(getResources().getColor(R.color.yellow));
        card_optionb.setBackgroundColor(getResources().getColor(R.color.yellow));
        card_optionc.setBackgroundColor(getResources().getColor(R.color.yellow));
        card_optiond.setBackgroundColor(getResources().getColor(R.color.yellow));

    }

    public void OptClicka(View view){
        card_optiona.setBackgroundColor(ContextCompat.getColor(question.this,R.color.green));  //變色
        disableButton();
    }
    public void OptClickb(View view){
        card_optionb.setBackgroundColor(ContextCompat.getColor(question.this,R.color.green));
        disableButton();
    }
    public void OptClickc(View view){
        card_optionc.setBackgroundColor(ContextCompat.getColor(question.this,R.color.green));
        disableButton();
    }
    public void OptClickd(View view){
        card_optiond.setBackgroundColor(ContextCompat.getColor(question.this,R.color.green));
        disableButton();
    }
}