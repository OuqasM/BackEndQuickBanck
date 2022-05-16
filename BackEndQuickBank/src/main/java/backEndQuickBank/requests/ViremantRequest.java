package backEndQuickBank.requests;

import lombok.Data;

@Data
public class ViremantRequest {

	private String numCompteEmetteur;
	private String numCompteRecepteur;
	private double montant;
}
