package demo.app.venus.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Products")
public class Products implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    int id;

    @ColumnInfo(name = "brandName")
    String brandName;

    @ColumnInfo(name = "productName")
    String productName;

    @ColumnInfo(name = "exp")
    String expdate;

    @ColumnInfo(name = "ingreJson")
    String ingreJson;

    @ColumnInfo(name= "kind")
    int kind;

    public Products(){
        this.brandName = "";
        this.productName = "";
        this.expdate = "";
        this.ingreJson = "";
        this.kind = 0;
    }
    @Ignore
    public Products(String bName, String pName, String exp, String iJson, int k) {
        this.brandName = bName;
        this.productName = pName;
        this.expdate = exp;
        this.ingreJson = iJson;
        this.kind = k;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getExpdate() {
        return expdate;
    }

    public void setExpdate(String expdate) {
        this.expdate = expdate;
    }

    public String getIngreJson() {
        return ingreJson;
    }

    public void setIngreJson(String ingreJson) {
        this.ingreJson = ingreJson;
    }
}