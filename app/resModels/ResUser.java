package resModels;

import java.util.Date;

import models.User;

public class ResUser {
	
	public long id;
	public String nickname;
	public String udid;
	public String image;
	public char status;
	public Date createDate;

	public ResUser(User obj) {
		// TODO Auto-generated constructor stub
		this.id = obj.id;
		this.nickname = obj.nickname;
		this.udid = obj.udid;
		this.image = obj.image;
		this.status = obj.status;
		this.createDate = obj.createDate;
	}
}
