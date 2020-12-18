package br.com.segware;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.segware.controller.PostController;
import br.com.segware.dto.UpVotesDTO;
import br.com.segware.model.Post;
import br.com.segware.repository.PostRepository;
import br.com.segware.util.UpVotesOperation;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PostController.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostControllerTests {

	private final String URI_POST = "http://localhost:8080/api/v1/posts";

	private static ObjectMapper mapper = new ObjectMapper();
	
	@Autowired MockMvc mockMvc;

	@MockBean PostRepository repository;

	@Test
	public void whenFindAllPostOrderByIdDescAndRerifyData_thenReturnStatusCode() throws Exception {
		List<Post> userList =  Arrays.asList(new Post(1L, "Spring Boot Test", "Test Post Repository - Save Post", 1), new Post(2L, "Spring Boot Test 2", "Test Post Repository 2 - Save Post 2", 2));

		when(repository.findAll(Sort.by(Sort.Direction.DESC, "id"))).thenReturn(userList);

		mockMvc.perform(get(URI_POST))
		.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
		
		.andExpect(jsonPath("$[0].id", is(1)))
		.andExpect(jsonPath("$[0].titulo", is("Spring Boot Test")))
		.andExpect(jsonPath("$[0].descricao", is("Test Post Repository - Save Post")))
		.andExpect(jsonPath("$[0].upvotes", is(1)))
		
		.andExpect(jsonPath("$[1].id", is(2)))
		.andExpect(jsonPath("$[1].titulo", is("Spring Boot Test 2")))
		.andExpect(jsonPath("$[1].descricao", is("Test Post Repository 2 - Save Post 2")))
		.andExpect(jsonPath("$[1].upvotes", is(2)));

		verify(repository).findAll(Sort.by(Sort.Direction.DESC, "id"));
	}

	@Test
	public void  whenSavePostCorrectData_thenReturnStatusCode200 () throws Exception {
		Post post = new Post(1L, "Spring Boot Test", "Test Post Repository - Save Post Correct", 1);

		when(repository.save(post)).thenReturn(post);

		String jsonString = mapper.writeValueAsString(post);

		mockMvc.perform(post(URI_POST).contentType(MediaType.APPLICATION_JSON).content(jsonString))
		.andExpect(status().isCreated())
		.andDo(print());
	}

	@Test
	public void  whenSavePostWrongDataValidFailed_thenReturnStatusCode400 () throws Exception {
		Post post = new Post("Test Post Repository - Save Post Valid Failed", 1);

		when(repository.save(post)).thenReturn(post);

		String jsonString = mapper.writeValueAsString(post);

		mockMvc.perform(post(URI_POST).contentType(MediaType.APPLICATION_JSON).content(jsonString))
		.andExpect(status().isBadRequest())
		.andDo(print());
	}
	
	@Test
    public void whenUpdateUpVotesIdPostNotExist_thenReturnStatusCode404() throws Exception {
        UpVotesDTO upVotes = new UpVotesDTO(9999L, UpVotesOperation.ADD);

        String json = mapper.writeValueAsString(upVotes);
        
        mockMvc.perform(put(URI_POST.concat("/upVote")).contentType(MediaType.APPLICATION_JSON)
                .content(json).accept(MediaType.APPLICATION_JSON))
        		.andExpect(status().isNotFound());
    }
}
