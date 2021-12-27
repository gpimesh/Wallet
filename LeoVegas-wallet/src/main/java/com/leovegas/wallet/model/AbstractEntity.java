package com.leovegas.wallet.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

	public static final String DATE_FORMAT= "yyyy-MM-dd HH:mm";


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(updatable = false)
	@JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
	protected Long id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
	@Column(name = "modified_date", nullable = false)
	@DateTimeFormat(pattern = DATE_FORMAT)
	@JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
	protected LocalDateTime modifiedAt;

	@Version
    protected int version;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
	@Column(name = "creation_date", nullable = false)
	@DateTimeFormat(pattern = DATE_FORMAT)
	@JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
	protected LocalDateTime createdAt;

	public AbstractEntity() {
		createdAt = LocalDateTime.now();
		modifiedAt = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(LocalDateTime modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractEntity other = (AbstractEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	
	
}
