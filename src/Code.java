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
import java.awt.image.BufferStrategy;

public class Code implements ActionListener {

    public BufferStrategy bufferStrategy;

    private JFrame mainFrame;

    private JLabel headerLabel, statusLabel, urlLabel, searchLabel, depthLabel, resultsLabel, urlLabel2, searchLabel2;
    private JPanel controlPanel;
    private JMenuBar mb;
    private JTextField url1, search, depthSearch;

    private JLabel pic1, pic2, space;

    private int WIDTH = 1000;
    private int HEIGHT = 800;

    public JTextArea results;
    public URL url;

    public int depth = 0;
    private int maxDepth;

    public ArrayList<String> correct = new ArrayList<>();

    public int numLinks = 0;

    private String startName;
    private String endName;

    private String startUrl; //starting page
    private String endUrl; //ending page
    private String URL;

    public boolean complete = false;
    public boolean searching = false;
    public boolean name = true;

    public Image loadingPic;

    public Code() {
//        loadingPic = Toolkit.getDefaultToolkit().getImage("loading-gif.webp");

        prepareGUI();
//        render();
    }

    public static void main(String[] args) {
        Code layout = new Code();
        layout.showEventDemo();
    }

    private void urlSearch(){
        statusLabel.setText("searching");

        startName = url1.getText();
        endName = search.getText();
        maxDepth = Integer.parseInt(depthSearch.getText()) - 1;

        if (name == true) {
            startUrl = "https://en.wikipedia.org/wiki/" + startName; //starting page
            endUrl = "https://en.wikipedia.org/wiki/" + endName; //ending page
        }
        else{
            startUrl = startName;
            endUrl = endName;
        }

        URL = startUrl;
    }

    private JTextField randomSearchStart(){
            try { //HTML READ

                URL look = new URL("https://en.wikipedia.org/wiki/Main_Page");

                BufferedReader reader = new BufferedReader(new InputStreamReader(look.openStream()));

                String line;

                while ((line = reader.readLine()) != null) {

                    int start = line.indexOf("/wiki/");
                    int end = line.indexOf("\"", start);

                    if (line.contains("/wiki/") && !line.contains(":") && !line.contains("Main_Page") && !line.contains("Wikipedia")) {
                        startUrl = "https://en.wikipedia.org" + line.substring(start, end);
//                        System.out.println(startUrl);

//                        url1 = new JTextField(startUrl);
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        return url1;
    }




    private JTextField randomSearchEnd(){
            try { //HTML READ

                URL look = new URL("https://de.wikipedia.org/wiki/Wikipedia:Hauptseite");

                BufferedReader reader = new BufferedReader(new InputStreamReader(look.openStream()));

                String line;

                while ((line = reader.readLine()) != null) {

                    int start = line.indexOf("/wiki/");
                    int end = line.indexOf("\"", start);

                    if (line.contains("/wiki/") && !line.contains(":") && !line.contains("Main_Page") && !line.contains("Wikipedia") && !line.contains(startUrl)) {
                        endUrl = "https://en.wikipedia.org" + line.substring(start, end);
//                        System.out.println("** " + endUrl);

//                        search = new JTextField(endUrl);
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
            return search;
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Java SWING Examples");
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setLayout(new GridLayout(5, 2));
//        mainFrame.setLayout(new GridBagLayout());
//        GridBagConstraints c = new GridBagConstraints();
//        if(shouldFill){
//            c.fill = GridBagConstraints.HORIZONTAL;
//        }

        pic1 = new JLabel("image of start person");
        pic2 = new JLabel("image of end person");
        space = new JLabel("");

        mb = new JMenuBar();

        url1 = new JTextField();
        search = new JTextField();
        depthSearch = new JTextField("3");

        urlLabel = new JLabel("start (if person, type: \"firstName_lastName\"; if other, paste Wikipedia URL)", JLabel.CENTER);
        searchLabel = new JLabel("end (if person, type: \"firstName_lastName\"; if other, paste Wikipedia URL)", JLabel.CENTER);
        depthLabel = new JLabel("max depth (recommended depth: 3)", JLabel.CENTER);
        resultsLabel = new JLabel("url route:", JLabel.CENTER);

        mainFrame.add(urlLabel);
        mainFrame.add(url1);
//        mainFrame.add(pic1);

        mainFrame.add(searchLabel);
        mainFrame.add(search);
//        mainFrame.add(pic2);

        mainFrame.add(depthLabel);
        mainFrame.add(depthSearch);
//        mainFrame.add(space);

        headerLabel = new JLabel("", JLabel.CENTER);
        statusLabel = new JLabel("", JLabel.CENTER);
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


        mainFrame.add(resultsLabel);

        JPanel panel = resultsPanel();
        mainFrame.add(BorderLayout.CENTER, new JScrollPane(panel));

        mainFrame.setVisible(true);


//        canvas = new Canvas();
//        panel.add(canvas);
//        canvas.createBufferStrategy(2);
//        bufferStrategy = canvas.getBufferStrategy();
    }

    public JPanel resultsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 1, 10, 10));
        results = new JTextArea();
        panel.add(results);
        return panel;
    }

    private void showEventDemo() {
        headerLabel.setText("Control in action: Button");

        JButton nameButton = new JButton("search people");
        JButton stopButton = new JButton("stop");
        JButton quitButton = new JButton("quit");
        JButton otherButton = new JButton("search other");
        JButton randomButton = new JButton ("random search");

        nameButton.setActionCommand("search people");
        stopButton.setActionCommand("stop");
        quitButton.setActionCommand("quit");
        otherButton.setActionCommand("search other");
        randomButton.setActionCommand("random search");

        nameButton.addActionListener(new ButtonClickListener());
        stopButton.addActionListener(new ButtonClickListener());
        quitButton.addActionListener(new ButtonClickListener());
        otherButton.addActionListener(new ButtonClickListener());
        randomButton.addActionListener(new ButtonClickListener());


        controlPanel.add(nameButton);
        controlPanel.add(otherButton);
        controlPanel.add(randomButton);
//        controlPanel.add(stopButton);
        controlPanel.add(quitButton);


        mainFrame.setVisible(true);
    }

//    private void render() {
//
//        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
////        g.clearRect(0, 0, WIDTH, HEIGHT);
//        g.drawImage(loadingPic, 0, 0, 100, 100, null);
//        g.dispose();
//
//        bufferStrategy.show();
//    }

        public boolean recursion(String startUrl, int depth, int maxDepth) {
        // BASE CASE
        if (startUrl.equals(endUrl)) {
            if (depth <= maxDepth) {
                System.out.println("complete");
                System.out.println(correct);
            }
            return true;
        }
        else if (depth > maxDepth) {
            System.out.println("failed: " + depth);
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

                            System.out.println(URL + " " + numLinks); // DO NOT DELETE

                            numLinks++;

                            if (recursion(URL, depth+1, maxDepth) == true) {
                                    searching = false;
                                    correct.add(startUrl);
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

            if (command.equals("search people")) {

                correct.clear();

                name = true;

                numLinks = 0;
                urlSearch();
                recursion(URL, 0, maxDepth);

                statusLabel.setText(startName + " -> " + endName);

                System.out.println("-------------------------------------------------------------------------------------------------");

                for (int x = maxDepth; x >=0; x--) {
                    System.out.println(correct.get(x));
                    System.out.println("                       V");
                }

                results.setText("1) " + correct.get(2) + "\n" + "2) " + correct.get(1) + "\n" + "3) " + correct.get(0) + "\n" + "4) " + URL + "\n" + "\n" + "links searched: " + numLinks);

                System.out.println(URL);

                System.out.println("links searched: " + numLinks);
            }

            if (command.equals("search other")) {

                correct.clear();

                name = false;

                numLinks = 0;
                urlSearch();
                recursion(URL, 0, maxDepth);

                statusLabel.setText(startName + " -> " + endName);

                System.out.println("-------------------------------------------------------------------------------------------------");

                for (int x = maxDepth; x >=0; x--) {
                    System.out.println(correct.get(x));
                    System.out.println("                       V");
                }

                results.setText("1) " + correct.get(2) + "\n" + "2) " + correct.get(1) + "\n" + "3) " + correct.get(0) + "\n" + "4) " + URL + "\n" + "\n" + "links searched: " + numLinks);

                System.out.println(URL);

                System.out.println("links searched: " + numLinks);
            }

            if (command.equals("random search")){ //***NEED TO FIX SO THAT URLs WORK WITH RECURSION (SET startURL and endURL)***
                randomSearchStart();
                randomSearchEnd();
                correct.clear();
                name = false;
                recursion(URL, 0, maxDepth); //***BUG***

                System.out.println("-------------------------------------------------------------------------------------------------");

                for (int x = maxDepth; x >=0; x--) {
                    System.out.println(correct.get(x));
                    System.out.println("                       V");
                }

                results.setText("1) " + correct.get(2) + "\n" + "2) " + correct.get(1) + "\n" + "3) " + correct.get(0) + "\n" + "4) " + URL + "\n" + "\n" + "links searched: " + numLinks);

                System.out.println(URL);

                System.out.println("links searched: " + numLinks);
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
