package demo.app.venus;

import java.util.List;

public class Ingredient {
    String ingreName;
    String irr;
    String com;
    String tier;
    List<String> funs;

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
