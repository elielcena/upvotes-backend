package br.com.segware.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.segware.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
