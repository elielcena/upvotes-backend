package br.com.segware.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.segware.dto.UpVotesDTO;
import br.com.segware.exception.RecordNotFoundException;
import br.com.segware.model.Post;
import br.com.segware.repository.PostRepository;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

	@Autowired PostRepository repository;

	@GetMapping()
	private ResponseEntity<Object> findAll() {
		return ResponseEntity.ok(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));	
	}

	@PostMapping()
	private ResponseEntity<Object> save(@Valid @RequestBody() Post post) {
		post = repository.save(Post.validaUpVotesNegativos(post));
		return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(post);
	}

	@PutMapping("/upVotes")
	private ResponseEntity<Object> updateUpVotes(@Valid @RequestBody() UpVotesDTO upVote) throws RecordNotFoundException {
		return repository.
				findById(upVote.getIdPost())
				.map(post -> { 
					post = Post.updateUpVotes(post, upVote.getOperacao());
					Object postObj = repository.save(post);
					return ResponseEntity.ok(postObj);
				})
				.orElseThrow(() -> new RecordNotFoundException("Field error in object 'post' with 'idpost' " + upVote.getIdPost() + " não encontrado"));
	}

}
