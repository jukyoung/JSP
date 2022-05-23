package com.board.dto;

public class BoardDTO {
	private int seq_board;
	private String title;
	private String content;
	private String writer_nickname;
	private String writer_id;
	private int view_count;
	private String written_date;
	
	public BoardDTO() {}
	public BoardDTO(int seq_board, String title, String content, String writer_nickname, String writer_id,
			int view_count, String written_date) {
		super();
		this.seq_board = seq_board;
		this.title = title;
		this.content = content;
		this.writer_nickname = writer_nickname;
		this.writer_id = writer_id;
		this.view_count = view_count;
		this.written_date = written_date;
	}
	public int getSeq_board() {
		return seq_board;
	}
	public void setSeq_board(int seq_board) {
		this.seq_board = seq_board;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public void setWritter_id(String writer_id) {
		this.writer_id = writer_id;
	}
	public int getView_count() {
		return view_count;
	}
	public void setView_count(int view_count) {
		this.view_count = view_count;
	}
	public String getWritten_date() {
		return written_date;
	}
	public void setWritten_date(String wirtten_date) {
		this.written_date = wirtten_date;
	}
	@Override
	public String toString() {
		return seq_board + " : " + title + " : " + content + " : "
				+ writer_nickname + " : " + writer_id + " : " + view_count + " : "
				+ written_date;
	}
	
	
	
}
