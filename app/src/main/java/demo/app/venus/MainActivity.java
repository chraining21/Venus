package demo.app.venus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import demo.app.venus.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        switchFragment(new listFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.list:
                    switchFragment(new listFragment());
                    break;
                case R.id.upload:
                    switchFragment(new uploadFragment());
                    break;
                case R.id.settings:
                    switchFragment(new settingFragment());
                    break;
            }

            return true;
        });
    }
    private void switchFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction tran  = manager.beginTransaction();
        tran.replace(R.id.frameLayout,fragment);
        tran.commit();
    }
}