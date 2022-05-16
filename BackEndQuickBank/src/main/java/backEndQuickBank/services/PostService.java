package backEndQuickBank.services;

import java.security.Principal;
import java.util.List;

import backEndQuickBank.entities.Post;
import backEndQuickBank.requests.PostRequest;
import backEndQuickBank.responses.PostResponse;

public interface PostService {
	
	List<PostResponse> Posts();
	PostResponse Post(Long postId);
	PostResponse ajouerUnPost(PostRequest postRequest,Principal principal);
	boolean supprimerPost(Long postId);	
	
}
