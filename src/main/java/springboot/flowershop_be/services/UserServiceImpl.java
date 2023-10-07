package springboot.flowershop_be.services;

import java.util.regex.Pattern;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springboot.flowershop_be.entities.User;
import springboot.flowershop_be.repositories.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User validateUser(String email, String password) {
        email = email.toLowerCase();
        return userRepository.getByEmailAndPassword(email, password);
    }

    @Override
    public User registerUser(String userName, String email, String password) {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        email = email.toLowerCase();
        if (!pattern.matcher(email).matches()) {
            throw new UnsupportedOperationException("UserService.register: Error: Email invalid");
        }
        if (userRepository.getCountByEmail(email) > 0) {
            throw new UnsupportedOperationException("UserService.register: Error: Email in used");
        }
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        int userId = userRepository.create(userName, email, hashedPassword);
        return userRepository.getById(userId);
    }

}
