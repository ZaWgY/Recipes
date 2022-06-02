package recipes.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.dto.RegistrationRequestDTO;
import recipes.model.User;
import recipes.repository.UserRepository;
import recipes.service.UserService;

@Service
public class UserServiceImpl  implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(RegistrationRequestDTO requestDTO) {
        System.out.println(requestDTO.getEmail());
        if(userRepository.findUserByEmail(
                requestDTO
                        .getEmail())
                        .isPresent()
        )
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        User newUser = new User();
        newUser.setEmail(requestDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        userRepository.save(newUser);
    }
}
