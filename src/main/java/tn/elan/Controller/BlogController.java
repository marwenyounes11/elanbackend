package tn.elan.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.elan.model.Blog;
import tn.elan.repository.BlogRepository;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class BlogController {
	@Autowired 	BlogRepository  repository;
	 @GetMapping("/blogs")
	  public List<Blog> getAllblogs() {
	     System.out.println("Get all blogs...");
	 
	    List<Blog> blogs = new ArrayList<>();
	    repository.findAll().forEach(blogs::add);
	 
	    return blogs;
	  }

}
