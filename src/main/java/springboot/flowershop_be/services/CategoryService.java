package springboot.flowershop_be.services;

import java.util.List;

import springboot.flowershop_be.entities.Category;

public interface CategoryService {
    List<Category> getAll();

    int addCategory(String category);

    void deleteById(int categoryId);
}
