package com.bridgelabz.fundo.user.model;

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
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.bridgelabz.fundo.labels.model.Labels;
import com.bridgelabz.fundo.notes.model.Notes;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.ToString;

@ToString
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userId;
	
	@NotBlank
    @Size(min = 4, max = 40)
	private String firstName;
	
	@NotBlank
    @Size(min = 4, max = 40)
	private String lastName;
	@NotBlank
    @Size(min = 10, max = 10)
	private String mobileNumber;
	
	private String profilePic;
	
	@NotBlank
	@Size(max = 40)
	@Email(message="Enter valid email")
	private String emailId;
	 @NotBlank
	 @Size(min = 6, max =15)
	private String password;
	private LocalDateTime registeredDate;
	private LocalDateTime modifiedDate;
	private boolean isVerified = false;
	@OneToMany(cascade = CascadeType.ALL)
    private List<Notes> notes;
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Labels> labels;
	
	 @ManyToMany(fetch =FetchType.LAZY ,cascade = { CascadeType.ALL})
	 @JsonIgnoreProperties("collaboratedUser") 
	private Set<Notes> collaboratedNotes;
   
	 //,mappedBy="collaboratedNotes"

	public Set<Notes> getCollaboratedNotes() {
		return collaboratedNotes;
	}

	public void setCollaboratedUser(Set<Notes> collaboratedNotes) {
		this.collaboratedNotes = collaboratedNotes;
	}

	public Set<Labels> getLabels() {
		return labels;
	}

	public void setLabels(Set<Labels> labels) {
		this.labels = labels;
	}

	public List<Notes> getNotes() {
		return notes;
	}

	public void setNotes(List<Notes> notes) {
		this.notes = notes;
	}

	public long getId() {
		return userId;
	}

	public void setId(long Id) {
		this.userId = Id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(LocalDateTime registeredDate) {
		this.registeredDate = registeredDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}
	
	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}



	

}
