package demo.app.venus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.UUID;

public class CropActivity extends AppCompatActivity {
    String result;
    Uri fileUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        result = getIntent().getStringExtra("DATA");
        fileUri = Uri.parse(result);
        String dest_uri = new StringBuilder(UUID.randomUUID().toString()).append(".jpg").toString();
        UCrop.Options options = new UCrop.Options();
        options.setFreeStyleCropEnabled(true);
        UCrop.of(fileUri,Uri.fromFile(new File(getCacheDir(),dest_uri)))
                .withOptions(options)
                .useSourceImageAspectRatio()
                .withMaxResultSize(2000,2000)
                .start(CropActivity.this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==UCrop.REQUEST_CROP){
            final Uri resultUri = UCrop.getOutput(data);
            Intent res = new Intent();
            res.putExtra("RESULT",resultUri+"");
            setResult(-1,res);
            finish();
        }
        else if(resultCode ==UCrop.RESULT_ERROR){
            final Throwable e = UCrop.getError(data);
        }

    }
}