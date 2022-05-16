package backEndQuickBank.controlleur;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backEndQuickBank.requests.CompteRequest;
import backEndQuickBank.requests.ViremantRequest;
import backEndQuickBank.responses.AdministrateurResponse;
import backEndQuickBank.responses.ClientResponse;
import backEndQuickBank.responses.CompteResponse;
import backEndQuickBank.responses.VirementResponse;
import backEndQuickBank.services.CompteService;

@RestController
@RequestMapping("/compte")
public class CompteContolleur {

	@Autowired 
	CompteService compteService;
	
	@PreAuthorize("hasAuthority('CLIENT')")
	@GetMapping("/comptes")
	public ResponseEntity<?> mesComptes(Principal principale){
		List<CompteResponse> reponse = compteService.mesCompte( principale);
		if(reponse == null) {
			return new ResponseEntity<String>("Vous avez aucune compte chez nous !",HttpStatus.NOT_FOUND); 
		}
		return new ResponseEntity<List<CompteResponse>>(reponse,HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('CLIENT')")
	@PostMapping("/creecompteauthentifie")
	public ResponseEntity<?> ajouterCompteAuthentifie(@RequestBody CompteRequest compteRequest ,
			Principal principale){
		CompteResponse reponse = compteService.ajouterCompte(compteRequest, principale);
		if(reponse == null) {
			return new ResponseEntity<String>("Opération non autorisée !",HttpStatus.NOT_FOUND); 
		}
		return new ResponseEntity<CompteResponse>(reponse,HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('CLIENT')")
	@GetMapping("/demandesuppression/{compteId}")
	public ResponseEntity<?> demmandeSuppCompte(@PathVariable("compteId") Long compteId ) {
		CompteResponse reponse = compteService.demandeDeSuppressionDuCompte(compteId);
		if(reponse == null) {
			return new ResponseEntity<String>("Compte n'existe pas !",HttpStatus.NOT_FOUND); 
		}
		return new ResponseEntity<CompteResponse>(reponse,HttpStatus.OK);	
	}
	
	@PreAuthorize("hasAuthority('CLIENT')")
	@GetMapping("/demandecarte/{compteId}")
	public ResponseEntity<?> demmandeCarte(@PathVariable("compteId") Long compteId ) {
		CompteResponse reponse = compteService.demandeDeCarte(compteId);
		if(reponse == null) {
			return new ResponseEntity<String>("Compte n'existe pas !",HttpStatus.NOT_FOUND); 
		}
		return new ResponseEntity<String>("Votre demande sera traité dans le plus bref delai !",HttpStatus.OK);	
	}

	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/comptesdemandecartes")
	public ResponseEntity<?> cartesDemande() {
		List<CompteResponse> reponse = compteService.CompteDemandeCarte();
		if(reponse == null) {
			return new ResponseEntity<String>("Aucune demande de carte",HttpStatus.NOT_FOUND); 
		}
		return new ResponseEntity<List<CompteResponse>>(reponse,HttpStatus.OK);	
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/comptesdesiresupprimer")
	public ResponseEntity<?> ComptesSuppression() {
		List<CompteResponse> reponse = compteService.ComptedesireSupprime();
		if(reponse == null) {
			return new ResponseEntity<String>("Aucune demande de suppression",HttpStatus.NOT_FOUND); 
		}
		return new ResponseEntity<List<CompteResponse>>(reponse,HttpStatus.OK);	
	}
	
	@PreAuthorize("hasAuthority('CLIENT')")
	@PostMapping("/virement")
	public ResponseEntity<?> virement(@RequestBody ViremantRequest virementRequest,Principal principal) {
		VirementResponse reponse = compteService.virement(virementRequest.getNumCompteEmetteur(),
				virementRequest.getNumCompteRecepteur(),
				virementRequest.getMontant(),principal);
		if(reponse == null) {
			return new ResponseEntity<String>("Quelque chose s'est mal passé ! Verifiée votre solde ",HttpStatus.NOT_FOUND); 
		}
		return new ResponseEntity<VirementResponse>(reponse,HttpStatus.OK);	
	}
	
	
	
}
