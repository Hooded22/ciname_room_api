package cinema.passwordValidation;

import cinema.passwordValidation.exceptions.WrongPasswordException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PasswordValidationService {

    @Value("${app.stats.password}")
    private String statsPassword;

    public void validatePassword(String password) {
        if (password != null && statsPassword.equals(password)) {
            return;
        }

        throw new WrongPasswordException();
    }
}
