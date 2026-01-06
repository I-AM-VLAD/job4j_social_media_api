package ru.job4j.smapi.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class ValidationErrorResponse {
    private final List<Violation> violations;
}
