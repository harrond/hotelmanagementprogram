

import java.sql.Date;
/**
 * author : 이주혁
 * communicating Mysql with BookingDTO object
 */
public class BookingDTO {
	private Date startDay;
	private Date endDay;
	private int roomNum;
	private String userName;
	private String service;
	private int price;
	private int headcount;
	private String guestName;
	private int id;
	private String phoneNum;
	
	public BookingDTO(Date startDay, Date endDay, int roomNum, String userName, String service, int price, int headcount,
			String guestName, int id,String phone) {
		super();
		this.id = id;
		this.startDay = startDay;
		this.endDay = endDay;
		this.roomNum = roomNum;
		this.userName = userName;
		this.service = service;
		this.price = price;
		this.headcount = headcount;
		this.guestName = guestName;
		this.phoneNum = phone;
	}
	public BookingDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * getPhoneNumber
	 */
	public String getPhoneNumber(){
		return phoneNum;
	}
	/**
	 * setPhoneNumber
	 */
	public void setPhoneNumber(String phone){
		this.phoneNum = phone;
	}
	/**
	 * getId
	 */
	public int getId(){
		return id;
	}
	/**
	 * setId
	 */
	public void setId(int id){
		this.id = id;
	}
	/**
	 * getStartDay
	 */
	public Date getStartDay() {
		return startDay;
	}
	/**
	 * setStartDay
	 */
	public void setStartDay(Date startDay) {
		this.startDay = startDay;
	}
	/**
	 * getEndDay
	 */
	public Date getEndDay() {
		return endDay;
	}
	/**
	 * setEndDay
	 */
	public void setEndDay(Date endDay) {
		this.endDay = endDay;
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
	 * getUserName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * setUserName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
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
	 * getPrice
	 */
	public int getPrice() {
		return price;
	}
	/**
	 * setPrice
	 */
	public void setPrice(int price) {
		this.price = price;
	}
	/**
	 * getHeadcount
	 */
	public int getHeadcount() {
		return headcount;
	}
	/**
	 * setHeadcount
	 */
	public void setHeadcount(int headcount) {
		this.headcount = headcount;
	}
	/**
	 * getGuestName
	 */
	public String getGuestName() {
		return guestName;
	}
	/**
	 * setGuestName
	 */
	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}
	
	
}