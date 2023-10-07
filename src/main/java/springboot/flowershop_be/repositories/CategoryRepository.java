package springboot.flowershop_be.repositories;

import java.util.List;

import springboot.flowershop_be.entities.Category;

public interface CategoryRepository {
    List<Category> getAll();

    Category getById(int categoryId);

    int create(String category);

    void update(int categoryId, Category category);

    void delete(int categoryId);
}
