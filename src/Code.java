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

    private String startName = "Keanu_Reeves";
    private String endName = "Ryan_Reynolds";
//    private String startName = url1.getText();
//    private String endName = search.getText();

    private String startUrl = "https://en.wikipedia.org/wiki/" + startName; //starting page
    private String endUrl = "https://en.wikipedia.org/wiki/" + endName; //ending page
    public String URL = startUrl;

    public Code() {
        prepareGUI();

//        recursion(URL, 0, maxDepth);
    }

    public static void main(String[] args) {
        Code layout = new Code();
        layout.showEventDemo();
    }



    private void prepareGUI() {
        mainFrame = new JFrame("Java SWING Examples");
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setLayout(new GridLayout(7, 1));

        mb = new JMenuBar();

        url1 = new JTextField();

        search = new JTextField();

        urlLabel = new JLabel("first person (typed: \"firstName_lastName\")", JLabel.CENTER);

        searchLabel = new JLabel("second person (typed: \"firstName_lastName\")", JLabel.CENTER);

        mainFrame.add(urlLabel);

        mainFrame.add(url1);

        mainFrame.add(searchLabel);

        mainFrame.add(search);

        mainFrame.setJMenuBar(mb);

        headerLabel = new JLabel("", JLabel.CENTER);
        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setSize(350, 100);

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

        JPanel panel = resultsPanel();
        mainFrame.add(BorderLayout.CENTER, new JScrollPane(panel));

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

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("search")) {
                recursion(URL, 0, maxDepth);
                statusLabel.setText("searching");
            }
        }
    }

//    public String HtmlRead(String startUrl, int depth){
//        try {
//
//            URL look = new URL(startUrl);
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(look.openStream()));
//
//            String line;
//
//            while ((line = reader.readLine()) != null) {
//
//                int start = line.indexOf("https");
//                int end = line.indexOf("\"", start);
//
//                if(line.contains("https://en.wikipedia.org/")) {
////                    if (!line.contains("Ryan_Reynolds")) {
//                    System.out.println(line.substring(start, end));
//
//                    if (!URL.contains("Ryan_Reynolds")) {
//                        search.add(new String(line.substring(start, end)));
//                        URL = line.substring(start, end);
//                        recursion(URL, depth+1, maxDepth);
//                    }
////                                    System.out.println(URL);
//                }
////                    }
//
//            }
//        }
//        catch(Exception ex) {
//            System.out.println(ex);
//        }
//
//        return URL;
//    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
