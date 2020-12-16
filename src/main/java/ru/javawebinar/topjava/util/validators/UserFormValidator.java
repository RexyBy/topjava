package ru.javawebinar.topjava.util.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.javawebinar.topjava.HasEmail;
import ru.javawebinar.topjava.HasId;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.to.UserTo;

@Component
public class UserFormValidator extends AbstractFormValidator {

    private UserRepository userRepository;

    public UserFormValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
        messageCode = "error.emailIsUsed";
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz) || UserTo.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        HasEmail targetWithEmail = (HasEmail) target;
        HasId targetWithId = (HasId) target;
        User userWithSuchEmail = userRepository.getByEmail(targetWithEmail.getEmail().toLowerCase());
        if (userWithSuchEmail != null && !userWithSuchEmail.getId().equals(targetWithId.getId())) {
            rejectValue(errors, "email");
        }
    }
}