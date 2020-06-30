package tn.elan.Controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tn.elan.domaine.Response;
import tn.elan.exception.ResourceNotFoundException;
import tn.elan.model.Candidat;
import tn.elan.model.Candidature;

import tn.elan.repository.CandidatureRepository;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class CandidatureController {
	@Autowired 	CandidatureRepository  repository;
	 @GetMapping("/candidatures/{id}")
		public ResponseEntity<Candidature> getCandidatureById(@PathVariable(value = "id") Long Id)
				throws ResourceNotFoundException {
		 Candidature candidature = repository.findById(Id)
					.orElseThrow(() -> new ResourceNotFoundException("Candidature not found for this id :: " + Id));
			return ResponseEntity.ok().body(candidature);
		}
	 
	 @GetMapping("/candidatures")
	  public List<Candidature> getAllCandidatures() {
	     System.out.println("Get all Candidatures...");
	 
	    List<Candidature> candidatures = new ArrayList<>();
	    repository.findAll().forEach(candidatures::add);
	 
	    return candidatures;
	  }

	@PostMapping("/candidatures")
	public ResponseEntity<Response> createCandidature( @RequestBody Candidature candidature) {
		Candidature candidatur =new Candidature();
		 candidatur.setCandidats(candidature.getCandidat().toString());
	Candidature cand = repository.save(candidatur);

	if (cand != null)
	{
		return new ResponseEntity<Response>(new Response (""),HttpStatus.OK);
	}
	else
	{
		return new ResponseEntity<Response>(new Response ("Candidature not saved"),HttpStatus.BAD_REQUEST);	
	}

	}


	@DeleteMapping("/candidatures/{id}")
	public Map<String, Boolean> deleteCandidature(@PathVariable(value = "id") Long candidatureId)
			throws ResourceNotFoundException {
		Candidature candidature = repository.findById(candidatureId)
				.orElseThrow(() -> new ResourceNotFoundException("Candidature not found  id :: " + candidatureId));
		repository.delete(candidature);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@DeleteMapping("/candidatures/delete")
	public ResponseEntity<String> deleteCandidatures() {
	  System.out.println("Delete All Candidatures...");
	  repository.deleteAll();
	  return new ResponseEntity<>("All Candidatures have been deleted!", HttpStatus.OK);
	}


	@PutMapping("/candidatures/{id}")
	public ResponseEntity<Candidature> updateCandidature(@PathVariable("id") long id, @RequestBody Candidature candidature) {
	  System.out.println("Update Candidature with ID = " + id + "...");
	  Optional<Candidature> candidatureInfo = repository.findById(id);
	  if (candidatureInfo.isPresent()) {
	  	Candidature cand = candidatureInfo.get();
	         cand.setCv(candidature.getCv());
	         cand.setDiplome(candidature.getDiplome());
	         cand.setType(candidature.getType());
	         cand.setCv(candidature.getCv());
	         cand.setCandidats(candidature.getCandidat().toString());
	         cand.setOffre(candidature.getOffre());
	    return new ResponseEntity<>(repository.save(cand), HttpStatus.OK);
	  } else {
	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	  }
	}

}
