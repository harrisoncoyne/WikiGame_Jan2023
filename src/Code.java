import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Code {

    private int depth;
    private ArrayList<String> path = new ArrayList<>();

    public static void main(String[] args) {
        Code reader = new Code();
    }

    public Code() {
        String startUrl = "https://en.wikipedia.org/wiki/Ryan_Reynolds"; //starting page
        String endUrl = "https://en.wikipedia.org/wiki/Jim_Carrey"; //ending page
        String URL = startUrl;
        depth = 6;

        ArrayList search = new ArrayList();
        ArrayList correct = new ArrayList();

    }

    public void recursion(String URL, String startUrl, String endUrl, int depth, ArrayList search, ArrayList correct) {

        if (URL == endUrl) {

            correct.add(search); // need to make it so only adds "correct" search results to correct arraylist

            if (correct.size() > depth) {
                System.out.println("complete");
                System.out.println(correct);
            }

            else {
                correct.clear();
                return;
            }
        }

        else {
            URL = startUrl;
            HtmlRead(URL, startUrl, endUrl, depth, search, correct);

        }
    }

    public String HtmlRead(String URL, String startUrl, String endUrl, int depth, ArrayList search, ArrayList correct){
        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(startUrl.openStream()));

            String line;

            while ((line = reader.readLine()) != null) {

                int start = line.indexOf("https");
                int end = line.indexOf("\"", start);

                    if (line.contains("https://")) {
//                        System.out.println(line.substring(start, end));
                        search.add(new String(line.substring(start, end)));
                        URL = line.substring(start,end);
                    }
            }
        }
        catch(Exception ex) {
            System.out.println(ex);
        }

        return URL;
    }

}
