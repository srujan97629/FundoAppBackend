package com.bridgelabz.fundo.notes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundo.notes.dto.NotesDto;
import com.bridgelabz.fundo.notes.model.Notes;
import com.bridgelabz.fundo.notes.service.INotesService;
import com.bridgelabz.fundo.response.Response;
import com.bridgelabz.fundo.utility.TokenGenerator;

@RestController
@RequestMapping("/user/notes")
public class NotesController {
	@Autowired
	INotesService iNotesService;
	@Autowired
	private TokenGenerator tokenGenerator;

	@PostMapping("/create")
	public ResponseEntity<Response> createNote(@RequestBody NotesDto notesDto,
			@RequestHeader(name = "token") String token) {
		System.out.println("in notes controller");
		Response response = iNotesService.createNotes(notesDto, token);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Response> deleteNote(@RequestParam(name = "noteId") Long noteId,
			@RequestHeader(value = "token") String token) {
		Response response = iNotesService.deleteNotes(noteId, token);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<Response> updateNote(@RequestBody NotesDto notesDto,
			@RequestHeader(value = "token") String token, @RequestParam long noteId) {
		Response response = iNotesService.updateNotes(notesDto, token, noteId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/allnotes")
	public List<Notes> readNotes(@RequestHeader(value = "token") String token) {
		List<Notes> listNotes = iNotesService.readNotes(token);
		return listNotes;
	}

	@PostMapping("/pin")
	public ResponseEntity<Response> pinNote(@RequestParam(name = "noteId") Long noteId,
			@RequestHeader(value = "token") String token) {
		Response response = iNotesService.pinNotes(noteId, token);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/trash")
	public ResponseEntity<Response> trashNote(@RequestParam(name = "noteId") Long noteId,
			@RequestHeader(value = "token") String token) {
		Response response = iNotesService.trashNotes(noteId, token);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/archive ")
	public ResponseEntity<Response> archiveNote(@RequestParam(name = "noteId") Long noteId,
			@RequestHeader(value = "token") String token) {
		Response response = iNotesService.archiveNotes(noteId, token);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/addLabel ")
	public ResponseEntity<Response> addNotesToLabel(@RequestParam(name = "noteId") Long noteId,
			@RequestParam(name = "labelId") Long labelId, @RequestHeader(value = "token") String token) {
		Response response = iNotesService.addNotesToLabel(noteId, labelId, token);
		System.out.println("ok");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/removeLabel")
	public ResponseEntity<Response> removeLabelToNotes(@RequestParam(name = "noteId") Long noteId,
			@RequestParam(name = "labelId") Long labelId, @RequestHeader(value = "token") String token) {
		Response response = iNotesService.removeNotesFromLabel(noteId, labelId, token);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/trashnotes")
	public List<Notes> trashNotes(@RequestHeader(value = "token") String token) {
		List<Notes> trashNotes = iNotesService.trashNotes(token);
		return trashNotes;
	}

	@GetMapping("/archivenotes")
	public List<Notes> archiveNotes(@RequestHeader(value = "token") String token) {
		List<Notes> archiveNotes = iNotesService.archiveNotes(token);
		return archiveNotes;
	}

	@GetMapping("/pinnotes")
	public List<Notes> pinNotes(@RequestHeader(value = "token") String token) {
		List<Notes> pinNotes = iNotesService.pinNotes(token);
		return pinNotes;
	}
 
	@PostMapping("/addCollaborate")
	public ResponseEntity<Response> addCollaborate(@RequestParam(value = "noteId") long noteId,
			@RequestHeader(name = "token") String token, @RequestParam(value = "emailId") String emailId) {
		long userId = tokenGenerator.decodeToken(token);
		Response response = iNotesService.addCollaborater(userId, noteId, emailId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/removeCollaborate")
	public ResponseEntity<Response>removeCollaborate(@RequestParam(value = "noteId") long noteId,
			@RequestHeader(name = "token") String token)
	{
		long userId=tokenGenerator.decodeToken(token);
		Response response=iNotesService.removeCollaborater(userId, noteId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}









