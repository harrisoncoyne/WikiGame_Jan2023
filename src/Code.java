import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class Code {

//    public int depth = 0;
    private int maxDepth = 2;
    public ArrayList<String> search = new ArrayList<>();
    public ArrayList<String> correct = new ArrayList<>();

    public int numLinks = 0;

    private String startName = "Keanu_Reeves";
    private String endName = "Ryan_Reynolds";

    private String startUrl = "https://en.wikipedia.org/wiki/" + startName; //starting page
    private String endUrl = "https://en.wikipedia.org/wiki/" + endName; //ending page
    public String URL = startUrl;

    public static void main(String[] args) {
        Code reader = new Code();
    }

    public Code() {
        recursion(URL, 0, maxDepth);
    }

    public boolean recursion(String startUrl, int depth, int maxDepth) {

        // BASE CASE
        if (startUrl.equals(endUrl)) {

//            correct.add(search); // need to make it so only adds "correct" search results to correct arraylist

            if (depth <= maxDepth) {
                System.out.println("complete");
                System.out.println(correct);
            }

            return true;
        }
        else if (depth > maxDepth) {
            System.out.println("failed: " + depth);
//            depth = 0;
            return false;
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // GENERAL RECURSION CASE
        else {

                System.out.println();
                System.out.println("depth: " + depth);
                System.out.println("url: " + URL);
                System.out.println();

                try { //HTML READ

                    URL look = new URL(startUrl);

                    BufferedReader reader = new BufferedReader(new InputStreamReader(look.openStream()));

                    String line;

                    while ((line = reader.readLine()) != null) {

                        int start = line.indexOf("/wiki/");
                        int end = line.indexOf("\"", start);

                        if(line.contains("/wiki/") && !line.contains(startName) && !line.contains(":")) {
                            URL = "https://en.wikipedia.org" + line.substring(start, end);

                            System.out.println(URL);
                            numLinks++;

                            if (recursion(URL, depth+1, maxDepth) == true) {
                                System.out.println("complete: " + URL + " " + depth); // show URL history here *****
                                System.out.println("links searched: " + numLinks);
                                return true;
                            }
                        }
                    }
                }
                catch(Exception ex) {
                    System.out.println(ex);
                }
        }
        return false;
    }

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
