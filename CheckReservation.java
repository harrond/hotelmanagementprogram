import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class CheckReservation extends JFrame {
	/**
	 * The user can confirm the information that they are booked.
	 * Booking Status window is displayed and the check-in date booking
	 * information appears when you click on the appropriate date.
	 * @author SugilAhn(안수길)
	 */
	
	private static final long serialVersionUID = 1L;
	static JFrame crFrame;
	static JLabel[] roomInfo;
	static Container pane;
	static JPanel pnl;
	static JFormattedTextField RoomNum;  // For Room number
	static JFormattedTextField StartDay; // For Start Day or check-in day
	static JFormattedTextField EndDay;   // For End Day or check-out day
	static JFormattedTextField UserName; // For User's Name
	static JFormattedTextField service;  // For Room Service list 
	static JFormattedTextField Price;    // For reservation price
	static JFormattedTextField headcount;// For reservation headcount
	static BookingDTO bd = new BookingDTO(); // for check booking information
	static JList<String> jl = new JList<String>(); //print startday list
	static MysqlConnection booksql = new MysqlConnection(); //exchanging data on DB
	static Vector<String> vt = new Vector<String>();// for storing  start day list
	static JButton cancel;

	CheckReservation() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (UnsupportedLookAndFeelException e) {
		}
		crFrame = new JFrame("Check Reservation"); //set Frame's name
		crFrame.setSize(350, 500); // set Frame's size
		pane = crFrame.getContentPane(); //add content pane to crFrame for show GUI object
	}

	public static void checkingRev() throws SQLException {
		pane.setLayout(null); // locate pane 

		vt.clear(); //Vector must me clear on starting method
		String[] registerLabel = { "Select Date.", "Room", "Check_In", "Check_Out", "Name", "Price", "Service",
				"Headcount" };
		Dimension frameSize = crFrame.getSize();// FrameSize
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		crFrame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2); // 화면
		//GUI window locate the center of my monitor screen 																			
		vt = booksql.selectBookingDay(MainFrame.Data.getPhone());
		/*
		 * vt is stored day list 
		 */

		if (vt.isEmpty() == true) { //if vector is Empty
			JOptionPane.showMessageDialog(null, "예약정보가 없습니다.", "Alert", 0);
			crFrame.setVisible(false);
		} else { //if vector is not empty
			jl.setListData(vt);
			jl.addListSelectionListener(new selection_Action());
			pnl = new JPanel(null);
			pane.add(pnl);
			roomInfo = new JLabel[8];
			RoomNum = new JFormattedTextField();
			StartDay = new JFormattedTextField();
			EndDay = new JFormattedTextField();
			UserName = new JFormattedTextField();
			service = new JFormattedTextField();
			Price = new JFormattedTextField();
			headcount = new JFormattedTextField();
			// initialize GUI objects
			cancel = new JButton("Cancel");
			for (int i = 0; i < 8; i++) { // set roominfo's content
				roomInfo[i] = new JLabel();
				roomInfo[i].setText(registerLabel[i]);
			} 
			pnl.add(jl);
			pnl.add(RoomNum);
			pnl.add(StartDay);
			pnl.add(EndDay);
			pnl.add(UserName);
			pnl.add(service);
			pnl.add(Price);
			pnl.add(headcount);
			pnl.add(cancel); 
			// GUI object must be added on panel to be show
			for (int i = 0; i < 8; i++)
				pnl.add(roomInfo[i]); 

			pnl.setBounds(0, 0, 400, 600); //panel locates 0 , 0 
			                               // panel's width = 400 , height = 600
			roomInfo[0].setBounds(40, 10, 100, 30); //labels locate 
			roomInfo[0].setFont(new Font("Arial", Font.BOLD, 15)); // label's font setting
			for (int i = 1; i < 8; ++i) {
				roomInfo[i].setBounds(40, 60 + (i * 40), 100, 30);
				roomInfo[i].setFont(new Font("Arial", Font.BOLD, 15));
			}
			//Add location set the rest of the object
			jl.setBounds(150, 10, 150, 80);
			RoomNum.setBounds(150, 100, 150, 30);
			StartDay.setBounds(150, 140, 150, 30);
			EndDay.setBounds(150, 180, 150, 30);
			UserName.setBounds(150, 220, 150, 30);
			Price.setBounds(150, 260, 150, 30);
			service.setBounds(150, 300, 150, 30);
			headcount.setBounds(150, 340, 150, 30);
			cancel.setBounds(200, 380, 100, 30);
			cancel.setFont(new Font("Arial", Font.BOLD, 15));
			cancel.addActionListener(new btnCancel_Action());
			crFrame.setResizable(false); // crFrame's size is not change
			crFrame.setVisible(true); // crFrame visible set true
		}
	}

	static class selection_Action implements ListSelectionListener {
		// when select start day , start this buttonEvent
		public void valueChanged(ListSelectionEvent e) {
			int bid = booksql.selectBookingId(MainFrame.Data.getPhone(), jl.getSelectedValue());
			bd = booksql.selectBooking(bid);
			RoomNum.setValue(bd.getRoomNum());    // Room's number
			StartDay.setValue(bd.getStartDay());  // start day
			EndDay.setValue(bd.getEndDay());      // End day
			UserName.setValue(bd.getUserName());  // User's Name
			Price.setValue(bd.getPrice());        // Reservation Price
			service.setValue(bd.getService());    // Room service
			headcount.setValue(bd.getHeadcount());// Reservation headcount
		}
	}
	static class btnCancel_Action implements ActionListener {
		// when the button 'cancel' was clicked by user, start this button event
		public void actionPerformed(ActionEvent e) {
			crFrame.setVisible(false); // crFrame' visible set false
			JFrame cancelFrame = new JFrame("취소확인 비밀번호를 입력하세요"); // new JFrame that has its name "취소확인 비밀번호를 입력하세요"
			cancelFrame.setSize(400, 100); // Set Frame size
			JPasswordField passwd = new JPasswordField();
			JPanel cancelPanel = new JPanel(null);
			JButton cancelButton = new JButton("Accept");

			Dimension frameSize = cancelFrame.getSize();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

			cancelFrame.setLocation((screenSize.width - frameSize.width) / 2,
					(screenSize.height - frameSize.height) / 2);
			//GUI window locate the center of my monitor screen 

			cancelFrame.add(cancelPanel);
			cancelPanel.setSize(400, 100);
			cancelPanel.add(passwd);
			cancelPanel.add(cancelButton);

			passwd.setBounds(70, 20, 150, 30);
			cancelButton.setBounds(230, 20, 100, 30);
			cancelButton.setFont(new Font("Arial", Font.BOLD, 15));
			cancelFrame.setResizable(false);
			cancelFrame.setVisible(true);
			cancelButton.addActionListener(new ActionListener() {
				@SuppressWarnings("deprecation")
				public void actionPerformed(ActionEvent ae) {
					if (passwd.getText().equals(MainFrame.Data.getPassword())) {
						// if textfield(passwd)'s text equals user's password
						int bid = booksql.selectBookingId(MainFrame.Data.getPhone(), jl.getSelectedValue());
						booksql.deleteBooking(bid);
						JOptionPane.showMessageDialog(null, "취소가 완료되엇습니다.", "Alert", 0);
						cancelFrame.setVisible(false);
					} else {
						// if textfield(passwd)'s text doesn't equals user's password
						JOptionPane.showMessageDialog(null, "비밀번호를 다시 입력하세요", "Alert", 0);
					}

					try {
						checkingRev();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

		}
	}
}
