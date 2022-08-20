package demo.app.venus.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "products")
public class Products implements Serializable {
    @PrimaryKey
    @NonNull
    String productName = "exampleName";

    @NonNull
    @ColumnInfo(name = "exp")
    String expDate = "exampleName";

    @NonNull
    @ColumnInfo(name = "ingredients")
    String ingreJson ="exampleJson";

    /*
    0 is none
    1 is honey
    2 is toxic
     */
    @NonNull
    @ColumnInfo(name = "kind")
    int kind = 0;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String  expDate) {
        this.expDate = expDate;
    }

    public String getIngreJson() {
        return ingreJson;
    }

    public void setIngreJson(String ingreJson) {
        this.ingreJson = ingreJson;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }
}
