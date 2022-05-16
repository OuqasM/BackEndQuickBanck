package backEndQuickBank.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor

public class SimpleClientResponse {

	private Long id;
	private String username;
	private String nom;
}
