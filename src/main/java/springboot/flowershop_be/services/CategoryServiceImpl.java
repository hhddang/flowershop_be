package springboot.flowershop_be.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springboot.flowershop_be.entities.Category;
import springboot.flowershop_be.repositories.CategoryRepository;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        return categoryRepository.getAll();
    }

    @Override
    public int addCategory(String category) {
        return categoryRepository.create(category);
    }

    @Override
    public void deleteById(int categoryId) {
        categoryRepository.delete(categoryId);
    }

}
