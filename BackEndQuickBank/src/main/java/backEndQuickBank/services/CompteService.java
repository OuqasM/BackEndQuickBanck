package backEndQuickBank.services;

import java.security.Principal;
import java.util.List;

import backEndQuickBank.entities.Client;
import backEndQuickBank.requests.CompteRequest;
import backEndQuickBank.requests.MailRequest;
import backEndQuickBank.responses.CompteResponse;
import backEndQuickBank.responses.VirementResponse;

public interface CompteService {

	List<CompteResponse> mesCompte(Principal principal);
	List<CompteResponse> ComptedesireSupprime();
	List<CompteResponse> CompteDemandeCarte();
	CompteResponse ajouterCompte(CompteRequest compte, Principal principal);
	CompteResponse demandeDeSuppressionDuCompte(Long compteId);
	CompteResponse demandeDeCarte(Long compteId);
	Boolean mail(MailRequest mail);
	VirementResponse virement(String numCompteEmetteur,String numCompteRecepteur, double montant,Principal principal);
	
}
