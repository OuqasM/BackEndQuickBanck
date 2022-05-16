package backEndQuickBank.services.implementation;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import backEndQuickBank.entities.Client;
import backEndQuickBank.entities.Compte;
import backEndQuickBank.entities.RoleEnum;
import backEndQuickBank.entities.Utilisateur;
import backEndQuickBank.repositories.AdministrateurRepository;
import backEndQuickBank.repositories.ClientRepository;
import backEndQuickBank.repositories.CompteRepository;
import backEndQuickBank.repositories.PostRepository;
import backEndQuickBank.repositories.UtilisateurRepository;
import backEndQuickBank.requests.ClientRequest;
import backEndQuickBank.responses.ClientResponse;
import backEndQuickBank.services.ClientService;

@Service
@Transactional
public class ClientServiceImp implements ClientService{

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
	public ClientResponse ajouterClient(ClientRequest client) {
		Utilisateur checkifAlreadyExist = utilisateurRepository.findByUsername(client.getUsername());
		if(checkifAlreadyExist != null) return null;
		
		ModelMapper modelMapper = new ModelMapper();
		Client c = modelMapper.map(client, Client.class);
		c.setConfirme(false);
		c.setDemandeSuppression(false);
		c.setMotdepasse(bCryptPasswordEncoder.encode(client.getMotdepasse()));
		c.setRole(RoleEnum.CLIENT);
		
		ClientResponse clientresponse = modelMapper.map(clientRepository.save(c), ClientResponse.class);		
		return clientresponse;
		
	}
	
	@Override
	public ClientResponse Client(Long clientId) {
		ModelMapper modelMapper = new ModelMapper();
		Client clientEntity = (Client)clientRepository.findById(clientId).orElse(null);
		if(clientEntity== null) return null;
		ClientResponse clientresponse = modelMapper.map(clientEntity, ClientResponse.class);		
		return clientresponse;
	}

	@Override
	public ClientResponse modifierClient(Principal principal, ClientRequest client) {
		ModelMapper modelMapper = new ModelMapper();
		Client c = modelMapper.map(client, Client.class);
		Client clientEntity = (Client) clientRepository.findByUsername(principal.getName());
		if(clientEntity== null) return null;
		clientEntity.setMail(client.getMail());
		clientEntity.setNom(client.getNom());
		clientEntity.setPrenom(client.getPrenom());
		clientEntity.setConfirme(false);
		clientEntity.setRole(RoleEnum.CLIENT);
		ClientResponse clientresponse = modelMapper.map(clientRepository.save(clientEntity), ClientResponse.class);		
		return clientresponse;
	}

	@Override
	public ClientResponse demandeDeSuppressionDuClient(Principal principal) {
		ModelMapper modelMapper = new ModelMapper();
		Client clientEntity = (Client) clientRepository.findByUsername(principal.getName());
		if(clientEntity == null) return null;
		clientEntity.setDemandeSuppression(true);
		ClientResponse clientresponse = modelMapper.map(clientRepository.save(clientEntity), ClientResponse.class);		
		return clientresponse;
	}

	@Override
	public List<ClientResponse> Clients() {
		List<ClientResponse> clients = new ArrayList<ClientResponse>();
		ModelMapper modelMapper = new ModelMapper();
		clientRepository.findByRole(RoleEnum.CLIENT).forEach(Client -> {
			clients.add(modelMapper.map(clientRepository.save(Client), ClientResponse.class));
		});
		
		return clients;
	}

	@Override
	public List<ClientResponse> ClientsDesireSupprimer() {
		List<ClientResponse> clients = new ArrayList<ClientResponse>();
		ModelMapper modelMapper = new ModelMapper();
		clientRepository.findByDemandeSuppression(true).forEach(Client -> {
			clients.add(modelMapper.map(clientRepository.save(Client), ClientResponse.class));
		});
		
		return clients;
	}

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Client client = clientRepository.findByUsername(username);
//		if(client != null) {
//			Collection<GrantedAuthority>  authorities = new ArrayList<GrantedAuthority>();
//			authorities.add(new SimpleGrantedAuthority(client.getRole().toString()));
//			return new User(client.getUsername(), client.getMotdepasse() , authorities);
//		}else throw new UsernameNotFoundException(username);
//	}

}
