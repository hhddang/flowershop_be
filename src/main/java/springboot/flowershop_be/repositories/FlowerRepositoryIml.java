package springboot.flowershop_be.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import springboot.flowershop_be.entities.Flower;

@Repository
public class FlowerRepositoryIml implements FlowerRepository {

    private static final String SQL_GET_ALL = "select flower_id, name, image_url, price, quantity, category from flowers f join categories c on f.category_id = c.category_id";
    private static final String SQL_GET_BY_ID = "select flower_id, name, image_url, price, quantity, category from flowers f join categories c on f.category_id = c.category_id and f.flower_id=?";
    private static final String SQL_CREATE = "insert into flowers (name, image_url, price, quantity, category_id) values (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "update flowers set name=?, image_url=?, price=?, quantity=?, category_id=? where flower_id=?";
    private static final String SQL_DELETE = "delete from flowers where flower_id=?";

    private static final String SQL_GET_CATEGORY_ID_BY_NAME = "select category_id from categories where category=?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Flower> getAll() {
        try {
            return jdbcTemplate.query(SQL_GET_ALL, flowerRowMapper);
        } catch (Exception e) {
            throw new UnsupportedOperationException("FlowerRepo.getAll: Error: " + e);
        }
    }

    @Override
    public Flower getById(int flowerId) {
        try {
            return jdbcTemplate.queryForObject(SQL_GET_BY_ID, new Object[] { flowerId }, flowerRowMapper);
        } catch (Exception e) {
            throw new UnsupportedOperationException("FlowerRepo.getById: Error: " + e);
        }
    }

    @Override
    public int create(String name, String imageUrl, int price, int quantity, String category) {
        try {
            int categoryId = getCategoryIdByName(category);
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, name);
                ps.setString(2, imageUrl);
                ps.setInt(3, price);
                ps.setInt(4, quantity);
                ps.setInt(5, categoryId);
                return ps;
            }, keyHolder);
            return (int) keyHolder.getKeys().get("FLOWER_ID");

        } catch (Exception e) {
            throw new UnsupportedOperationException("FlowerRepo.create: Error: " + e);
        }
    }

    @Override
    public void update(int flowerId, Flower flower) {
        try {
            int categoryId = getCategoryIdByName(flower.getCategory());

            jdbcTemplate.update(SQL_UPDATE,
                    new Object[] { flower.getName(), flower.getImageUrl(), flower.getPrice(), flower.getQuantity(),
                            categoryId, flowerId });

        } catch (Exception e) {
            throw new UnsupportedOperationException("FlowerRepo.getById: update: " + e);
        }
    }

    @Override
    public void delete(int flowerId) {
        try {
            jdbcTemplate.update(SQL_DELETE, new Object[] { flowerId });

        } catch (Exception e) {
            throw new UnsupportedOperationException("FlowerRepo.delete: Error: " + e);
        }
    }

    // Utils
    private RowMapper<Flower> flowerRowMapper = ((rs, rowNum) -> {
        return new Flower(rs.getInt("flower_id"), rs.getString("name"), rs.getString("image_url"), rs.getInt("price"),
                rs.getInt("quantity"), rs.getString("category"));
    });

    private int getCategoryIdByName(String category) {
        try {
            return jdbcTemplate.queryForObject(SQL_GET_CATEGORY_ID_BY_NAME, new Object[] { category }, int.class);
        } catch (Exception e) {
            throw new UnsupportedOperationException("FlowerRepo.getCategoryIdByName: Error: " + e);
        }
    }
}
