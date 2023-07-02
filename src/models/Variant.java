package models;
public class Variant {
    boolean isCorrect ;
    String variantBody ="";
    public String getVariantBody() {
        return variantBody;
    }
    public void setVariantBody(String variantBody) {
        this.variantBody = variantBody;
    }
    public  boolean isCorrect(){
        return isCorrect;
    }
    public  Variant (boolean isCorrect){
        this.isCorrect=isCorrect;
    }
}
