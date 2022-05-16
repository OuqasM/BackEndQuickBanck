package backEndQuickBank.requests;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ClientRequest {

	private String nom;
	private String prenom;
	private String mail;
	private String username;
	private String motdepasse;
}
