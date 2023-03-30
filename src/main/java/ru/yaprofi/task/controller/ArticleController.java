package ru.yaprofi.task.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yaprofi.task.data.ArticleData;
import ru.yaprofi.task.model.Article;
import ru.yaprofi.task.model.Category;
import ru.yaprofi.task.repository.ArticleRepository;
import ru.yaprofi.task.repository.CategoryRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/articles")
@AllArgsConstructor
public class ArticleController {

    ArticleRepository articleRepository;
    CategoryRepository categoryRepository;

    @PostMapping
    public ResponseEntity<ArticleData> createItem(@RequestBody ArticleData data) {
        Category category = categoryRepository.findById(data.getCategory_id()).orElseThrow(EntityNotFoundException::new);
        Article article = articleRepository.save(new Article(0L,data.getName(),data.getDescription(), category));
        return ResponseEntity.status(HttpStatus.CREATED).body(new ArticleData(article.getId(),article.getName(),article.getDescription(),article.getCategory().getId()));
    }

    @GetMapping
    public ResponseEntity<List<ArticleData>> getAll(@RequestParam(required = false) String query) {
        List<Article> articles = new ArrayList<>();
        if (query==null||query.isEmpty())
            articles = articleRepository.findAll();
        else {
            articles.addAll(articleRepository.findAllByNameLikeIgnoreCase(query));
            articles.addAll(articleRepository.findAllByDescriptionLikeIgnoreCase(query));
            List<Category> categories = categoryRepository.findCategoriesByNameLikeIgnoreCase(query);
            articles.addAll(articleRepository.findAllByCategoryIn(categories));
        }

        return ResponseEntity.ok(articles.stream().map(a -> new ArticleData(a.getId(), a.getName(), a.getDescription(), a.getCategory().getId())).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleData> getOne(@PathVariable Long id) {

        Article article = articleRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return ResponseEntity.ok(new ArticleData(article.getId(),article.getName(),article.getDescription(),article.getCategory().getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleData> updateItem(@PathVariable Long id, ArticleData data) {
        Article article = articleRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Category category = categoryRepository.findById(data.getCategory_id()).orElseThrow(EntityNotFoundException::new);
        article.setName(data.getName());
        article.setDescription(data.getDescription());
        article.setCategory(category);
        article = articleRepository.save(article);
        return ResponseEntity.ok(new ArticleData(article.getId(),article.getName(),article.getDescription(),article.getCategory().getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        Article article = articleRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        articleRepository.delete(article);
        return ResponseEntity.ok().build();
    }



}
