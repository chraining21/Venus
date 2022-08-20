package demo.app.venus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class quiz extends AppCompatActivity {

    public static ArrayList<questionlist> listOfQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        listOfQ = new ArrayList<>();
        listOfQ.add(new questionlist("Q1﹕晚上沒使用保養品﹐早上起床時感覺皮膚狀況如何﹖","有緊繃感","無緊繃感","T字部有點油膩感","全臉都有點油膩感"));
        listOfQ.add(new questionlist("Q2﹕晚上沒使用保養品﹐早上起床皮膚觸摸的狀況如何﹖","有細碎脫屑","光滑無脫屑","T字部油油的","全臉都油油的"));
        listOfQ.add(new questionlist("Q3﹕臉部肌膚常出現的問題﹖","乾燥脫皮","大致上沒問題","T字部位毛孔粗大","易長痘痘粉刺"));
        listOfQ.add(new questionlist("Q4﹕容易長痘痘的部位﹖","不太常長","兩頰","T字部位","全臉"));
        listOfQ.add(new questionlist("Q5﹕臉部毛孔比較粗大的地方﹖","沒有","T字部位","T字部位及兩頰",""));
        listOfQ.add(new questionlist("Q6﹕臉部肌膚容易覺得乾燥的部位﹖","全臉","臉頰","無特別乾燥部位",""));
        listOfQ.add(new questionlist("Q7﹕肌膚受到氣候變化而影響的程度如何﹖","冬天特別容易乾燥","無明顯變化","夏天特別容易出油",""));

    }

    public void btn_test(View view){
        Intent intent = new Intent(this,question.class);
        startActivity(intent);
    }

    public void testend(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}