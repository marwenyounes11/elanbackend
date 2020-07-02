package tn.elan.Controller;


import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tn.elan.domaine.Response;
import tn.elan.exception.ResourceNotFoundException;

import tn.elan.model.Candidature;

import tn.elan.repository.CandidatureRepository;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class CandidatureController {
	@Autowired 	CandidatureRepository  repository;
	@Autowired  ServletContext context;
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
	 public ResponseEntity<Response> createArticle (@RequestParam("file1") MultipartFile file1,@RequestParam("file2") MultipartFile file2,
			 @RequestParam("candidature") String candidature) throws JsonParseException , JsonMappingException , Exception
	 {
		 Candidature candidatur =new Candidature(); 
		 System.out.println("Ok .............");
		 Candidature cand = new ObjectMapper().readValue(candidature, Candidature.class);
        boolean isExit = new File(context.getRealPath("/Images/")).exists();
        if (!isExit)
        {
        	
        	new File (context.getRealPath("/Images/")).mkdir();
        	System.out.println("mk dir............."+context.getRealPath("/Images/"));
        }
        String filename1 = file1.getOriginalFilename();
        String filename2 = file2.getOriginalFilename();
        String newFileName1 = FilenameUtils.getBaseName(filename1)+"."+FilenameUtils.getExtension(filename1);
        String newFileName2 = FilenameUtils.getBaseName(filename2)+"."+FilenameUtils.getExtension(filename2);
        File serverFile1 = new File (context.getRealPath("/Images/"+File.separator+newFileName1));
        File serverFile2 = new File (context.getRealPath("/Images/"+File.separator+newFileName2));
        try
        {
        	System.out.println("Image");
        	 FileUtils.writeByteArrayToFile(serverFile1,file1.getBytes());
        	 FileUtils.writeByteArrayToFile(serverFile2,file2.getBytes());
        	 
        }catch(Exception e) {
        	e.printStackTrace();
        }

        
        candidatur.setCv(newFileName1);
        candidatur.setDiplome(newFileName2);
       // candidatur.setCandidats(cand.getCandidat().toString());
        Candidature cnd = repository.save(candidatur);
        if (cnd != null)
        {
        	return new ResponseEntity<Response>(new Response (""),HttpStatus.OK);
        }
        else
        {
        	return new ResponseEntity<Response>(new Response ("candidature not saved"),HttpStatus.BAD_REQUEST);	
        }
	 }
	 
	 @GetMapping ("/getAllCandidature")
	 public ResponseEntity<List<String>> getAll()
	 {
		 List<String> listCandidature = new ArrayList<String>();
		 String filesPath = context.getRealPath("/Images");
		 File filefolder = new File(filesPath);
		 if (filefolder != null)
		 {
			for (File file :filefolder.listFiles())
			{
				if(!file.isDirectory())
				{
				  String encodeBase64 = null;
				  try {
					  String extension = FilenameUtils.getExtension(file.getName());
					  FileInputStream fileInputStream = new FileInputStream(file);
				      byte[] bytes = new byte[(int)file.length()];
				      fileInputStream.read(bytes);
				      encodeBase64 = Base64.getEncoder().encodeToString(bytes);
				      listCandidature.add("data:image/"+extension+";base64,"+encodeBase64);
				      fileInputStream.close();
				      
				      
				  }catch (Exception e){
					  
				  }
				}
			}
		 }
		 return new ResponseEntity<List<String>>(listCandidature,HttpStatus.OK);
	 }
	 
	 @GetMapping(path="/cvCandidatures/{id}")
	 public byte[] getCv(@PathVariable("id") Long id) throws Exception{
		 Candidature candidature  = repository.findById(id).get();
		 return Files.readAllBytes(Paths.get(context.getRealPath("/Images/")+candidature.getCv()));
	 }

	 @GetMapping(path="/diplomeCandidatures/{id}")
	 public byte[] getDiplome(@PathVariable("id") Long id) throws Exception{
		 Candidature candidature  = repository.findById(id).get();
		 return Files.readAllBytes(Paths.get(context.getRealPath("/Images/")+candidature.getDiplome()));
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
	         //cand.setCandidats(candidature.getCandidat().toString());
	         cand.setOffre(candidature.getOffre());
	    return new ResponseEntity<>(repository.save(cand), HttpStatus.OK);
	  } else {
	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	  }
	}

}
