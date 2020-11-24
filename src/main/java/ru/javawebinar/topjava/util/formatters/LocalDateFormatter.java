package ru.javawebinar.topjava.util.formatters;

import org.springframework.format.Formatter;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class LocalDateFormatter implements Formatter<LocalDate> {
    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        return text.length() == 0 ? null : DateTimeUtil.parseLocalDate(text);
    }

    @Override
    public String print(LocalDate date, Locale locale) {
        return date == null ? "" : date.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }
}
