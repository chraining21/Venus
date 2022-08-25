package demo.app.venus;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;


public class ingredient_list extends AppCompatActivity {

    LinearLayout details;
    LinearLayout ingredient;

    @Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ingredient_list);

    details = findViewById(R.id.details);
    ingredient = findViewById(R.id.ingredient);
    ingredient.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

    }



    public void expand(View view){
        int v = (details.getVisibility() == View.GONE)? View.VISIBLE : View.GONE;

        TransitionManager.beginDelayedTransition(ingredient,new AutoTransition());
        details.setVisibility(v);
    }
}

