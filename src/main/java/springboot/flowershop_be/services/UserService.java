package springboot.flowershop_be.services;

import springboot.flowershop_be.entities.User;

public interface UserService {
    User validateUser(String email, String password);

    User registerUser(String userName, String email, String password);
}
