/**
 * author : 이주혁
 * communicating Mysql with RoomDTO object
 */

public class RoomDTO {
	private int roomNum;
	private int roomState;
	private int roomPrice;
	private String roomInfo;
	private String roomGrade;
	
	public RoomDTO(int roomNum, int roomState, int roomPrice, String roomInfo, String roomGrade) {
		super();
		this.roomNum = roomNum;
		this.roomState = roomState;
		this.roomPrice = roomPrice;
		this.roomInfo = roomInfo;
		this.roomGrade = roomGrade;
	}

	public RoomDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * get roomNum
	 */
	public int getRoomNum() {
		return roomNum;
	}

	/**
	 * set roomNum
	 */
	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}
	
	/**
	 * get roomState 
	 */
	public int getRoomState() {
		return roomState;
	}
	
	/**
	 * set roomState 
	 */
	public void setRoomState(int roomState) {
		this.roomState = roomState;
	}

	/**
	 * get roomPrice 
	 */
	public int getRoomPrice() {
		return roomPrice;
	}

	/**
	 * set roomPrice 
	 */
	public void setRoomPrice(int roomPrice) {
		this.roomPrice = roomPrice;
	}
	
	/**
	 * get roomInfo 
	 */
	public String getRoomInfo() {
		return roomInfo;
	}

	/**
	 * set roomInfo 
	 */
	public void setRoomInfo(String roomInfo) {
		this.roomInfo = roomInfo;
	}
	
	/**
	 * get roomGrade 
	 */
	public String getRoomGrade() {
		return roomGrade;
	}

	/**
	 * set roomGrade 
	 */
	public void setRoomGrade(String roomGrade) {
		this.roomGrade = roomGrade;
	}
	
}
