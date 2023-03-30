package ru.yaprofi.task.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryData {

    Long id;
    String name;
    String description;
    Long parent_id;
}
