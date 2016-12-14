import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.ArrayList;
/**
 * 클래스 명 : BookingTable
 * 기능 : 예약테이블을 보여준다.
 * @author HoyoungJI(지호영)
 *
 */
public class BookingTable {

	static JLabel lblMonth, lblYear;
    static JButton btnPrev, btnNext;
    static JTable tblCalendar;
    static JComboBox cmbYear;
    static JFrame frmMain; //메인 프레임
    static Container pane;
    static DefaultTableModel mtblCalendar; //Table model
    static JScrollPane stblCalendar; //The scrollpane
    static JPanel pnlCalendar;
    static int realYear, realMonth, realDay, currentYear, currentMonth; //연도와 달, 일을 나타내는 변수
 
    static MysqlConnection db; //연결된 디비
    static int bookingID; //선택한 셀의 예약 ID를 저장
    
    static JFrame bookingCellFrame; // 예약 상세정보 창
    static Container bookingCellPane;
    static JButton deleteBookingCell,cancel;
    static JPanel bookingCellPanel;
    public BookingTable(){
    	
    }
    
    /**
     * 메소드명 :makeTable
     * 기능 : 예약 화면을 생성해 보여준다.
     */
    public void makeTable(){
    	db=db.getDbcom(); //db
    	
    	//Look and feel
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
        catch (ClassNotFoundException e) {}
        catch (InstantiationException e) {}
        catch (IllegalAccessException e) {}
        catch (UnsupportedLookAndFeelException e) {}
        
        //Prepare frame
        frmMain = new JFrame ("Gestionnaire de clients"); //Create frame
        frmMain.setSize(1300, 600); //Set size to 400x400 pixels 330 375
        pane = frmMain.getContentPane(); //Get content pane
        pane.setLayout(null); //Apply null layout
     //   frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Close when X is clicked
        
        //Create controls
        lblMonth = new JLabel ("January");
        lblYear = new JLabel ("Change year:");
        cmbYear = new JComboBox();//연도 선택
        btnPrev = new JButton ("Prev");//&lt;&lt;
        btnNext = new JButton ("Next");//&gt;&gt;
        mtblCalendar = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};
        tblCalendar = new JTable(mtblCalendar);
        stblCalendar = new JScrollPane(tblCalendar);
        pnlCalendar = new JPanel(null);
        
        JTable a = new JTable();
        
        //화면 중앙에 창을 나타냄
        Dimension frameSize = frmMain.getSize();//프레임 사이즈
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //내 컴퓨터 화면 사이즈
		frmMain.setLocation((screenSize.width-frameSize.width)/2,(screenSize.height-frameSize.height)/2); //화면 중앙
    	
        //Set border
        pnlCalendar.setBorder(BorderFactory.createTitledBorder("Booking"));
        
        //Register action listeners
        btnPrev.addActionListener(new btnPrev_Action());
        btnNext.addActionListener(new btnNext_Action());
        cmbYear.addActionListener(new cmbYear_Action());
        
        //Add controls to pane
        pane.add(pnlCalendar);
        pnlCalendar.add(lblMonth);
        pnlCalendar.add(lblYear);
        pnlCalendar.add(cmbYear);
        pnlCalendar.add(btnPrev);
        pnlCalendar.add(btnNext);
        pnlCalendar.add(stblCalendar);
        
        //Set bounds
        pnlCalendar.setBounds(10, 10, 1260, 550);
        lblMonth.setBounds(600-lblMonth.getPreferredSize().width/2, 25, 100, 25);
        lblYear.setBounds(1050, 520, 80, 20);
        cmbYear.setBounds(1150, 520, 80, 20);
        btnPrev.setBounds(440, 25, 70, 25);
        btnNext.setBounds(700, 25, 70, 25);
        stblCalendar.setBounds(15, 50, 1220, 450);
        
        //Make frame visible
        frmMain.setResizable(false);
        frmMain.setVisible(true);
        
        //Get real month/year
        GregorianCalendar cal = new GregorianCalendar(); //Create calendar
        realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
        realMonth = cal.get(GregorianCalendar.MONTH); //Get month
        realYear = cal.get(GregorianCalendar.YEAR); //Get year
        currentMonth = realMonth; //Match month and year
        currentYear = realYear;
        
        //예약된 cell을 선택했을때의 event
        tblCalendar.addMouseListener(new BookingCellMouseListener());
        
        //Add headers
        int nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
 
        //table에 column값(일) 넣는다.
       // for (int i=1; i<=nod; i++){
       //     mtblCalendar.addColumn(i);
       // }
        
        tblCalendar.getParent().setBackground(tblCalendar.getBackground()); //Set background
        
        //No resize/reorder
        tblCalendar.getTableHeader().setResizingAllowed(false);
        tblCalendar.getTableHeader().setReorderingAllowed(false);
        
        //Single cell selection
        tblCalendar.setColumnSelectionAllowed(true);
        tblCalendar.setRowSelectionAllowed(true);
        tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

         
        //Set row/column count
        tblCalendar.setRowHeight(30); //38
        mtblCalendar.setColumnCount(32); //7
        mtblCalendar.setRowCount(25); //6
        
        //Populate table
        for (int i=realYear-100; i<=realYear+100; i++){
            cmbYear.addItem(String.valueOf(i));
        }
        
        //Refresh calendar
        refreshCalendar (realMonth, realYear); //Refresh calendar
    }
    
    
    /**
     * 메소드명 :refreshCalendar
     * 기능 : 매개변수로 넘어온 연도와 달로 예약현항을 새로 화면에 표시해주는 메소드
     * @param month 달
     * @param year 연도
     */
    public static void refreshCalendar(int month, int year){
        //Variables
        String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        int nod, som; //Number Of Days, Start Of Month
        tblCalendarRenderer tcr =new tblCalendarRenderer();
        //Allow/disallow buttons
        btnPrev.setEnabled(true);
        btnNext.setEnabled(true);
        if (month == 0 && year <= realYear-10){btnPrev.setEnabled(false);} //Too early
        if (month == 11 && year >= realYear+100){btnNext.setEnabled(false);} //Too late
        lblMonth.setText(months[month]); //Refresh the month label (at the top)
        lblMonth.setBounds(600-lblMonth.getPreferredSize().width/2, 25, 180, 25); //Re-align label with calendar
        cmbYear.setSelectedItem(String.valueOf(year)); //Select the correct year in the combo box
        
        //Clear table
        for (int i=0; i<10; i++){
            for (int j=0; j<32; j++){
                mtblCalendar.setValueAt(null, i, j);
            }
        }
        
        //Get first day of month and number of days
        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
        nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
 
        
        //coulumn (일)수 추가
        String []arr = new String[32];
        arr[0] = "Room＼Day";
        for(int i=1;i<32;i++){
        	if(i<nod+1) arr[i]=i+"";
        	else arr[i]=" ";
        }
        mtblCalendar.setColumnIdentifiers(arr);
        
        //첫번째 열 크기 조절
        tblCalendar.getColumnModel().getColumn(0).setPreferredWidth(200);
        
        tcr.setHorizontalAlignment(SwingConstants.CENTER); //중앙정렬

        //room number Draw
        int room=100;
        for(int i=0;i<=4;++i){
        	for(int j=1;j<=5;++j)
        		mtblCalendar.setValueAt( room+j , i*5+j-1, 0);
        	room+=100;
        }
        
        
        //Apply renderers
        tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), tcr );
    }
    
    /**
     * 클래스명 : tblCalendarRenderer
     * 기능 : 예약 일에 색깔을 넣음. 
     * @author HoyoungJi
     *
     */
    static class tblCalendarRenderer extends DefaultTableCellRenderer{ //색깔 넣음
        public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
           
            if (column == 0 ){ //Room number 부분에 색을 넣음
            	if(0<=row&&row<=4)//1층
            		setBackground(new Color(128, 231, 231));
            	else if(5<=row&&row<=9)//2층
            		setBackground(new Color(106,233,147));
            	else if(10<=row&&row<=14)//3층
            		setBackground(new Color(147,106,233));
            	else if(15<=row&&row<=19)//4층
            		setBackground(new Color(245,245,37));
            	else if(20<=row&&row<=24)//5층
            		setBackground(new Color(231,165,210));
            }
            else{ //예약 일에 색깔을 넣는다.
            	if(bookingCell(Integer.parseInt(tblCalendar.getModel().getValueAt(row, 0).toString()),column)){
            		if(0<=row&&row<=4)//1층
                		setBackground(new Color(128, 231, 231));
                	else if(5<=row&&row<=9)//2층
                		setBackground(new Color(106,233,147));
                	else if(10<=row&&row<=14)//3층
                		setBackground(new Color(147,106,233));
                	else if(15<=row&&row<=19)//4층
                		setBackground(new Color(245,245,37));
                	else if(20<=row&&row<=24)//5층
                		setBackground(new Color(231,165,210));
            		
            	}
            	else setBackground(new Color(255, 255, 255));//아무것도 없을 때 기본 색깔
        		
            	
            }
            if (value != null){
                if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){ //Today
                    setBackground(new Color(220, 220, 255));
                }
            }
            setBorder(null);
            setForeground(Color.black);
            return this;
        }
        
        
    }
    
    /**
     * 메소드명 : bookingCell
     * 기능 : 예약일을 표시하기위해 예약일인지 확인하는 작업을 해주는 메소드
     * @param roomNumber 방번호
     * @param column table의 column은 해당달의 일수를 나타낸다.
     * @return
     */
    private static boolean bookingCell(int roomNumber, int column){
    	int sMonth,eMonth,sDay,eDay,year;//예약 시작일과 끝일등을 나타내는 변수
    	ArrayList<BookingDTO> list; //층을 기준으로
    	
    	//방번호로 DB에서 예약한 현황을 검색 하여 검색된 결과들을 list로 받아온다.
    	list = db.selectBookings(roomNumber);
    	
    	//반복문으로 받아온 결과 탐색
    	for(int j=0; j<list.size();++j){
    		String[] sDate=new String(list.get(j).getStartDay().toString()).split("-");//시작일을 -기준으로 나누어 배열생성
    		String[] eDate=new String(list.get(j).getEndDay().toString()).split("-");// 끝일을 - 기준으로 나누어 배열생성
    		//나눈 배열로 날짜 할당 부분
    		year = Integer.parseInt(sDate[0]);   		 
    		sMonth = Integer.parseInt(sDate[1]);  		 
    		sDay = Integer.parseInt(sDate[2]);
      		eMonth = Integer.parseInt(eDate[1]);      		
    		eDay = Integer.parseInt(eDate[2]);
    		
    		if(currentMonth==sMonth-1&&currentYear==year){//현재의 달과 연도랑 일치하는지 검사
    			//sMonth, 즉 받아온 달에 -1하는 이유는 currentMonth가 0부터 시작해서 실제달에서 -1을 해줘야 제대로 비교 할 수 있다.
    			if((sDay<=column&&column<=eDay)&&sMonth==eMonth){
    				//해당 일이 예약 시작일과 끝일 사이이고 시작달과 끝달이 같은 경우
    				bookingID = list.get(j).getId();//그때의 예약정보 ID를 따로 저장
    				return true;
     			}
    			else if((sMonth!=eMonth)&&sDay<=column){
    				//예약 시작 달과 끝달이 다른 경우이며 시작달 부분이다.
    				bookingID = list.get(j).getId();//그때의 예약정보 ID를 따로 저장
     				return true;
     			}
     			
     		}
     		else if (currentMonth==eMonth-1&&currentYear==year){//예약 시작 달과 끝 달이 다른 경우이며 끝달 부분이다.
     			if((sMonth!=eMonth)&&column<=eDay){
     				bookingID = list.get(j).getId();//그때의 예약정보 ID를 따로 저장
     				return true;
     			}
     		}
    		
    		
    	}
    	
    	return false;//예약한 일이 아니면 false를 리턴한다.
    }

    /**
     * 메소드명 : selectBooking
     * 기능 : 해당 예약 정보를 보여주는 창을 띄운다.
     * @param bookingID 예약 ID
     */
    public void selectBooking(int bookingID){
    	BookingDTO tempDTO=db.selectBooking(bookingID);//ID를 통해 해당 에약 정보를 검색해 DTO객체 생성
    	
    	//예약 창에 해당하는 Frame관련 설정
    	bookingCellFrame = new JFrame("Reservation Information");
    	bookingCellFrame.setSize(400,500); //창 size
    	bookingCellPane= bookingCellFrame.getContentPane();
    	bookingCellPane.setLayout(null);
    	
    	bookingCellPanel = new JPanel(null);
    	deleteBookingCell = new JButton("Delete"); //삭제버튼
    	cancel = new JButton("Cancel"); //취소버튼
    	bookingCellPane.add(bookingCellPanel);
    	
    	
    	//창의 label들
    	JLabel name = new JLabel("Name");
    	JLabel date = new JLabel("Date");
    	JLabel rn = new JLabel("Room No.");
    	JLabel hc = new JLabel("HeadCount");
    	JLabel price = new JLabel("Total Price"); 
    	
    	//검색해서 얻어온 DTO객체를 이용하여 정보들로 창에 표시할 Label을 생성한다.
    	JLabel nameContent; //이름같은경우는 회원과 비회원이 있기때문에 조건으로 나눠서 비어있지 않은 필드를 가져다 넣는다.

    	if(tempDTO.getGuestName()==null) nameContent = new JLabel(tempDTO.getUserName());
    	else nameContent = new JLabel(tempDTO.getGuestName());
    	JLabel dateContent = new JLabel(tempDTO.getStartDay().toString()+" ~ "+tempDTO.getEndDay().toString());
    	JLabel rnContent = new JLabel(tempDTO.getRoomNum()+"");
    	JLabel hcContent = new JLabel(tempDTO.getHeadcount()+"");
    	JLabel priceContent = new JLabel(tempDTO.getPrice()+"");
    	
    	//화면 중앙에 창을 띄우기 위한 코드
    	Dimension frameSize = bookingCellFrame.getSize();//프레임 사이즈
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //내 컴퓨터 화면 사이즈
		bookingCellFrame.setLocation((screenSize.width-frameSize.width)/2,(screenSize.height-frameSize.height)/2); //화면 중앙
    	
    	
		
		//각 label들과 button들을 panel에 추가한다
    	bookingCellPanel.add(name);
    	bookingCellPanel.add(date);
    	bookingCellPanel.add(rn);
    	bookingCellPanel.add(hc);
    	bookingCellPanel.add(price);
    	bookingCellPanel.add(deleteBookingCell);
    	bookingCellPanel.add(cancel);
    	
    	bookingCellPanel.add(nameContent);
    	bookingCellPanel.add(dateContent);
    	bookingCellPanel.add(rnContent);
    	bookingCellPanel.add(hcContent);
    	bookingCellPanel.add(priceContent);
    	
    	//삭제 버튼과 취소 버튼에 대한 이벤트
    	deleteBookingCell.addActionListener(new btnDelete_Action(tempDTO.getId()));
    	cancel.addActionListener(new btnCancel_Action());
    	
    	bookingCellPanel.setBounds(0,0,400,500);
    	rn.setBounds(30,50,100,50);
    	date.setBounds(30,100,100,50);
    	name.setBounds(30,150,100,50);
    	hc.setBounds(230,150,100,50);
    	price.setBounds(230,400,100,50);
    	
    	nameContent.setBounds(150,150,100,50);
    	dateContent.setBounds(150,100,200,50);
    	rnContent.setBounds(150,50,150,50);
    	hcContent.setBounds(330,150,70,50);
    	priceContent.setBounds(330,400,100,50);
    	deleteBookingCell.setBounds(115, 405, 75, 30);
    	cancel.setBounds(30,405,75,30);
    	
    	//폰트 설정
    	rn.setFont(new Font("Arial",Font.BOLD,15));
    	date.setFont(new Font("Arial",Font.BOLD,15));
    	name.setFont(new Font("Arial",Font.BOLD,15));
    	hc.setFont(new Font("Arial",Font.BOLD,15));
    	price.setFont(new Font("Arial",Font.BOLD,15));
    	
    	nameContent.setFont(new Font("Arial",Font.BOLD,13));
    	dateContent.setFont(new Font("Arial",Font.BOLD,13));
    	rnContent.setFont(new Font("Arial",Font.BOLD,15));
    	hcContent.setFont(new Font("Arial",Font.BOLD,13));
    	priceContent.setFont(new Font("Arial",Font.BOLD,13));
    	
    	nameContent.setForeground(Color.BLUE);
    	dateContent.setForeground(Color.BLUE);
    	rnContent.setForeground(Color.BLUE);
    	hcContent.setForeground(Color.BLUE);
    	priceContent.setForeground(Color.RED);
    	
    	
    	//화면 사이즈 조정불가 화면보이기
    	bookingCellFrame.setResizable(false);
    	bookingCellFrame.setVisible(true);
    	
    	
    }
    
    /**
     * 클래스명 : BookingCellMouseListener
     * 기능	: 예약날짜 테이블에서 예약된 셀을 클릭시에 상세정보를 보여주도록 동작하게 한다.
     * @author HoyoungJi
     *
     */
    private class BookingCellMouseListener implements MouseListener{
    	public void mouseClicked(MouseEvent e){
    	
    		if(tblCalendar.getSelectedColumn()==0) return; //날짜를 나타내는 곳일겨우
    		int roomNumber = Integer.parseInt(tblCalendar.getModel().getValueAt(tblCalendar.getSelectedRow(), 0).toString());
    		if(bookingCell(roomNumber,tblCalendar.getSelectedColumn())){//예약된 셀 경우
    	
    			selectBooking(bookingID);
    		}
    		else {
    			return; //예약된 셀이 아닌경우
    		}
    		
    		
    	}
    	public void mouseEntered(MouseEvent e){
    		
    	}
    	public void mouseExited(MouseEvent e){
    		
    	}
    	public void mousePressed(MouseEvent e){
    		
    	}
    	public void mouseReleased(MouseEvent e){
    		
    	}
    }
    
    /**
     * 클래스명 : btnDelete_Action
     * 기능 : 삭제 버튼을 눌렀을때 id로 검색을하여 해당 예약정보를 삭제 한다.
     * @author HoyoungJi(지호영)
     */
    static class btnDelete_Action implements ActionListener{
    	private int id;
    	public btnDelete_Action(){
    		super();
    	}
    	public btnDelete_Action(int id){
    		super();
    		this.id = id;
    	}
    	public void actionPerformed(ActionEvent e){
    		db.deleteBooking(id);
    		refreshCalendar(currentMonth, currentYear); //셀을 지우고 다시 새로고침
    		bookingCellFrame.dispose();
    	}
    }
    
    /**
     * 클래스명 : btnCancel_Action
     * 기능 : 캔슬 눌렀을 경우
     * @author HoyoungJi(지호영)
     */
    static class btnCancel_Action implements ActionListener{
    	public void actionPerformed(ActionEvent e){
    		bookingCellFrame.dispose();
    	}
    }
    /**
     * 클래스명 : btnPrev_Action
     * 기능 : 현재 달에서 전버튼을 누른경우
     * @author HoyoungJi(지호영)
     */
    static class btnPrev_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            if (currentMonth == 0){ //Back one year
                currentMonth = 11;
                currentYear -= 1;
            }
            else{ //Back one month
                currentMonth -= 1;
            }
            refreshCalendar(currentMonth, currentYear);
        }
    }
    /**
     * 클래스명 : btNext_Action
     * 기능 : 현재 달에서 전버튼을 누른경우
     * @author HoyoungJi(지호영)
     */
    static class btnNext_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            if (currentMonth == 11){ //Foward one year
                currentMonth = 0;
                currentYear += 1;
            }
            else{ //Foward one month
                currentMonth += 1;
            }
            refreshCalendar(currentMonth, currentYear);
        }
    }
    /**
     * 클래스명 : cmdYear_Action
     * 기능 : 년도를 선택한 경우 해당년도로 바꾼다.
     * @author HoyoungJi(지호영)
     */
    static class cmbYear_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            if (cmbYear.getSelectedItem() != null){
                String b = cmbYear.getSelectedItem().toString();
                currentYear = Integer.parseInt(b);
                refreshCalendar(currentMonth, currentYear);
            }
        }
    }
    
}


