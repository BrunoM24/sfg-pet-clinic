package com.brunom24.sfgpetclinic.controllers;

import com.brunom24.sfgpetclinic.model.Owner;
import com.brunom24.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Set;

@RequestMapping("/owners")
@Controller
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping({"", "/", "/index", "/index.html"})
    public String listOwners(Owner owner, Model model, BindingResult result) {

        if (owner.getLastName() == null) {
            owner.setLastName("");
        }

        Set<Owner> ownerSet = ownerService.findAllByLastName(owner.getLastName());

        if (ownerSet.isEmpty()) {
            result.rejectValue("lastName", "notFound", "not found");
            return "owners/findOwners";
        }

        if (ownerSet.size() == 1) {
            owner = ownerSet.iterator().next();

            return "redirect:/owners/" + owner.getId();
        }

        model.addAttribute("owners", ownerSet);

        return "owners/ownersList";
    }

    @RequestMapping("/{ownerId}")
    public ModelAndView displayOwner(@PathVariable Long ownerId) {
        ModelAndView modelAndView = new ModelAndView("owners/ownerDetails");

        modelAndView.addObject("owner", ownerService.findById(ownerId));

        return modelAndView;
    }

    @RequestMapping("/find")
    public String findOwners(Model model) {
        model.addAttribute("owner", new Owner());

        return "owners/findOwners";
    }

    @GetMapping("/new")
    public String initCreationForm(Model model) {
        Owner owner = new Owner();

        model.addAttribute("owner", owner);

        return "owners/createOrUpdateOwnerForm";
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid Owner owner, BindingResult result) {
        if (result.hasErrors()) {
            return "owners/createOrUpdateOwnersForm";
        }

        Owner savedOwner = ownerService.save(owner);

        return "redirect:/owners/" + savedOwner.getId();
    }

    @GetMapping("/{ownerId}/update")
    public String initUpdateForm(@PathVariable Long ownerId, Model model) {
        Owner owner = ownerService.findById(ownerId);

        model.addAttribute("owner", owner);

        return "owners/createOrUpdateOwnerForm";
    }

    @PostMapping("/{ownerId}/update")
    public String processUpdateForm(@Valid Owner owner, BindingResult result) {
        if (result.hasErrors()) {
            return "owners/createOrUpdateOwnerForm";
        }

        Owner savedOwner = ownerService.save(owner);

        return "redirect:/owners/" + savedOwner.getId();
    }

}
