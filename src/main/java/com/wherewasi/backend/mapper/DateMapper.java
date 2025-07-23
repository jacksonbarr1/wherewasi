package com.wherewasi.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.time.DateTimeException;
import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface DateMapper {

    @Named("stringToLocalDate")
    default LocalDate stringToLocalDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }

        try {
            return LocalDate.parse(dateString);
        } catch (DateTimeException e) {
            return null;
        }
    }
}
