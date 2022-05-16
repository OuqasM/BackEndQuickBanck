package backEndQuickBank.controlleur;

import java.security.Principal;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backEndQuickBank.requests.PostRequest;
import backEndQuickBank.responses.PostResponse;
import backEndQuickBank.services.PostService;

@RestController
@RequestMapping("/post")
public class PostControlleur {

	@Autowired
	PostService postService;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping
	public ResponseEntity<List<PostResponse>> getAll(){
	return new ResponseEntity<List<PostResponse>>(postService.Posts(),HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/{postid}")
	public ResponseEntity<?> getOnePost(@PathVariable("postid") Long postId){
		PostResponse reponse = postService.Post(postId);
		if(reponse == null) {
			return new ResponseEntity<String>("Post n'existe pas !",HttpStatus.NOT_FOUND); 
		}
		return new ResponseEntity<PostResponse>(reponse,HttpStatus.OK);
	}
	
	
	@PreAuthorize("hasAuthority('CLIENT')")
	@PostMapping
	public ResponseEntity<?> createPost(@RequestBody PostRequest postRequest,Principal principal){
		PostResponse reponse = postService.ajouerUnPost(postRequest,principal);
		return new ResponseEntity<PostResponse>(reponse,HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/{postid}")
	public ResponseEntity<String> deletePost(@PathVariable("postid") Long postId){
		boolean reponse = postService.supprimerPost(postId);
		if(!reponse) {
			return new ResponseEntity<String>("Post n'existe pas !",HttpStatus.NOT_FOUND); 
		}
		return new ResponseEntity<String>("Post bien été supprimé",HttpStatus.OK);
	}
	
	
	
	
}
