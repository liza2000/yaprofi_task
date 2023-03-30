package ru.yaprofi.task.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yaprofi.task.model.Article;
import ru.yaprofi.task.model.Category;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByNameLikeIgnoreCase(String name);
    List<Article> findAllByDescriptionLikeIgnoreCase(String description);

    List<Article> findAllByCategoryIn(List<Category> categories);

}
