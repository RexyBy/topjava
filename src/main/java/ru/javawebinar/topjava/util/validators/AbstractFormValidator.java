package ru.javawebinar.topjava.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.util.Util;

public abstract class AbstractFormValidator implements Validator {
    protected String messageCode;

    @Autowired
    protected MessageSource messageSource;

    protected void rejectValue(Errors errors, String field) {
        errors.rejectValue(field, messageCode, Util.localizeMessage(messageCode, messageSource));
    }
}