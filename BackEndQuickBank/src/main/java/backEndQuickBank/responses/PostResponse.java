package backEndQuickBank.responses;

import backEndQuickBank.entities.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class PostResponse {
	
	private Long id;
	private String corp;
	private SimpleClientResponse client;
}
