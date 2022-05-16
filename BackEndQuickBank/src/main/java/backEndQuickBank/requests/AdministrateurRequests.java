package backEndQuickBank.requests;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class AdministrateurRequests {
	
	private Long id;
	private String nomComplet;
	private String username;
	private String motdepasse;
}
