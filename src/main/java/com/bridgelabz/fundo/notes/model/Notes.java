
package com.bridgelabz.fundo.notes.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.bridgelabz.fundo.labels.model.Labels;
import com.bridgelabz.fundo.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.ToString;

@ToString
@Entity
@Table
public class Notes {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long noteId;

	private long labelId;
	private long userId;
	@NotNull
	@NotEmpty(message = "Please enter valid title")
	private String title;
	@NotEmpty(message = " Please write description")
	private String description;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	private String color;
	private boolean isPin;
	private boolean isTrash;
	private boolean isArchive;
	private String reminder;

	@ManyToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Labels> notesLabels;

	@ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
	@JsonIgnoreProperties("collaboratedNotes")
	private Set<User> collaboratedUser;


	public Set<User> getCollaboratedUser() {
		return collaboratedUser;
	}

	public void setCollaboratedUser(Set<User> collaboratedUser) {
		this.collaboratedUser = collaboratedUser;
	}

	// Constructor
	public Notes() {}

	public List<Labels> getNotesLabels() {
		return notesLabels;
	}

	public void setNotesLabels(List<Labels> notesLabels) {
		this.notesLabels = notesLabels;
	}

	public long getLabelId() {
		return labelId;
	}

	public void setLabelId(long labelId) {
		this.labelId = labelId;
	}

	public long getNoteId() {
		return noteId;
	}

	public long getUserId() {
		return userId;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public boolean isPin() {
		return isPin;
	}

	public boolean isTrash() {
		return isTrash;
	}

	public boolean isArchive() {
		return isArchive;
	}

	public void setNoteId(long noteId) {
		this.noteId = noteId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setPin(boolean isPin) {
		this.isPin = isPin;
	}

	public void setTrash(boolean isTrash) {
		this.isTrash = isTrash;
	}

	public void setArchive(boolean isArchive) {
		this.isArchive = isArchive;
	}

	public String getReminder() {
		return reminder;
	}

	public void setReminder(String reminder) {
		this.reminder = reminder;
	}

	

}
