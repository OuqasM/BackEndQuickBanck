package backEndQuickBank.services.implementation;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backEndQuickBank.requests.PostRequest;
import backEndQuickBank.responses.PostResponse;
import backEndQuickBank.services.PostService;
import backEndQuickBank.entities.Client;
import backEndQuickBank.entities.Post;
import backEndQuickBank.repositories.ClientRepository;
import backEndQuickBank.repositories.PostRepository;
@Service
public class PostServiceImp implements PostService {

	@Autowired
	PostRepository postRepository;
	@Autowired 
	ClientRepository clientRepository;
	@Override
	public List<PostResponse> Posts() {
		ModelMapper modelMapper = new ModelMapper();
		List<Post> posts = postRepository.findAll();
		List<PostResponse> postsReponses = new ArrayList<PostResponse>();
		posts.forEach(post -> {
			postsReponses.add(modelMapper.map(post, PostResponse.class));
		});	
		return postsReponses;
	}

	@Override
	public PostResponse Post(Long postId) {
		ModelMapper modelMapper = new ModelMapper();
		Post post = postRepository.findById(postId).orElse(null);
		if(post == null ) return null;
		PostResponse postResponse = modelMapper.map(post, PostResponse.class);
		return postResponse;
	}

	@Override
	public PostResponse ajouerUnPost(PostRequest postRequest,Principal principal) {
		ModelMapper modelMapper = new ModelMapper();
		
		Client client = clientRepository.findByUsername(principal.getName());
		Post postEntity = modelMapper.map(postRequest, Post.class);
		postEntity.setValide(false);
		postEntity.setVisible(false);
		postEntity.setClient(client);
		
		
		return modelMapper.map(postRepository.save(postEntity), PostResponse.class);
	}

	@Override
	public boolean supprimerPost(Long postId) {
		Post postEntity = postRepository.findById(postId).orElse(null);
		if(postEntity == null) return false;
		postRepository.delete(postEntity);
		return true;
	}

}
