package ru.javawebinar.topjava.util.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;

import java.util.List;

@Component
public class UserFormValidator implements Validator {

    private UserService userService;

    public UserFormValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserTo.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserTo userTo = (UserTo) target;
        List<User> allUsers = userService.getAll();
        for (User user : allUsers) {
            if ((userTo.getId() == null || userTo.getId().equals(user.getId()) == false)
                    && userTo.getEmail().toLowerCase().equals(user.getEmail().toLowerCase())) {
                errors.rejectValue("email", "error.emailIsUsed");
                break;
            }
        }
    }
}