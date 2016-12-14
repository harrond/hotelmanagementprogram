import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.*;


import java.awt.event.*;
import java.sql.Date;
import java.sql.SQLException;

public class modifyUserInfo {
	/*
	 * this class is GUI window to modify user information
	 * first user click modify user info button, then modify the imported data to the data modified.
	 * last when click accept button, it is stored in DB. 
	 * @author SugilAhn(안수길)
	 */
	static JFrame caFrame;
	static JLabel[] userInfo;
	static JLabel modify;
	static Container pane;
	static JPanel pnl;
	static JTextField idField;                            //For user Id
	static JTextField[] userInfoField;                    //For lest of the information
	static JPasswordField passwdField1;                   //For user password
	static JTextArea info;                                //For user information
	static JButton sub;                                   //To modify user Data
	static UserDTO UIFO = new UserDTO();                  //To modify user Data
	static JRadioButton Male, Female;                     //For user gender
	static ButtonGroup bg = new ButtonGroup();
	static JScrollPane scrollbar;
	static MysqlConnection sql = new MysqlConnection();
	static JTextField year = new JTextField();            //For user's birthday
	static JTextField month = new JTextField();
	static JTextField day = new JTextField();

	modifyUserInfo() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (UnsupportedLookAndFeelException e) {
		}
		caFrame = new JFrame("Modify Account Information");
		caFrame.setSize(440, 600);
		pane = caFrame.getContentPane();
	}

	@SuppressWarnings("deprecation")
	public static void modifyRegister() {
		pane.setLayout(null);

		String[] registerLabel = { "ID", "Password", "Name", "Phone", "E-Mail", "BirthDay", "Info" };

		Dimension frameSize = caFrame.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
		caFrame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2); 
		//GUI window locate the center of my monitor screen 
		
		/*
		 * initialize GUI objects
		 */
		pnl = new JPanel(null);
		pane.add(pnl);
		modify = new JLabel("Modify");
		userInfo = new JLabel[7];
		idField = new JTextField(20);
		passwdField1 = new JPasswordField(20);
		userInfoField = new JTextField[4];
		Male = new JRadioButton("Male");
		Female = new JRadioButton("Female");
		if (MainFrame.Data.getSex().equals("M")) {
			Male = new JRadioButton("Male",true);
		} 
		else
		{
			Female = new JRadioButton("Female",true);
		}
		bg.add(Male);
		bg.add(Female);
		info = new JTextArea(MainFrame.Data.getInfo());
		scrollbar = new JScrollPane(info);
		info.setLineWrap(true);
		sub = new JButton("Accept");
		/*
		 * initialize GUI objects
		 */
		sub.addActionListener(new btnmod_Action()); // To modify event
		for (int i = 0; i < 7; i++) {
			userInfo[i] = new JLabel();
			userInfo[i].setText(registerLabel[i]);
		} //To set label text
		for (int i = 0; i < 3; ++i)
			userInfoField[i] = new JTextField(30);
		  //To initialize userinfo

		pnl.add(idField);
		pnl.add(passwdField1);
		pnl.add(year);
		pnl.add(month);
		pnl.add(day);
		pnl.add(scrollbar);
		pnl.add(modify);
		pnl.add(sub);
		pnl.add(Male);
		pnl.add(Female);
		// GUI object must be added on panel to be show
		
		for (int i = 0; i < 7; i++)
			pnl.add(userInfo[i]);
		for (int i = 0; i < 3; ++i)
			pnl.add(userInfoField[i]);
		// GUI object must be added on panel to be show
		
		pnl.setBounds(0, 0, 400, 600);
		Male.setBounds(150, 70, 55, 30);
		Female.setBounds(205, 70, 70, 30);
		idField.setBounds(150, 100, 150, 30);
		passwdField1.setBounds(150, 140, 150, 30);
		modify.setBounds(150, 30, 200, 30);
		scrollbar.setBounds(150, 340, 150, 120);
		year.setBounds(150, 300, 65, 30);
		month.setBounds(220, 300, 38, 30);
		day.setBounds(263, 300, 38, 30);
		sub.setBounds(130, 500, 130, 30);

		for (int i = 0; i < 3; ++i)
			userInfoField[i].setBounds(150, 180 + (i * 40), 150, 30);

		for (int i = 0; i < 7; ++i) {
			userInfo[i].setBounds(40, 100 + (i * 40), 150, 30);
			userInfo[i].setFont(new Font("Arial", Font.BOLD, 15));
		}
		modify.setFont(new Font("Arial", Font.BOLD, 25));
		sub.setFont(new Font("Arial", Font.BOLD, 15));

		idField.setText(MainFrame.Data.getUserId());
		idField.setEditable(false);
		passwdField1.setText(MainFrame.Data.getPassword());
		Integer yearValue = (MainFrame.Data.getBirth().getYear() + 1990);
		year.setText(yearValue.toString());
		Integer monthValue = (MainFrame.Data.getBirth().getMonth() + 1);
		month.setText(monthValue.toString());
		Integer dayValue = (MainFrame.Data.getBirth().getDate());
		day.setText(dayValue.toString());
		userInfoField[0].setText(MainFrame.Data.getName());
		userInfoField[1].setText(MainFrame.Data.getPhone());
		userInfoField[2].setText(MainFrame.Data.getEmail());//To set location of objects  
		caFrame.setResizable(false); // crFrame's size is not change
		caFrame.setVisible(true);    // crFrame visible set true
	}

	static class btnmod_Action implements ActionListener {
		//when click modify button
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == sub) {
				Date date = new Date(Integer.valueOf(year.getText()) - 1900, Integer.valueOf(month.getText()) - 1,
						Integer.valueOf(day.getText()));
				//The Date's original type is 'd = new Date(year - 1900, month - 1, day);
				
				UIFO.setUserId(idField.getText());
				UIFO.setPassword(passwdField1.getText());
				UIFO.setName(userInfoField[0].getText());
				UIFO.setBirth(date);
				UIFO.setEmail(userInfoField[2].getText());
				UIFO.setPhone(userInfoField[1].getText());
				UIFO.setInfo(info.getText());
				UIFO.setUserGrade(MainFrame.Data.getUserGrade());
				UIFO.setVisit(MainFrame.Data.getVisit());
				// Set the modified data object to userDTO
				if (Male.isSelected() == true) {
					UIFO.setSex("M");
				} else if (Female.isSelected() == true) {
					UIFO.setSex("F");
				}
				try {
					sql.updateUser(UIFO); // execute update query
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				caFrame.setVisible(false);
			}
		}
	}
}
