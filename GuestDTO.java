
/**
 * author : 이주혁
 * communicating Mysql with GusetDTO object
 */
public class GuestDTO {
	private String name;
	private String password;
	private String phone;
	private String email;
	private String info;
	
	public GuestDTO(String name, String password, String phone, String email, String info) {
		super();
		this.name = name;
		this.password = password;
		this.phone = phone;
		this.email = email;
		this.info = info;
	}
	public GuestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * get Name
	 */
	public String getName() {
		return name;
	}
	/**
	 * set Name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * get Password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * set Password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * get Phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * set Phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * get Email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * set Email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * get Info
	 */
	public String getInfo() {
		return info;
	}
	/**
	 * set Info
	 */
	public void setInfo(String info) {
		this.info = info;
	}
	
	
}
