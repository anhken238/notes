package com.codegym.controller;


import com.codegym.model.Category;
import com.codegym.model.Note;
import com.codegym.service.CategoryService;
import com.codegym.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.Optional;

@Controller
public class NoteController {
    @Autowired
    private NoteService noteService;
    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }

    @GetMapping(value = "/home",produces = "application/json;charset=UTF-8")
    public ModelAndView goHome(){
        ModelAndView modelAndView = new ModelAndView("/home/homes");
        return modelAndView;
    }

    // show list post
    @GetMapping(value = "/list",produces = "application/json;charset=UTF-8")
    public ModelAndView listNotes(@RequestParam("s") Optional<String> s, Pageable pageable) {
        Page<Note> notes;
        if (s.isPresent()) {
            notes = noteService.findAllByPostNameContaining(s.get(), pageable);
        } else {
            notes = noteService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/note/list-note");
        modelAndView.addObject("notes", notes);
        return modelAndView;

    }

    // create
    @GetMapping(value = "/create-note",produces = "application/json;charset=UTF-8")
    public ModelAndView showFormCreate() {
        ModelAndView modelAndView = new ModelAndView("/note/create-note");
        modelAndView.addObject("note", new Note());
        return modelAndView;
    }

    @PostMapping("/create-note")
    public ModelAndView saveCreateNote(@Validated @ModelAttribute("note") Note note, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("/note/create-note");
            return modelAndView;
        }
        else{
            noteService.save(note);
            ModelAndView modelAndView = new ModelAndView("/note/create-note");
            modelAndView.addObject("note", new Note());
            modelAndView.addObject("message", "create was success");
            return modelAndView;
        }
    }
    @GetMapping(value = "/edit-note/{id}",produces = "application/json;charset=UTF-8")
    public ModelAndView showEditForm(@PathVariable Long id){
        Note note = noteService.findById(id);
        if(note != null) {
            ModelAndView modelAndView = new ModelAndView("/note/edit");
            modelAndView.addObject("note", note);
            return modelAndView;

        }else {
            return new ModelAndView("/error.404");
        }
    }

    @PostMapping("/edit-note")
    public String updateNote(@Validated @ModelAttribute("note") Note note,BindingResult bindingResult){
        if(bindingResult.hasErrors())
        {
            return "redirect:/edit-note";
        }
        else {
            noteService.save(note);
            ModelAndView modelAndView = new ModelAndView("/note/create-note");
            modelAndView.addObject("message", "Edit was success");
            return "redirect:/list";
        }

    }

    @GetMapping("/delete-note/{id}")
    public String deleteNote(@PathVariable Long id)throws Exception{
        try {
            noteService.remove(id);
            return "redirect:/list";
        }catch (Exception e){
            return "error.404";
        }
    }

    @GetMapping(value = "/view-note/{id}",produces = "application/json;charset=UTF-8")
    public String viewNote(@PathVariable Long id, Model model){

        Note notes = noteService.findById(id);
        model.addAttribute("note", notes);
        return "/note/view-note";
    }
}
