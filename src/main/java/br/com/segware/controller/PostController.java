package br.com.segware.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.segware.dto.UpVotesDTO;
import br.com.segware.model.Post;
import br.com.segware.repository.PostRepository;
import br.com.segware.util.JsonResponse;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

	@Autowired PostRepository repository;

	@GetMapping()
	private ResponseEntity<Object> findAll() {
		try {
			return ResponseEntity.ok(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
		} catch(Exception e) {
			e.printStackTrace();
			return JsonResponse.build(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping()
	private ResponseEntity<Object> save(@RequestBody() Post post) {
		try {
			post = repository.save(post);
			return ResponseEntity.ok(post);
		} catch(Exception e) {
			e.printStackTrace();
			return JsonResponse.build(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PutMapping()
	private ResponseEntity<Object> updateUpVotes(@RequestBody() UpVotesDTO upVote) {
		try {
			Optional<Post> result = repository.findById(upVote.getIdPost());

			if(result.isPresent()) {
				Post post = (Post) result.get();
				switch(upVote.getOperacao()) {
				case ADD:
					post.setUpVotes(post.getUpVotes() + 1);
					break;
				case REMOVE:
					//post.setUpVotes(post.getUpVotes() - 1);
					break;
				}
				return ResponseEntity.ok(repository.save(post));
			}

			String response = "Post com id " + upVote.getIdPost() + " não encontrado!";
			return JsonResponse.build(HttpStatus.BAD_REQUEST, response);
		} catch(Exception e) {
			e.printStackTrace();
			return JsonResponse.build(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

}
