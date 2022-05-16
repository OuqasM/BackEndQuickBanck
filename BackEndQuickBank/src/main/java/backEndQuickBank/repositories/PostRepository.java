package backEndQuickBank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import backEndQuickBank.entities.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

}
