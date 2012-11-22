package controllers;

import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;

import models.Content;
import models.Recommand;
import models.Reply;
import models.User;
 
import play.mvc.Controller;
import play.mvc.Result;
import resModels.ResContent;
import resModels.ResRecommand;
import resModels.ResReply;
import resModels.ResUser;
import views.html.index;

public class Application extends Controller {
  
  public static Result index() {
    return ok(index.render("Your new application is ready."));
  }
  
  public static Result contentList(String content_idx) {
	  List<Content> data = Content.getContentList(content_idx);
		List<ResContent> result = new LinkedList<ResContent>();
		for (Content model : data) {
			result.add(new ResContent(model));
		}
		return ok(new Gson().toJson(result));
  }
  
  public static Result contentDetail(String content_idx) {
	  Content obj = Content.getContentDetail(content_idx);
	  return ok(new Gson().toJson(new ResContent(obj)));
  }
  
  public static Result contentUpload(String user_idx, String title, String content) {
	  String image = "";
	  Content obj = Content.upload(user_idx, title, content, image);
	  return ok(new Gson().toJson(new ResContent(obj)));
//	  return ok("");
  }
  
  public static Result userLogin(String device_id) {
	  User obj = User.getUserId("0", device_id);
	  return ok(new Gson().toJson(new ResUser(obj)));
  }
  
  public static Result userInfo(String user_idx) {
	  User obj = User.getUserInfo(user_idx);
	  if(obj != null)
		  return ok(new Gson().toJson(new ResUser(obj)));
	  return ok("");
  }
  
  public static Result userUpdate(String user_idx, String device_id, String nickname) {
	  String image = "";
	  User obj = User.update(user_idx, device_id, nickname, image);
	  return ok(new Gson().toJson(new ResUser(obj)));
  }
  
  public static Result contentRecommand(String user_idx, String content_idx) {
	  Recommand obj = Recommand.addRecommand(user_idx, content_idx);
	  return ok(new Gson().toJson(new ResRecommand(obj)));
  }
  
  public static Result replyList(String content_idx, String reply_idx) {
	  List<Reply> obj = Reply.getContentReplies(content_idx, reply_idx);
	  List<ResReply> result = new LinkedList<ResReply>();
	  for (Reply reply : obj) {
		  result.add(new ResReply(reply));
	  }
	  return ok(new Gson().toJson(result));
  }
  
  public static Result replyUpload(String user_idx, String content_idx, String content) {
	  Reply temp = Reply.upload(user_idx, content_idx, content);
	  if(temp == null) return ok();
	  
	  List<Reply> obj = Reply.getContentReplies(content_idx, "0");
	  List<ResReply> result = new LinkedList<ResReply>();
	  for (Reply reply : obj) {
		  result.add(new ResReply(reply));
	  }
	  return ok(new Gson().toJson(result));
  }
}

 

