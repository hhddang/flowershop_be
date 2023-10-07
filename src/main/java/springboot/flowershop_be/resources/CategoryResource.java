package springboot.flowershop_be.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springboot.flowershop_be.entities.Category;
import springboot.flowershop_be.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryResource {
    @Autowired
    CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<List<Category>> getAll() {
        List<Category> categories = categoryService.getAll();
        return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Map<String, Boolean>> addCategory(@RequestBody Category category) {
        categoryService.addCategory(category.getCategory());
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<Map<String, Boolean>>(map, HttpStatus.CREATED);
    }

    @PostMapping("/delete/{categoryId}")
    public ResponseEntity<Map<String, Boolean>> deleteCategory(@PathVariable("categoryId") int categoryId) {
        categoryService.deleteById(categoryId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<Map<String, Boolean>>(map, HttpStatus.OK);
    }
}
