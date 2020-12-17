package br.com.segware.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.segware.util.UpVotesOperation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class UpVotesDTO {
	
	@JsonProperty("idpost")
	private Long idPost;
	
	private UpVotesOperation operacao;

}
