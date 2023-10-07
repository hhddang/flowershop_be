package springboot.flowershop_be.repositories;

import springboot.flowershop_be.entities.User;

public interface UserRepository {
    int create(String userName, String email, String password);

    User getByEmailAndPassword(String email, String password);

    int getCountByEmail(String email);

    User getById(int userId);
}
