package backEndQuickBank.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class MailRequest {

	private String emetteur;
	private String recepteur;
	private String corp;
}
