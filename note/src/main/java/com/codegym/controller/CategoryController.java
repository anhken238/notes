package com.codegym.controller;


import com.codegym.model.Category;
import com.codegym.service.CategoryService;
import com.codegym.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private NoteService noteService;
    //show list
    @GetMapping(value = "/category",produces = "application/json;charset=UTF-8")
    public ModelAndView listCategory(){
        Iterable<Category> categories = categoryService.findAll();
        ModelAndView modelAndView = new ModelAndView("/category/list");
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }
    //show form create and create category
    @GetMapping(value = "/create-category",produces = "application/json;charset=UTF-8")
    public ModelAndView showCreateCategory(){
        ModelAndView modelAndView = new ModelAndView("/category/create");
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }
    @PostMapping("/create-category")
    public ModelAndView saveCategory(@ModelAttribute Category category){
        categoryService.save(category);
        ModelAndView modelAndView= new ModelAndView("/category/create");
        modelAndView.addObject("message", " create was success");
        return modelAndView;
    }
    // show form edit and edit category
    @GetMapping(value = "/edit-category/{id}",produces = "application/json;charset=UTF-8")
    public  ModelAndView ShowFormEditCategory(@PathVariable("id") Long id, Category category){
        category = categoryService.findById(id);
        if(category != null){
            ModelAndView modelAndView = new ModelAndView("/category/edit");
            modelAndView.addObject("category", category);
            return modelAndView;
        }
        else{
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }
    @PostMapping("/edit-category")
    public ModelAndView saveEdit( Category category){
        categoryService.save(category);
        ModelAndView modelAndView = new ModelAndView("/category/edit");
        modelAndView.addObject("category",category);
        modelAndView.addObject("message", "edit was success");
        return modelAndView;
    }
    //delete category
    @GetMapping(value = "/delete-category/{id}",produces = "application/json;charset=UTF-8")
    public String showFormDelete(@PathVariable("id") Long id, Category category){
        categoryService.remove(id);
        return "redirect:/category";
    }
    // show view
    @GetMapping(value = "/view-province/{id}",produces = "application/json;charset=UTF-8")
    public ModelAndView viewCategory(@PathVariable("id") Long id){
        Category category = categoryService.findById(id);
        if(category == null){
            return new ModelAndView("/error.404");
        }

        Iterable<com.codegym.model.Note> notes = noteService.findAllByCategory(category);

        ModelAndView modelAndView = new ModelAndView("/category/view");
        modelAndView.addObject("categories", category);
        modelAndView.addObject("note",notes);
        return modelAndView;
    }
}

