package resModels;

import java.util.Date;

 
import models.Content;
import models.User;
 
public class ResContent {

	public long id;
	
 	public String title;
	
 	public String contents;
	
 	public Date createDate;
	
	public int recCount;
	
	public int replyCount;
	
 	public String image;
	
 	public char status;

 	public long user_id;
	
	private final static int pSize = 10;
	
	public ResContent(Content obj) {
		// TODO Auto-generated constructor stub
		this.id = obj.id;
		this.title = obj.title;
		this.contents = obj.contents;
		this.createDate = obj.createDate;
		this.recCount = obj.recCount;
		this.replyCount = obj.replyCount;
		this.image = obj.image;
		this.status = obj.status;
		this.user_id = obj.user.id;
	}
}
