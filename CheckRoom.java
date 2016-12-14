
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.ArrayList;
/**
 * Check out room state and show button which is connected with Check Info class.
 * @author Jin daehan
 */
public class CheckRoom extends JFrame
{
    //MysqlConnect instance
    MysqlConnection sql = new MysqlConnection();
    private JLabel positionLabel;
    private JButton resetButton;
    private JButton exitButton;
    //control n and make nXn room 
    private static int numberOfRoom = 5;
    String roomNumOfText;
    CheckInfo info = new CheckInfo();
    CurrentDTO cd;
      /**
     * constructor to set frame name with super("name");
     */
    public CheckRoom()
    {
        super("Check room information");
    }

    /**
     * return String type of room state 
     * @param roomState
     * @return
     */
    public String showRoomState(int roomState)
    {
    	//if room's state is unbooked then return "Unbooked"
    	if(roomState == 0)
    	{
    		return "Unbooked";
    	}
    	//if room's state is booked then return "Booked"
    	else if(roomState == 1)
    	{
    		return "Booked";
    	}
    	//if room's state is Unserviceable then return "Unserviceable this room"
    	else
    	{
    		return "Unserviceable this room";
    	}
    }
    /**
     * show main display with GUI
     */
    void display()
    {       
    	
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //set layout
        JPanel contentPane = new JPanel();
	//set border color and thickness
        contentPane.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        contentPane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
         //exit button
        exitButton = new JButton("exit");
	//exit button's action
        exitButton.addActionListener(new ActionListener()
        {
		//if button is clicked then exit program
        	public void actionPerformed(ActionEvent ae)
            {
        		System.exit(0);
            }
        });
        //panel showing room state and button connected with CheckInfo class
        JPanel leftPanel = new JPanel();
	//set border color and thickness
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
	//set layout
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        //panel showing room number and button connected with leftpanel for showing room state
	JPanel labelPanel = new JPanel();
        positionLabel = new JLabel("search room.", JLabel.CENTER);
        JPanel buttonLeftPanel = new JPanel();
	//make new button
        resetButton = new JButton("change room info");
        resetButton.addActionListener(info);
	//resetButton's action is showing checkinfo frame
        resetButton.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent ae)
            {
        		//set main frame of checkRoom unvisible
        		setVisible(false);
            }
        });
        
        labelPanel.add(positionLabel);
        buttonLeftPanel.add(resetButton);
        buttonLeftPanel.add(exitButton);
        leftPanel.add(labelPanel);
        leftPanel.add(buttonLeftPanel);

        contentPane.add(leftPanel);
    
        JPanel buttonPanel = new JPanel();
	//It is GridLayout(int rows, int cols, int hgap, int vgap)
        buttonPanel.setLayout(new GridLayout(numberOfRoom, numberOfRoom, 5, 5));//GridLayOut(����, ����, ����, ����)
    
        for (int i = 0; i < numberOfRoom; i++)
        {
            for (int j = 0; j < numberOfRoom; j++)
            {
            	makeCurrent((i+1)*100+j+1);
            	//set button with roomNumber
                JButton button = new JButton((i+1)*100+j+1 + " room ");
                //if room is booked
                if(sql.selectRoomNum((i+1)*100+j+1).getRoomState() == 1)
                {	
                	//show room state with color
                	button.setBackground(Color.RED);
            	}
                //if room is unserviceable
                else if(sql.selectRoomNum((i+1)*100+j+1).getRoomState() == 2)
                {	
                	//show room state with color
                	button.setBackground(Color.YELLOW);
                }
                //if room is usable
                else
            	{
                	//show room state with color
                	button.setBackground(Color.GREEN);
                }
		//fill out button with room number and state
                button.setActionCommand(String.valueOf(sql.selectRoomNum((i+1)*100+j+1).getRoomNum()) + " room "
                + showRoomState(sql.selectRoomNum((i+1)*100+j+1).getRoomState()));
                
                button.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent ae)
                    {
                        JButton but = (JButton) ae.getSource();
                        positionLabel.setText(but.getActionCommand());
                        info.setRoomNumber(getTextNumber());
                    }
                });
                buttonPanel.add(button);
            }
        }
        contentPane.add(buttonPanel);
        setContentPane(contentPane);
        contentPane.setSize(500,500);
	//setSize of frame
        setSize(500,500);
    	//It is used for showing frame at center of screen
        
		Dimension frameSize = this.getSize();//프레임 사이즈
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //내 컴퓨터 화면 사이즈
		this.setLocation((screenSize.width-frameSize.width)/2,(screenSize.height-frameSize.height)/2); //화면 중앙
		//set frame unresizable
		this.setResizable(false);
        setVisible(true);
    }
    /**
     * get number in text
     * ex) text : 101 room => return : 101
     * @return
     */
    public int getTextNumber(){
    	String text;
  
    	positionLabel.getText();
    	text = positionLabel.getText();
    	roomNumOfText = text.substring(0, text.indexOf("room")-1);
    	//change type : String to int
    	int num = Integer.parseInt(roomNumOfText);
    	return num;
    }
    /**
     * make current_db from booking_db
     * @param roomNum
     */
    public void makeCurrent(int roomNum){
		ArrayList <BookingDTO> bookingList;
		bookingList = sql.selectBookings(roomNum);
		Date startDay;
		Date endDay;
		Date today = new Date();//today date
		cd  = new CurrentDTO();
		if(bookingList == null) return;
		for(int i=0;i <bookingList.size();i++)
		{
			startDay = bookingList.get(i).getStartDay();
			endDay = bookingList.get(i).getEndDay();
			//check out room booked or unbooked
			if(today.after(startDay)&&today.before(endDay))
			{
					cd.setRoomNum(roomNum);
					cd.setName(bookingList.get(i).getUserName());
					cd.setUserId(null);
					cd.setService(bookingList.get(i).getService());
					cd.setCheckinDate(bookingList.get(i).getStartDay());
					cd.setCheckoutDate(bookingList.get(i).getEndDay());
					cd.setState(1);
					sql.updateCurrent(cd);
			}
			else
			{
				cd.setRoomNum(roomNum);
				cd.setName(null);
				cd.setUserId(null);
				cd.setService(null);
				cd.setCheckinDate(null);
				cd.setCheckoutDate(null);
				cd.setState(0);
				sql.updateCurrent(cd);
			}
		}
	}
 
}
