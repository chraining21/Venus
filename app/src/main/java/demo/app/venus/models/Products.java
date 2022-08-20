package demo.app.venus.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "products")
public class Products implements Serializable {
    @PrimaryKey
    String productName = "";

    @ColumnInfo(name = "exp")
    Long expDate = null;

    @ColumnInfo(name = "ingredients")
    String ingreJson ="";

    /*
    0 is none
    1 is honey
    2 is toxic
     */
    @ColumnInfo(name = "kind")
    int kind = 0;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getExpDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(expDate*1000);
        String str = dateFormat.format(date);
        return str;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate.getTime();
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
