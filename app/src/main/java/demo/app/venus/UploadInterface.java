package demo.app.venus;

import demo.app.venus.uploadHelper.IngreRes;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadInterface {
    @POST("/upload")
    Call<IngreRes> upload(
            @Part MultipartBody.Part file
            );
}
