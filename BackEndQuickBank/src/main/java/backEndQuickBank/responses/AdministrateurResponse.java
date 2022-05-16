package backEndQuickBank.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class AdministrateurResponse {

	private Long id;
	private String nomComplet;
	private String username;
}
