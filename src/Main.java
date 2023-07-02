import models.Question;
import models.Variant;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = null;
        PrintWriter pw = null;
        try {
            scanner = new Scanner(new File("C:\\Users\\Acer\\Desktop\\Practice\\TestToHtml\\resources\\questions.txt"));
            pw = new PrintWriter("C:\\Users\\Acer\\Desktop\\Practice\\TestToHtml\\resources\\test.html");
        }catch (Exception e){
            System.out.println("PATH TROUBLES");
        }
        ArrayList<Question> questions = new ArrayList<Question>();
        Question q =null;
        String readValue ;

        ///Main loop for each question
        while(scanner.hasNext()){
            readValue=scanner.next();
            ///Caution against incorrect format ex: 324.Bla Bla (has to be) =>324. Bla Bla
            if( readValue.indexOf(readValue.indexOf((questions.size()+1)+"")+((questions.size()+1)+"").length() ) == '.')
                if(readValue.length()==((questions.size()+1)+".").length() )
                    readValue=scanner.next();
                else
                    readValue=readValue.substring(((questions.size()+1)+".").length());
            else
                readValue=readValue.substring(((questions.size()+1)+"").length());
            ///Start of a new Question
            q = new Question();
            q.setQuestion(readValue);///First word of the question

            while (scanner.hasNext()){
                ///Reading the rest of the question until reaching answer variants
                readValue=scanner.next();
                if(readValue.contains("//")){
                    ///Reached the end of a Question body;
                    q.setQuestion(q.getQuestion()+" "+readValue.substring(0,readValue.indexOf("//")));
                    break;
                }else {
                    ///Didn't finish the question end
                    q.setQuestion(q.getQuestion()+" "+readValue);
                }
            }
            ///Question Body is set

            ///Constructing variants

            Variant variant = new Variant(true);///Constructing the correct variant of the question
            while (scanner.hasNext()){
                ///Creating variants
                while (scanner.hasNext()) {
                    ///Constructing the variant
                    readValue = scanner.next();
                    if (readValue.contains("***")){
                        ///End of the whole question
                        q.getVariants().add(variant);
                        break;
                    }else if (readValue.contains("//")){
                        ///The end of the variant
                        variant.setVariantBody(variant.getVariantBody() + " " + readValue.substring(0, readValue.indexOf("//")));
                        q.getVariants().add(variant);
                        variant=new Variant(false);
                        break;
                    } else {
                        ///Not the end and keep going
                        variant.setVariantBody(variant.getVariantBody() + " " + readValue);
                    }
                }
                if(readValue.contains("***")){
                    break;
                }
            }
            ///Variants are constructed
            questions.add(q);
        }


        ///Creating a html file
        String htmlFile ="<html>\n" +
                "    <head>\n" +
                "<meta content='IE=edge' http-equiv='X-UA-Compatible'/>\n" +
                "<meta charset=\"utf-8\">\n" +
                "<META HTTP-EQUIV=\"CONTENT-LANGUAGE\" CONTENT=\"ru\">\n" +
                "    \n" +
                "    <style>\n" +
                "        body{\n" +
                "            font-size: 20px;\n" +
                "            font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;\n" +
                "            -webkit-appearance: none; \n" +
                "            -moz-appearance: none; \n" +
                "            appearance: none;\n" +
                "        }\n" +
                "        .btn{\n" +
                "            width: 650px;\n" +
                "            height: 45px;\n" +
                "            border-radius: 10px;\n" +
                "            text-align: left;\n" +
                "            font-size: 17.5px;\n" +
                "            cursor: pointer;\n" +
                "        }\n" +
                "        .btn:focus{\n" +
                "            background-color: red;\n" +
                "            border-radius:10px ;\n" +
                "            color: black\n" +
                "        }\n" +
                "        .btn:hover{\n" +
                "            filter:brightness(60%);\n" +
                "            transition: 0.6s;\n" +
                "            color: black;\n" +
                "        }\n" +
                "        \n" +
                "        \n" +
                "        .btn_otvet{\n" +
                "            width: 650px;\n" +
                "            height: 45px;\n" +
                "            border-radius: 0%;\n" +
                "            text-align: left;\n" +
                "            border-radius:10px ;\n" +
                "            font-size: 17.5px;\n" +
                "            cursor: pointer;\n" +
                "        }\n" +
                "        .btn_otvet:focus{\n" +
                "            background-color: green;\n" +
                "            border-radius:10px ;\n" +
                "            color: black\n" +
                "\n" +
                "        }\n" +
                "        .btn_otvet:hover{\n" +
                "            filter:brightness(60%);\n" +
                "            transition: 0.6s;\n" +
                "            color: black;\n" +
                "        }\n" +
                "        .quiz{\n" +
                "            border: 3px solid black;\n" +
                "            border-radius:15px;\n" +
                "            padding: 10px;\n" +
                "        }\n" +
                "    </style>\n" +
                "    </head>\n" +
                "    <body>";

        for (int i = 0; i < questions.size(); i++) {
            htmlFile+="<div class=\"quiz\">\n<h3>"+(i+1)+questions.get(i).getQuestion()+"</h3>";
            ///Random order of variants
            Collections.shuffle(questions.get(i).getVariants());
            ///Setting all the variants
            for (int j = 0; j < questions.get(i).getVariants().size(); j++) {
                htmlFile+="<button  class=\""+( (questions.get(i).getVariants().get(j).isCorrect())?"btn_otvet":"btn")+"\">"+questions.get(i).getVariants().get(j).getVariantBody()+"</button><br>";
            }

            htmlFile+="</div>";
        }

        htmlFile+="</body>\n" +
                "</html>";
        ///Writing constructed html to a file
        pw.write(htmlFile);
        pw.close();
    }
}