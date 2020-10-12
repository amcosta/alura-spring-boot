package br.com.alura.forum.dto;

public class FormValidationErrorDto {
    private String name;
    private String message;

    public FormValidationErrorDto(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
