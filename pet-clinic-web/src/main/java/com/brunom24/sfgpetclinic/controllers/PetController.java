package com.brunom24.sfgpetclinic.controllers;

import com.brunom24.sfgpetclinic.model.Owner;
import com.brunom24.sfgpetclinic.model.Pet;
import com.brunom24.sfgpetclinic.model.PetType;
import com.brunom24.sfgpetclinic.services.OwnerService;
import com.brunom24.sfgpetclinic.services.PetService;
import com.brunom24.sfgpetclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/owners/{ownerId}/pets")
public class PetController {

    private final OwnerService ownerService;

    private final PetService petService;

    private final PetTypeService petTypeService;

    public PetController(OwnerService ownerService, PetService petService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.petService = petService;
        this.petTypeService = petTypeService;
    }

    @ModelAttribute("petTypes")
    public Collection<PetType> getPetTypes() {
        return petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable Long ownerId) {
        return ownerService.findById(ownerId);
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/new")
    public String initCreationForm(Owner owner, Model model) {
        Pet pet = new Pet();
        owner.addPet(pet);
        pet.setOwner(owner);

        model.addAttribute("pet", pet);

        return "pets/createOrUpdatePetForm";
    }

    @PostMapping("/new")
    public String processCreationForm(Owner owner, Pet pet, Model model, BindingResult result) {
        // NOT IMPLEMENTED - Lesson 226
        /*if (StringUtils.hasLength(pet.getName()) && pet.isNew()) {
            result.rejectValue("name", "duplicate", "already exists");
        }*/

        owner.addPet(pet);

        if (result.hasErrors()) {
            model.addAttribute("pet", pet);
            return "pets/createOrUpdatePetForm";
        }

        petService.save(pet);

        return "redirect:/owners/" + owner.getId();
    }

    @GetMapping("/{petId}/update")
    public String initUpdateForm(@PathVariable Long petId, Model model) {
        Pet pet = petService.findById(petId);

        model.addAttribute("pet", pet);

        return "pets/createOrUpdatePetForm";
    }

    @PostMapping("/{petId}/update")
    public String processUpdateForm(Owner owner, @Valid Pet pet, Model model, BindingResult result) {
        if (result.hasErrors()) {
            pet.setOwner(owner);
            model.addAttribute("pet", pet);
            return "pets/createOrUpdatePetForm";
        }

        owner.addPet(pet);

        petService.save(pet);

        return "redirect:/owners/" + owner.getId();
    }

}
