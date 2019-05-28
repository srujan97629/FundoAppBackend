package com.bridgelabz.fundo.notes.service;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundo.labels.model.Labels;
import com.bridgelabz.fundo.labels.repository.LabelsRepository;
import com.bridgelabz.fundo.notes.dto.NotesDto;
import com.bridgelabz.fundo.notes.model.Notes;
import com.bridgelabz.fundo.notes.repository.NotesRepository;
import com.bridgelabz.fundo.rabbitmq.MessageProducer;
import com.bridgelabz.fundo.response.Response;
import com.bridgelabz.fundo.user.dto.EmailIdDto;
import com.bridgelabz.fundo.user.exceptions.UserException;
import com.bridgelabz.fundo.user.model.User;
import com.bridgelabz.fundo.user.repository.UserRepository;
//import com.bridgelabz.fundo.utility.EmailService;
import com.bridgelabz.fundo.utility.ResponseStatus;
import com.bridgelabz.fundo.utility.TokenGenerator;

@Service
public class NotesServiceImpl implements INotesService {
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	NotesRepository notesRepository;
	@Autowired
	TokenGenerator tokenGenerator;
	@Autowired
	private Environment environment;
	@Autowired
	UserRepository userRepository;
	@Autowired
	LabelsRepository labelRepository;
//	@Autowired
//	private EmailService emailService;
	@Autowired
	MessageProducer messageProduce;

	@Override
	public Response createNotes(NotesDto notesDto, String token) {
		Long userId = tokenGenerator.decodeToken(token);
		System.out.println("create start");
		User currentUser = userRepository.findById(userId)
				.orElseThrow(() -> new UserException(environment.getProperty("createNotes.failure"), 400));
		Notes note = modelMapper.map(notesDto, Notes.class);
		note.setArchive(false);
		note.setPin(false);
		note.setTrash(false);
		note.setCreatedDate(LocalDateTime.now());
		note.setModifiedDate(LocalDateTime.now());
		note.setUserId(userId);
		note.setNoteId(note.getNoteId());
		currentUser.getNotes().add(note);
		System.out.println("in implimentation");
		notesRepository.save(note);
		userRepository.save(currentUser);
		return ResponseStatus.sendResponse(environment.getProperty("createNotes.success"), 401);
	}

	@Override
	public Response deleteNotes(Long noteId, String token) {
		notesRepository.deleteById(noteId);
		return ResponseStatus.sendResponse(environment.getProperty("notesDelete.success"), 450);
	}

	@Override
	public Response updateNotes(NotesDto notesDto, String token, long noteId) {

		Long userId = tokenGenerator.decodeToken(token);
		Notes notes = notesRepository.findByNoteIdAndUserId(noteId, userId);
		notes.setTitle(notesDto.getTitle());
		notes.setDescription(notesDto.getDescription());
		notes.setModifiedDate(LocalDateTime.now());
		notesRepository.save(notes);
		Response response = ResponseStatus.sendResponse(environment.getProperty("noteUpdate.success"), 4);
		return response;
	}

	@Override
	public List<Notes> readNotes(String token) {

		Long userId = tokenGenerator.decodeToken(token);
		List<Notes> notes = (List<Notes>) notesRepository.findByUserId(userId);
		List<Notes> allNotes = new ArrayList<>();
		for (Notes userNotes : notes) {
			Notes note = modelMapper.map(userNotes, Notes.class);
			if (note.isArchive() == false && note.isPin() == false && note.isTrash() == false) {
				allNotes.add(note);
			}
		}

		return allNotes;
	}

	@Override
	public Response pinNotes(Long noteId, String token) {
		Response response = null;
		Long userId = tokenGenerator.decodeToken(token);
		Notes notes = notesRepository.findByNoteIdAndUserId(noteId, userId);
		if (notes.isPin() == false) {
			notes.setPin(true);
			notes.setModifiedDate(LocalDateTime.now());
			notesRepository.save(notes);
			response = ResponseStatus.sendResponse(environment.getProperty("notesPin.success"), 280);
		}

		else if (notes.isPin() == true) {
			notes.setPin(false);
			notes.setModifiedDate(LocalDateTime.now());
			notesRepository.save(notes);
			response = ResponseStatus.sendResponse(environment.getProperty("notesUnpin.success"), 281);
		}

		else {
			response = ResponseStatus.sendResponse(environment.getProperty("notesPin.failure"), 282);
		}

		return response;
	}

	@Override
	public Response trashNotes(Long noteId, String token) {
		Response response = null;
		Long userId = tokenGenerator.decodeToken(token);
		Notes notes = notesRepository.findByNoteIdAndUserId(noteId, userId);
		if (notes.isTrash() == false) {
			notes.setTrash(true);
			notes.setModifiedDate(LocalDateTime.now());
			notesRepository.save(notes);
			response = ResponseStatus.sendResponse(environment.getProperty("trash.success"), 50);
		}

		else if (notes.isTrash() == true) {
			notes.setTrash(false);
			notes.setModifiedDate(LocalDateTime.now());
			notesRepository.save(notes);
			response = ResponseStatus.sendResponse(environment.getProperty("removedFromTrash.success"), 51);
		} else {
			response = ResponseStatus.sendResponse(environment.getProperty("trash.unsuccess"), 52);
		}

		return response;
	}

	@Override
	public Response archiveNotes(Long noteId, String token) {

		Response response = null;
		Long userId = tokenGenerator.decodeToken(token);
		Notes notes = notesRepository.findByNoteIdAndUserId(noteId, userId);
		if (notes.isArchive() == false) {
			notes.setArchive(true);
			notes.setModifiedDate(LocalDateTime.now());
			notesRepository.save(notes);
			response = ResponseStatus.sendResponse(environment.getProperty("archive.success"), 70);
		} else if (notes.isArchive() == true) {
			notes.setArchive(false);
			notes.setModifiedDate(LocalDateTime.now());
			notesRepository.save(notes);
			response = ResponseStatus.sendResponse(environment.getProperty("unArchive.success"), 71);

		} else {
			response = ResponseStatus.sendResponse(environment.getProperty("archive.failure"), 72);
		}

		return response;
	}

	@Override
	public Response addNotesToLabel(long noteId, long labelId, String token) {
		Response response = null;
		long userid = tokenGenerator.decodeToken(token);
		// System.out.println("user id " + userid);
		Optional<User> user = userRepository.findById(userid);
		// System.out.println(user);
		Notes notes = notesRepository.findByNoteIdAndUserId(noteId, userid);
		// System.out.println("note id is =" + noteId + "labelId =" + labelId);
		Labels labels = labelRepository.findByLabelIdAndUserId(labelId, userid);
		if (user.isPresent()) {
			notes.setModifiedDate(LocalDateTime.now());
			// System.out.println("if");
			notes.setLabelId(labelId);
			labels.getLabelNotes().add(notes);
			labelRepository.save(labels);
			notesRepository.save(notes);
			response = ResponseStatus.sendResponse(environment.getProperty("addNotesToLabel.success"), 80);
		} else {
			System.out.println("else");
			response = ResponseStatus.sendResponse(environment.getProperty("addNotesToLabel.failure"), 81);
		}

		return response;
	}

	@Override
	public Response removeNotesFromLabel(long noteId, long labelId, String token) {

		Response response = null;
		long userid = tokenGenerator.decodeToken(token);
		Optional<User> user = userRepository.findById(userid);
		Notes notes = notesRepository.findByNoteIdAndUserId(noteId, userid);
		Labels labels = labelRepository.findByLabelIdAndUserId(labelId, userid);
		if (user.isPresent()) {
			notes.setModifiedDate(LocalDateTime.now());
			notes.setLabelId(labelId);
			labels.getLabelNotes().remove(notes);
			labelRepository.save(labels);
			notesRepository.save(notes);
			response = ResponseStatus.sendResponse(environment.getProperty("removeLabel.success"), 85);
		} else {
			response = ResponseStatus.sendResponse(environment.getProperty("removeLabel.failure"), 86);
		}

		return response;
	}

	@Override
	public List<Notes> trashNotes(String token) {

		Long userId = tokenGenerator.decodeToken(token);
		List<Notes> notes = (List<Notes>) notesRepository.findByUserId(userId);
		List<Notes> trashNotes = new ArrayList<>();
		for (Notes userNotes : notes) {
			Notes note = modelMapper.map(userNotes, Notes.class);
			if (note.isArchive() == false && note.isPin() == false && note.isTrash() == true) {
				trashNotes.add(note);
			}
		}

		return trashNotes;
	}

	@Override
	public List<Notes> archiveNotes(String token) {

		Long userId = tokenGenerator.decodeToken(token);
		List<Notes> notes = (List<Notes>) notesRepository.findByUserId(userId);
		List<Notes> archiveNotes = new ArrayList<>();
		for (Notes userNotes : notes) {
			Notes note = modelMapper.map(userNotes, Notes.class);
			if (note.isArchive() == true && note.isPin() == false && note.isTrash() == false) {
				archiveNotes.add(note);
			}
		}

		return archiveNotes;
	}

	@Override
	public List<Notes> pinNotes(String token) {

		Long userId = tokenGenerator.decodeToken(token);
		List<Notes> notes = (List<Notes>) notesRepository.findByUserId(userId);
		List<Notes> pinNotes = new ArrayList<>();
		for (Notes userNotes : notes) {
			Notes note = modelMapper.map(userNotes, Notes.class);
			if (note.isArchive() == false && note.isPin() == true && note.isTrash() == false) {
				pinNotes.add(note);
			}
		}

		return pinNotes;
	}

	@Override
	public Response addCollaborater(long userId, long noteId, String emailId) {
		Response response = null;
		userRepository.findById(userId).orElseThrow(() -> new UserException(environment.getProperty("noUser"), 404));
		Notes note = notesRepository.findById(noteId).get();
		Optional<User> searchUser = userRepository.findByEmailId(emailId);
		if (searchUser.isPresent()) {
			Set<Notes> userCollab = searchUser.get().getCollaboratedNotes();
			boolean isPresent = userCollab.stream().filter(value -> value.getNoteId() == note.getNoteId()).findFirst()
					.isPresent();
			if (isPresent) {
				response = ResponseStatus.sendResponse(environment.getProperty("collabUser.exist"), 250);
			} else {
				searchUser.get().getCollaboratedNotes().add(note);
				String name = searchUser.get().getFirstName();
				// System.out.println(searchUser.get().getCollaboratedNotes().add(note));
				note.getCollaboratedUser().add(searchUser.get());
				notesRepository.save(note);
				userRepository.save(searchUser.get());
				// Sending email
				EmailIdDto email = new EmailIdDto();
				email.setFrom(environment.getProperty("username"));
				email.setTo(emailId);
				email.setSubject(name + "(" + emailId + ")" + "shared a note with you.");
				email.setBody("Open keep to get the information");
				messageProduce.sendMessage(email);
				response = ResponseStatus.sendResponse(environment.getProperty("collaborate.success"), 200);

			}

		} else {
			response = ResponseStatus.sendResponse(environment.getProperty("collabUser.notPresent"), 404);
		}
		return response;

		// if(labels.stream().filter(g->g.getLabelId().equals(label.getLabelId())).findFirst().isPresent())
	}

	@Override
	public Response removeCollaborater(long userId, long noteId) {
		Response response = null;
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserException(environment.getProperty("noUser"), 404));
		Notes note = notesRepository.findById(noteId).get();
		Set<Notes> userCollab = user.getCollaboratedNotes();
		boolean isPresent = userCollab.stream().filter(value -> value.getNoteId() == note.getNoteId()).findFirst()
				.isPresent();
		if (isPresent) {
			System.out.println("ok");
			user.getCollaboratedNotes().remove(note);
			userRepository.save(user);
			notesRepository.save(note);
			response = ResponseStatus.sendResponse(environment.getProperty("collaboraterRemoved.success"), 200);
		} else {
			response = ResponseStatus.sendResponse(environment.getProperty("collaboraterRemoved.failure"), 404);
		}
		return response;
	}

	@Override
	public Response addReminder(long userId, long noteId, String reminder) {
		Response response = null;
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserException(environment.getProperty("noUser"), 404));
		Date date = null;
		DateFormat dateAndTimeFormate = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		try {
			date = (Date) dateAndTimeFormate.parse(reminder);
			SimpleDateFormat simpleDateFormate = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
			Notes note = notesRepository.findById(noteId).get();
			note.setReminder(simpleDateFormate.format(date));
			note.setModifiedDate(LocalDateTime.now());
			user.getNotes().add(note);
			userRepository.save(user);
			response = ResponseStatus.sendResponse(environment.getProperty("reminder.success"), 200);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public Response deleteReminder(long userId, long noteId) {

		return null;
	}

}
