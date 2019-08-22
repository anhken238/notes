package com.codegym.model;

import javax.persistence.*;
import java.util.Set;
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nameCategory;

    public Category() {
    }

    @OneToMany(mappedBy = "category",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<com.codegym.model.Note> notes;


    public Category(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public Set<com.codegym.model.Note> getNotes() {return notes;}

    public void setNotes(Set<com.codegym.model.Note> notes){this.notes = notes;}
}


