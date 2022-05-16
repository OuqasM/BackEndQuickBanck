package backEndQuickBank.responses;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class VirementResponse {

	private String nomEmmeteur;
	private String emmeteur;
	private String nomRecepteur;
	private String recepteur;
	private Date date;
	private double montant;
	
	
}
