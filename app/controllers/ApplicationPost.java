package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.h2.util.IOUtils;

import models.Content;
import models.Recommand;
import models.Reply;
import models.User;

import com.google.gson.Gson;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http.MultipartFormData.FilePart;
import resModels.ResContent;
import resModels.ResRecommand;
import resModels.ResReply;
import resModels.ResUser;
import utils.ThumbnailGenerator;

public class ApplicationPost extends Controller{

	public static Result contentList() {
		Map<String, String[]> params = request().body().asFormUrlEncoded();
		String content_idx = params.get("content_idx")[0];
		
		List<Content> data = Content.getContentList(content_idx);
		List<ResContent> result = new LinkedList<ResContent>();
		for (Content model : data) {
			result.add(new ResContent(model));
		}
		return ok(new Gson().toJson(result));
	  }
	  
	  public static Result contentDetail() {
		  Map<String, String[]> params = request().body().asFormUrlEncoded();
		  String content_idx = params.get("content_idx")[0];
			
		  Content obj = Content.getContentDetail(content_idx);
		  return ok(new Gson().toJson(new ResContent(obj)));
	  }
	  
	  public static Result contentUpload() {
		  Map<String, String[]> params = null;
		  if(request().body().asFormUrlEncoded() != null) {
			  params = request().body().asFormUrlEncoded();
			  
		  }
		  
		  if (request().body().asMultipartFormData() != null) {
			  params = request().body()
						.asMultipartFormData().asFormUrlEncoded();
		  }
		  
		  String user_idx = params.get("user_idx")[0];
		  String title = params.get("title")[0];
		  String content = params.get("content")[0];
		  
		  String image = "";
		  
		  if (request().body().asMultipartFormData() != null) {
			  image = imageUpload(request().body().asMultipartFormData().getFiles(), user_idx, TYPE_CONTENT);
		  }
		  
		  Content obj = Content.upload(user_idx, title, content, image);
		  return ok(new Gson().toJson(new ResContent(obj)));
//		  return ok("");
	  }
	  
	  public static Result userLogin() {
		  Map<String, String[]> params = request().body().asFormUrlEncoded();
		  String device_id = params.get("device_id")[0];
		  
		  User obj = User.getUserId("0", device_id);
		  return ok(new Gson().toJson(new ResUser(obj)));
	  }
	  
	  public static Result userInfo() {
		  Map<String, String[]> params = request().body().asFormUrlEncoded();
		  String user_idx = params.get("user_idx")[0];
		  
		  User obj = User.getUserInfo(user_idx);
		  if(obj != null)
			  return ok(new Gson().toJson(new ResUser(obj)));
		  return ok("");
	  }
	  
	  public static Result userUpdate() {
		  Map<String, String[]> params = null;
		  if(request().body().asFormUrlEncoded() != null) {
			  params = request().body().asFormUrlEncoded();
			  
		  }
		  
		  if (request().body().asMultipartFormData() != null) {
			  params = request().body()
						.asMultipartFormData().asFormUrlEncoded();
		  }
		
		  String user_idx = params.get("user_idx")[0];
		  String device_id = params.get("device_id")[0];
		  String nickname = params.get("nickname")[0];
		  
		  String image = "";
		  
		  if (request().body().asMultipartFormData() != null) {
			  image = imageUpload(request().body().asMultipartFormData().getFiles(), user_idx, TYPE_PROFILE);
		  }
		  
		  User obj = User.update(user_idx, device_id, nickname, image);
		  return ok(new Gson().toJson(new ResUser(obj)));
	  }
	  
	  public static Result contentRecommand() {
		  Map<String, String[]> params = request().body().asFormUrlEncoded();
		  String user_idx = params.get("user_idx")[0];
		  String content_idx = params.get("content_idx")[0];
		  
		  Recommand obj = Recommand.addRecommand(user_idx, content_idx);
		  return ok(new Gson().toJson(new ResRecommand(obj)));
	  }
	  
	  public static Result replyList() {
		  Map<String, String[]> params = request().body().asFormUrlEncoded();
		  String content_idx = params.get("content_idx")[0];
		  String reply_idx = params.get("reply_idx")[0];
		  
		  List<Reply> obj = Reply.getContentReplies(content_idx, reply_idx);
		  List<ResReply> result = new LinkedList<ResReply>();
		  for (Reply reply : obj) {
			  result.add(new ResReply(reply));
		  }
		  return ok(new Gson().toJson(result));
	  }
	  
	  public static Result replyUpload() {
		  Map<String, String[]> params = request().body().asFormUrlEncoded();
		  String user_idx = params.get("user_idx")[0];
		  String content_idx = params.get("content_idx")[0];
		  String content = params.get("content")[0];
		  
		  Reply.upload(user_idx, content_idx, content);
		  List<Reply> obj = Reply.getContentReplies(content_idx, "0");
		  List<ResReply> result = new LinkedList<ResReply>();
		  for (Reply reply : obj) {
			  result.add(new ResReply(reply));
		  }
		  return ok(new Gson().toJson(result));
	  }
	  
	  public static Result deleteContent() {
		  Map<String, String[]> params = request().body().asFormUrlEncoded();
		  String user_idx = params.get("user_idx")[0];
		  String content_idx = params.get("content_idx")[0];
		  return ok(new Gson().toJson(new ResContent(Content.deleteContent(user_idx, content_idx))));
	  }
	  
	  public static Result deleteReply() {
		  Map<String, String[]> params = request().body().asFormUrlEncoded();
		  String user_idx = params.get("user_idx")[0];
		  String reply_idx = params.get("reply_idx")[0];
		  return ok(new Gson().toJson(new ResReply(Reply.deleteReply(user_idx, reply_idx))));
	  }
	  
	  private static final int TYPE_CONTENT = 1;
	  private static final int TYPE_PROFILE = 2;
	  
	  private static String imageUpload(List<FilePart> files, String user_idx, int type) {
		  
		  if(files == null) return "";
		  String imageURL = "";
		  String s_imageURL = "";
		  String folder = "Contents";
		  if(type == TYPE_PROFILE)
			  folder = "Profile";
		  
			 
			for (FilePart part : files) {
				if (part != null) {
					File file = part.getFile();
					Date date = new Date();
					SimpleDateFormat format = new SimpleDateFormat(
							"yyyyMMdd_HHmmss");
					imageURL = "Images/" + folder + "/" + user_idx + "_" + format.format(date) + ".JPG";
					s_imageURL = "Images/" + folder + "/s_" + user_idx + "_" + format.format(date) + ".JPG";
					
					File saveFile = new File(imageURL);
					FileInputStream is;
					try {
						is = new FileInputStream(file);
						IOUtils.copy(is, new FileOutputStream(saveFile));
						ThumbnailGenerator generator = new ThumbnailGenerator();
						generator.transform(imageURL, s_imageURL, 320, 320, 1);
					} catch (FileNotFoundException e) { 
						e.printStackTrace();
					} catch (IOException e) { 
						e.printStackTrace();
					} catch (Exception e) { 
						e.printStackTrace();
					}
				}
			}
			
			return imageURL;
	  }
}
