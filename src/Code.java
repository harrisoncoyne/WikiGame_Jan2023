import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class Code {

//    private int depth = 0;
    private int maxDepth = 6;
    public ArrayList<String> search = new ArrayList<>();
    public ArrayList<String> correct = new ArrayList<>();

    private String startUrl = "https://en.wikipedia.org/wiki/Ryan_Reynolds"; //starting page
    private String endUrl = "https://en.wikipedia.org/wiki/Jim_Carrey"; //ending page
    private String URL = startUrl;

    public static void main(String[] args) {
        Code reader = new Code();
    }

    public Code() {
        recursion(startUrl, 0, maxDepth);
    }

    //    public String recursion(String URL, String startUrl, String endUrl, int depth, int maxDepth, ArrayList search, ArrayList correct) {
    public String recursion(String startUrl, int depth, int maxDepth) {

        // BASE CASE
        if (URL == endUrl) {

//            correct.add(search); // need to make it so only adds "correct" search results to correct arraylist

            if (depth <= maxDepth) {
                System.out.println("complete");
                System.out.println(correct);
            }

            else {
                correct.clear();
//                return URL;
            }
        }
        else if (depth > maxDepth) {
            System.out.println("failed: " + depth);
        }

        // GENERAL RECURSION CASE
        else {

            if (depth <=maxDepth){
                System.out.println("depth: " + depth);


                //VVV HtmlRead VVV

                try {

                    URL look = new URL(URL);

                    BufferedReader reader = new BufferedReader(new InputStreamReader(look.openStream()));

                    String line;

                    while ((line = reader.readLine()) != null) {

                        int start = line.indexOf("https");
                        int end = line.indexOf("\"", start);

                        if(line.contains("https://en.wikipedia.org/")) {
//                    if (!line.contains("Ryan_Reynolds")) {
                            System.out.println(line.substring(start, end));

                            if (!URL.contains("Ryan_Reynolds")) {
                                search.add(new String(line.substring(start, end)));
                                URL = line.substring(start, end);

                                recursion(URL, depth+1, maxDepth);
                            }
//                                    System.out.println(URL);
                        }
//                    }

                    }
                }
                catch(Exception ex) {
                    System.out.println(ex);
                }

            }
        }
        return URL;
    }

    //    public String HtmlRead(String URL, String startUrl, String endUrl, int depth, ArrayList search, ArrayList correct){
    public String HtmlRead(String startUrl, int depth){
        try {

            URL look = new URL(startUrl);

            BufferedReader reader = new BufferedReader(new InputStreamReader(look.openStream()));

            String line;

            while ((line = reader.readLine()) != null) {

                int start = line.indexOf("https");
                int end = line.indexOf("\"", start);

                if(line.contains("https://en.wikipedia.org/")) {
//                    if (!line.contains("Ryan_Reynolds")) {
                    System.out.println(line.substring(start, end));

                    if (!URL.contains("Ryan_Reynolds")) {
                        search.add(new String(line.substring(start, end)));
                        URL = line.substring(start, end);
                        recursion(URL, depth+1, maxDepth);
                    }
//                                    System.out.println(URL);
                }
//                    }

            }
        }
        catch(Exception ex) {
            System.out.println(ex);
        }

        return URL;
    }

}
