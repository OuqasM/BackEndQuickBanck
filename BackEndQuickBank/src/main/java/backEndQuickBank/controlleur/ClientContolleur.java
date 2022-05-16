package backEndQuickBank.controlleur;

import java.security.Principal;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backEndQuickBank.requests.AdministrateurRequests;
import backEndQuickBank.requests.ClientRequest;
import backEndQuickBank.responses.AdministrateurResponse;
import backEndQuickBank.responses.ClientResponse;
import backEndQuickBank.services.ClientService;

@RestController
@RequestMapping("/client")
public class ClientContolleur {
	
	@Autowired
	ClientService clientService;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping
	public ResponseEntity<List<ClientResponse>> getAllClients() {
		return new ResponseEntity<List<ClientResponse>>(clientService.Clients(),HttpStatus.OK); 
	}
	
	@PostMapping
	public ResponseEntity<?> addClient(@RequestBody ClientRequest client) {
		ClientResponse reponse = clientService.ajouterClient(client);
		if(reponse == null) {
			return new ResponseEntity<String>("Client déja existe !",HttpStatus.CONFLICT); 
		}
		return new ResponseEntity<ClientResponse>(reponse,HttpStatus.OK);	
	
	}	

	@PostAuthorize("hasAuthority('CLIENT')")
	@PutMapping
	public ResponseEntity<?> modifierClient(@RequestBody ClientRequest client, Principal principal) {
		ClientResponse reponse = clientService.modifierClient(principal,client);
		if(reponse == null) {
			return new ResponseEntity<String>("Client n'existe pas !",HttpStatus.NOT_FOUND); 
		}
		return new ResponseEntity<ClientResponse>(reponse,HttpStatus.OK);	
	}
	
	@PostAuthorize("hasAnyAuthority('CLIENT')")
	@GetMapping("/demandesuppression")
	public ResponseEntity<?> demmandeSuppClient(Principal principal) {
		ClientResponse reponse = clientService.demandeDeSuppressionDuClient(principal);
		if(reponse == null) {
			return new ResponseEntity<String>("Client n'existe pas !",HttpStatus.NOT_FOUND); 
		}
		return new ResponseEntity<ClientResponse>(reponse,HttpStatus.OK);	
	}
	
//	@PostAuthorize("hasAnyAuthority('CLIENT','ADMIN')")
//	@PutMapping("/ajoutercompteauclient")
//	public ResponseEntity<?> ajouterCompteAuClient(@PathParam("clientId") Long clientId, @PathParam("compteId") Long compteId ) {
//
//		ClientResponse reponse = clientService.ajouterCompteAuClient(clientId,compteId);
//		if(reponse == null) {
//			return new ResponseEntity<String>("Compte ou client n'existe pas !",HttpStatus.NOT_FOUND); 
//		}
//		return new ResponseEntity<ClientResponse>(reponse,HttpStatus.OK); 
//	}

	@PostAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/{clientId}")
	public ResponseEntity<?> getClient(@PathVariable("clientId") Long clientId ) {
		ClientResponse reponse = clientService.Client(clientId);
		if(reponse == null) {
			return new ResponseEntity<String>("Client n'existe pas!",HttpStatus.CONFLICT); 
		}
		return new ResponseEntity<ClientResponse>(reponse,HttpStatus.OK);	
	
	}
	
	@PostAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/clientssupp")
	public ResponseEntity<?> getClientsDesireSupprimer() {
		List<ClientResponse> reponse = clientService.ClientsDesireSupprimer();
		if(reponse == null) {
			return new ResponseEntity<String>("Client n'existe pas!",HttpStatus.CONFLICT); 
		}
		return new ResponseEntity<List<ClientResponse>>(reponse,HttpStatus.OK);	
	
	}
		
	
}

















