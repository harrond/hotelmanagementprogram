import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
/**
 * 클래스 명 : Reservation
 * 기능 : 예약을 하는 기능을 가진다.
 * @author HoyoungJI(지호영)
 *
 */
public class Reservation {

	// Make the basic Interface.
	static JFrame MyFrame ; //
	static JPanel MyPanel1 ; //
	static JPanel MyPanel2 ; //
	static JTextField name,phone,price,email; //
	static JPasswordField password; //
	static JComboBox<Integer> room,selectCiY,selectCiM,selectCiD,selectCoY,selectCoM,selectCoD,headcount; //
	private static MysqlConnection db; //
	static UserDTO user; //
	static RoomDTO tempRoom; //
	static GregorianCalendar cal; //
	static Date sDay,eDay;
	static int realYear, realMonth,realDay,sCinyear,sCoutyear; //
	static ArrayList<BookingDTO> list;
	
	// basic constructor
	public Reservation(){
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
        catch (ClassNotFoundException e) {}
        catch (InstantiationException e) {}
        catch (IllegalAccessException e) {}
        catch (UnsupportedLookAndFeelException e) {}
		db = db.getDbcom();
	}
	
	/*
	 * �޼ҵ� �� : makeReservationFrame
	 * ��� : ���� ȭ���� �Խ�Ʈ���,����ڸ��� ����� �����ش�.
	 */
	static void makeReservationFrame(String userid){
		BookingDTO temp = new BookingDTO();

		// basic Frame
		MyFrame = new JFrame("Reserviation");
		MyPanel1 = new JPanel();
		MyPanel2 = new JPanel();
		
		// Make the choosed box for reservation.
		room = new JComboBox();
		selectCiY = new JComboBox();
		selectCiM = new JComboBox();
		selectCiD = new JComboBox();
		selectCoY = new JComboBox();
		selectCoM = new JComboBox();
		selectCoD = new JComboBox();
		headcount = new JComboBox();
		
		// Make the inserted box for reservation.
		name = new JTextField();
		phone = new JTextField();
		price = new JTextField();
		email = new JTextField();
		password = new JPasswordField();

		// select location of frame
		MyFrame.setLayout(null);
		MyFrame.setSize(700,450);
		
		MyFrame.add(MyPanel1);
		MyFrame.add(MyPanel2);
		
		Dimension frameSize = MyFrame.getSize();// size of frame
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // size of my computer screen ������
		MyFrame.setLocation((screenSize.width-frameSize.width)/2,(screenSize.height-frameSize.height)/2); //ȭ�� �߾�
		
		MyPanel1.setBounds(0, 0, 200, 500);
		MyPanel1.setLayout(null);
		MyPanel1.setBackground(new Color(150, 200, 205));
		MyPanel2.setBounds(200, 0, 500, 500);
		MyPanel2.setLayout(null);
		MyPanel2.setBackground(new Color(215, 252, 231));

		// create and locate shown string information to screen
		JButton reservation = new JButton("Reservation");
		JLabel roomLabel = new JLabel("  Choose the Room");
		JLabel checkinLabel = new JLabel("Check In");
		JLabel checkoutLabel = new JLabel("Check Out");
		JLabel nameLabel = new JLabel("Name");
		JLabel phoneLabel = new JLabel("Phone Number");
		JLabel emailLabel = new JLabel("E-mail");
		JLabel priceLabel = new JLabel("Price");
		JLabel hcLabel = new JLabel("HeadCount");
		JLabel pwd = new JLabel("Guest Password");
		
		MyPanel2.add(roomLabel);
		MyPanel2.add(checkinLabel);
		MyPanel2.add(checkoutLabel);
		MyPanel2.add(nameLabel);		
		MyPanel2.add(phoneLabel);
		MyPanel2.add(emailLabel);
		MyPanel2.add(priceLabel);
		MyPanel2.add(hcLabel);
		MyPanel2.add(pwd);

		roomLabel.setBounds(30, 10, 130, 30);
		checkinLabel.setBounds(65, 50, 130, 30);
		checkoutLabel.setBounds(65, 90, 130, 30);
		nameLabel.setBounds(70, 130, 70, 30);
		phoneLabel.setBounds(50, 170, 100, 30);
		priceLabel.setBounds(80, 250, 250, 30);
		emailLabel.setBounds(70, 210, 100, 30);
		
		// create and locate must selected information to screen
		MyPanel2.add(room);
		MyPanel2.add(selectCiY);
		MyPanel2.add(selectCiM);
		MyPanel2.add(selectCiD);
		MyPanel2.add(selectCoY);
		MyPanel2.add(selectCoM);
		MyPanel2.add(selectCoD);
		MyPanel2.add(headcount);
		
		room.setBounds(180, 10, 250, 30);
		selectCiY.setBounds(180, 50, 65, 30);
		selectCiM.setBounds(260, 50, 55, 30);
		selectCiD.setBounds(325, 50, 55, 30);
		selectCoY.setBounds(180, 90, 65, 30);
		selectCoM.setBounds(260, 90, 55, 30);
		selectCoD.setBounds(325, 90, 55, 30);
		headcount.setBounds(343,250,85,30);
		
		
		// create and locate must entered information to screen
		MyPanel2.add(name);
		MyPanel2.add(phone);
		MyPanel2.add(price);
		MyPanel2.add(email);
		MyPanel2.add(password);
		
		name.setBounds(180, 130, 250, 30);
		phone.setBounds(180, 170, 250, 30);
		price.setBounds(180, 250, 85, 30);
		email.setBounds(180, 210, 250, 30);
		
		price.setEnabled(false);
		
		MyPanel2.add(reservation);
		
		reservation.setBounds(150, 335, 200, 50);
		
		// if userid exists, user-mode access. fill in the user-information.
		if(userid!=""){
			user = db.selectUserId(userid);
			name.setText(user.getName());
			phone.setEditable(false);
			phone.setText(user.getPhone());
			email.setText(user.getEmail());
		}
		else { // if userid not exists, guest-mode access, add must entered the guest_password block 
			pwd.setBounds(50,290,100,30);
			password.setBounds(180, 290, 250, 30);
		}
		
		// entire room
		for(int i=100;i<600;i=i+100){
			for(int j=1;j<6;++j){
				room.addItem(i+j);
			}
		}
		
		cal = new GregorianCalendar();
		realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
		System.out.println(realDay);
	    realMonth = cal.get(GregorianCalendar.MONTH); //Get month
	    realYear = cal.get(GregorianCalendar.YEAR); //Get year
		
	    // show the selected year(current year ~ current year added 4years)
		for(int i=0;i<5;++i){
			selectCiY.addItem(realYear+i);
			selectCoY.addItem(realYear+i);
			headcount.addItem(i+1);
		}
		
		// show the selected month(1 ~ 12)
		for(int i=realMonth+1;i<13;++i){
			selectCiM.addItem(i);
			selectCoM.addItem(i);
		}
		
		// show the selected day according to leap year
		for(int i=realDay;i<cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH)+1;++i){
			selectCiD.addItem(i);
			selectCoD.addItem(i);
		}
		
		tempRoom = db.selectRoomNum(Integer.parseInt(room.getSelectedItem().toString()));
	    selectCiY.addActionListener(new selectCiY_Action());
	    selectCoY.addActionListener(new selectCoY_Action());
	    selectCiM.addActionListener(new selectCiM_Action());
	    selectCoM.addActionListener(new selectCoM_Action());
	    selectCiD.addActionListener(new selectDay_Action());
	    selectCoD.addActionListener(new selectDay_Action());
	    room.addActionListener(new room_Action());
	    reservation.addActionListener(new btnReservation_Action(userid));
	    
		MyFrame.setResizable(false);
		MyFrame.setVisible(true);
	}
	
	/*
	 * Ŭ���� �� : settingPrice()
	 * ��� : �� ������ �������ش�.
	 */
	public static void settingPrice(){
		
		// make data type value to selected information(year, month, day) by user
	    sDay = new Date(Integer.parseInt(selectCiY.getSelectedItem().toString())-1900,
				Integer.parseInt(selectCiM.getSelectedItem().toString())-1,
				Integer.parseInt(selectCiD.getSelectedItem().toString()));
		eDay = new Date(Integer.parseInt(selectCoY.getSelectedItem().toString())-1900,
				Integer.parseInt(selectCoM.getSelectedItem().toString())-1,
				Integer.parseInt(selectCoD.getSelectedItem().toString()));
		
		// selected information(year, month, day) changes the integer type 
	    int iYear =Integer.parseInt(selectCiY.getSelectedItem().toString());
	    int iMonth = Integer.parseInt(selectCiM.getSelectedItem().toString());
	    int iDay = Integer.parseInt(selectCiD.getSelectedItem().toString());
	    int oYear =Integer.parseInt(selectCoY.getSelectedItem().toString());
	    int oMonth = Integer.parseInt(selectCoM.getSelectedItem().toString());
	    int oDay = Integer.parseInt(selectCoD.getSelectedItem().toString());
	    
		Calendar ca = Calendar.getInstance();
		ca.setTime(eDay);
		Calendar ca2 = Calendar.getInstance();
		ca2.set(iYear,iMonth-1,iDay);
		int totalDay=0;
		while(!ca2.after(ca)){
			++totalDay;
			ca2.add(Calendar.DATE,1);
		}
		// decide room price
		price.setText((totalDay*tempRoom.getRoomPrice())+"");
	}
	
	/*
	 * Ŭ���� �� : room_Action
	 * ��� : room��ư Ŭ���� �����ϴ� ���.
	 */
	static class room_Action implements ActionListener{
		public void actionPerformed(ActionEvent e){
			// save room_num,state,price,info,grade at object 'tempRoom'
			tempRoom = db.selectRoomNum(Integer.parseInt(room.getSelectedItem().toString()));
			// save start_day,end_day,room_num,user_name,etc at list
			list = db.selectBookings(Integer.parseInt(room.getSelectedItem().toString()));
			settingPrice();
		}
	}
	
	/*
	 * Ŭ���� �� : selectDay_Action
	 * ��� : ��¥�� �����ϸ� ������ �����ϴ� ���.
	 */
	// if you choose the day and click the day button, decided price
	static class selectDay_Action implements ActionListener{
		public void actionPerformed(ActionEvent e){
			settingPrice();
		}
	}
	
	/*
	 * Ŭ���� �� : selectCiY_Action
	 * ��� : üũ�� �⵵�� �����ϸ� üũ�� ���� �����ϴ� ���
	 */
	// if you click the the year of 'check in', initialize month list and save check in year
	static class selectCiY_Action implements ActionListener{
		public void actionPerformed(ActionEvent e){
			int i=0;
			try{
			selectCiM.removeAll(); //initialize check in month list
			}catch(Exception ef){}
			int year=Integer.parseInt(selectCiY.getSelectedItem().toString());
			if(year==realYear) i = realMonth;
			sCinyear=year;
			for(i=i+1;i<13;++i){
				selectCiM.addItem(i);
			}
			settingPrice();
		}
	}
	
	/*
	 * Ŭ���� �� : selectCoY_Action
	 * ��� : üũ�ƿ� �⵵�� �����ϸ� üũ�ƿ� ���� �����ϴ� ���
	 */
	// if you click the the year of 'check out', initialize month list and save check out year
	static class selectCoY_Action implements ActionListener{
		public void actionPerformed(ActionEvent e){
			int i=0;
			try{
			selectCoM.removeAll(); //initialize check out month list
			}catch(Exception ef){}
			int year = Integer.parseInt(selectCoY.getSelectedItem().toString());
			if(year==realYear) i = realMonth;
			sCoutyear=year;
			for(i=i+1;i<13;++i){
				selectCoM.addItem(i);
			}
			settingPrice();
		}
	}
	
	/*
	 * Ŭ���� �� : selectCiM_Action
	 * ��� : üũ�� ���� �����ϸ� üũ�� ���� �����ϴ� ���
	 */
	// if you click the the month of 'check in', initialize day list and save check in month
	static class selectCiM_Action implements ActionListener{
		public void actionPerformed(ActionEvent e){
			int i=1;
			selectCiD.removeAll(); //initialize check in day list
			int month = Integer.parseInt(selectCiM.getSelectedItem().toString());
			if(month==realMonth+1) i = realDay;
	        
			GregorianCalendar temp = new GregorianCalendar(sCinyear, month, 1);
			int nod = temp.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
			for(;i<nod+1;++i){
				selectCiD.addItem(i);
			}
			settingPrice();
		}
	}
	
	/*
	 * Ŭ���� �� : selectCoM_Action
	 * ��� : üũ�ƿ� ���� �����ϸ� üũ�ƿ� ���� �����ϴ� ���
	 */
	// if you click the the month of 'check out', initialize day list and save check out month
	static class selectCoM_Action implements ActionListener{
		public void actionPerformed(ActionEvent e){
		
			int i=1;
			selectCoD.removeAll(); //initialize check out day list
			int month = Integer.parseInt(selectCoM.getSelectedItem().toString());
			if(month==realMonth+1) i = realDay;
	        
			GregorianCalendar temp = new GregorianCalendar(sCoutyear, month, 1);
			int nod = temp.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
			for(;i<nod+1;++i){
	
				selectCoD.addItem(i);
			}
			settingPrice();
		}
	}
	
	/*
	 * Ŭ���� �� : btnReservation_Action
	 * ��� : ���� ��ư�� ������ �Է��� ������ db���̺� �����ϴ� ���
	 */
	// if click the 'reservation' button, save information(field) at db_table
	static class btnReservation_Action implements ActionListener{
		private String userid;
		public btnReservation_Action(){
			super();
		}
		public btnReservation_Action(String id){
			super();
			userid = id;
		}
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e){
			//create temp 2objects(user mode, guest mode) and Date
			BookingDTO temp = new BookingDTO();
			GuestDTO guesttemp = new GuestDTO();
			Date tm;
			boolean decide = false;
			// save start_day,end_day,room_num,user_name,etc at ArrayList 'list'
			list = db.selectBookings(Integer.parseInt(room.getSelectedItem().toString()));
			if(list!=null){
		    	for(int i=0;i<list.size();++i){
		    		if(sDay.compareTo(list.get(i).getStartDay())>=0&&eDay.compareTo(list.get(i).getEndDay())<=0){
		    			decide = true;
		    			break;
		    		}
		    	}
			}
	    	if(decide) // If the others have previously been booked, show the message.
	    		JOptionPane.showMessageDialog(null, "�ش� ��¥�� �̹� ����� ���Դϴ�.", "Alert", 0);
	    	else{
		    	if(userid==""){ //
					temp.setGuestName(name.getText());
					temp.setStartDay(sDay);
					temp.setEndDay(eDay);
					temp.setUserName("");
					temp.setService("");
					temp.setPhoneNumber(phone.getText());
					temp.setPrice(Integer.parseInt(price.getText()));
					temp.setRoomNum(Integer.parseInt(room.getSelectedItem().toString()));
					temp.setHeadcount(Integer.parseInt(headcount.getSelectedItem().toString()));
					guesttemp=db.selectGuest(phone.getText());
					if(guesttemp!=null){
					}
					else{
						guesttemp.setPhone(phone.getText());
						guesttemp.setEmail(email.getText());
						guesttemp.setName(name.getText());
						guesttemp.setPassword(password.getText());
					}
					db.insertGuest(guesttemp); // value(reservation information) of guesttemp send to db
					db.insertBooking(temp);    // value(reservation information) of temp send to db
				}
				
				else { //
					temp.setUserName(user.getName());
					temp.setStartDay(sDay);
					temp.setEndDay(eDay);
					temp.setPhoneNumber(phone.getText());
					temp.setPrice(Integer.parseInt(price.getText()));
					temp.setRoomNum(Integer.parseInt(room.getSelectedItem().toString()));
					temp.setHeadcount(Integer.parseInt(headcount.getSelectedItem().toString()));
					db.insertBooking(temp);    // value(reservation information) of temp send to db
				}
	    	}
		}
	}
}
