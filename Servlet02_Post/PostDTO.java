package com.kh.dto;

public class PostDTO {
	
	private String id;
	private String post;
	
	public PostDTO() {}
	public PostDTO(String id, String post) {
		super();
		this.id = id;
		this.post = post;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	@Override
	public String toString() {
		return  id + " : " + post;
	}
	
	
}
