package br.com.segware.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.segware.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	
	@Transactional
	@Query("SELECT p FROM Post p WHERE to_char(p.datagravacao, 'yyyy-MM-dd') = :data ORDER BY p.id DESC")
	List<Post> findByData(@Param("data") String data);
}
