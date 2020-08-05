package tn.elan.model;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "article")
public class Medias {
	 @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	 private String image;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Medias(long id, String image) {
		super();
		this.id = id;
		this.image = image;
	}
	public Medias() {
		
	}
	 

}
