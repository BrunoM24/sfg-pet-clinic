package com.brunom24.sfgpetclinic.services;

import com.brunom24.sfgpetclinic.model.Pet;

import java.util.Set;

public interface PetService {

	Pet findById(Long id);

	Pet save(Pet pet);

	Set<Pet> findAll();

}
