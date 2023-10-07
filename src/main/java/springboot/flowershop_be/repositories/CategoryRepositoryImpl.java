package springboot.flowershop_be.repositories;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import springboot.flowershop_be.entities.Category;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
    private static final String SQL_GET_ALL = "select * from categories";
    private static final String SQL_GET_BY_ID = "select * from categories where category_id=?";
    private static final String SQL_CREATE = "insert into categories (category) values (?)";
    private static final String SQL_UPDATE = "update categories set category=? where category_id=?";
    private static final String SQL_DELETE = "delete from categories where category_id=?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Category> getAll() {
        try {
            return jdbcTemplate.query(SQL_GET_ALL, categoryRowMapper);
        } catch (Exception e) {
            throw new UnsupportedOperationException("CategoryRepo.getAll: Error: " + e);
        }
    }

    @Override
    public Category getById(int categoryId) {
        try {
            return jdbcTemplate.queryForObject(SQL_GET_BY_ID, categoryRowMapper);
        } catch (Exception e) {
            throw new UnsupportedOperationException("CategoryRepo.getById: Error: " + e);
        }
    }

    @Override
    public int create(String category) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, 1);
                ps.setString(1, category);
                return ps;
            }, keyHolder);
            return (int) keyHolder.getKeys().get("category_id");
        } catch (Exception e) {
            throw new UnsupportedOperationException("CategoryRepo.create: Error: " + e);
        }
    }

    @Override
    public void update(int categoryId, Category category) {
        try {
            jdbcTemplate.update(SQL_UPDATE, new Object[] { category.getCategory(), categoryId });
        } catch (Exception e) {
            throw new UnsupportedOperationException("CategoryRepo.update: Error: " + e);
        }
    }

    @Override
    public void delete(int categoryId) {
        try {
            jdbcTemplate.update(SQL_DELETE, new Object[] { categoryId });
        } catch (Exception e) {
            throw new UnsupportedOperationException("CategoryRepo.delete: Error: " + e);
        }
    }

    private RowMapper<Category> categoryRowMapper = ((rs, rowNum) -> {
        return new Category(rs.getInt("category_id"), rs.getString("category"));
    });
}
