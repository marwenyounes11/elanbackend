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

import org.springframework.web.bind.annotation.RestController;


import tn.elan.domaine.Response;
import tn.elan.exception.ResourceNotFoundException;

import tn.elan.model.Contact;
import tn.elan.repository.ContactRepository;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ContactController {
	@Autowired 	ContactRepository  repository;

	@GetMapping("/contacts/{id}")
	public ResponseEntity<Contact> getContactById(@PathVariable(value = "id") Long Id)
			throws ResourceNotFoundException {
		Contact contact = repository.findById(Id)
				.orElseThrow(() -> new ResourceNotFoundException("contact not found for this id :: " + Id));
		return ResponseEntity.ok().body(contact);
	}

	@GetMapping("/contacts")
	  public List<Contact> getAllContacts() {
	     System.out.println("Get all contacts...");
	 
	    List<Contact> contacts = new ArrayList<>();
	    repository.findAll().forEach(contacts::add);
	 
	    return contacts;
	  }

	@PostMapping("/contacts")
	public ResponseEntity<Response> createContact( @RequestBody Contact contact){
		Contact cont =new Contact();
		 cont.setCandidats(contact.getCandidat().toString());
	Contact con = repository.save(cont);

	if (con != null)
	{
		return new ResponseEntity<Response>(new Response (""),HttpStatus.OK);
	}
	else
	{
		return new ResponseEntity<Response>(new Response ("Contact not saved"),HttpStatus.BAD_REQUEST);	
	}

	}

	

	


	@DeleteMapping("/contacts/{id}")
	public Map<String, Boolean> deleteContact(@PathVariable(value = "id") Long contactId)
			throws ResourceNotFoundException {
		Contact contact = repository.findById(contactId)
				.orElseThrow(() -> new ResourceNotFoundException("Contact not found  id :: " + contactId));
		repository.delete(contact);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@DeleteMapping("/contacts/delete")
	public ResponseEntity<String> deleteContacts() {
	  System.out.println("Delete All Contacts...");
	  repository.deleteAll();
	  return new ResponseEntity<>("All Contacts have been deleted!", HttpStatus.OK);
	}


	@PutMapping("/contacts/{id}")
	public ResponseEntity<Contact> updateContact(@PathVariable("id") long id, @RequestBody Contact contact) {
	  System.out.println("Update Contact with ID = " + id + "...");
	  Optional<Contact> contactInfo = repository.findById(id);
	  if (contactInfo.isPresent()) {
	  	Contact con = contactInfo.get();
	         con.setCandidats(contact.getCandidat().toString());
	         con.setMessage(contact.getMessage());
	         con.setSujet(contact.getSujet());
	         
	    return new ResponseEntity<>(repository.save(con), HttpStatus.OK);
	  } else {
	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	  }
	}

}
