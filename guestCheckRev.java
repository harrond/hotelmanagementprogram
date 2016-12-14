import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class guestCheckRev extends JFrame {
	/**
	 *  Also The Guest can confirm the information that they are booked.
	 *  Guest can check their information by entering a phone number.
	 *  @author SugilAhn(안수길)
	 */
	private static final long serialVersionUID = 1L;
	static JFrame crFrame;
	static JLabel[] roomInfo;
	static JLabel jl;
	static JButton confirmName;
	static JButton cancel;
	static Container pane;
	static JPanel pnl;
	static JTextField phone;                              //To confirm booking Data by phone
	static JFormattedTextField name;                      //For Booking Data's Guest Name
	static JFormattedTextField RoomNum;                   //For Booking Data's Room Number
	static JFormattedTextField StartDay;                  
	static JFormattedTextField EndDay;
	//For Booking Day
	static JFormattedTextField service;                   //For Booking Data's service
	static JFormattedTextField Price;                     //For Booking Price
	static JFormattedTextField headcount;                 //For Booking Data's headcount
	static BookingDTO bd = new BookingDTO();              //For import Booking Data
	static MysqlConnection sql = new MysqlConnection();   //To link mysql

	guestCheckRev() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (UnsupportedLookAndFeelException e) {
		}
		crFrame = new JFrame("Check Reservation");
		crFrame.setSize(350, 500);
		pane = crFrame.getContentPane();
	}

	public static void checkingRev() {
		pane.setLayout(null);

		String[] registerLabel = { "Phone", "Room", "Name", "Check_In", "Check_Out", "Price", "Service", "Headcount" };

		Dimension frameSize = crFrame.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
		crFrame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2); 
		//GUI window locate the center of my monitor screen 
		
		/*
		 * initialize GUI objects
		 */
		pnl = new JPanel(null);
		pane.add(pnl);
		roomInfo = new JLabel[8];
		jl = new JLabel("Enter your phone number.");
		name = new JFormattedTextField();
		phone = new JTextField();
		RoomNum = new JFormattedTextField();
		StartDay = new JFormattedTextField();
		EndDay = new JFormattedTextField();
		service = new JFormattedTextField();
		Price = new JFormattedTextField();
		headcount = new JFormattedTextField();
		confirmName = new JButton("Accept");
		confirmName.setFont(new Font("Arial", Font.BOLD, 15));
		confirmName.addActionListener(new btnFind_Action());
		cancel = new JButton("Cancel");
		cancel.setFont(new Font("Arial", Font.BOLD, 15));
		cancel.addActionListener(new btnCancel_Action());
		jl.setFont(new Font("Arial", Font.BOLD, 15));
		/*
		 * initialize GUI objects
		 */
		
		for (int i = 0; i < 8; i++) {
			roomInfo[i] = new JLabel();
			roomInfo[i].setText(registerLabel[i]);
		}
		pnl.add(confirmName);
		pnl.add(cancel);
		pnl.add(jl);
		pnl.add(name);
		pnl.add(phone);
		pnl.add(RoomNum);
		pnl.add(StartDay);
		pnl.add(EndDay);
		pnl.add(service);
		pnl.add(Price);
		pnl.add(headcount);
		// GUI object must be added on panel to be show
		
		for (int i = 0; i < 8; i++)
			pnl.add(roomInfo[i]);

		pnl.setBounds(0, 0, 400, 600);

		for (int i = 0; i < 8; ++i) {
			roomInfo[i].setBounds(40, 60 + (i * 40), 100, 30);
			roomInfo[i].setFont(new Font("Arial", Font.BOLD, 15));
		}
		//
		jl.setBounds(85, 10, 300, 30);
		phone.setBounds(150, 60, 150, 30);
		RoomNum.setBounds(150, 100, 150, 30);
		name.setBounds(150, 140, 150, 30);
		StartDay.setBounds(150, 180, 150, 30);
		EndDay.setBounds(150, 220, 150, 30);
		Price.setBounds(150, 260, 150, 30);
		service.setBounds(150, 300, 150, 30);
		headcount.setBounds(150, 340, 150, 30);
		confirmName.setBounds(100, 380, 100, 30);
		cancel.setBounds(210, 380, 100, 30);
		//To set location of objects  
		crFrame.setResizable(false); // crFrame's size is not change
		crFrame.setVisible(true);    // crFrame visible set true

	}

	static class btnFind_Action implements ActionListener {
		//when click button later entering phone number, 
		public void actionPerformed(ActionEvent e) {
			int i = 1;			
			if (phone.getText().equals("")) { //textfield for phone is empty
				JOptionPane.showMessageDialog(null, "전화번호를 입력하세요", "Alert", 0);
			} else {
				while(sql.selectBooking(i)==null) //find DB data stored information
				{		
					i++;				
				}
				if(sql.selectBooking(i)!=null){   //for loop Data stored information 
					bd = sql.selectBooking(i);
					while ((bd).getPhoneNumber()!=null) {
						
						if (bd.getPhoneNumber().equals(phone.getText())) { //To compare Db data and phone value
							break;
						} else {
							i++;
						}
						bd = sql.selectBooking(i);
						if(bd==null) break;
					}
				}

				 
				if (bd == null) { // When no value is stored in the DB
					JOptionPane.showMessageDialog(null, "예약 정보가 없습니다.", "Alert", 0);
				} else {
					RoomNum.setValue(bd.getRoomNum());
					name.setValue(bd.getGuestName());
					StartDay.setValue(bd.getStartDay());
					EndDay.setValue(bd.getEndDay());
					Price.setValue(bd.getPrice());
					service.setValue(bd.getService());
					headcount.setValue(bd.getHeadcount());
					//print that Booking Data
				}
			}
		}
	}



	static class btnCancel_Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFrame cancelFrame = new JFrame("취소확인 비밀번호를 입력하세요");
			cancelFrame.setSize(400, 100);
			JPasswordField passwd = new JPasswordField();
			JPanel cancelPanel = new JPanel(null);
			JButton cancelButton = new JButton("Accept");

			Dimension frameSize = cancelFrame.getSize();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

			cancelFrame.setLocation((screenSize.width - frameSize.width) / 2,
					(screenSize.height - frameSize.height) / 2);

			cancelFrame.add(cancelPanel);
			cancelPanel.setSize(400, 100);
			cancelPanel.add(passwd);
			cancelPanel.add(cancelButton);

			passwd.setBounds(70, 20, 150, 30);
			cancelButton.setBounds(230, 20, 100, 30);
			cancelButton.setFont(new Font("Arial", Font.BOLD, 15));
			cancelFrame.setResizable(false);
			if (phone.getText().equals("")) {
				cancelFrame.setVisible(false);
			} else {
				cancelFrame.setVisible(true);
			}
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					if (passwd.getText().equals(sql.selectGuest(bd.getPhoneNumber()).getPassword())) {
						System.out.print(bd.getId());
						sql.deleteBooking(bd.getId());
						JOptionPane.showMessageDialog(null, "취소가 완료되엇습니다.", "Alert", 0);
						cancelFrame.setVisible(false);
					} else {
						JOptionPane.showMessageDialog(null, "비밀번호를 다시 입력하세요", "Alert", 0);
					}
				}
			});

		}
	}
}