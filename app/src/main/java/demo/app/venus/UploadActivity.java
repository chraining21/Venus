package demo.app.venus;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;

import demo.app.venus.uploadHelper.IngreRes;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadActivity extends AppCompatActivity {

    String filePath = "imageUri";
    Button select, submit, edit;
    ImageView pic;
    ProgressBar bar;
    ActivityResultLauncher<String> photo;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://")
            .build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        select =findViewById(R.id.selectpic);
        submit = findViewById(R.id.submitpic);
        pic = findViewById(R.id.upimage);
        edit = findViewById(R.id.editpic);
        bar = findViewById(R.id.progressBar);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload(filePath);

            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photo.launch("image/*");

            }
        });

        photo = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        pic.setImageURI(result);
                        filePath = result.toString();
                    }
                }
        );
    }
    public void upload(String filePath){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("")
                .addConverterFactory(GsonConverterFactory.create()).build();

        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        UploadInterface apiService = retrofit.create(UploadInterface.class);
        Call<IngreRes> call = apiService.upload(body);
        call.enqueue(new Callback<IngreRes>() {
            @Override
            public void onResponse(Call<IngreRes> call, Response<IngreRes> response) {
                if(response.body().getStatus().equals("Success")){
                    Intent intent = new Intent(UploadActivity.this, ProductDtlActivity.class);
                    intent.putExtra("ingre",response.body().getData());
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<IngreRes> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "上傳失敗", Toast.LENGTH_SHORT).show();
            }
        });

    }


}

