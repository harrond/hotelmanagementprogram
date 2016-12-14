import javax.swing.*;
import javax.swing.JFrame;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.*;
import java.sql.Date;
import java.util.GregorianCalendar;

public class sendGuestQna {
	/*
	 *  This class is GUI for send Question to administer
	 *  Guest enter name, email, phone, title and question content
	 *  click button and send Question
	 *  @author SugilAhn(안수길)
	 */
	static JTextField name;           //For Guest's Name
	static JTextField email;          //For Guest's email
	static JTextField phone;          //For Guest's phone number
	static JTextField title;          //For Question title
	static JTextField question;       //For Question content
	static JFrame caFrame; 
	static Container pane;
	static JLabel jl[];
	static JButton jb;
	static JPanel pnl;
	static GregorianCalendar gc = new GregorianCalendar(); // For current time

	sendGuestQna() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (UnsupportedLookAndFeelException e) {
		}
		caFrame = new JFrame("Q n A"); //set Frame's name
		caFrame.setSize(500, 650);     //set Frame's size
		pane = caFrame.getContentPane();//add contentpane to JFrame
	}

	public static void guestQna() {
		pane.setLayout(null);
		Dimension frameSize = caFrame.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		caFrame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2); 
		//GUI window locate the center of my monitor screen 
		
	    /*
	     * initialize GUI objects
	     */
		name = new JTextField();
		email = new JTextField();
		phone = new JTextField();
		title = new JTextField();
		question = new JTextField();
		jl = new JLabel[5];
		jb = new JButton("Send");
		pnl = new JPanel(null);
		String[] caption = { "Name", "Email", "Phone", "Title", "Question" };
		jb.addActionListener(new send_Action());
		/*
		 * initialize GUI objects
		 */

		pane.add(pnl);
		for (int i = 0; i < 5; i++) {
			jl[i] = new JLabel();
			jl[i].setText(caption[i]);
			jl[i].setFont(new Font("Arial", Font.BOLD, 15));
			pnl.add(jl[i]);
		}
		pnl.add(name);
		pnl.add(email);
		pnl.add(phone);
		pnl.add(title);
		pnl.add(question);
		pnl.add(jb);
		// GUI object must be added on panel to be show
		pnl.setBounds(0, 0, 550, 650);
		for (int i = 0; i < 5; i++) {
			jl[i].setBounds(10, 10 + i * 40, 70, 30);
		}
		name.setBounds(100, 10, 350, 30);
		email.setBounds(100, 50, 350, 30);
		phone.setBounds(100, 90, 350, 30);
		title.setBounds(100, 130, 350, 30);
		question.setBounds(100, 170, 350, 350);
		jb.setBounds(215, 560, 70, 40);
		jb.setFont((new Font("Arial", Font.BOLD, 15)));
		//To set location of objects  
		caFrame.setResizable(false); // crFrame's size is not change
		caFrame.setVisible(true);    // crFrame visible set true

	}

	static class send_Action implements ActionListener {
		//when click send button
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == jb) {
				MysqlConnection sql = new MysqlConnection(); //To link mysql(DB)
				GuestDTO guestData = new GuestDTO();         //To import GuestData
				QuestionDTO qd = new QuestionDTO();          //To import Question
				qd.setTitle(title.getText());
				qd.setQuestion(question.getText());
				qd.setName(name.getText());
				qd.setDate(new Date(System.currentTimeMillis()));
				qd.setState(0);
				qd.setAnswer(null);
				//To store Question Data to QnADTO
				guestData.setName(name.getText());
				guestData.setEmail(email.getText());
				guestData.setPhone(phone.getText());
				//To store Guest info to GuestDTO
				sql.insertQuestion(qd);
				sql.insertGuest(guestData);
				//execute insert data
				caFrame.setVisible(false);
			}

		}
	}
}
