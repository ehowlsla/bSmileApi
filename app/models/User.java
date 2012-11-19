package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.format.Formats;
import play.db.ebean.Model;

@Entity
public class User extends Model{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public long id;
	
	@Column(columnDefinition = "nvarchar(100)")
	public String nickname;
	
	@Column(columnDefinition = "nvarchar(100)")
	public String udid;
	
	@Column(columnDefinition = "nvarchar(100)")
	public String image;
	
	@Column(columnDefinition = "char(1)")
	public char status;
	
	@Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
	public Date createDate;
	
	public static Finder<Long,User> find = new Finder<Long,User>(Long.class, User.class); 
	
	public User (String udid) {
		this.nickname = "이름없음";
		this.udid = udid;
		this.status = 'Y';
		this.createDate = new Date();
		this.image = "";
	}
	
	public static User getUser(String user_idx, String nickname) {
		return find.where().ne("id", Long.valueOf(user_idx)).eq("nickname", nickname).findUnique();
	}
	
	public static User getUserInfo(String user_idx) {
		return find.where().eq("id", Long.valueOf(user_idx)).findUnique();
	}
	
	public static User getUser(String udid) {
		return find.where().eq("udid", udid).findUnique();
	}
	
	public static User getUserId(String user_idx, String udid) {
		User user = null;
		
		if(user_idx.equals("0")) {
			user = getUser(udid);
			
			if(user == null) {
				//회원가입 
				user = new User(udid);
				user.save();
			}
			
		}
		else {
			//user 정보 가져오기 
			user =  find.where().eq("udid", udid).findUnique();
		}
		
		return user;
	}
	
	public static User update (String user_idx, String udid, String nickname, String image) {
		User user = null;
		if(User.getUser(user_idx, nickname) == null)
		{
			user = User.getUserId(user_idx, udid);
			//중복처리 해야
			user.nickname = nickname;
			if(image != null)
				user.image = image;
			//이미지 업로드
			
			user.update();
		}
		
		
		return user;
	}
}
