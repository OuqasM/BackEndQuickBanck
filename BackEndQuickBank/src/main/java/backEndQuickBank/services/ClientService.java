package backEndQuickBank.services;

import java.security.Principal;
import java.util.List;

import backEndQuickBank.entities.Client;
import backEndQuickBank.entities.Compte;
import backEndQuickBank.repositories.UtilisateurRepository;
import backEndQuickBank.requests.ClientRequest;
import backEndQuickBank.responses.ClientResponse;

public interface ClientService {

	List<ClientResponse> Clients();
	List<ClientResponse> ClientsDesireSupprimer();
	ClientResponse Client(Long clientId);
	ClientResponse ajouterClient(ClientRequest client);
	ClientResponse modifierClient( Principal principal,ClientRequest client);
	ClientResponse demandeDeSuppressionDuClient(Principal principal);
//	ClientResponse ajouterCompteAuClient(Long clientId, Long compteId);
	
}
