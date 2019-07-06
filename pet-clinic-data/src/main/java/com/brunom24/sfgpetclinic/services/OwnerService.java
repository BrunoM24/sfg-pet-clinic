package com.brunom24.sfgpetclinic.services;

import com.brunom24.sfgpetclinic.model.Owner;

public interface OwnerService extends CrudService<Owner, Long> {

	Owner findByLastName(String lastName);
}
