package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.data.format.Formats;
import play.db.ebean.Model;

@Entity
public class Reply extends Model {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	public long id;
	
	@ManyToOne
	public User user;
	
	public long content_id;
	
	@Column(columnDefinition = "text")
	public String replyContents;
	
	@Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
	public Date createDate;
	
	@Column(columnDefinition = "char(1)")
	public char status;
	
	private static final int rSize = 10;
	
	public static Finder<Long,Reply> find = new Finder<Long,Reply>(Long.class, Reply.class); 
	
	
	public Reply(User user, Content content, String replyContents) {
		// TODO Auto-generated constructor stub
		this.user = user;
		this.content_id = content.id;
		this.replyContents = replyContents;
		this.createDate = new Date();
		this.status = 'Y';
	}	
	
	
	public static List<Reply> getContentReplies (String content_idx, String reply_idx) {
		if("0".equals(reply_idx))
			return find.where().eq("content_id", Long.parseLong(content_idx)).eq("status", "Y").orderBy("id asc").findPagingList(rSize).getPage(0).getList();
		else
			return find.where().eq("content_id", Long.parseLong(content_idx)).gt("id", reply_idx).eq("status", "Y").orderBy("id asc").findPagingList(rSize).getPage(0).getList();
	}

//	public static Reply getContentReplyByContent (String user_idx, String udid, String content_idx, String content) {
//		return find.where().eq("user_id", Long.valueOf(user_idx)).eq("content_id", Long.valueOf(content_idx)).eq("reply_contents", content).eq("status", "Y").findUnique();
//	}
//	
//	public static Reply getContentReplyByTime (String user_idx, String udid, String content_idx, String reDate) {
//		return find.where().eq("user_id", Long.valueOf(user_idx)).eq("content_id", Long.valueOf(content_idx)).eq("create_date", reDate).eq("status", "Y").findUnique();
//	}
	
	public static Reply upload (String user_idx, String content_idx, String content) {
		Reply reply = null;
//		if(Reply.getContentReplyByContent(user_idx, udid, content_idx, content) == null)
		{
			User user = User.getUserInfo(user_idx);
			if(user == null) return null;
			
			Content contents = Content.getContentDetail(content_idx);
			int replyCount = contents.replyCount;
			contents.replyCount = replyCount + 1;
			contents.update();
			
			reply = new Reply(user, contents, content);
			reply.save();
		}
		return reply;
	}
}
