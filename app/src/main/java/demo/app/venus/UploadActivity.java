package demo.app.venus;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;

import demo.app.venus.uploadHelper.IngreRes;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UploadActivity extends AppCompatActivity {
    Bitmap bitmap;
    String filePath;
    Button select, submit, edit;
    ImageView pic;

    int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        select = findViewById(R.id.selectpic);
        pic = findViewById(R.id.upimage);
        submit = findViewById(R.id.submitpic);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                            uploadFile(filePath);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
            }
        });

    }

    public void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }
    private String convertToString()
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImageUri);
                        pic.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void uploadFile(String fileUri) throws JSONException {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://163.13.201.83:80")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JSONObject paramObject = new JSONObject();
        paramObject.put("file", convertToString());

        UploadInterface api = retrofit.create(UploadInterface.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),paramObject.toString());
        Call<IngreRes> call = api.upload(body);
        call.enqueue(new Callback<IngreRes>() {
            @Override
            public void onResponse(Call<IngreRes> call, Response<IngreRes> response) {
                if (response.isSuccessful()) {
                    Gson g = new Gson();
                    Intent intent = new Intent(UploadActivity.this,ProductDtlEditActivity.class);
                    intent.putExtra("ingrejson",  g.toJson(response.body().getData()));
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Some error occurred...", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<IngreRes> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}


