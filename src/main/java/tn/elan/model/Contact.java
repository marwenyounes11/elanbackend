package tn.elan.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "contact")
public class Contact {
	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String sujet;
	private String message;
	private String candidats;
	
	@OneToMany(mappedBy = "contact",cascade = CascadeType.REMOVE,orphanRemoval = true,fetch=FetchType.EAGER)
	 @JsonManagedReference 
   Set<Candidat> candidat;	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSujet() {
		return sujet;
	}
	public void setSujet(String sujet) {
		this.sujet = sujet;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getCandidats() {
		return candidats;
	}
	public void setCandidats(String candidats) {
		this.candidats = candidats;
	}
	public Set<Candidat> getCandidat() {
		return candidat;
	}
	public void setCandidat(Set<Candidat> candidat) {
		this.candidat = candidat;
	}
	
	
	public Contact(long id, String sujet, String message, String candidats, Set<Candidat> candidat) {
		super();
		this.id = id;
		this.sujet = sujet;
		this.message = message;
		this.candidats = candidats;
		this.candidat = candidat;
	}
	public Contact() {
		
	}
	
}
