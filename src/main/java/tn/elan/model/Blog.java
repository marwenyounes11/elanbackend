package tn.elan.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "blog")
public class Blog {
	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String type;
	private String author;
	private String title;
	private String detail;
	private String postdate;
	private int view;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getPostdate() {
		return postdate;
	}
	public void setPostdate(String postdate) {
		this.postdate = postdate;
	}
	public int getView() {
		return view;
	}
	public void setView(int view) {
		this.view = view;
	}
	public Blog(long id, String type, String author, String title, String detail, String postdate, int view) {
		super();
		this.id = id;
		this.type = type;
		this.author = author;
		this.title = title;
		this.detail = detail;
		this.postdate = postdate;
		this.view = view;
	}
	public Blog() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Blog [id=" + id + ", type=" + type + ", author=" + author + ", title=" + title + ", detail=" + detail
				+ ", postdate=" + postdate + ", view=" + view + "]";
	}



}
