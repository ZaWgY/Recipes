package recipes.service;

import recipes.dto.RegistrationRequestDTO;

public interface UserService {
    void registerUser(RegistrationRequestDTO requestDTO);
}
