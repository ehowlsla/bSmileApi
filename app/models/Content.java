package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

 
import play.data.format.Formats;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Content extends Model{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	public long id;
	
	@Column(columnDefinition = "nvarchar(255)")
	public String title;
	
	@Column(columnDefinition = "text")
	public String contents;
	
	@Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
	public Date createDate;
	
	public int recCount;
	
	public int replyCount;
	
	@Column(columnDefinition = "nvarchar(100)")
	public String image;
	
	@Column(columnDefinition = "char(1)")
	public char status;

	@ManyToOne
	public User user;
	
	private final static int pSize = 10;
	
public static Finder<Long,Content> find = new Finder<Long,Content>(Long.class, Content.class); 
	
	
	public Content(User user, String title, String content, String image) {
		// TODO Auto-generated constructor stub
		this.title = title;
		this.contents = content;
		this.createDate = new Date();
		this.recCount = 0;
		this.status = 'Y';
		this.user = user;
		this.image = image;
		this.replyCount = 0;
	}
	
	public static List<Content> getContentList  (String content_idx) {
		if(content_idx.equals("0"))
			return find.where().eq("status", "Y").orderBy("id desc").findPagingList(pSize).getPage(0).getList();
		else
			return find.where().eq("status", "Y").lt("id", Integer.valueOf(content_idx)).orderBy("id desc").findPagingList(pSize).getPage(0).getList();
	}
	

	public static Content getContentDetail (String content_idx) {
		return find.where().eq("status", "Y").eq("id", Long.valueOf(content_idx)).findUnique();
	}
	
	public static Content upload (String user_idx, String title, String content, String image) {
		Content contents = null;
		//중복체크 
//		if(Content.getContent(user_idx, udid, title, content, busTag, isNotice) == null)
		{
			User user = User.getUserInfo(user_idx);
			contents = new Content(user, title, content, image);
			contents.save();
		}
		return contents;
	}
}
