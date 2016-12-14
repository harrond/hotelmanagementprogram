import java.sql.Date;

/**
 * author : 이주혁
 * communicating Mysql with UserDTO object
 */
public class UserDTO {
	private String userId;
	private String password;
	private String name;
	private Date birth;
	private String phone;
	private String info;
	private String userGrade;
	private String sex;
	private String email;
	private int visit;

	public UserDTO(String userId, String password, String name, Date birth, String phone, String info, String userGrade,
			int visit, String sex, String email) {
		super();
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.birth = birth;
		this.phone = phone;
		this.info = info;
		this.userGrade = userGrade;
		this.visit = visit;
		this.sex = sex;
		this.email = email;
	}

	public UserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * get UserId
	 */
	public String getUserId() {
		return userId;
	}
	
	/**
	 * set userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * get password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * set password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * get name
	 */
	public String getName() {
		return name;
	}

	/**
	 * set name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * get birth
	 */
	public Date getBirth() {
		return birth;
	}

	/**
	 * set birth
	 */
	public void setBirth(Date birth) {
		this.birth = birth;
	}

	/**
	 * get phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * set phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * get info
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * wet info
	 */
	public void setInfo(String info) {
		this.info = info;
	}

	/**
	 * get userGrade
	 */
	public String getUserGrade() {
		return userGrade;
	}

	/**
	 * set userGrade
	 */
	public void setUserGrade(String userGrade) {
		this.userGrade = userGrade;
	}

	/**
	 * get visit
	 */
	public int getVisit() {
		return visit;
	}

	/**
	 * set visit
	 */
	public void setVisit(int visit) {
		this.visit = visit;
	}

	/**
	 * get sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * set sex
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * get email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * set email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
