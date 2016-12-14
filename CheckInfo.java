import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 * Check information of room and modify room information
 * @author JinDaeHane
 */
class CheckInfo implements ActionListener {
	//MysqlConnect instance
	MysqlConnection sql = new MysqlConnection();
	CheckRoom cr;
	RoomDTO rd= new RoomDTO();

	int roomNumber;
	UserDTO ud = new UserDTO();
	//make label
	Label roomNum;
	Label roomState;
	Label roomPrice;
	Label roomInfo;
	Label roomGrade;
	
	Label user;
	Label CheckOut;
	Label phone;
	Label info;
	Label checkOut;
	//make textfield
	TextField DBroomNum = new TextField(7);
	TextField DBroomState = new TextField(7);
	TextField DBroomPrice = new TextField(7);
	TextArea comments;
	TextField DBroomGrade = new TextField(7);
	TextField DBuser = new TextField(11);
	TextField DBCheckOut = new TextField(11);
	TextField DBphone = new TextField(11);
	TextField DBinfo = new TextField(11);
	/**
	 * get roomNumber of this class
	 * @return
	 */
	public int getRoomNumber() {
		return roomNumber;
	}
	/**
	 * set roomNumber of this class 
	 * @param roomNumber
	 */
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	
	public void actionPerformed(ActionEvent e){
		//make new JFrame
		JFrame modifyFrame = new JFrame("Modify room information");
		//set frame size
		modifyFrame.setSize(600, 500);
		modifyFrame.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
	
		Choice condition = new Choice();
		condition.add("usable room");
		condition.add("unusable room");
		condition.add("unserviable room");
		condition.setSize(180,50);
		//comments mean room info
		TextArea comments = new TextArea("info",10,70);
		//modify room button
		Panel panelYes = new Panel();
		panelYes.setSize(100,100);
		//cancel button
		Panel panelNo = new Panel();
		//set button size
		panelNo.setSize(100,100);
		//Room Number
		roomNum = new Label("Room Number");
		//Room State
		roomState = new Label("Room State");
		//Room price
		roomPrice = new Label("Room price");
		//Room Info
		roomInfo = new Label("Room Info");
		//Room Grade
		roomGrade = new Label("Room Grade");
		//User Name
		user = new Label("user name");
		//User CheckOut
		CheckOut = new Label("CheckOut");
		//User phone number
		phone = new Label("phone");
		//modify button
		Button yes = new Button("modify");
		//cancel button
		Button no = new Button("cancel");
		
		//make firstPnale with roomNum
		JPanel firstPanel = new JPanel();//panel ����
		firstPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		firstPanel.setLayout(new BoxLayout(firstPanel, BoxLayout.Y_AXIS));  
		firstPanel.add(roomNum);
		firstPanel.add(DBroomNum);

		//make secondPnale with roomState
		JPanel secondPanel = new JPanel();
		secondPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		secondPanel.setLayout(new BoxLayout(secondPanel, BoxLayout.Y_AXIS));  
		secondPanel.add(roomState);
		secondPanel.add(condition);

		//make thirdPanel showing roomPrice
		JPanel thirdPanel = new JPanel();
		thirdPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		thirdPanel.setLayout(new BoxLayout(thirdPanel, BoxLayout.Y_AXIS));  
		thirdPanel.add(roomPrice);
		thirdPanel.add(DBroomPrice);
		
		//make fivePanel showing roomGrade
		JPanel fivePanel = new JPanel();
		fivePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		fivePanel.setLayout(new BoxLayout(fivePanel, BoxLayout.Y_AXIS)); 
		fivePanel.add(roomGrade);
		fivePanel.add(DBroomGrade);
		
		//make sixPanel showing modify button and cancel button
		JPanel sixPanel = new JPanel();
		sixPanel.setLayout(new BoxLayout(sixPanel, BoxLayout.X_AXIS)); 
		sixPanel.add(panelYes);
		sixPanel.add(panelNo);
		
		//make userPanel showing UserName
		JPanel userPanel = new JPanel();
		userPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.X_AXIS)); 
		userPanel.add(user);
		userPanel.add(DBuser);
		
		//make CheckOutPanel showing UserCheckOut
		JPanel CheckOutPanel = new JPanel();
		CheckOutPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		CheckOutPanel.setLayout(new BoxLayout(CheckOutPanel, BoxLayout.X_AXIS)); 
		CheckOutPanel.add(CheckOut);
		CheckOutPanel.add(DBCheckOut);
		
		//make phonePanel showing UserPhone
		JPanel phonePanel = new JPanel();
		phonePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		phonePanel.setLayout(new BoxLayout(phonePanel, BoxLayout.X_AXIS)); 
		phonePanel.add(phone);
		phonePanel.add(DBphone);
	
		modifyFrame.add(firstPanel);
		modifyFrame.add(secondPanel);
		modifyFrame.add(thirdPanel);
		modifyFrame.add(fivePanel);
		modifyFrame.add(comments);
		comments.selectAll();
		
		modifyFrame.add(userPanel);
		modifyFrame.add(CheckOutPanel);
		modifyFrame.add(phonePanel);
		
		//It is used for showing frame at center of screen
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int screenXpos = (int)(screen.getWidth()/2) - modifyFrame.getWidth()/2;
		int screenYpos = (int)(screen.getHeight()/2) - modifyFrame.getHeight()/2;
		modifyFrame.setLocation(screenXpos, screenYpos);

		//add Button to Panel
		panelNo.add(no);
		panelYes.add(yes);

		modifyFrame.add(sixPanel);
		modifyFrame.setVisible(true);
		modifyFrame.setResizable(false);
		
		//fill textfield with DB
		DBroomNum.setText(String.valueOf(sql.selectRoomNum(roomNumber).getRoomNum()));
		DBroomPrice.setText(String.valueOf(sql.selectRoomNum(roomNumber).getRoomPrice()));
		comments.setText(sql.selectRoomNum(roomNumber).getRoomInfo());
		DBroomGrade.setText(String.valueOf(sql.selectRoomNum(roomNumber).getRoomGrade()));
		
		//set RoomNumber unmodified
		DBroomNum.setEnabled(false);
		DBuser.setText(sql.selectCurrent(roomNumber).getName());	
		
		//set Name unmodified
		DBuser.setEnabled(false);
		DBCheckOut.setText(String.valueOf(sql.selectCurrent(roomNumber).getCheckinDate()));
		
		//set CheckIn unmodified
		DBCheckOut.setEnabled(false);
		ArrayList <BookingDTO> bookingList;
		bookingList = sql.selectBookings(roomNumber);
		Date startDay;
		Date endDay;
		Date currentCIday = sql.selectCurrent(roomNumber).getCheckinDate();
		Date currentCOday = sql.selectCurrent(roomNumber).getCheckoutDate();
		CurrentDTO cd  = new CurrentDTO();
		for(int i=0;i <bookingList.size();i++)
		{
			startDay = bookingList.get(i).getStartDay();
			endDay = bookingList.get(i).getEndDay();
			if(currentCIday.compareTo(startDay)==0&&currentCOday.compareTo(endDay)==0)
			{
				DBphone.setText(bookingList.get(i).getPhoneNumber());
				break;
			}
		}
		
		//set Phone unmodified
		DBphone.setEnabled(false);
		/**
		 * modify button's action is modifying DB
		 */
		yes.addActionListener(new ActionListener()
        {	
			public void actionPerformed(ActionEvent ae)
            {
				//modify only room DB
				rd.setRoomNum(roomNumber);
				rd.setRoomState(condition.getSelectedIndex());
				
				//make CurrentDTO to null
				if(condition.getSelectedIndex()==0){
					CurrentDTO cd  = new CurrentDTO();
					cd.setRoomNum(roomNumber);
					cd.setName(null);
					cd.setUserId(null);
					cd.setService(null);
					cd.setCheckinDate(null);
					cd.setCheckoutDate(null);
					cd.setState(0);
					sql.updateCurrent(cd);
				}
				rd.setRoomPrice(Integer.valueOf(DBroomPrice.getText()));
				rd.setRoomInfo(comments.getText());
				rd.setRoomGrade(DBroomGrade.getText());
				sql.updateRoomInfo(rd);
				modifyFrame.dispose();
				new CheckRoom().display();//call CheckRoom
				
            }
        });
		/**
		 * no button's action is calling CheckRoom
		 */
		no.addActionListener(new ActionListener()
        {	
			public void actionPerformed(ActionEvent ae)
            {
				//set modifyFrame dispose
				modifyFrame.dispose();
				//call CheckRoom
				new CheckRoom().display();
            }
        });
		
	}
}