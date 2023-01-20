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
    private JLabel searchLabel, depthLabel;
    private JLabel stopLabel;
    private JPanel controlPanel;
    private JMenuBar mb;
    private JTextField url1, search, depthSearch;
    private int WIDTH = 800;
    private int HEIGHT = 700;

    public JTextArea results;
    public URL url;




    public int depth = 0;
    private int maxDepth;

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
        maxDepth = Integer.parseInt(depthSearch.getText()) - 1;

        startUrl = "https://en.wikipedia.org/wiki/" + startName; //starting page
        endUrl = "https://en.wikipedia.org/wiki/" + endName; //ending page
        URL = startUrl;
    }




    private void prepareGUI() {
        mainFrame = new JFrame("Java SWING Examples");
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setLayout(new GridLayout(4, 2));

        mb = new JMenuBar();

        url1 = new JTextField("Keanu_Reeves"); // remove "name" once finished *************
        search = new JTextField("Ryan_Reynolds"); // remove "name" once finished *************
        depthSearch = new JTextField("3");

        urlLabel = new JLabel("first person (typed: \"firstName_lastName\")", JLabel.CENTER);
        searchLabel = new JLabel("second person (typed: \"firstName_lastName\")", JLabel.CENTER);
        depthLabel = new JLabel("max depth (recommended depth: 3)", JLabel.CENTER);

        mainFrame.add(urlLabel);
        mainFrame.add(url1);

        mainFrame.add(searchLabel);
        mainFrame.add(search);

        mainFrame.add(depthLabel);
        mainFrame.add(depthSearch);

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
        JButton stopButton = new JButton("stop");
        JButton quitButton = new JButton("quit");

        okButton.setActionCommand("search");
        stopButton.setActionCommand("stop");
        quitButton.setActionCommand("quit");

        okButton.addActionListener(new ButtonClickListener());
        stopButton.addActionListener(new ButtonClickListener());
        quitButton.addActionListener(new ButtonClickListener());


        controlPanel.add(okButton);
//        controlPanel.add(stopButton);
        controlPanel.add(quitButton);


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

                            searching = true;

                            System.out.println(URL + " " + numLinks);

//                            results.setText(results.getText() + "https://en.wikipedia.org" + line.substring(start, end)+ "\n");

                            numLinks++;

                            if (recursion(URL, depth+1, maxDepth) == true) {
                                    searching = false;
                                    correct.add(startUrl);
//                                    correct.add(URL);
                                    System.out.println("complete: " + URL + " " + depth);
                                    System.out.println("links searched: " + numLinks);
                                    statusLabel.setText(startName + " -> " + endName + "    links searched: " + numLinks);
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

//                if (recursion(URL, depth+1, maxDepth) == true) {
//                    searching = false;
//                    correct.add(startUrl);
//                    correct.add(URL);
//                    System.out.println("complete: " + URL + " " + depth);
//                    System.out.println("links searched: " + numLinks);
//                    statusLabel.setText(startName + " -> " + endName + "    links searched: " + numLinks);
//                }

                for (int x = maxDepth; x >=0; x--) {
                    System.out.println(correct.get(x));
                }

            }

            if (command.equals("stop")){
//                recursion(URL,3,maxDepth) = false;
            }

            if (command.equals("quit")){
                System.exit(0);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

// NEXT STEPS: fix end printing, add correct URLs to ArrayList Correct, make it so you can clear the console to make another search,...
