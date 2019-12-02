package com.brunom24.sfgpetclinic.repositories;

import com.brunom24.sfgpetclinic.model.PetType;
import org.springframework.data.repository.CrudRepository;

public interface PetTypeRepository extends CrudRepository<PetType, Long> {
}
