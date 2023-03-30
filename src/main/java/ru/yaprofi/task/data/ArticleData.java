package ru.yaprofi.task.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleData {

    Long id;
    String name;
    String description;
    Long category_id;

}
