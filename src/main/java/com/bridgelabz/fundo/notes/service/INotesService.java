package com.bridgelabz.fundo.notes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundo.notes.dto.NotesDto;
import com.bridgelabz.fundo.notes.model.Notes;
import com.bridgelabz.fundo.response.Response;

@Service
public interface INotesService 
{
    Response createNotes(NotesDto notesDto,String token);
    List<Notes> readNotes(String token);
    Response deleteNotes(Long noteId,String token);
    Response updateNotes(NotesDto notesDto,String token,long noteId);
    Response pinNotes(Long noteId,String token);
    Response trashNotes(Long noteId,String token);
    Response archiveNotes(Long noteId,String token);
    Response addNotesToLabel(long noteId,long labelId,String token);
    Response removeNotesFromLabel(long noteId,long labelId,String token);
    List<Notes> trashNotes(String token);
    List<Notes> archiveNotes(String token);
    List<Notes> pinNotes(String token);
    Response addCollaborater(long userId,long noteId,String emailId);
    Response removeCollaborater(long userId,long noteId);
    Response addReminder(long userId,long noteId,String reminder);
    Response deleteReminder(long userId,long noteId);
}
