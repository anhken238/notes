package com.codegym.service.impl;

import com.codegym.model.Category;
import com.codegym.model.Note;
import com.codegym.repository.NoteRepository;
import com.codegym.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;



public class NoteServiceImpl implements NoteService {
    @Autowired
    private NoteRepository noteRepository;

    @Override
    public Page<Note> findAll(Pageable pageable) {
        pageable = new PageRequest(pageable.getPageNumber(), 4);
        return noteRepository.findAll(pageable);
    }

    @Override
    public Note findById(Long id) {
        return noteRepository.findOne(id);
    }

    @Override
    public void save(Note note) {
        noteRepository.save(note);
    }

    @Override
    public void remove(Long id) {
        noteRepository.delete(id);
    }

    @Override
    public Iterable<Note> findAllByCategory(Category category) {
        return noteRepository.findAllByCategory(category);
    }

    @Override
    public Page<Note> findAllByPostNameContaining(String postName, Pageable pageable) {
        return noteRepository.findAllByNameNoteContaining(postName, pageable);
    }
}
