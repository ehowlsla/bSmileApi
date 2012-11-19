package resModels;

import java.util.Date;

import models.Recommand;

public class ResRecommand {

	public String user_id;
	public String content_id;
	public Date createDate;
	public char status; 
	
	public ResRecommand(Recommand obj) {
		// TODO Auto-generated constructor stub
		this.user_id = obj.user_id;
		this.content_id = obj.content_id;
		this.createDate = obj.createDate;
		this.status = obj.status;
	}
}
