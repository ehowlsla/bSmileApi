package resModels;

import java.util.Date;

import models.Reply;
import models.User;

public class ResReply {
	
	public long id;
	public ResUser user;
	public long content_id;
	public String replyContents;
	public Date createDate;
	public char status;
	
	public ResReply(Reply obj) {
		// TODO Auto-generated constructor stub
		this.id = obj.id;
		this.user = new ResUser(obj.user);
		this.content_id = obj.content_id;
		this.replyContents = obj.replyContents;
		this.createDate = obj.createDate;
		this.status = obj.status;
	}
}
