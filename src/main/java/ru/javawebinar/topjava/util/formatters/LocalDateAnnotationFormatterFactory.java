package ru.javawebinar.topjava.util.formatters;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class LocalDateAnnotationFormatterFactory implements AnnotationFormatterFactory<LocalDateTimeFormat> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<>(Arrays.asList(LocalDate.class, LocalTime.class));
    }

    @Override
    public Printer<?> getPrinter(LocalDateTimeFormat annotation, Class<?> fieldType) {
        return getFormatter(fieldType);
    }

    @Override
    public Parser<?> getParser(LocalDateTimeFormat annotation, Class<?> fieldType) {
        return getFormatter(fieldType);
    }

    private Formatter<?> getFormatter(Class<?> fieldType) {
        return fieldType.equals(LocalDate.class) ? new LocalDateFormatter() : new LocalTimeFormatter();
    }
}
