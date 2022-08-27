package demo.app.venus;

import demo.app.venus.uploadHelper.IngreRes;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface UploadInterface {
    @POST("/upload")
    Call<IngreRes> upload(
            @Body RequestBody body
            );
}
