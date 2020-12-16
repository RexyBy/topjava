package ru.javawebinar.topjava.util.exception;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.List;

public class ErrorInfo {
    private final String url;
    private final ErrorType type;
    private final List<String> details = new ArrayList<>();

    @ConstructorProperties({"url", "type", "details"})
    public ErrorInfo(CharSequence url, ErrorType type, List<String> details) {
        this.url = url.toString();
        this.type = type;
        this.details.addAll(details);
    }

    public ErrorInfo(CharSequence url, ErrorType type, String detail) {
        this.url = url.toString();
        this.type = type;
        this.details.add(detail);
    }

    public String getUrl() {
        return url;
    }

    public ErrorType getType() {
        return type;
    }

    public List<String> getDetails() {
        return details;
    }
}