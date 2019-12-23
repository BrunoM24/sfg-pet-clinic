package com.brunom24.sfgpetclinic.formatters;

import com.brunom24.sfgpetclinic.model.PetType;
import com.brunom24.sfgpetclinic.services.PetTypeService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

@Component
public class PetTypeFormatter implements Formatter<PetType> {

    private final PetTypeService petTypeService;

    public PetTypeFormatter(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    @Override
    public PetType parse(String text, Locale locale) throws ParseException {
        Optional<PetType> optionalPetType = petTypeService.findAll()
                .stream()
                .filter(petType -> petType.getName().equals(text)).findFirst();

        if (!optionalPetType.isPresent()){
            throw new ParseException("Type not found: " + text, 0);
        }

        return optionalPetType.get();
    }

    @Override
    public String print(PetType petType, Locale locale) {
        return petType.getName();
    }
}
