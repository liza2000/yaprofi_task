package ru.yaprofi.task.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;
import ru.yaprofi.task.model.Category;

import java.util.List;

@Repository
public interface CategoryRepository  extends JpaRepository<Category, Long> {
    List<Category> findCategoriesByNameLikeIgnoreCase(String name);
    List<Category> findCategoriesByDescriptionLikeIgnoreCase(String name);
}
