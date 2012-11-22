package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.format.Formats;
import play.db.ebean.Model;

@Entity
public class Recommand extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	public String user_id;
	 
	@ManyToOne
	public String content_id;
	
	@Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
	public Date createDate;
	
	@Column(columnDefinition = "char(1)")
	public char status; 
	
	public static Finder<Long,Recommand> find = new Finder<Long,Recommand>(Long.class, Recommand.class); 
	
	public Recommand(String user_id, String content_id) {
		// TODO Auto-generated constructor stub
		this.user_id = user_id;
		this.content_id = content_id;
		this.createDate = new Date();
		this.status = 'Y';
	}
	
	public static Recommand getRecommand (String user_id, String content_id) {
		return find.where().eq("user_id", user_id).eq("content_id", content_id).findUnique();
	}
	
	public static Recommand addRecommand (String user_id, String content_id) {
		Recommand recommands = Recommand.getRecommand(user_id, content_id);
		if(recommands == null) {
			recommands = new Recommand(user_id, content_id);
			recommands.save();
			
			Content contents = Content.getContentDetail(content_id);
			int recCount = contents.recCount;
			contents.recCount = recCount + 1;
			contents.update();
			
			return recommands;
		} else {
			return null;
		}
	}
}
