package com.brunom24.sfgpetclinic.repositories;

import com.brunom24.sfgpetclinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface OwnerRepository extends CrudRepository<Owner, Long> {

    Owner findByLastName(String lastName);

    Set<Owner> findAllByLastNameContainingIgnoreCase(String lastName);

}
