package br.com.segware.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.segware.util.UpVotesOperation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "post")
public class Post extends Auditable {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	@NotNull
	private String titulo;	

	@NotBlank
	@NotNull
	private String descricao;

	@NotNull
	@Column(name = "upvotes")
	@JsonProperty("upvotes")
	private Integer upVotes;

	public Post(String titulo, String descricao, Integer upVotes) {
		this.titulo = titulo;
		this.descricao = descricao;
		this.upVotes = upVotes;
	}
	
	public Post(String descricao, Integer upVotes) {
		this.descricao = descricao;
		this.upVotes = upVotes;
	}

	public static Post updateUpVotes(Post post, UpVotesOperation operacao) {
		switch(operacao) {
		case ADD:
			post.setUpVotes(post.getUpVotes() + 1);
			break;
		case REMOVE:
			//post.setUpVotes(post.getUpVotes() - 1);
			break;
		}
		return post;
	}

	public static Post validaUpVotesNegativos(Post post) {
		if(post.getUpVotes() < 0) post.setUpVotes(0);
		return post;
	}
}
