package demo.app.venus;

public class questionlist {

    String Question;
    String oA;
    String oB;
    String oC;
    String oD;

    public questionlist(String question,String oA,String oB,String oC,String oD){
        Question = question;
        this.oA = oA;
        this.oB = oB;
        this.oC = oC;
        this.oD = oD;
    }

    public String getQuestion() {
        return Question;
    }

    public String getoA() {
        return oA;
    }

    public String getoB() {
        return oB;
    }

    public String getoC() {
        return oC;
    }

    public String getoD() {
        return oD;
    }

}
