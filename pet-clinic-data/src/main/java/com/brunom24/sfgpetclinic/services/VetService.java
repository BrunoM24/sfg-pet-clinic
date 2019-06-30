package com.brunom24.sfgpetclinic.services;

import com.brunom24.sfgpetclinic.model.Vet;

import java.util.Set;

public interface VetService {

	Vet findById(Long id);

	Vet save(Vet vet);

	Set<Vet> findAll();

}
