package com.brunom24.sfgpetclinic.services;

import com.brunom24.sfgpetclinic.model.Owner;

import java.util.Set;

public interface OwnerService extends CrudService<Owner, Long> {

	Owner findByLastName(String lastName);

    Set<Owner> findAllByLastName(String anyString);

}
