package com.brunom24.sfgpetclinic.controllers;

import com.brunom24.sfgpetclinic.model.Pet;
import com.brunom24.sfgpetclinic.model.Visit;
import com.brunom24.sfgpetclinic.services.PetService;
import com.brunom24.sfgpetclinic.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class VisitController {

    private final VisitService visitService;

    private final PetService petService;

    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable Long petId, Model model) {
        Pet pet = petService.findById(petId);

        model.addAttribute("pet", pet);

        Visit visit = new Visit();

        pet.addVisit(visit);

        return visit;
    }

    @GetMapping("/owners/*/pets/{petId}/visits/new")
    public String initCreationForm() {

        return "pets/createOrUpdateVisitForm";
    }

    @PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String processCreationForm(@PathVariable Long ownerId, @Valid Visit visit, BindingResult result) {
        if (result.hasErrors()) {
            return "pets/createOrUpdateVisitForm";
        }

        visitService.save(visit);

        return "redirect:/owners/" + ownerId;
    }

}
