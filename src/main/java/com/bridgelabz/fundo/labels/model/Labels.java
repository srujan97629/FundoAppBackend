package com.bridgelabz.fundo.labels.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import com.bridgelabz.fundo.notes.model.Notes;

@Component
@Entity
@Table
public class Labels {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long labelId;

	private long userId;
	@NotNull
	@NotEmpty(message = "Please enter label name")
	private String labelName;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	private long noteId;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Notes> labelNotes;
	
	//constructor
	public Labels() {
		
	}

	public long getLabelId() {
		return labelId;
	}

	public long getUserId() {
		return userId;
	}

	public String getLabelName() {
		return labelName;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public long getNoteId() {
		return noteId;
	}

	public List<Notes> getLabelNotes() {
		return labelNotes;
	}

	public void setLabelId(long labelId) {
		this.labelId = labelId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public void setNoteId(long noteId) {
		this.noteId = noteId;
	}

	public void setLabelNotes(List<Notes> labelNotes) {
		this.labelNotes = labelNotes;
	}

	@Override
	public String toString() {
		return "Labels [labelId=" + labelId + ", userId=" + userId + ", labelName=" + labelName + ", createdDate="
				+ createdDate + ", modifiedDate=" + modifiedDate + ", noteId=" + noteId + ", labelNotes=" + labelNotes
				+ "]";
	}
	
	

}
