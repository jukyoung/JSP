package com.intro.dto;

public class MessageDTO {

	private int seq;
	private String nickname;
	private String message;
	
	public MessageDTO() {}
	public MessageDTO(int seq, String nickname, String message) {
		super();
		this.seq = seq;
		this.nickname = nickname;
		this.message = message;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return seq + " : " + nickname + " : " + message;
	}
	
	
}
