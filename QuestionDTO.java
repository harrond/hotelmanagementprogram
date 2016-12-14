import java.sql.Date;


/**
 * author : 이주혁
 * communicating Mysql with QuestionDTO object
 */
public class QuestionDTO {
	private String name;
	private String title;
	private String question;
	private String answer;
	private int state;
	private int id;
	private Date date;
	private String userId;
	
	public QuestionDTO(String name, String title, String q,String answer,int state,int id,Date date,String userId){
		super();
		this.name = name;
		this.title = title;
		this.question = q;
		this.answer = answer;
		this.state = state;
		this.id = id;
		this.date = date;
		this.userId = userId;
	}
	
	public QuestionDTO(){
		super();
	}

	/**
	 * get name
	 */
	public String getName(){
		return name;
	}

	/**
	 * set name
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * get data
	 */
	public Date getDate(){
		return date;
	}

	/**
	 * set data
	 */
	public void setDate(Date date){
		this.date=date;
	}

	/**
	 * get title
	 */
	public String getTitle(){
		return title;
	}

	/**
	 * set title
	 */
	public void setTitle(String title){
		this.title=title;
	}

	/**
	 * get question
	 */
	public String getQuestion(){
		return question;
	}

	/**
	 * set question
	 */
	public void setQuestion(String question){
		this.question=question;
	}

	/**
	 * get answer
	 */
	public String getAnswer(){
		return answer;
	}

	/**
	 * set answer
	 */
	public void setAnswer(String answer){
		this.answer = answer;
	}

	/**
	 * get state
	 */
	public int getState(){
		return state; 
	}

	/**
	 * set state
	 */
	public void setState(int state){
		this.state = state;
	}

	/**
	 * get id
	 */
	public int getId(){
		return id;
	}

	/**
	 * set id
	 */
	public void setId(int id){
		this.id=id;
	}

	/**
	 * get userId
	 */
	public String getUserId(){
		return userId;
	}

	/**
	 * set userId
	 */
	public void setUserId(String userId){
		this.userId=userId;
	}
}
