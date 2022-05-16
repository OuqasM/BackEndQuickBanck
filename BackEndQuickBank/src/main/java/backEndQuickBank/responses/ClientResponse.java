package backEndQuickBank.responses;

import java.util.List;

import backEndQuickBank.entities.Administrateur;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ClientResponse {
	
	private Long id;
	private String nom;
	private String prenom;
	private String mail;
	private String username;
	private String motdepasse;
	private List<CompteResponse> comptes;

}
