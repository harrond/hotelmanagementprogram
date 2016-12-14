import java.util.Vector;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
/**
 * This class is related to connecting with Mysql
 */
public class MysqlConnection {

	public Connection conn;
	private PreparedStatement preparedStatement;
	public static MysqlConnection db;
	
	//this is command of insert which is used in mysql
	public static final String insertUser = "insert user_Db (user_id,password,name,sex,birth,phone,email,info,user_grade,visit) values (?,?,?,?,?,?,?,?,?,?)";
	//this is command of select which is used in mysql
	public static final String selectUser = "select * from user_db where user_id =?";
	//this is command of update which is used in mysql
	public static final String updateUser = "update user_db set password=?, name=?,sex=?,birth=?,phone=?,email=?,info=? where user_id=?";
	
	//this is command of update which is used in mysql
	public static final String updateRoomInfo = "update room_db set room_state=?,room_price=?,room_info=?,room_grade=? where room_num=?";
	//this is command of insert which is used in mysql
	public static final String insertRoomInfo = "insert room_Db (room_num,room_state,room_price,room_info,room_grade) values (?,?,?,?,?)";
	//this is command of select which is used in mysql
	public static final String selectRoom = "select * from room_db where room_num =?";
	
	//this is command which is used in mysql
	public static final String insertRoomService = "insert roomservice_Db (kind, date, name, price) values (?,?,?,?)";
	//this is command of select which is used in mysql
	public static final String selectRoomService = "select * from roomservice_db where name =?";
	
	//this is command which is used in mysql
	public static final String insertBooking = "insert into booking_Db (start_day,end_day,room_num,user_name,service,price,headcount,guest_name,phone) values (?,?,?,?,?,?,?,?,?)";
	//this is command of select id related to booking_db
	public static final String selectBooking = "select * from booking_Db where id =?";
	//this is command of select room_num related to booking_db
	public static final String selectBookings = "select * from booking_Db where room_num = ?";
	//this is command of delete which is used in mysql
	public static final String deleteBooking = "delete from booking_db where id = ?";
	//this is command of select phone and start day related booking_db
	public static final String selectBookingID = "select * from booking_Db where phone =?and start_day=? ";
	//this is command of select phone related booking_db
	public static final String selectDay = "select * from booking_db where phone=?";
	
	//this is command which is used in mysql
	public static final String updateCurrent = "update current_db set name=?,user_id=?,service=?,checkin_date=?,checkout_date=?,state=? where room_num=?";
	//this is command of insert which is used in mysql
	public static final String insertCurrent = "insert current_db (room_num,name,user_id,service,checkin_date,checkout_date,state) values (?,?,?,?,?,?,?)";
	//this is command of select room_num related current_db
	public static final String selectCurrent = "select * from current_db where room_num =?";
	
	//this is command which is used in mysql
	public static final String insertGuest = "insert guest_Db (name, password, phone, email, info) values (?,?,?,?,?)";
	//this is command of select phone related guest_db
	public static final String selectGuest = "select * from guest_db where phone =?";
	//this is command of delete which is used in mysql
	public static final String deleteGuest = "delete from guest_db where name =?";
	
	//this is command which is used in mysql
	public static final String updateQuestion = "update QnA_db set title=?, question=?,name=?,state=?,answer=?,date=? where q_id=?;";
	//this is command which is used in mysql
	public static final String insertQuestion = "insert into QnA_Db (title,question,name,state,answer,date) value (?,?,?,?,?,?)";
	//this is command which is used in mysql
	public static final String selectQuestion = "select * from QnA_Db where q_id = ?";
	public static final String checkId = "select user_id from user_db;";
	
	//This constructer is information connecting with mysql  
	public MysqlConnection() {
		//url is address of mysql
		String url = "jdbc:mysql://localhost:3306/";
		String dbName = "hotel_Db";//mysql DB name
		String driver = "com.mysql.jdbc.Driver";
		String userName = "root";//mysql user name
		String password = "wlghdud123";//mysql password
		try {
			Class.forName(driver).newInstance();
			this.conn = DriverManager.getConnection(url + dbName, userName, password);
		} catch (Exception sqle) {
			sqle.printStackTrace();

		}
	}
	/**
	 * using synchronized because of preventing overlap
	 */
	public static synchronized MysqlConnection getDbcom() {
		if (db == null) {
			db = new MysqlConnection();
		}
		return db;
	}
	/**
	 * This method inserts UserDTO object into mysql 
	 */
	public void insertUser(UserDTO userInfo) {
		try {
			//using command to insert preparedStatement mysql DB
			preparedStatement = conn.prepareStatement(insertUser);
			preparedStatement.setString(1, userInfo.getUserId());
			preparedStatement.setString(2, userInfo.getPassword());
			preparedStatement.setString(3, userInfo.getName());
			preparedStatement.setString(4, userInfo.getSex());
			preparedStatement.setDate(5, userInfo.getBirth());
			preparedStatement.setString(6, userInfo.getPhone());
			preparedStatement.setString(7, userInfo.getEmail());
			preparedStatement.setString(8, userInfo.getInfo());
			preparedStatement.setString(9, userInfo.getUserGrade());
			preparedStatement.setInt(10, userInfo.getVisit());
			//preparedStatement is changed into userInfo(object)
			
			preparedStatement.executeUpdate();//update mysql

			preparedStatement.close();//close preparedStatement

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * This method updates RoomInfo object into mysql 
	 */
	public void updateRoomInfo(RoomDTO ri) {
		try {
			//using command to update preparedStatement mysql DB
			preparedStatement = conn.prepareStatement(updateRoomInfo);
			preparedStatement.setInt(1, ri.getRoomState());
			preparedStatement.setInt(2, ri.getRoomPrice());
			preparedStatement.setString(3, ri.getRoomInfo());
			preparedStatement.setString(4, ri.getRoomGrade());
			preparedStatement.setInt(5, ri.getRoomNum());
			//preparedStatement is changed into ri(RoomDTO object)
			
			preparedStatement.executeUpdate();//update mysql

			preparedStatement.close();//close preparedStatement
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method inserts RoomInfo object into mysql 
	 */
	public void insertRoomInfo(RoomDTO ri) {
		try {
			//using command to insert preparedStatement mysql DB
			preparedStatement = conn.prepareStatement(insertRoomInfo);
			preparedStatement.setInt(1, ri.getRoomNum());
			preparedStatement.setInt(2, ri.getRoomState());
			preparedStatement.setInt(3, ri.getRoomPrice());
			preparedStatement.setString(4, ri.getRoomInfo());
			preparedStatement.setString(5, ri.getRoomGrade());
			//preparedStatement is changed into ri(RoomDTO object)
			
			preparedStatement.executeUpdate();//update mysql

			preparedStatement.close();//close preparedStatement
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method updates RoomInfo object into mysql 
	 */
	public RoomDTO selectRoomNum(int roomNum) {
		try {
			//using command to select preparedStatement mysql DB
			preparedStatement = conn.prepareStatement(selectRoom);
			preparedStatement.setInt(1, roomNum);
			RoomDTO ri = new RoomDTO();
			ResultSet rs = preparedStatement.executeQuery();
			
			//load mysql attribute until no more attribute is showed 
			while (rs.next()) {//load mysql attribute until no more attribute is showed 
				ri.setRoomNum(rs.getInt("room_num"));
				ri.setRoomState(rs.getInt("room_state"));
				ri.setRoomPrice(rs.getInt("room_price"));
				ri.setRoomInfo(rs.getString("room_info"));
				ri.setRoomGrade(rs.getString("room_grade"));
			}
			rs.close();
			return ri;//return object
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This method inserts GuestDTO object into mysql 
	 */
	public void insertGuest(GuestDTO rs) {
		try {
			preparedStatement = conn.prepareStatement(insertGuest);
			preparedStatement.setString(1, rs.getName());
			preparedStatement.setString(2, rs.getPassword());
			preparedStatement.setString(3, rs.getPhone());
			preparedStatement.setString(4, rs.getEmail());
			preparedStatement.setString(5, rs.getInfo());
			preparedStatement.executeUpdate();

			preparedStatement.close();
			//conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method selects Guest DB by name
	 */
	public GuestDTO selectGuest(String name) {
		try {
			preparedStatement = conn.prepareStatement(selectGuest);
			preparedStatement.setString(1, name);
			GuestDTO ri = new GuestDTO();
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				ri.setName(rs.getString("name"));
				ri.setPassword(rs.getString("password"));
				ri.setPhone(rs.getString("phone"));
				ri.setEmail(rs.getString("email"));
				ri.setInfo(rs.getString("info"));
			}
			rs.close();
			return ri;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * This method inserts BookingDTO object into mysql 
	 */
	public void insertBooking(BookingDTO rs) {
		try {
			preparedStatement = conn.prepareStatement(insertBooking);
			preparedStatement.setDate(1, rs.getStartDay());
			preparedStatement.setDate(2, rs.getEndDay());
			preparedStatement.setInt(3, rs.getRoomNum());
			preparedStatement.setString(4, rs.getUserName());
			preparedStatement.setString(5, rs.getService());
			preparedStatement.setInt(6, rs.getPrice());
			preparedStatement.setInt(7, rs.getHeadcount());
			preparedStatement.setString(8, rs.getGuestName());
			preparedStatement.setString(9, rs.getPhoneNumber());
			preparedStatement.executeUpdate();

			preparedStatement.close();
			//conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method deletes Booking DB 
	 */
	public void deleteBooking(int id){
		try{
			preparedStatement = conn.prepareStatement(deleteBooking);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();

			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method selects Booking DB of the room and gets ArrayList of BookingDTO object.
	 */
	public ArrayList<BookingDTO> selectBookings(int room_num){
		ArrayList<BookingDTO> tempList=new ArrayList<BookingDTO>();
		try{
			preparedStatement = conn.prepareStatement(selectBookings);
			preparedStatement.setInt(1, room_num);
			BookingDTO ri;
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				ri = new BookingDTO();
				ri.setStartDay(rs.getDate("start_day"));
				ri.setEndDay(rs.getDate("end_day"));
				ri.setRoomNum(rs.getInt("room_num"));
				ri.setUserName(rs.getString("user_name"));
				ri.setService(rs.getString("service"));
				ri.setPrice(rs.getInt("price"));
				ri.setHeadcount(rs.getInt("headcount"));
				ri.setGuestName(rs.getString("guest_name"));
				ri.setPhoneNumber(rs.getString("phone"));
				ri.setId(rs.getInt("id"));
				tempList.add(ri);
			}
			rs.close();
			return tempList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * This method selects Booking DB by id
	 */
	public BookingDTO selectBooking(int id) {
		try {
			preparedStatement = conn.prepareStatement(selectBooking);
			preparedStatement.setInt(1, id);
			BookingDTO ri = new BookingDTO();
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				ri.setStartDay(rs.getDate("start_day"));
				ri.setEndDay(rs.getDate("end_day"));
				ri.setRoomNum(rs.getInt("room_num"));
				ri.setUserName(rs.getString("user_name"));
				ri.setService(rs.getString("service"));
				ri.setPrice(rs.getInt("price"));
				ri.setHeadcount(rs.getInt("headcount"));
				ri.setGuestName(rs.getString("guest_name"));
				ri.setId(rs.getInt("id"));
				ri.setPhoneNumber(rs.getString("phone"));
			}
			rs.close();
			if(ri.getId()==0) return null;
			return ri;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * This method inserts QuestionDTO object into mysql 
	 */
	public void insertQuestion(QuestionDTO rs){
		
		try{
			preparedStatement = conn.prepareStatement(insertQuestion);
			preparedStatement.setString(1, rs.getTitle());
			preparedStatement.setString(2, rs.getQuestion() );
			preparedStatement.setString(3, rs.getName());
			preparedStatement.setInt(4, rs.getState());
			preparedStatement.setString(5, rs.getAnswer());
			preparedStatement.setDate(6, rs.getDate());
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			//conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * This method selects Question DB by id
	 */
	public QuestionDTO selectQuestion(int id){
		try{
			preparedStatement = conn.prepareStatement(selectQuestion);
			preparedStatement.setInt(1, id);
			QuestionDTO ri = new QuestionDTO();
			ResultSet rs = preparedStatement.executeQuery();
			ri.setId(-1);
			while(rs.next()){
				ri.setTitle(rs.getString("title"));
				ri.setQuestion(rs.getString("question"));
				ri.setId(rs.getInt("q_id"));
				ri.setName(rs.getString("name"));
				ri.setState(rs.getInt("state"));
				ri.setDate(rs.getDate("date"));
				ri.setAnswer(rs.getString("answer"));
			}
			rs.close();
			if(ri.getId()==-1) return null;
			return ri;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * This method updates Question DB
	 */
	public void updateQuestionInfo(QuestionDTO rs) {
		try {
			preparedStatement = conn.prepareStatement(updateQuestion);
			preparedStatement.setString(1, rs.getTitle());
			preparedStatement.setString(2, rs.getQuestion() );
			preparedStatement.setString(3, rs.getName());
			preparedStatement.setInt(4, rs.getState());
			preparedStatement.setString(5, rs.getAnswer());
			preparedStatement.setDate(6, rs.getDate());
			preparedStatement.setInt(7, rs.getId());
			preparedStatement.executeUpdate();

			preparedStatement.close();
		//	conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method inserts CurrentDTO object into mysql 
	 */
	public void insertCurrent(CurrentDTO rs) {
		try {
			preparedStatement = conn.prepareStatement(insertCurrent);
			preparedStatement.setInt(1, rs.getRoomNum());
			preparedStatement.setString(2, rs.getName());
			preparedStatement.setString(3, rs.getUserId());
			preparedStatement.setString(4, rs.getService());
			preparedStatement.setDate(5, rs.getCheckinDate());
			preparedStatement.setDate(6, rs.getCheckoutDate());
			preparedStatement.setInt(7, rs.getState());
			preparedStatement.executeUpdate();

			preparedStatement.close();
			//conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method selects Current DB by room_num
	 */
	public CurrentDTO selectCurrent(int room_num) {
		try {
			preparedStatement = conn.prepareStatement(selectCurrent);
			preparedStatement.setInt(1, room_num);
			CurrentDTO ri = new CurrentDTO();
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				ri.setRoomNum(rs.getInt("room_num"));
				ri.setName(rs.getString("name"));
				ri.setUserId(rs.getString("user_id"));
				ri.setService(rs.getString("service"));
				ri.setCheckinDate(rs.getDate("checkin_date"));
				ri.setCheckoutDate(rs.getDate("checkout_date"));
				ri.setState(rs.getInt("state"));
			}
			rs.close();
			return ri;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> checkId() {
		try {
			ArrayList<String> id_List = new ArrayList<String>();
			id_List.clear();
			preparedStatement = conn.prepareStatement(checkId);
			ResultSet rs = preparedStatement.executeQuery();
			int i = 1;
			while (rs.next()) {
				id_List.add(rs.getString(i));
			}

			return id_List;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String userEmailAccept(String Id) {
		try{
			String emailAddress;
			preparedStatement = conn.prepareStatement(selectUser);
			preparedStatement.setString(1, Id);
			ResultSet rs = preparedStatement.executeQuery();
			emailAddress = rs.getString(7);
			return emailAddress;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public Vector<String> selectBookingDay(String phone) {
		try {
			Vector<String> day_List = new Vector<String>();
			day_List.clear();
			preparedStatement = conn.prepareStatement(selectDay);
			preparedStatement.setString(1, phone);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				day_List.addElement(rs.getString("start_day"));
			}

			return day_List;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * This method select UserId DB by id
	 */
	public UserDTO selectUserId(String id) {
		try {
			preparedStatement = conn.prepareStatement(selectUser);
			preparedStatement.setString(1, id);
			UserDTO t = new UserDTO();
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				t.setUserId(rs.getString("user_id"));
				t.setPassword(rs.getString("password"));
				t.setName(rs.getString("name"));
				t.setSex(rs.getString("sex"));
				t.setBirth(rs.getDate("birth"));
				t.setPhone(rs.getString("phone"));
				t.setEmail(rs.getString("email"));
				t.setInfo(rs.getString("info"));
				t.setUserGrade(rs.getString("user_grade"));
				t.setVisit(rs.getInt("visit"));
			}

			rs.close();
			return t;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * This method updates User DB
	 */
	public void updateUser(UserDTO ud) { // �쉶�썝�젙蹂� �닔�젙 硫붿냼�뱶
		try {
			preparedStatement = conn.prepareStatement(updateUser);
			preparedStatement.setString(8, ud.getUserId());
			preparedStatement.setString(1, ud.getPassword());
			preparedStatement.setString(2, ud.getName());
			preparedStatement.setString(3, ud.getSex());
			preparedStatement.setDate(4, ud.getBirth());
			preparedStatement.setString(5, ud.getPhone());
			preparedStatement.setString(6, ud.getEmail());
			preparedStatement.setString(7, ud.getInfo());

			preparedStatement.executeUpdate();

			preparedStatement.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method selects Booking DB by phone and startDay
	 */
	public int selectBookingId(String phone, String startDay){
		try { 

			int id = 0;
			preparedStatement = conn.prepareStatement(selectBookingID);
			preparedStatement.setString(1, phone);
			preparedStatement.setString(2, startDay);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
			id = rs.getInt(10);
			}
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * This method selects Booking DB by phone
	 */
	public int selectBookingId(String phone){
		try { 

			int id = 0;
			preparedStatement = conn.prepareStatement(selectBookingID);
			preparedStatement.setString(1, phone);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
			id = rs.getInt(10);
			}
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * This method deletes Guest DB which is same as pharameter name
	 */
	public void deleteGuest(String name) {
		try {
			preparedStatement = conn.prepareStatement(deleteGuest);
			preparedStatement.setString(1, name);
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method updates Current DB
	 */
	public void updateCurrent(CurrentDTO rs) {
		
		try {
			preparedStatement = conn.prepareStatement(updateCurrent);
			preparedStatement.setString(1, rs.getName());
			preparedStatement.setString(2, rs.getUserId());
			preparedStatement.setString(3, rs.getService());
			preparedStatement.setInt(7, rs.getRoomNum());
			preparedStatement.setDate(4, rs.getCheckinDate());
			preparedStatement.setDate(5, rs.getCheckoutDate());
			preparedStatement.setInt(6, rs.getState());
			preparedStatement.executeUpdate();

			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
