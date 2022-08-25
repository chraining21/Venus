package demo.app.venus.uploadHelper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ingredient {
    @SerializedName("name")
    @Expose
    String ingreName;
    @SerializedName("irritancy")
    @Expose
    String irr;
    @SerializedName("comedogenicity")
    @Expose
    String com;
    @SerializedName("tier")
    @Expose
    String tier;
    @SerializedName("whatitdoes")
    @Expose
    List<String> funs;
    Ingredient(){

    }
    Ingredient(String name, String irr,String com, String tier,List<String> funs){
        this.ingreName = name;
        this.irr = irr;
        this.com = com;
        this.tier = tier;
        this.funs = funs;
    }
    public String getIngreName() {
        return ingreName;
    }

    public void setIngreName(String ingreName) {
        this.ingreName = ingreName;
    }

    public String getIrr() {
        return irr;
    }

    public void setIrr(String irr) {
        this.irr = irr;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public List<String> getFuns() {
        return funs;
    }

    public void setFuns(List<String> funs) {
        this.funs = funs;
    }
}
