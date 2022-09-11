package demo.app.venus;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.LinearLayout;

public class ingredient_list extends Activity {

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
}

