package ru.yaprofi.task.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import ru.yaprofi.task.data.ArticleData;
import ru.yaprofi.task.data.CategoryData;
import ru.yaprofi.task.model.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yaprofi.task.repository.CategoryRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
@RequestMapping("/categories")
public class CategoryController {


    CategoryRepository categoryRepository;

    @PostMapping
    public ResponseEntity<CategoryData> createItem(@RequestBody CategoryData data) {
        Category parent = null;
        if (data.getParent_id()>0)
             parent = categoryRepository.findById(data.getParent_id()).orElseThrow(EntityNotFoundException::new);
        Category category = categoryRepository.save(new Category(0L,data.getName(),data.getDescription(),parent,new ArrayList<>()));
        return ResponseEntity.status(HttpStatus.CREATED).body(new CategoryData(category.getId(),category.getName(),category.getDescription(),parent.getId()));
    }

    @GetMapping
    public ResponseEntity<List<ArticleData>> getAll(@RequestParam(required = false) String query) {
        List<Category> categories = new ArrayList<>();
        if (query==null||query.isEmpty())
            categories = categoryRepository.findAll();
        else {
            categories.addAll(categoryRepository.findCategoriesByDescriptionLikeIgnoreCase(query));
            categories.addAll(categoryRepository.findCategoriesByNameLikeIgnoreCase(query));
        }
        return ResponseEntity.ok(categories.stream().map(a -> new ArticleData(a.getId(), a.getName(), a.getDescription(), a.getParent().getId())).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryData> getOne(@PathVariable Long id) {

        Category category = categoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return ResponseEntity.ok(new CategoryData(category.getId(),category.getName(),category.getDescription(),category.getParent().getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryData> updateItem(@PathVariable Long id, CategoryData data) {

        Category parent = categoryRepository.findById(data.getParent_id()).orElseThrow(EntityNotFoundException::new);
        Category category = categoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        category.setName(data.getName());
        category.setDescription(data.getDescription());
        category.setParent(parent);
        category = categoryRepository.save(category);
        return ResponseEntity.ok(new CategoryData(category.getId(),category.getName(),category.getDescription(),category.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        categoryRepository.delete(category);
        return ResponseEntity.ok().build();
    }

}
