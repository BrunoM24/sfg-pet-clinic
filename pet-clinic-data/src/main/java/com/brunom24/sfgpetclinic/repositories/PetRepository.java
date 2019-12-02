package com.brunom24.sfgpetclinic.repositories;

import com.brunom24.sfgpetclinic.model.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet, Long> {
}
