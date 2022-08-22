package demo.app.venus.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "Products")
public class Products implements Serializable {
    @PrimaryKey
    @NonNull
    String productName;

    @ColumnInfo(name = "exp")
    String expDate;

    @ColumnInfo(name = "ingredients")
    String ingreJson;

    /*
    0 is none
    1 is honey
    2 is toxic
     */
    @ColumnInfo(name = "kind")
    int kind;

    public Products() {
        this.productName = "";
        this.expDate = "";
        this.ingreJson = "";
        this.kind = 0;
    }
    @Ignore
    public Products(String productName, String expDate, String ingreJson,int kind) {
        this.productName = productName;
        this.expDate = expDate;
        this.ingreJson = ingreJson;
        this.kind = kind;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
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