package com.codegym.repository;

import com.codegym.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NoteRepository extends PagingAndSortingRepository<com.codegym.model.Note, Long> {
    Iterable<com.codegym.model.Note> findAllByCategory(Category category);
    Page<com.codegym.model.Note> findAllByNameNoteContaining(String noteName, Pageable pageable);
}
