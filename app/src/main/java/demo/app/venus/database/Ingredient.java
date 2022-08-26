package demo.app.venus.database;

public class Ingredient {
    String ingreName;
    String irr;
    String com;
    String tier;
    String funs;
    Ingredient(String a, String b, String c, String d, String e){
        this.ingreName = a;
        this.irr = b;
        this.com = c;
        this.tier = d;
        this.funs = e;
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

    public String getFuns() {
        return funs;
    }

    public void setFuns(String funs) {
        this.funs = funs;
    }
}
