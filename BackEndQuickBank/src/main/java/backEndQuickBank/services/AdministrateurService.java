package backEndQuickBank.services;

import java.security.Principal;
import java.util.List;

import backEndQuickBank.requests.AdministrateurRequests;
import backEndQuickBank.responses.AdministrateurResponse;
import backEndQuickBank.responses.ClientResponse;
import backEndQuickBank.responses.CompteResponse;
import backEndQuickBank.responses.PostResponse;

public interface AdministrateurService {

	List<AdministrateurResponse> Administrateurs();
	AdministrateurResponse ajouterAdministrateur(AdministrateurRequests administrateur);
	AdministrateurResponse supprimerAdministrateur(Long administrateurId);
	PostResponse setPostVisible(Long postId);
	PostResponse setPostValide(Long postId);
	ClientResponse setClientConfirme(Long clientId, Principal principal);
	CompteResponse confirmeCompteSuppression(Long compteId);
	ClientResponse confirmeClientSuppression(Long clientId);
	
}
