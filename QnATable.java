import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;


/**
 * 클래스 명 : QnATable
 * 기능 : 답변이 안된 질문들을 모아 테이블로 보여주고 하나를 선택해 답변을 보내는 기능을 가진다.
 * @author HoyoungJI(지호영)
 *
 */
public class QnATable {

	static JFrame qnaFrame, detailQnAFrame; //질문을 모은 화면 프레임과 하나의 질문을 자세히 보여주는 프레임
	static JPanel pnl, detailPnl; //각 버튼이나 label들이 올라오는 panel
	static Container pane, detailPane;
	static JTable qnaList; // 질문 들 table
	static JScrollPane scrollList;//
	static DefaultTableModel defaultTB;
	static MysqlConnection db;//db연결
	static JButton sendAnswer; //답변버튼
	static JTextArea answerText;//답변 필드
	static String qnaEmail = new String(); //email필드

	public QnATable() {
		// Look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (UnsupportedLookAndFeelException e) {
		}
		db = db.getDbcom();
	}

	/**
	 *  메소드명 : qnaTable
	 *  기능 : 답변이 안된 질문들을 모아 테이블형식의 리스트로 보여준다.
	 * 
	 */
	public void qnaTable() {

		
		qnaFrame = new JFrame("Q&A");
		qnaFrame.setSize(600, 700);
		pane = qnaFrame.getContentPane();
		pane.setLayout(null);

		defaultTB = new DefaultTableModel() {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		qnaList = new JTable(defaultTB);
		scrollList = new JScrollPane(qnaList);

		pnl = new JPanel(null);
		

		Dimension frameSize = qnaFrame.getSize();// 프레임 사이즈
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // 내컴퓨터화면 사이즈																	
		qnaFrame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2); // 화면중앙에 배치
																													
		//질문 리스트 panel에 추가
		pane.add(pnl);
		pnl.add(scrollList);

		//위치와 크기 설정
		pnl.setSize(600, 700);
		scrollList.setBounds(40, 50, 500, 550);

		qnaFrame.setResizable(false);
		qnaFrame.setVisible(true);

		//table cell 선택허용
		qnaList.setColumnSelectionAllowed(true);
		qnaList.setRowSelectionAllowed(true);
		qnaList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		qnaList.getTableHeader().setResizingAllowed(false);  //테이블 칼럼의 사이즈 변경을 방지
		qnaList.getTableHeader().setReorderingAllowed(false); //테이블 칼럼의 이동을 방지

		qnaList.setRowHeight(30);//높이
		defaultTB.setColumnCount(4);//칼럼갯수

		String[] arr = { "Id", "Name", "Title", "Date" }; //테이블 top 칼럼명

		defaultTB.setColumnIdentifiers(arr);
		qnaList.addMouseListener(new qnaMouseListener());
		int i = 1;
		//반복문을 통해서 답변이 안된 질문을 테이블에 추가시킴
		while (true) {
			if (db.selectQuestion(i) == null)//null이 반환되는 경우. (질문이 없는 경우)
				break;
			if (db.selectQuestion(i).getState() == 0) { //답변이 안된 질문의 경우
				if (db.selectQuestion(i).getUserId() == null) { // 손님일 경우
					Object[] cellDate = { db.selectQuestion(i).getId(), db.selectQuestion(i).getName(),
							db.selectQuestion(i).getTitle(), db.selectQuestion(i).getDate() };
					defaultTB.addRow(cellDate); //테이블에 등록
				} else { // 회원 일 경우
					Object[] cellDate = { db.selectQuestion(i).getId(), db.selectQuestion(i).getName(),
							db.selectQuestion(i).getTitle(), db.selectQuestion(i).getDate() };
					defaultTB.addRow(cellDate); //테이블에 등록
				}
			}
			i++;
		}

	}

	/**
	 *  메소드명 : selectQuestion
	 *  기능 : 질문을 선택을 하였을때 그질문의 내용을 창을 띄워 보여주고 답변을 남길 수 있게 한다.
	 * @param questionId 질문의 id (db에서 pk로서 질문을 검색할때 쓴다.)
	 */
	public void selectQuestion(int questionId) {
		//frame과 버튼 객체생성
		detailQnAFrame = new JFrame("Show Question.!");
		detailQnAFrame.setSize(700, 900);
		detailPane = detailQnAFrame.getContentPane();
		detailPane.setLayout(null);
		detailPnl = new JPanel(null);
		sendAnswer = new JButton("Reply");

		QuestionDTO rs = db.selectQuestion(questionId); //매개변수로 넘어온 ID로 질문을 검색한다.
		if (rs.getUserId() == null) {//유저의 경우
			qnaEmail = db.selectGuest(rs.getName()).getEmail();
		} else {//손님의 경우
			qnaEmail = db.selectUserId(rs.getUserId()).getEmail();
		}
		JTextArea text = new JTextArea();
		answerText = new JTextArea();
		JScrollPane answerScroll = new JScrollPane(answerText);
		JScrollPane textScroll = new JScrollPane(text);
		JLabel title, id, date, name;

		//질문을 올린 정보들을 label에 넣는다.
		title = new JLabel(rs.getTitle());
		id = new JLabel(rs.getId() + "");
		date = new JLabel(rs.getDate().toString());
		name = new JLabel(rs.getName());

		//각 요소들을 panel에 추가/////////
		detailPane.add(detailPnl);
		detailPnl.add(textScroll);
		detailPnl.add(title);
		detailPnl.add(id);
		detailPnl.add(date);
		detailPnl.add(name);
		detailPnl.add(sendAnswer);
		detailPnl.add(answerScroll);
		////////////////////////////
		
		//답변완료 버튼을 눌럿을때의 이벤트를 등록
		sendAnswer.addActionListener(new btnAnswer_Action(questionId));

		text.setText(rs.getQuestion());//질문을 text area에 넣는다.
		text.setEditable(false);//질문 내용이 보이는 text area는 수정이 불가능 하게 한다.
		text.setLineWrap(true);//자동줇바꿈
		answerText.setLineWrap(true);//자동줄바꿈

		
		//label들과 text공간 위치 설정
		detailPnl.setBounds(0, 0, 700, 900);
		id.setBounds(50, 30, 20, 30);
		name.setBounds(100, 30, 60, 30);
		title.setBounds(300, 30, 300, 30);
		date.setBounds(600, 30, 100, 30);
		textScroll.setBounds(100, 100, 500, 400);
		sendAnswer.setBounds(290, 770, 80, 50);
		answerScroll.setBounds(100, 550, 500, 200);

		//font 사이즈 설정
		id.setFont(new Font("Arial", Font.BOLD, 25));
		title.setFont(new Font("Arial", Font.BOLD, 25));
		sendAnswer.setFont(new Font("Arial", Font.BOLD, 15));

		detailQnAFrame.setResizable(false); // 프레임 사이즈 조정을 못하게 한다.
		detailQnAFrame.setVisible(true); //프레임을 보이게한다.
		// detailQnAFrame.dispose();
	}

	/**
	 * 클래스 명 : btnAnswer_Action
	 * 기능 : 답변 버튼을 눌렀을 경우 이메일로 답변을 보내고 DB에 답변내용을 등록하고 상태를 답변함으로 바꾼다.
	 * @author HoyoungJI(지호영)
	 */
	class btnAnswer_Action implements ActionListener {
		private int id;

		public btnAnswer_Action() {
			super();
		}

		public btnAnswer_Action(int id) {
			super();
			this.id = id;
		}

		public void actionPerformed(ActionEvent e) {
			emailSystem es = new emailSystem();
			detailQnAFrame.dispose(); //현 프레임창만 종료 자원해제
			QuestionDTO temp;
			temp = db.selectQuestion(id); //db에서 id로 질문을 가져옴
			temp.setState(1);//답변함 상태로 바꾼다.
			temp.setAnswer(answerText.getText()); //가져온 질문에 답변을 등록한다.
			db.updateQuestionInfo(temp); //수정내용을 다시 db로 반영한다.
			es.sendEmail("hotel@hotel.com", qnaEmail, "질문에 대한 답변입니다.", answerText.getText());
			if (temp.getUserId() == null && db.selectGuest(temp.getName()).getPassword() == null) { // 비밀번호를 설정한 예약한 손님이면 손님정보를 삭제하지 않는다
	
				db.deleteGuest(temp.getName());
			}

			//detailQnAFrame.setVisible(false);

		}
	}

	/**
	 * 클래스 명 : qnaMouseListener
	 * 기능 : 답변이 안된 질문을 모아놓은 테이블에서 질문을 눌렀을때 해당질문 상세내용과 답변을 보내는 창을 연다.
	 * @author HoyoungJI(지호영)
	 */
	private class qnaMouseListener implements MouseListener {

		public void mouseClicked(MouseEvent e) {
			int row = qnaList.getSelectedRow();
			String id = defaultTB.getValueAt(row, 0).toString(); //id값을 받음

			selectQuestion(Integer.parseInt(id)); //상세 내용과 답변을 보낼수 잇는 창을 연다.
		}

		public void mouseEntered(MouseEvent e) {

		}

		public void mouseExited(MouseEvent e) {

		}

		public void mousePressed(MouseEvent e) {

		}

		public void mouseReleased(MouseEvent e) {

		}

	}
}
