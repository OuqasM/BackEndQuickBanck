package backEndQuickBank.services.implementation;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backEndQuickBank.entities.Client;
import backEndQuickBank.entities.Compte;
import backEndQuickBank.repositories.ClientRepository;
import backEndQuickBank.repositories.CompteRepository;
import backEndQuickBank.requests.CompteRequest;
import backEndQuickBank.requests.MailRequest;
import backEndQuickBank.responses.ClientResponse;
import backEndQuickBank.responses.CompteResponse;
import backEndQuickBank.responses.VirementResponse;
import backEndQuickBank.services.CompteService;
import backEndQuickBank.shared.Utils;
@Service
@Transactional
public class CompteServiceImp implements CompteService {


	@Autowired
	CompteRepository compteRepository;
	@Autowired
	ClientRepository clientRepository;
	
	@Override
	public CompteResponse ajouterCompte(CompteRequest compte, Principal principal) {
		Client client = clientRepository.findByUsername(principal.getName());
		if (client == null) return null;
		ModelMapper modelMapper = new ModelMapper();
		Compte c = modelMapper.map(compte, Compte.class);
		c.setClient(client);
		c.setDateCreation(new Date(System.currentTimeMillis()));
		c.setNumCompte(Utils.generateString(20));
		c.setSolde(0);
		c.setTyepCompte(compte.getTyepCompte());
		CompteResponse compteresponse = modelMapper.map(compteRepository.save(c), CompteResponse.class);		
		return compteresponse;
	}

	@Override
	public CompteResponse demandeDeSuppressionDuCompte(Long compteId) {
		ModelMapper modelMapper = new ModelMapper();
		Compte compteEntity = compteRepository.findById(compteId).orElse(null);
		if(compteEntity== null) return null;
		compteEntity.setDamandeSuppression(true);
		CompteResponse compteresponse = modelMapper.map(compteRepository.save(compteEntity), CompteResponse.class);		
		return compteresponse;
	}

	@Override
	public CompteResponse demandeDeCarte(Long compteId) {
		ModelMapper modelMapper = new ModelMapper();
		Compte compteEntity = compteRepository.findById(compteId).orElse(null);
		if(compteEntity== null) return null;
		compteEntity.setDamandeCarte(true);
		CompteResponse compteresponse = modelMapper.map(compteRepository.save(compteEntity), CompteResponse.class);		
		return compteresponse;
	}

	@Override
	public Boolean mail(MailRequest mail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VirementResponse virement(String numCompteEmetteur, String numCompteRecepteur, double montant, Principal principal) {
		Client client = clientRepository.findByUsername(principal.getName());
		System.out.println(client.getMail());
		Compte emetteur = compteRepository.findByNumCompte(numCompteEmetteur);
		Compte recepteur = compteRepository.findByNumCompte(numCompteRecepteur);
		
		
		
		if(emetteur == null || recepteur ==null) return null;
		if(emetteur.getSolde() < montant) return null;
		
		System.out.println("Emetteur " + emetteur.getNumCompte());
		System.out.println("Recepteur " + recepteur.getClient().getUsername());
		
		
		emetteur.setSolde(emetteur.getSolde()-montant);
		recepteur.setSolde(recepteur.getSolde()+montant);
	
		compteRepository.save(emetteur);
		compteRepository.save(recepteur);
		VirementResponse vr = new VirementResponse();
		vr.setDate(new Date(System.currentTimeMillis()));
		vr.setEmmeteur(numCompteEmetteur);
		vr.setMontant(montant);
		vr.setNomEmmeteur(client.getNom());
		vr.setNomRecepteur(recepteur.getClient().getNom());
		vr.setRecepteur(numCompteRecepteur);
//		MailRequest mailToEmetteur = new MailRequest();
//			mailToEmetteur.setEmetteur(Utils.BANK_MAIL);
//			mailToEmetteur.setRecepteur(this.emetteur.getClient().getMail());
//			mailToEmetteur.setCorp("Vous avez effectuer un virment de "+montant+"DH vers "+recepteur.getClient().getNom());
//		MailRequest mailToRecepteur = new MailRequest();
//			mailToRecepteur.setEmetteur(Utils.BANK_MAIL);
//			mailToRecepteur.setRecepteur(recepteur.getClient().getMail());
//			mailToRecepteur.setCorp("Vous avez recu un virment de "+montant+" depuit "+this.emetteur.getClient().getNom());
//		this.mail(mailToEmetteur);	
//		this.mail(mailToRecepteur);	
		return vr;
	}

	@Override
	public List<CompteResponse> mesCompte(Principal principal) {
		List<Compte> comptes = compteRepository.findByClient(clientRepository.findByUsername(principal.getName()));
		ModelMapper modelMapper = new ModelMapper();
		List<CompteResponse> comptesReponse = new ArrayList<CompteResponse>();
		
		if(comptes.size()<1) return null;
		comptes.forEach(compte-> {
			comptesReponse.add(modelMapper.map(compte, CompteResponse.class));
		});
		return comptesReponse;
	}

	@Override
	public List<CompteResponse> ComptedesireSupprime() {
		List<Compte> comptes = compteRepository.findByDamandeSuppression(true);
		ModelMapper modelMapper = new ModelMapper();
		List<CompteResponse> comptesReponse = new ArrayList<CompteResponse>();
		
		if(comptes.size()<1) return null;
		comptes.forEach(compte-> {
			comptesReponse.add(modelMapper.map(compte, CompteResponse.class));
		});
		return comptesReponse;
	}

	@Override
	public List<CompteResponse> CompteDemandeCarte() {
		List<Compte> comptes = compteRepository.findByDamandeCarte(true);
		ModelMapper modelMapper = new ModelMapper();
		List<CompteResponse> comptesReponse = new ArrayList<CompteResponse>();
		
		if(comptes.size()<1) return null;
		comptes.forEach(compte-> {
			comptesReponse.add(modelMapper.map(compte, CompteResponse.class));
		});
		return comptesReponse;
	}
}
