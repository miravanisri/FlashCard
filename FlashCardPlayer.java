package Flash.java;
import java.util.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.BorderLayout;
import javax.swing.*;

public class FlashCardPlayer {
    private JTextArea display;
    private JTextArea answer;
    private ArrayList<FlashCard> cardList;
    private Iterator<FlashCard> cardIterator;
    private FlashCard currentCard;
    private JFrame frame;
    private boolean isShowAnswer;

    public FlashCardPlayer() {
        frame = new JFrame("Flash Card Player");
        JPanel mainpanel = new JPanel();
        Font mfont = new Font("Helvetica Neue", Font.BOLD, 22);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        display = new JTextArea(10, 20);
        display.setFont(mfont);
        JScrollPane qjscrollpane = new JScrollPane(display);
        qjscrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qjscrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        JButton showAnswer = new JButton("Show Answer");
        mainpanel.add(qjscrollpane);
        mainpanel.add(showAnswer);

        // Initialize answer JTextArea
        answer = new JTextArea(10, 20);
        answer.setFont(mfont);
        mainpanel.add(answer);

        showAnswer.addActionListener(new NextCardListener());

        JMenuBar menubar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem loadMenuItem = new JMenuItem("Load Card Set");
        loadMenuItem.addActionListener(new OpenMenuListener());
        fileMenu.add(loadMenuItem);
        menubar.add(fileMenu);

        frame.setJMenuBar(menubar);
        frame.getContentPane().add(BorderLayout.CENTER, mainpanel);

        frame.setSize(640, 500);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FlashCardPlayer();
            }
        });
    }

    class NextCardListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isShowAnswer) {
                display.setText(currentCard.getAns());
                answer.setText(""); // Clear the answer text
                isShowAnswer = false;
            } else {
                if (cardIterator.hasNext()) {
                    showNextCard();
                } else {
                    display.setText("That was the last card");
                    answer.setEnabled(false);
                }
            }
        }
    }

    class OpenMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileopen = new JFileChooser();
            fileopen.showOpenDialog(frame);
            loadFile(fileopen.getSelectedFile());
        }
    }

    private void loadFile(File selectedFile) {
        cardList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
            String line;
            while ((line = reader.readLine()) != null) {
                makeCard(line);
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Couldn't open file");
            e.printStackTrace();
        }
        cardIterator = cardList.iterator();
        showNextCard();
    }

    private void showNextCard() {
        currentCard = cardIterator.next();
        display.setText(currentCard.getQ());
        answer.setText(""); // Clear the answer text
        isShowAnswer = true;
    }

    private void makeCard(String lineToParse) {
        String[] result = lineToParse.split("/");
        FlashCard card = new FlashCard(result[0], result[1]);
        cardList.add(card);
    }
}
