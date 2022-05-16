package backEndQuickBank.controlleur;

import java.security.Principal;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backEndQuickBank.requests.AdministrateurRequests;
import backEndQuickBank.responses.AdministrateurResponse;
import backEndQuickBank.responses.ClientResponse;
import backEndQuickBank.responses.CompteResponse;
import backEndQuickBank.responses.PostResponse;
import backEndQuickBank.services.AdministrateurService;

@RestController
@RequestMapping("/administrateur")
public class AdministrateurControlleur {

	@Autowired
	AdministrateurService adminService;
	
	@PostAuthorize("hasAuthority('ADMIN')")
	@GetMapping
	public ResponseEntity<List<AdministrateurResponse>> getAllAdmins() {
		return new ResponseEntity<List<AdministrateurResponse>>(adminService.Administrateurs(),HttpStatus.OK); 
	}
	
	@PostAuthorize("hasAuthority('ADMIN')")
	@PostMapping
	public ResponseEntity<?> addAdmin(@RequestBody AdministrateurRequests administrateur) {
		AdministrateurResponse reponse = adminService.ajouterAdministrateur(administrateur);
		if(reponse == null) {
			return new ResponseEntity<String>("Administrateur déja existe !",HttpStatus.CONFLICT); 
		}
		return new ResponseEntity<AdministrateurResponse>(reponse,HttpStatus.OK);	
	}
	
	@PostAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/{adminId}")
	public ResponseEntity<String> dropAdministrateur(@PathVariable(name = "adminId") Long adminId){
		
		AdministrateurResponse reponse = adminService.supprimerAdministrateur(adminId);
		if(reponse == null) {
			return new ResponseEntity<String>("Administrateur administrateur !",HttpStatus.NOT_FOUND)  ;
		}
		return new ResponseEntity<String>("Administrateur Supprimée avec succée !",HttpStatus.OK);
	
	}
	
	@PostAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/setvalidepost/{postId}")
	public ResponseEntity<?> setvalidatePost(@PathVariable(name = "postId")Long postId){
		
		PostResponse reponse = adminService.setPostValide(postId);
		if(reponse == null) {
			return new ResponseEntity<String>("Post n'existe pas !",HttpStatus.NOT_FOUND)  ;
		}
		return new ResponseEntity<PostResponse>(reponse,HttpStatus.OK);		
	}

	@PostAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/setvisiblepost/{postId}")
	public ResponseEntity<?> setvisiblePost(@PathVariable(name = "postId")Long postId){
		PostResponse reponse = adminService.setPostVisible(postId);
		if(reponse == null) {
			return new ResponseEntity<String>("Post n'existe pas !",HttpStatus.NOT_FOUND)  ;
		}
		return new ResponseEntity<PostResponse>(reponse,HttpStatus.OK);		
	}
	
	@PostAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/confirmeclient/{clientId}") // wssel hna khessni nbeddel idadmin b bprincipal
	public ResponseEntity<?> confirmeClient(@PathVariable("clientId")Long clientId,Principal principal){
		ClientResponse reponse = adminService.setClientConfirme(clientId, principal);
		if(reponse == null) {
			return new ResponseEntity<String>("Administrateur ou client n'existe pas !",HttpStatus.NOT_FOUND)  ;
		}
		return new ResponseEntity<ClientResponse>(reponse,HttpStatus.OK);		
	
	}
	
	@PostAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/confirmeclientsupp/{clientId}")
	public ResponseEntity<?> confirmeClientSuppression(@PathVariable("clientId")Long clientId){
		ClientResponse reponse = adminService.confirmeClientSuppression(clientId);
		if(reponse == null) {
			return new ResponseEntity<String>("Client n'existe pas !",HttpStatus.NOT_FOUND)  ;
		}
		return new ResponseEntity<ClientResponse>(reponse,HttpStatus.OK);		
	}
	
	@PostAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/confirmecomptesupp/{compteId}")
	public ResponseEntity<?> confirmeCompteSuppression(@PathVariable("compteId")Long compteId){
		CompteResponse reponse = adminService.confirmeCompteSuppression(compteId);
		if(reponse == null) {
			return new ResponseEntity<String>("Compte n'existe pas !",HttpStatus.NOT_FOUND)  ;
		}
		return new ResponseEntity<CompteResponse>(reponse,HttpStatus.OK);		
	}
}














