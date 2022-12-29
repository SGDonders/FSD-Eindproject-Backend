package com.fsdeindopdracht.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.xml.bind.ValidationException;

public class Utils {
    public static void reportErrors(BindingResult br) throws ValidationException {
        if (br.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            throw new ValidationException(sb.toString());
        }
    }
}
