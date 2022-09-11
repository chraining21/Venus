package demo.app.venus.uploadHelper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

import demo.app.venus.database.Ingredient;

public class IngreRes {
    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("data")
    @Expose
    List<Ingredient> data;
    public String toString(){
        return status+" : "+data.toString();
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Ingredient> getData() {
        return data;
    }

    public void setData(List<Ingredient> data) {
        this.data = data;
    }
}
