package springboot.flowershop_be.repositories;

import java.sql.PreparedStatement;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import springboot.flowershop_be.entities.User;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final String SQL_CREATE = "insert into users (user_name, email, password) values (?, ?, ?)";
    private static final String SQL_GET_BY_EMAIL = "select * from users where email=?";
    private static final String SQL_GET_COUNT_BY_EMAIL = "select count(*) from users where email=?";
    private static final String SQL_GET_BY_ID = "select * from users where user_id=?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public int create(String userName, String email, String password) {
        password = "12345";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, 1);
                ps.setString(1, userName);
                ps.setString(2, email);
                ps.setString(3, hashedPassword);
                return ps;
            }, keyHolder);
            return (int) keyHolder.getKeys().get("user_id");
        } catch (Exception e) {
            throw new UnsupportedOperationException("UserRepo.create: Error: " + e);
        }
    }

    @Override
    public User getByEmailAndPassword(String email, String password) {
        try {
            User user = jdbcTemplate.queryForObject(SQL_GET_BY_EMAIL, new Object[] { email },
                    userRowMapper);

            if (!BCrypt.checkpw(password, user.getPassword())) {
                throw new UnsupportedOperationException(
                        "UserRepo.getByEmailAndPassword: Error: Wrong password" + password + user.getPassword());
            }
            return user;

        } catch (Exception e) {
            throw new UnsupportedOperationException("UserRepo.getByEmailAndPassword: Error: " + e);
        }
    }

    @Override
    public int getCountByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject(SQL_GET_COUNT_BY_EMAIL, new Object[] { email },
                    int.class);
        } catch (Exception e) {
            throw new UnsupportedOperationException("UserRepo.getCountByEmail: Error: " + e);
        }
    }

    @Override
    public User getById(int userId) {
        try {
            return jdbcTemplate.queryForObject(SQL_GET_BY_ID, new Object[] { userId },
                    userRowMapper);
        } catch (Exception e) {
            throw new UnsupportedOperationException("UserRepo.getById: Error: " + e);
        }
    }

    private RowMapper<User> userRowMapper = ((rs, rowNum) -> {
        return new User(rs.getInt("user_id"), rs.getString("user_name"), rs.getString("email"),
                rs.getString("password"));
    });

}
