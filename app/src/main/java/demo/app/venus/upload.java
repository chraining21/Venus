package demo.app.venus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.Permission;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class upload<PermissionListener> extends Activity {

    Uri selecteduri;
    Button buttonpick, buttonupload;
    ImageView imageView;
    Bitmap bitmap;
    String url = "http://163.13.201.83/80";
    String encodeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        buttonpick = findViewById(R.id.pickimage);
        buttonupload = findViewById(R.id.uploadimage);
        imageView = findViewById(R.id.imageView);


        buttonpick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                askpermission();
            }
        });

        buttonupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(upload.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(upload.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> param = new HashMap<String, String>();

                        param.put("image",encodeImage);

                        return param;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(upload.this);
                requestQueue.add(request);

            }
        });

    }

    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 || requestCode == RESULT_OK || data != null || data.getData() != null) {

            selecteduri = data.getData();

            InputStream inputStream = null;
            try {
                inputStream = getContentResolver().openInputStream(selecteduri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
                imageStore(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    private void imageStore(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        byte[] imagebyte = stream.toByteArray();

        encodeImage = android.util.Base64.encodeToString(imagebyte, Base64.DEFAULT);
    }

    private void askpermission() {


        PermissionListener permissionListener = new PermissionListener() {

            @Override
            public void onPermissionGranted() {

                chooseImage();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {

                Toast.makeText(upload.this, "需要許可", Toast.LENGTH_SHORT).show();
            }

        };
        TedPermission.with(upload.this)
                .setPermissionListener(permissionListener)
                .setPermission(Manifest.permission.INTERNET,Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();



    }
}
