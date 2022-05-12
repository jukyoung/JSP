package com.post.dto;

public class PostDTO {
	private int seq;
	private String id;
	private String post;
	
	public PostDTO() {}
	public PostDTO(int seq, String id, String post) {
		super();
		this.seq = seq;
		this.id = id;
		this.post = post;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
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
		return  seq + " : " + id + " : " + post;
	}
	
	
}
