package demo.app.venus;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class quiz extends Activity implements View.OnClickListener{

    TextView questionTextView;
    Button ansA, ansB, ansC, ansD;
    Button submitBtn;

    int score=0;
    int totalQuestion = QuestionAnswer.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionTextView = findViewById(R.id.question);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);
        submitBtn = findViewById(R.id.submit_btn);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        loadNewQuestion();

    }

    @Override
    public void onClick(View view) {

        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);


        Button clickedButton = (Button) view;
        if(clickedButton.getId()==R.id.submit_btn){
            if(selectedAnswer.equals(QuestionAnswer.choices[currentQuestionIndex][0])){
                score++;
            }else if(selectedAnswer.equals(QuestionAnswer.choices[currentQuestionIndex][1])){
                score+=2;
            }
            else if(selectedAnswer.equals(QuestionAnswer.choices[currentQuestionIndex][2])){
                score+=3;
            }else{
                score+=4;
            }
            currentQuestionIndex++;
            loadNewQuestion();


        }else{

            selectedAnswer  = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.parseColor("#F7F9BF"));

        }

    }

    void loadNewQuestion(){

        if(currentQuestionIndex == totalQuestion ){
            finishQuiz();
            return;
        }

        questionTextView.setText(QuestionAnswer.question[currentQuestionIndex]);
        ansA.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
        ansB.setText(QuestionAnswer.choices[currentQuestionIndex][1]);
        ansC.setText(QuestionAnswer.choices[currentQuestionIndex][2]);
        ansD.setText(QuestionAnswer.choices[currentQuestionIndex][3]);

    }

    void finishQuiz(){

        String skinStatus = " ";

        if(score<=10){
            skinStatus="乾性膚質";
        } else if(score<=14){
            skinStatus="中性膚質";
        } else if(score<=20){
            skinStatus="混合性膚質";
        } else{
            skinStatus="油性膚質";
        }


        new AlertDialog.Builder(this)
                .setTitle("檢測結果")
                .setMessage(skinStatus)
                .setIcon(R.drawable.neutral)
                .setPositiveButton("再測一次",(dialogInterface, i) -> restartQuiz() )
                .setCancelable(false)
                .show();

    }

    void restartQuiz(){
        score = 0;
        currentQuestionIndex =0;
        loadNewQuestion();
    }


}