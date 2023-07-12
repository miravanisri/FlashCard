package Flash.java;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument.Iterator;

public class FlashCardBulider {
	private JTextArea question;
	private JTextArea answer;
	private ArrayList<FlashCard> cardlist;
	private JFrame frame; 
	public FlashCardBulider()
	{
		frame=new JFrame("Flash Card");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel=new JPanel();
	    Font gfont=new Font("Helvetica Neue",Font.BOLD,21);
	    question=new JTextArea(6,20);
	    question.setLineWrap(true);
	    question.setWrapStyleWord(true);
	    question.setFont(gfont);
	    answer=new JTextArea(6,20);
	    answer.setLineWrap(true);
	    answer.setWrapStyleWord(true);
	    answer.setFont(gfont);
	    JScrollPane p=new JScrollPane(question);
	    p.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    p.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    JScrollPane p1=new JScrollPane(answer);
	    p1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    p1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    JButton nextbutton =new JButton("Next Card");
	    cardlist=new ArrayList<FlashCard>();
	    JLabel qlabel=new JLabel("Question");
	    JLabel alabel=new JLabel("Answer");
	    panel.add(qlabel);
	    panel.add(p);
	    panel.add(alabel);
	    panel.add(p1);
	    panel.add(nextbutton);
	    nextbutton.addActionListener(new NextCardListener());
	    JMenuBar t1=new JMenuBar();
	    JMenu t2=new JMenu("File");
	    JMenuItem t3=new JMenuItem("New");
	    JMenuItem t4=new JMenuItem("save");
	    t2.add(t3);
	    t2.add(t4);
	    t1.add(t2);
	    frame.setJMenuBar(t1);
	    t3.addActionListener(new NewListener());
	    t4.addActionListener(new SaveListener());
	    
	    
	    frame.getContentPane().add(BorderLayout.CENTER,panel);
	    frame.setSize(500, 500);
	    frame.setVisible(true);
	    
	    
	    
	    
	    
	    
	    
		
		
	}
	

	public static void main(String[] args) {
		
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				new FlashCardBulider();
			}
		});
		

	}
	class NextCardListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			FlashCard card=new FlashCard(question.getText(),answer.getText());
			cardlist.add(card);
			System.out.print("size:"+cardlist.size());
			clearcard();
			
		}

		
	}
	class NewListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	class SaveListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			FlashCard card=new FlashCard(question.getText(),answer.getText());
			cardlist.add(card);
			//create file dialog box
			JFileChooser filesave=new JFileChooser();
			filesave.showSaveDialog(frame);
			savefile(filesave.getSelectedFile());
			
			
		
			
		}

		
	}
	private void clearcard() {
		question.setText("");
		answer.setText("");
		question.requestFocus();
		
		
		
		
	}
	private void savefile(File selectedFile) {
		try {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile))) {
				java.util.Iterator<FlashCard> cardIterator=cardlist.iterator();
				while(cardIterator.hasNext())
				{
					FlashCard card=(FlashCard)cardIterator.next();
					writer.write(card.getQ()+"/");
					writer.write(card.getAns()+"\n");
					
					
					
				}
			}
			
			
		}
		catch(Exception e)
		{
			System.out.println("couldn't open file");
			e.printStackTrace();
			
			
		}
		
	}
	
	

}
