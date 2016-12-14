import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;



/**
 * 클래스 명 : MainFrame
 * 기능 : 처음 로그인 화면과 관리자화면, 유저화면 그리고 손님화면을 보여주고 해당 내용을 클릭했을시 해당 기능에 연결해준다.
 * @author HoyoungJI(지호영)
 */
public class MainFrame {
	static JFrame mainFrame;//프레임
	static JLabel id,passwd,title;//메인 로그이니 화면의 label들
	static JButton login,signUp,guestLogin;//login관련 버튼
	static Container pane;
	static JPanel pnl;
	static JTextField idField; //id입력 필드
	static JPasswordField passwdField; //password입력 필드
	
	static JButton rooms,booking,qna,notice; //버튼들
	
	static UserDTO Data;
	static MysqlConnection db;//db에 정보를 받아오거나 쓴다.
	
	MainFrame(){
		///look an feel
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
        catch (ClassNotFoundException e) {}
        catch (InstantiationException e) {}
        catch (IllegalAccessException e) {}
        catch (UnsupportedLookAndFeelException e) {}
		mainFrame = new JFrame("Hotel Management System"); //프레임 객체 생성
		mainFrame.setSize(800,600);//프레임 사이즈 설정
		pane=mainFrame.getContentPane();
		Dimension frameSize = mainFrame.getSize();//프레임 사이즈
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //내 컴퓨터 화면 사이즈
		mainFrame.setLocation((screenSize.width-frameSize.width)/2,(screenSize.height-frameSize.height)/2); //화면 중앙
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //x키를 눌렀을때 전체 종료
		db=db.getDbcom();//db객체 생성하고 받아온다
	}

	/**
	 * 메소드 명 : main
	 * 기능 : 프로그램 시작
	 */
	public static void main(String[] args) {
		MainFrame mainControl = new MainFrame();
		
		mainControl.makeMainFrame();
	}
	

	/**
	 * 메소드 명 : makeMainFrame
	 * 기능 : 제일 처음 로그인 화면을 보여준다.
	 */
	public static void makeMainFrame(){
		
		//각 객체 생성부분///////////////////////////
		pane.setLayout(null);
		pnl= new JPanel(null);
		login = new JButton("Login");
		signUp = new JButton("SignUp");
		guestLogin = new JButton("For Guest");
		id=new JLabel("ID : ");
		passwd= new JLabel("Pssword : ");
		title = new JLabel("Hotel Management System");
		idField = new JTextField(20);
		passwdField = new JPasswordField(20);
		///////////////////////////////////////
		
		//pane에 panel을올리고 그위에 각 요소들을 올린다.///
		pane.add(pnl);
		pnl.add(id);
		pnl.add(passwd);
		pnl.add(login);
		pnl.add(signUp);
		pnl.add(guestLogin);
		pnl.add(title);
		pnl.add(idField);
		pnl.add(passwdField);
		///////////////////////////////////
		
		//버튼들을 눌렀을때의 event등록
		signUp.addActionListener(new btnSignUp_Action()); //회원가입
		login.addActionListener(new btnLogin_Action());//로그인
		guestLogin.addActionListener(new btnGuestLogin_Action());//guest(손님)
		///////////////////////////////////////////
		
		//각 요소들 위치설정////////////////////
		pnl.setBounds(0,0,800,600);
		title.setBounds(140,40,550,60);
		id.setBounds(225,200,50,30);
		passwd.setBounds(150,240,150,30);
		login.setBounds(470,300,80,30);
		guestLogin.setBounds(560,300,100,30);
		signUp.setBounds(140,300,90,30);
		idField.setBounds(280,200,260,30);
		passwdField.setBounds(280,240,260,30);
		////////////////////////////////
		
		//폰트 설정/////////////////////////////////
		signUp.setFont(new Font("Arial",0,12));
		login.setFont(new Font("Arial",0,12));
		guestLogin.setFont(new Font("Arial",0,12));
		id.setFont(new Font("Arial",Font.BOLD,23));
		passwd.setFont(new Font("Arial",Font.BOLD,23));
		title.setFont(new Font("Arial",Font.BOLD,40));
		///////////////////////////////////////////
		
		mainFrame.setResizable(false);
		mainFrame.setVisible(true); 
	}
	
	/**
	 * 메소드 명 : userFrame
	 * 기능 : 유저로 로그인 햇을때 볼 수 잇는 화면을 보여준다.
	 */
	public static void userFrame(){
		pane.removeAll();//기존의 pane을 초기화
		pane.setLayout(new GridLayout(2,2));
		mainFrame.setTitle(Data.getName() + "님 환영합니다.");//title 설정
		
		//버튼 객체 생성///////////////////////////////
		rooms= new JButton("Check My reservation");
		booking = new JButton("Reservation");
		qna = new JButton("Question");
		notice = new JButton("su");
		////////////////////////////////////////
		
		//사용자 메뉴설정/////////////////////////////////////////
		//예약확인
		rooms.setIcon(new ImageIcon ("myroom.png"));//이미지 삽입
		rooms.setHorizontalTextPosition(SwingConstants.CENTER);//텍스트의 위치를 중앙으로 정렬
		rooms.setVerticalTextPosition(SwingConstants.BOTTOM);//텍스트를 아래에 배치
		//예약
		booking.setIcon(new ImageIcon ("reservation.png"));//이미지 삽입
		booking.setHorizontalTextPosition(SwingConstants.CENTER);//텍스트의 위치를 중앙으로 정렬
		booking.setVerticalTextPosition(SwingConstants.BOTTOM);//텍스트를 아래에 배치
		//질문
		qna.setIcon(new ImageIcon ("question.png"));//이미지 삽입
		qna.setHorizontalTextPosition(SwingConstants.CENTER);//텍스트의 위치를 중앙으로 정렬
		qna.setVerticalTextPosition(SwingConstants.BOTTOM);//텍스트를 아래에 배치
		//회원정보수정
		notice.setIcon(new ImageIcon ("about.png"));//이미지 삽입
		notice.setHorizontalTextPosition(SwingConstants.CENTER);//텍스트의 위치를 중앙으로 정렬
		notice.setVerticalTextPosition(SwingConstants.BOTTOM);//텍스트를 아래에 배치
		///////////////////////////////////////////////////////
		
		//pane에 각 요소 등록
		pane.add(rooms);
		pane.add(booking);
		pane.add(qna);
		pane.add(notice);
		
		//font 사이즈 설정
		rooms.setFont(new Font("Arial",Font.BOLD,20));
		booking.setFont(new Font("Arial",Font.BOLD,20));
		qna.setFont(new Font("Arial",Font.BOLD,20));
		notice.setFont(new Font("Arial",Font.BOLD,20));
		
		booking.addActionListener(new btnUserBooking_Action()); //예약 버튼을 눌렀을 경우 event등록
		qna.addActionListener(new btnUserQnA_Action()); //질문 버튼을 눌렀을때 event 등록
		
		mainFrame.setResizable(false);
		mainFrame.setVisible(true); 
		
		rooms.addActionListener(new ActionListener() {//예약 확인버튼을 눌렀을 경우
			public void actionPerformed(ActionEvent ae) {
				CheckReservation cr = new CheckReservation();
				try {
					cr.checkingRev();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		notice.addActionListener(new ActionListener() {//회원정보 수정 버튼을 눌렀을 경우
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e1) {
				modifyUserInfo mui = new modifyUserInfo();
				mui.modifyRegister();
			}
		});
	}

	/**
	 * 메소드 명 : adminFrame
	 * 기능 : 유저로 로그인 햇을때 볼 수 잇는 화면을 보여준다.
	 */
	public static void adminFrame(){

		pane.removeAll();
		pane.setLayout(new GridLayout(2,2));
		
		//객체 생성//////////////////////////////////////
		rooms= new JButton("Checking Room");
		booking = new JButton("Reservation Confirm");
		qna = new JButton("Answer");
		notice = new JButton("Notice");
		//////////////////////////////////////////////
		
		//버튼들 event 등록//////////////////////////////////
		booking.addActionListener(new btnBooking_Action()); //예약확인 버튼을 눌렀을 경우
		qna.addActionListener(new btnQnA_Action()); // qna리스트 버튼을 눌럿을 경우
		rooms.addActionListener(new btnRooms_Action());//방확인을 눌럿을 경우
		/////////////////////////////////////////////////
		
		//버튼들 설정////////////////////////////////////////
		rooms.setIcon(new ImageIcon ("hotel.png"));
		rooms.setHorizontalTextPosition(SwingConstants.CENTER);
		rooms.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		booking.setIcon(new ImageIcon ("booking.png"));
		booking.setHorizontalTextPosition(SwingConstants.CENTER);
		booking.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		qna.setIcon(new ImageIcon ("qna.png"));
		qna.setHorizontalTextPosition(SwingConstants.CENTER);
		qna.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		notice.setIcon(new ImageIcon ("notice.png"));
		notice.setHorizontalTextPosition(SwingConstants.CENTER);
		notice.setVerticalTextPosition(SwingConstants.BOTTOM);
		///////////////////////////////////////////////////
		//panel에 버튼 추가////
		pane.add(rooms);
		pane.add(booking);
		pane.add(qna);
		pane.add(notice);
		/////////////////
		//font 설정//////////////////////////////
		rooms.setFont(new Font("Arial",Font.BOLD,20));
		booking.setFont(new Font("Arial",Font.BOLD,20));
		qna.setFont(new Font("Arial",Font.BOLD,20));
		notice.setFont(new Font("Arial",Font.BOLD,20));
		////////////////////////////////////
		mainFrame.setResizable(false);
		mainFrame.setVisible(true); 
		
	}
	

	/**
	 * 메소드 명 : guestFrame
	 * 기능 : 유저로 로그인 햇을때 볼 수 잇는 화면을 보여준다.
	 */
	public static void guestFrame(){
		pane.removeAll();
		pane.setLayout(new GridLayout(2, 2));

		//객체 생성///////////////////////////////////////
		rooms = new JButton("Check My reservation");
		booking = new JButton("Reservation");
		qna = new JButton("Question");
		notice = new JButton("su");
		//////////////////////////////////////////////
		//버튼 내용 설정//////////////////////////////////////////
		rooms.setIcon(new ImageIcon("myroom.png"));
		rooms.setHorizontalTextPosition(SwingConstants.CENTER);
		rooms.setVerticalTextPosition(SwingConstants.BOTTOM);

		booking.setIcon(new ImageIcon("reservation.png"));
		booking.setHorizontalTextPosition(SwingConstants.CENTER);
		booking.setVerticalTextPosition(SwingConstants.BOTTOM);

		qna.setIcon(new ImageIcon("question.png"));
		qna.setHorizontalTextPosition(SwingConstants.CENTER);
		qna.setVerticalTextPosition(SwingConstants.BOTTOM);

		notice.setIcon(new ImageIcon("about.png"));
		notice.setHorizontalTextPosition(SwingConstants.CENTER);
		notice.setVerticalTextPosition(SwingConstants.BOTTOM);
		////////////////////////////////////////////////////

		//panel에 내용등록/////////////////////////////////
		pane.add(rooms);
		pane.add(booking);
		pane.add(qna);
		pane.add(notice);
		///////////////////////////////////////

		//font 설정///////////////////////////////
		rooms.setFont(new Font("Arial", Font.BOLD, 20));
		booking.setFont(new Font("Arial", Font.BOLD, 20));
		qna.setFont(new Font("Arial", Font.BOLD, 20));
		notice.setFont(new Font("Arial", Font.BOLD, 20));
		///////////////////////////////
		//버튼들을 눌렀을 경우 event등록////////////////////////////
		booking.addActionListener(new btnGuestBooking_Action()); //예약 확인버튼을 눌렀을 경우
		qna.addActionListener(new btnGuestQnA_Action()); //question버튼을 눌럿을 경우
		/////////////////////////////////////////////////
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
		
		rooms.addActionListener(new ActionListener() //방 확인 설정
        {	
			public void actionPerformed(ActionEvent ae)
            {
				guestCheckRev cr = new guestCheckRev();
				cr.checkingRev();
            }
        });
	}
	/**
	 * 클래스명  : btnRooms_Action
	 * 기능 : 방 확인 버튼을 눌럿을 때 해당 화면을 보여준다.
	 * @author HoyoungJi(지호영)
	 */
	static class btnRooms_Action implements ActionListener{
		public void actionPerformed(ActionEvent e){
			 new CheckRoom().display(); //방확인에 대한 창
		}
	}
	/**
	 * 클래스명  : btnSingUp_Action
	 * 기능 : singup버튼을 눌렀을경우 회원가입 화면을 보여준다.
	 * @author HoyoungJi(지호영)
	 */
	static class btnSignUp_Action implements ActionListener{
		public void actionPerformed(ActionEvent e){
			createAccount a=new createAccount();
			a.makeRegister();
		}
	}
	/**
	 * 클래스명  : btnLogin_Action
	 * 기능 : 로그인버튼을 눌럿을 경우 있는 user인지 확인하여 관리자이면 관리자화면 아니면 유저화면을 보여준다.
	 * @author HoyoungJi(지호영)
	 */
	static class btnLogin_Action implements ActionListener{
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e) {
			if(idField.getText().equals("")||passwdField.getText().equals("")){ //입력을 안한경우
				JOptionPane.showMessageDialog(null, "아이디or 비밀번호를 입력 해주세요", "Alert", 0);
			}

			else{//입력을 한경우
				Data = new UserDTO();
				Data = db.selectUserId(idField.getText()); //id로 유저정보 받아온다.
				if (idField.getText().equals("admin")) { //관리자인 경우
					if (passwdField.getText().equals(Data.getPassword())) {//password비교
						adminFrame();
					} else {//비밀번호가 틀린경우
						JOptionPane.showMessageDialog(null, "비밀번호를 확인하세요", "Alert", 0);
					}
				}
	
				else if (idField.getText().equals(Data.getUserId())) { //user인 경우
					if (passwdField.getText().equals(Data.getPassword())) {//password 비교
						userFrame();
					} else {//비밀번호가 틀린경우
						JOptionPane.showMessageDialog(null, "비밀번호를 확인하세요", "Alert", 0);
					}
				} else {//해당아이디가 없는 경우
					JOptionPane.showMessageDialog(null, "아이디를 확인하세요", "Alert", 0);
				}
	
			}
		}
	}
	/**
	 * 클래스명  : btnBooking_Action
	 * 기능 : 예약확인 버튼을 눌렀을때 예약정보를 확인할 수 잇는 창을 보여준다.
	 * @author HoyoungJi(지호영)
	 */
	static class btnBooking_Action implements ActionListener{
		public void actionPerformed(ActionEvent e){
			BookingTable bt = new BookingTable();
			bt.makeTable(); //예약 확인창을 불러옴
		}
	}

	/**
	 * 클래스명  : btnQnA_Action
	 * 기능 : 예약확인 버튼을 눌렀을때 예약정보를 확인할 수 잇는 창을 보여준다.
	 * @author HoyoungJi(지호영)
	 */
	static class btnQnA_Action implements ActionListener{
		public void actionPerformed(ActionEvent e){
			QnATable qna = new QnATable();
			qna.qnaTable();//qna List창을 만듬
		}
	}
	
	/**
	 * 클래스명  : btnGuest_Action
	 * 기능 : guest로 로그인 하기를 눌렀을 경우
	 * @author HoyoungJi(지호영)
	 */
	static class btnGuestLogin_Action implements ActionListener{
		public void actionPerformed(ActionEvent e){
			guestFrame();
		}
	}
	
	/**
	 * 클래스명  : btnGuestBooking_Action
	 * 기능 : guest화면에서 예약상태 확인하기를 눌렀을 경우 해당 창을 띄운다.
	 * @author HoyoungJi(지호영)
	 */
	static class btnGuestBooking_Action implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Reservation guest = new Reservation();
			guest.makeReservationFrame("");
		}
	}
	/**
	 * 클래스명  : btnUserBooking_Action
	 * 기능 : user화면에서 예약확인을 눌렀을 경우 해당창을 띄운다.
	 * @author HoyoungJi(지호영)
	 */
	static class btnUserBooking_Action implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Reservation user = new Reservation();
			user.makeReservationFrame(Data.getUserId());
		}
	}
	/**
	 * 클래스명  : btnUserQnA_Action
	 * 기능 : user화면에서 qna를 눌럿을 경우 해당창을 보여준다.
	 * @author HoyoungJi(지호영)
	 */
	static class btnUserQnA_Action implements ActionListener{
		public void actionPerformed(ActionEvent e){
			sendQna qna = new sendQna();
			qna.userQna();
		}
	}
	/**
	 * 클래스명  : btnGuestQnA_Action
	 * 기능 : guest화면에서 qna를 눌럿을 경우 해당창을 보여준다.
	 * @author HoyoungJi(지호영)
	 */
	static class btnGuestQnA_Action implements ActionListener{
		public void actionPerformed(ActionEvent e){
			sendGuestQna qna = new sendGuestQna();
			qna.guestQna();
		}
	}
	
}
