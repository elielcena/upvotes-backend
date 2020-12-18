package br.com.segware;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.segware.model.Post;
import br.com.segware.repository.PostRepository;
import br.com.segware.util.UpVotesOperation;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostRepositoryTests {

	@Autowired PostRepository repository;

	@Test
	public void save () {
		Post post = new Post("Spring Boot Test", "Test Post Repository - Save Post", 1);

		repository.save(post);
		
		Integer countPost = repository.findAll().size();
		
		assertEquals(1, countPost);
		assertThat(post.getId()).isNotNull();
		assertThat(post.getTitulo()).isEqualTo("Spring Boot Test");
		assertThat(post.getDescricao()).isEqualTo("Test Post Repository - Save Post");
		assertThat(post.getUpVotes()).isEqualTo(1);
	}
	
	@Test
	public void updateUpVotes () {
		Post post = new Post("Spring Boot Test", "Test Post Repository - Update Votes in Post", 1);

		repository.save(post);
		
		post = Post.updateUpVotes(post, UpVotesOperation.ADD);
		
		repository.save(post);
		
		post = repository.findById(post.getId()).orElse(null);
		
		assertThat(post.getId()).isNotNull();
		assertThat(post.getTitulo()).isEqualTo("Spring Boot Test");
		assertThat(post.getDescricao()).isEqualTo("Test Post Repository - Update Votes in Post");
		assertThat(post.getUpVotes()).isEqualTo(2);
	}

}
