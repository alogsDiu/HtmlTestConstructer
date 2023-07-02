package models;
import java.util.ArrayList;
import java.util.List;

public class Question {
    String question;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    List<Variant> variants ;

    public List<Variant> getVariants() {
        return variants;
    }

    public Question (){
        variants=new ArrayList<>();
        question="";
    }
    public Question (String q){
        variants=new ArrayList<>();
        question=q;
    }
}
