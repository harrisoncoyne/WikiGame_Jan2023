import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Code implements ActionListener {

    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JLabel urlLabel;
    private JLabel searchLabel;
    private JPanel controlPanel;
    private JMenuBar mb;
    private JTextField url1, search;
    private int WIDTH = 800;
    private int HEIGHT = 700;

    public JTextArea results;
    public URL url;




//    public int depth = 0;
    private int maxDepth = 2;
//    public ArrayList<String> search = new ArrayList<>();
    public ArrayList<String> correct = new ArrayList<>();

    public int numLinks = 0;

    private String startName;
    private String endName;

    private String startUrl; //starting page
    private String endUrl; //ending page
    private String URL;

    public boolean complete = false;
    public boolean searching = false;

    public Code() {
        prepareGUI();
    }

    public static void main(String[] args) {
        Code layout = new Code();
        layout.showEventDemo();
    }

    private void urlSearch(){
        startName = url1.getText();
        endName = search.getText();

        startUrl = "https://en.wikipedia.org/wiki/" + startName; //starting page
        endUrl = "https://en.wikipedia.org/wiki/" + endName; //ending page
        URL = startUrl;
    }




    private void prepareGUI() {
        mainFrame = new JFrame("Java SWING Examples");
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setLayout(new GridLayout(3, 2));

        mb = new JMenuBar();

        url1 = new JTextField("Keanu_Reeves"); // remove "name" once finished *************

        search = new JTextField("Ryan_Reynolds"); // remove "name" once finished *************

        urlLabel = new JLabel("first person (typed: \"firstName_lastName\")", JLabel.CENTER);

        searchLabel = new JLabel("second person (typed: \"firstName_lastName\")", JLabel.CENTER);

        mainFrame.add(urlLabel);

        mainFrame.add(url1);

        mainFrame.add(searchLabel);

        mainFrame.add(search);

//        mainFrame.setJMenuBar(mb);

        headerLabel = new JLabel("", JLabel.CENTER);
        statusLabel = new JLabel("type names", JLabel.CENTER);
        statusLabel.setSize(350, 10);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        mainFrame.add(controlPanel);
        mainFrame.add(statusLabel);

//        JLabel title = new JLabel("results", JLabel.CENTER);
//        mainFrame.add(title);

//        JPanel panel = resultsPanel();
//        mainFrame.add(BorderLayout.CENTER, new JScrollPane(panel));

        mainFrame.setVisible(true);
    }

    public JPanel resultsPanel() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 1, 10, 10));

//        JLabel label = new JLabel("RESULTS:");

        results = new JTextArea();

//        panel.add(label);
        panel.add(results);

        return panel;
    }

    private void showEventDemo() {
        headerLabel.setText("Control in action: Button");

        JButton okButton = new JButton("search");

        okButton.setActionCommand("search");

        okButton.addActionListener(new ButtonClickListener());

        controlPanel.add(okButton);

        mainFrame.setVisible(true);
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
            statusLabel.setText("searching");

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

                            System.out.println(URL + " " + numLinks);

//                            results.setText(results.getText() + "https://en.wikipedia.org" + line.substring(start, end)+ "\n");

                            numLinks++;

                            if (recursion(URL, depth+1, maxDepth) == true) { // ***** make it so that it doesn't repeat...can't use a boolean, as it doesn't stop the recursion *****

                                correct.add(URL); // *** ADD CORRECT ROUTE URLs *** --- see if possible/required???

                                depth = 0; // ***** doesn't stop recursion...find some other way to stop *****

                                System.out.println("complete: " + URL + " " + depth);

                                // show URL history here?????

                                System.out.println("links searched: " + numLinks);

                                statusLabel.setText("links searched: " + numLinks);

                                System.out.println(correct); // **CHECK**

//                                complete = true; // ***** doesn't do anything *****

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

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("search")) {
                numLinks = 0;
                urlSearch();
                recursion(URL, 0, maxDepth);


                if (searching == true) { // **CONFIRM THAT THIS CODE WORKS**
                    statusLabel.setText("searching");
                }
                else{
                    statusLabel.setText("");
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
