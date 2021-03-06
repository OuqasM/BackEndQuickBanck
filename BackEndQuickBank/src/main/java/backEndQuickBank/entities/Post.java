package backEndQuickBank.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Post {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String corp;
	private boolean valide= false;
	private boolean visible=false;
	@ManyToOne
	private Client client;
}
