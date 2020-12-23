package br.com.segware.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable {

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "datagravacao")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonProperty("datagravacao")
	private Date datagravacao;

	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dataalteracao")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonProperty("dataalteracao")
	private Date dataalteracao;

}
