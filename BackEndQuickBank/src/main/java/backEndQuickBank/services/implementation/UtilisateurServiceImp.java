package backEndQuickBank.services.implementation;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import backEndQuickBank.entities.Client;
import backEndQuickBank.entities.Utilisateur;
import backEndQuickBank.repositories.UtilisateurRepository;
import backEndQuickBank.services.UtilisteurService;

@Service
public class UtilisateurServiceImp implements UtilisteurService{

	@Autowired
	UtilisateurRepository utilisateurRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println(username + "dddddddddddddd");
		Utilisateur utilisateur = utilisateurRepository.findByUsername(username);
		if(utilisateur != null) {
			Collection<GrantedAuthority>  authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority(utilisateur.getRole().toString()));
			System.out.println(utilisateur.getMotdepasse() + "5555555");
			return new User(utilisateur.getUsername(), utilisateur.getMotdepasse() , authorities);
		}else throw new UsernameNotFoundException(username);
	
	}

}
