package com.brunom24.sfgpetclinic.services.map;

import com.brunom24.sfgpetclinic.model.Owner;
import com.brunom24.sfgpetclinic.model.Pet;
import com.brunom24.sfgpetclinic.services.OwnerService;
import com.brunom24.sfgpetclinic.services.PetService;
import com.brunom24.sfgpetclinic.services.PetTypeService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OwnerMapService extends AbstractMapService<Owner, Long> implements OwnerService {

	private final PetService petService;
	private final PetTypeService petTypeService;

	public OwnerMapService(PetService petService, PetTypeService petTypeService) {
		this.petService = petService;
		this.petTypeService = petTypeService;
	}

	@Override
	public Set<Owner> findAll() {
		return super.findAll();
	}

	@Override
	public void deleteById(Long id) {
		super.deleteById(id);
	}

	@Override
	public void delete(Owner object) {
		super.delete(object);
	}

	@Override
	public Owner save(Owner owner) {
		if (owner != null){
			if (owner.getPets() != null){
				owner.getPets().forEach(pet -> {
					if (pet.getPetType() != null){
						if (pet.getPetType().getId() == null){
							pet.setPetType(petTypeService.save(pet.getPetType()));
						}
					}else {
						throw new RuntimeException("Pet Type is required");
					}

					if (pet.getId() == null){
						Pet savedPet = petService.save(pet);
						pet.setId(savedPet.getId());
					}
				});
			}

			return super.save(owner);
		}else {
			return null;
		}
	}

    @Override
    public Owner findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Owner findByLastName(String lastName) {
        return null;
    }
}