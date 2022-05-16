package backEndQuickBank.responses;

import java.util.Date;
import backEndQuickBank.entities.Client;
import backEndQuickBank.requests.TypeCompte;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CompteResponse {
	private Long id;
	private String numCompte;
	private Date dateCreation;
	private double solde;
	private TypeCompte tyepCompte;
}
