package com.bridgelabz.fundo.notes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundo.notes.model.Notes;

@Repository
public interface NotesRepository extends JpaRepository<Notes , Long>
{
    public  Optional<Notes> findBytitle(String title);
    public  Optional<Notes> deleteBytitle(String title);
    public Notes findByNoteIdAndUserId(long noteId,long userId);
    public List<Notes> findByUserId(long userId);
	
}
