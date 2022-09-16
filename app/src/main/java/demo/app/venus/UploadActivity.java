package demo.app.venus;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    Button select, submit;
    ImageView pic;
    public static UploadActivity uploadActivity;
    ActivityResultLauncher<String> cropImage;
    Dialog dialog;
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
                cropImage.launch("image/*");
            }
        });
        cropImage = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                Intent intent = new Intent(UploadActivity.this,CropActivity.class);
                intent.putExtra("DATA",result.toString());
                startActivityForResult(intent,101);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dialog = new Dialog(UploadActivity.this);
                    dialog.startDialog();
                    uploadFile();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        uploadActivity=this;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==-1 && requestCode==101){
            String re = data.getStringExtra("RESULT");
            Uri reUri = null;
            if(re != null){
                reUri = Uri.parse(re);
            }
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),reUri);
                pic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private String convertToString()
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }

    private void uploadFile() throws JSONException {

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
                    dialog.dismiss();
                    Gson g = new Gson();
                    Intent intent = new Intent(UploadActivity.this,ProductDtlEditActivity.class);
                    intent.putExtra("ingrejson",  g.toJson(response.body().getData()));
                    startActivity(intent);
                } else {
                    dialog.dismiss();
                    Snackbar.make(findViewById(R.id.uploadLayout),"Some Error Occur...",Snackbar.LENGTH_LONG)
                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    uploadFile();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .show();
                }
            }

            @Override
            public void onFailure(Call<IngreRes> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}


