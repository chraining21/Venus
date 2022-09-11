package demo.app.venus;

import demo.app.venus.uploadHelper.IngreRes;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;



public interface UploadInterface {
    @POST("/upload")
    Call<IngreRes> upload(
            @Body RequestBody body
            );
}
