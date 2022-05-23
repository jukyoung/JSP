package com.board.dto;

public class ReplyDTO {
	private int seq_reply;
	private int seq_board;
	private String content;
	private String writer_nickname;
	private String writer_id;
	private String written_date;
	
	public ReplyDTO() {}
	public ReplyDTO(int seq_reply, int seq_board, String content, String writer_nickname, String writer_id,
			String written_date) {
		super();
		this.seq_reply = seq_reply;
		this.seq_board = seq_board;
		this.content = content;
		this.writer_nickname = writer_nickname;
		this.writer_id = writer_id;
		this.written_date = written_date;
	}
	public int getSeq_reply() {
		return seq_reply;
	}
	public void setSeq_reply(int seq_reply) {
		this.seq_reply = seq_reply;
	}
	public int getSeq_board() {
		return seq_board;
	}
	public void setSeq_board(int seq_board) {
		this.seq_board = seq_board;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter_nickname() {
		return writer_nickname;
	}
	public void setWriter_nickname(String writer_nickname) {
		this.writer_nickname = writer_nickname;
	}
	public String getWriter_id() {
		return writer_id;
	}
	public void setWriter_id(String writer_id) {
		this.writer_id = writer_id;
	}
	public String getWritten_date() {
		return written_date;
	}
	public void setWritten_date(String written_date) {
		this.written_date = written_date;
	}
	@Override
	public String toString() {
		return seq_reply + " : " + seq_board + " : " + content
				+ " : " + writer_nickname + " : " + writer_id + " : " + written_date;
	}
	
	
}
