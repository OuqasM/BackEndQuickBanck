package backEndQuickBank.services.implementation;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import backEndQuickBank.entities.Administrateur;
import backEndQuickBank.entities.Client;
import backEndQuickBank.entities.Compte;
import backEndQuickBank.entities.Post;
import backEndQuickBank.entities.RoleEnum;
import backEndQuickBank.entities.Utilisateur;
import backEndQuickBank.repositories.AdministrateurRepository;
import backEndQuickBank.repositories.ClientRepository;
import backEndQuickBank.repositories.CompteRepository;
import backEndQuickBank.repositories.PostRepository;
import backEndQuickBank.repositories.UtilisateurRepository;
import backEndQuickBank.requests.AdministrateurRequests;
import backEndQuickBank.responses.AdministrateurResponse;
import backEndQuickBank.responses.ClientResponse;
import backEndQuickBank.responses.CompteResponse;
import backEndQuickBank.responses.PostResponse;
import backEndQuickBank.services.AdministrateurService;

@Service
@Transactional
public class AdministrateurServiceImp implements AdministrateurService {

	@Autowired
	AdministrateurRepository administrateurRepository;
	@Autowired
	ClientRepository clientRepository;
	@Autowired
	CompteRepository compteRepository;
	@Autowired
	PostRepository postRepository; 
	@Autowired
	PasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	UtilisateurRepository utilisateurRepository;
	
	@Override
	public AdministrateurResponse ajouterAdministrateur(AdministrateurRequests administrateur) {
		Utilisateur checkifAlreadyExist = utilisateurRepository.findByUsername(administrateur.getUsername());
		if(checkifAlreadyExist != null) return null;
		ModelMapper moddelMapper = new ModelMapper();
		Administrateur adminEntity = moddelMapper.map(administrateur, Administrateur.class);
		adminEntity.setRole(RoleEnum.ADMIN);
		adminEntity.setMotdepasse(bCryptPasswordEncoder.encode(administrateur.getMotdepasse()));
		AdministrateurResponse reponse = moddelMapper.map( administrateurRepository.save(adminEntity), AdministrateurResponse.class);
		return reponse;
	}

	@Override
	public AdministrateurResponse supprimerAdministrateur(Long administrateurId) {
		ModelMapper moddelMapper = new ModelMapper();
		Administrateur administrateur = (Administrateur) administrateurRepository.findById(administrateurId).orElse(null);
		if(administrateur == null) return null;
		administrateurRepository.delete(administrateur);	
		return moddelMapper.map(administrateur, AdministrateurResponse.class);
	}

	@Override
	public PostResponse setPostVisible(Long postId) {
		ModelMapper moddelMapper = new ModelMapper();
		Post post = postRepository.findById(postId).orElse(null);
		if(post == null) return null;
		post.setVisible(true);
		return  moddelMapper.map(postRepository.save(post), PostResponse.class);
	}

	@Override
	public PostResponse setPostValide(Long postId) {
		ModelMapper moddelMapper = new ModelMapper();
		Post post = postRepository.findById(postId).orElse(null);
		if(post == null) return null;
		post.setValide(true);
		return  moddelMapper.map(postRepository.save(post), PostResponse.class);

	}

	@Override
	public ClientResponse setClientConfirme(Long clientId , Principal principal) {
		ModelMapper moddelMapper = new ModelMapper();		
		Administrateur administrateur = (Administrateur) administrateurRepository.findByUsername(principal.getName());
		Client client = (Client) clientRepository.findById(clientId).orElse(null);
		if(client == null || administrateur==null ) return null;
		client.setConfirme(true);
		client.setAdministrateur(administrateur);
		return  moddelMapper.map(clientRepository.save(client), ClientResponse.class);

	}

	@Override
	public CompteResponse confirmeCompteSuppression(Long compteId) {
		ModelMapper moddelMapper = new ModelMapper();
		Compte compte = compteRepository.findById(compteId).orElse(null);
		if(compte == null) return null;
		compteRepository.delete(compte);
		return  moddelMapper.map(compte, CompteResponse.class);

	}

	@Override
	public ClientResponse confirmeClientSuppression(Long clientId) {
		ModelMapper moddelMapper = new ModelMapper();
		Client client = (Client) clientRepository.findById(clientId).orElse(null);
		if(client == null) return null;
		clientRepository.delete(client);
		return  moddelMapper.map(client, ClientResponse.class);

	}

	@Override
	public List<AdministrateurResponse> Administrateurs() {
		List<AdministrateurResponse> admins = new ArrayList<AdministrateurResponse>();
		ModelMapper modelMapper = new ModelMapper();
		administrateurRepository.findByRole(RoleEnum.ADMIN).forEach(admin -> {
			admins.add(modelMapper.map(administrateurRepository.save(admin), AdministrateurResponse.class));
		});		
		return admins;
	}

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Administrateur administrateur = administrateurRepository.findByUsername(username);
//		if(administrateur != null) {
//			Collection<GrantedAuthority>  authorities = new ArrayList<GrantedAuthority>();
//			authorities.add(new SimpleGrantedAuthority(administrateur.getRole().toString()));
//			return new User(administrateur.getUsername(), administrateur.getMotdepasse() , authorities);
//		}else throw new UsernameNotFoundException(username);
//	}

}
