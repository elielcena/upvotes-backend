package br.com.segware.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.segware.util.UpVotesOperation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor
public class UpVotesDTO {

	@NotNull
	@JsonProperty("idpost")
	private Long idPost;

	@NotNull
	private UpVotesOperation operacao;

}
