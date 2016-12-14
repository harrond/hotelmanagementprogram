import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.*;

import java.awt.event.*;
import java.sql.Date;

public class createAccount {
	/*
	 * This class is for the registration, and stores the member information in the DB
	 * Register while there is a duplicate check button and the id is compared with that stored in the DB.
	 * when submission button is clicked by customer, each TextField value is stored in the corresponding data DB.
	 * @author SugilAhn(안수길)
	 */
	static JFrame caFrame;
	static JLabel[] userInfo;
	static JLabel register;
	static Container pane;
	static JPanel pnl;
	static JTextField idField;                           //For user's id that is wanted by user
	static JTextField[] userInfoField;                   //For user's name, phone, email, birthday.
	static JPasswordField passwdField1;                  //For user's password
	static JTextArea info;                               //For user's information
	static JButton sub;                                  //For submission create account
	static JButton confirmRep;                           //For duplicate check
	static UserDTO UIFO = new UserDTO();                 //To store user Data
	static JRadioButton Male, Female;                    //For user's gender
	static ButtonGroup bg = new ButtonGroup();           
	static JScrollPane scrollbar;
	static MysqlConnection sql = new MysqlConnection();  //To link mysql
	// For user's birthday
	static JTextField year = new JTextField();          
	static JTextField month = new JTextField();
	static JTextField day = new JTextField();

	createAccount() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (UnsupportedLookAndFeelException e) {
		}
		caFrame = new JFrame("Create Account");
		caFrame.setSize(440, 600);
		pane = caFrame.getContentPane();
	}

	public static void makeRegister() {
		pane.setLayout(null);

		String[] registerLabel = { "ID", "Password", "Name", "Phone", "E-Mail", "BirthDay", "Info" };

		Dimension frameSize = caFrame.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
		caFrame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		//GUI window locate the center of my monitor screen 
		
		pnl = new JPanel(null);
		pane.add(pnl);
		register = new JLabel("Register");
		userInfo = new JLabel[7];
		idField = new JTextField(20);
		passwdField1 = new JPasswordField(20);
		userInfoField = new JTextField[4];
		Male = new JRadioButton("Male", true);
		Female = new JRadioButton("Female", false);
		//button group defalut value
		bg.add(Male);
		bg.add(Female);
		info = new JTextArea("특이 사항을 써주세요.");
		scrollbar = new JScrollPane(info);
		info.setLineWrap(true);
		sub = new JButton("Submission");
		confirmRep = new JButton("Confirm ID");
		//initialize GUI object
		
		sub.addActionListener(new btnSub_Action());
		confirmRep.addActionListener(new confirm_Action());
		for (int i = 0; i < 7; i++) {
			userInfo[i] = new JLabel();
			userInfo[i].setText(registerLabel[i]);
		}
		for (int i = 0; i < 3; ++i)
			userInfoField[i] = new JTextField(30);

		pnl.add(idField);
		pnl.add(confirmRep);
		pnl.add(passwdField1);
		pnl.add(year);
		pnl.add(month);
		pnl.add(day);
		pnl.add(scrollbar);
		pnl.add(register);
		pnl.add(sub);
		pnl.add(Male);
		pnl.add(Female);
		// GUI object must be added on panel to be show
		for (int i = 0; i < 7; i++)
			pnl.add(userInfo[i]);
		for (int i = 0; i < 3; ++i)
			pnl.add(userInfoField[i]);

		pnl.setBounds(0, 0, 400, 600);
		Male.setBounds(150, 70, 55, 30);
		Female.setBounds(205, 70, 70, 30);
		idField.setBounds(150, 100, 150, 30);
		confirmRep.setBounds(300, 100, 100, 30);
		passwdField1.setBounds(150, 140, 150, 30);
		register.setBounds(150, 30, 200, 30);
		scrollbar.setBounds(150, 340, 150, 120);
		year.setBounds(150, 300, 65, 30);
		month.setBounds(220, 300, 38, 30);
		day.setBounds(263, 300, 38, 30);
		sub.setBounds(130, 500, 130, 30);
		//to set object's location 
		for (int i = 0; i < 3; ++i)
			userInfoField[i].setBounds(150, 180 + (i * 40), 150, 30);

		for (int i = 0; i < 7; ++i) {
			userInfo[i].setBounds(40, 100 + (i * 40), 150, 30);
			userInfo[i].setFont(new Font("Arial", Font.BOLD, 15));
		}
		register.setFont(new Font("Arial", Font.BOLD, 25));
		sub.setFont(new Font("Arial", Font.BOLD, 15));
		confirmRep.setFont(new Font("Arial", Font.BOLD, 15));
		caFrame.setResizable(false);  // crFrame's size is not change
		caFrame.setVisible(true);     // crFrame visible set true

	}

	static class btnSub_Action implements ActionListener {
		// when click submission button
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == sub) { 
				@SuppressWarnings("deprecation")
				Date date = new Date(Integer.valueOf(year.getText())-1990, Integer.valueOf(month.getText())-1,Integer.valueOf(day.getText())); 
				//The Date's original type is 'd = new Date(year - 1900, month - 1, day);
				UIFO.setUserId(idField.getText());
				UIFO.setPassword(passwdField1.getText());
				UIFO.setName(userInfoField[0].getText());
				UIFO.setBirth(date);
				UIFO.setEmail(userInfoField[2].getText());
				UIFO.setPhone(userInfoField[1].getText());
				UIFO.setInfo(info.getText());
				UIFO.setUserGrade("0");
				UIFO.setVisit(0);
				if (Male.isSelected() == true) {
					UIFO.setSex("M");
				} else if (Female.isSelected() == true) {
					UIFO.setSex("F");
				}
				sql.insertUser(UIFO);
				//each TextField value store userDTO object
				//and then execute query on mysql
				caFrame.setVisible(false);
				emailSystem Es = new emailSystem();
				Es.sendEmail("hotel@hotel.com", userInfoField[2].getText(), "회원가입을 환영합니다.",
						userInfoField[0].getText() + "님의 회원가입을 축하합니다.");
				//when success create account, send the email
			}
		}
	}

	static class confirm_Action implements ActionListener {
		//when click duplicate check button
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == confirmRep) {
				int i = 0;
				boolean temp = false; //For print fail massage
				UIFO.setUserId(idField.getText().trim());
				if (idField.getText().length() == 0) { //when TextField's value is null, print alert massage
					JOptionPane.showMessageDialog(null, "아이디를 입력하세요.", "중복확인", 2);
				} else {
					for (i = 0; i < sql.checkId().size(); ++i) {
						if (sql.checkId().get(i).equals(idField.getText())) { // if idField's value equal id data in DB.
							temp = false;
							break;
						} else {
							temp = true;
							continue;
						}
					}
					if (temp == true) { //duplicate check is true(success)
						JOptionPane.showMessageDialog(null, "사용가능한 아이디 입니다.", "중복확인", 1); 
					} else {//duplicate check is false(fail)
						JOptionPane.showMessageDialog(null, "중복된 아이디 입니다.", "중복확인", 2);
						idField.setText(""); //if duplicate id is fail, idField is clear.
					}
				}
			}
		}
	}
}
