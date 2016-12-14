import javax.swing.*;
import javax.swing.JFrame;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.*;
import java.sql.Date;
import java.util.GregorianCalendar;

public class sendQna {
	/*
	 *  This class is GUI for send Question to administer
	 *  user write title and content. then send Email to use EmailSystem class.
	 *  click button and send Question
	 *  @author SugilAhn(안수길)
	 */
	static JTextField title;                                  //For Question title
	static JTextField question;                               //For Question content
	static JFrame caFrame;
	static Container pane;
	static JLabel jl[];
	static JPanel jp[];
	static JButton jb;
	static JPanel pnl;
	static UserDTO Data;
	static GregorianCalendar gc = new GregorianCalendar();     // For current time

	sendQna() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (UnsupportedLookAndFeelException e) {
		}
		caFrame = new JFrame("Q n A");    //set Frame's name
		caFrame.setSize(500, 650);        //set Frame's size
		pane = caFrame.getContentPane();  //add contentpane to JFrame
	}

	public static void userQna() {
		pane.setLayout(null);
		Dimension frameSize = caFrame.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		caFrame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2); 
		//GUI window locate the center of my monitor screen 
		
		/*
		 * initialize GUI objects
		 */		
		title = new JTextField();
		question = new JTextField();
		jl = new JLabel[2];
		jb = new JButton("Send");
		pnl = new JPanel(null);
		String[] caption = { "Title", "Question" };
		/*
		 * initialize GUI objects
		 */	
		jb.addActionListener(new send_Action());

		pane.add(pnl);
		for (int i = 0; i < 2; i++) {
			jl[i] = new JLabel();
			jl[i].setText(caption[i]);
			jl[i].setFont(new Font("Arial", Font.BOLD, 15));
			pnl.add(jl[i]);
		}
		pnl.add(title);
		pnl.add(question);
		pnl.add(jb);
		// GUI object must be added on panel to be show
		pnl.setBounds(0, 0, 550, 650);
		for (int i = 0; i < 2; i++) {
			jl[i].setBounds(10, 10 + i * 40, 70, 30);
		}
		title.setBounds(100, 10, 350, 30);
		question.setBounds(100, 50, 350, 500);
		jb.setBounds(215, 560, 70, 40);
		jb.setFont((new Font("Arial", Font.BOLD, 15)));
		//To set location of objects
		caFrame.setResizable(false);// crFrame's size is not change
		caFrame.setVisible(true);    // crFrame visible set true

	}

	static class send_Action implements ActionListener {
		//when click send button
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == jb) {
				MysqlConnection sql = new MysqlConnection();   //To link mysql(DB)
				QuestionDTO qd = new QuestionDTO();            //To import QuestionData
				qd.setTitle(title.getText());
				qd.setQuestion(question.getText());
				qd.setUserId(MainFrame.Data.getUserId());
				System.out.println(MainFrame.Data.getUserId());
				qd.setDate(new Date(System.currentTimeMillis()));
				qd.setState(0);
				qd.setAnswer(null);
				//To store Question Data to QnADTO
				sql.insertQuestion(qd);
				//execute insert data
				caFrame.setVisible(false);
			}

		}
	}
}
