package com.bridgelabz.fundo.labels.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundo.labels.dto.LabelsDto;
import com.bridgelabz.fundo.labels.model.Labels;
import com.bridgelabz.fundo.labels.repository.LabelsRepository;
import com.bridgelabz.fundo.notes.model.Notes;
import com.bridgelabz.fundo.notes.repository.NotesRepository;
import com.bridgelabz.fundo.response.Response;
import com.bridgelabz.fundo.user.exceptions.UserException;
import com.bridgelabz.fundo.user.model.User;
import com.bridgelabz.fundo.user.repository.UserRepository;
import com.bridgelabz.fundo.utility.ResponseStatus;
import com.bridgelabz.fundo.utility.TokenGenerator;

@Service
public class LabelsServiceImpl implements ILabelsService {
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
	LabelsRepository labelsRepository;

	@Override
	public Response createLabel(LabelsDto labelsDto, String token) {
		
		Response response = null;
		long userId = tokenGenerator.decodeToken(token);		
		Labels label = modelMapper.map(labelsDto, Labels.class);
		User user = userRepository.findById(userId)
				.orElseThrow(()-> new UserException(environment.getProperty("label.noUser"),90));
		label.setUserId(userId);		
		label.setCreatedDate(LocalDateTime.now());
		user.getLabels().add(label);
		labelsRepository.save(label);
		userRepository.save(user);
		response = ResponseStatus.sendResponse(environment.getProperty("label.success"), 91);
		return response;
	}

	@Override
	public Response updateLabel(LabelsDto labelsDto, String token, long labelId) {

		long userId = tokenGenerator.decodeToken(token);
		Labels labels = labelsRepository.findByLabelIdAndUserId(labelId, userId);
		labels.setLabelName(labelsDto.getLabelName());
		labels.setModifiedDate(LocalDateTime.now());
		labelsRepository.save(labels);
		return ResponseStatus.sendResponse(environment.getProperty("labelsUpdate.success"), 92);
	}

	@Override
	public List<Labels> getAllLabels(String token) {
		System.out.println("1");
		long userId = tokenGenerator.decodeToken(token);
		System.out.println("in labelas");
	    userRepository.findById(userId)
				.orElseThrow(() -> new UserException(environment.getProperty("label.noUser"), 90));
	
		return labelsRepository.findAll();
	}

	@Override
	public Response deleteLabel(String token, long labelId) {

		long userId = tokenGenerator.decodeToken(token);
		Labels labels = labelsRepository.findByLabelIdAndUserId(labelId, userId);
		labelsRepository.delete(labels);
		return ResponseStatus.sendResponse(environment.getProperty("labelsDelete.sucess"), 93);
	}

	@Override
	public Response addLabelToNote(String token, long noteId, long labelId) {
		Response response = null;
		long userId = tokenGenerator.decodeToken(token);
//		User user = userRepository.findById(userId).
//				orElseThrow(()-> new UserException(environment.getProperty("label.noUser"),90));
		Labels labels = labelsRepository.findByLabelIdAndUserId(labelId, userId);
		Notes notes = notesRepository.findByNoteIdAndUserId(noteId, userId);		
			labels.setModifiedDate(LocalDateTime.now());
			labels.setNoteId(noteId);
			notes.getNotesLabels().add(labels);
			labelsRepository.save(labels);
			notesRepository.save(notes);
			response = ResponseStatus.sendResponse(environment.getProperty("addLabel.success"), 40);
		
		return response;
	}

	@Override
	public Response removeLabelFromNote(String token, long noteId, long labelId) {

		Response response = null;
		long userId = tokenGenerator.decodeToken(token);
		Optional<User> user = userRepository.findById(userId);
		Labels labels = labelsRepository.findByLabelIdAndUserId(labelId, userId);
		Notes notes = notesRepository.findByNoteIdAndUserId(noteId, userId);
		if (user.isPresent()) {
			labels.setModifiedDate(LocalDateTime.now());
			labels.setNoteId(noteId);
			notes.getNotesLabels().remove(labels);
			labelsRepository.save(labels);
			notesRepository.save(notes);
			response = ResponseStatus.sendResponse(environment.getProperty("removeLabel.success"), 40);
		}

		return response;
	}

}
