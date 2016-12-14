

import java.sql.Date;
/**
 * author : 이주혁
 * communicating Mysql with CurrentDTO object
 */
public class CurrentDTO {
	private int roomNum;
	private String name;
	private String userId;
	private String service;
	private Date checkinDate;
	private Date checkoutDate;
	private int state;
	
	public CurrentDTO(int roomNum, String name, String userId,String service, Date checkinDate, Date checkoutDate, int state) {
		super();
		this.roomNum = roomNum;
		this.name = name;
		this.userId = userId;
		this.service = service;
		this.checkinDate = checkinDate;
		this.checkoutDate = checkoutDate;
		this.state = state;
	}
	public CurrentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * getUserId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * setUserId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * getRoomNum
	 */
	public int getRoomNum() {
		return roomNum;
	}
	/**
	 * setRoomNum
	 */
	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}
	/**
	 * getName
	 */
	public String getName() {
		return name;
	}
	/**
	 * setName
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * getService
	 */
	public String getService() {
		return service;
	}
	/**
	 * setService
	 */
	public void setService(String service) {
		this.service = service;
	}
	/**
	 * get check-in date
	 */
	public Date getCheckinDate() {
		return checkinDate;
	}
	/**
	 * set check-in date
	 */
	public void setCheckinDate(Date checkinDate) {
		this.checkinDate = checkinDate;
	}
	/**
	 * get check-out date
	 */
	public Date getCheckoutDate() {
		return checkoutDate;
	}
	/**
	 * set check-out date
	 */
	public void setCheckoutDate(Date checkoutDate) {
		this.checkoutDate = checkoutDate;
	}
	/**
	 * getState
	 */
	public int getState() {
		return state;
	}
	/**
	 * setState
	 */
	public void setState(int state) {
		this.state = state;
	}
	
	
}
