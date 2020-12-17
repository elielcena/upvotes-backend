package br.com.segware.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name = "post")
public class Post extends Auditable {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String titulo;	
	
	private String descricao;
	
	@Column(name = "upvotes")
	@JsonProperty("upvotes")
	private Integer upVotes;
}
