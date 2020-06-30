package tn.elan.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "candidature")
public class Candidature {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String cv;
	private String diplome;
	private String type;
	private String candidats;
	@OneToMany(mappedBy = "candidature",cascade = CascadeType.REMOVE,orphanRemoval = true,fetch=FetchType.EAGER)
	 @JsonBackReference
  Set<Candidat> candidat;
	
	@ManyToOne
	@JoinColumn(name = "offre_id")
	@JsonManagedReference
	private Offre offre;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCv() {
		return cv;
	}
	public void setCv(String cv) {
		this.cv = cv;
	}
	public String getDiplome() {
		return diplome;
	}
	public void setDiplome(String diplome) {
		this.diplome = diplome;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	
	public Offre getOffre() {
		return offre;
	}
	public void setOffre(Offre offre) {
		this.offre = offre;
	}
	
	
	public Candidature(long id, String cv, String diplome, String type, String candidats, Set<Candidat> candidat,
			Offre offre) {
		super();
		this.id = id;
		this.cv = cv;
		this.diplome = diplome;
		this.type = type;
		this.candidats = candidats;
		this.candidat = candidat;
		this.offre = offre;
	}
	public Candidature() {
		
	}
	
	
}
