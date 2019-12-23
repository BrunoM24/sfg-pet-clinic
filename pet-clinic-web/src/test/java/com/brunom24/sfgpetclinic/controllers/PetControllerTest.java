package com.brunom24.sfgpetclinic.controllers;

import com.brunom24.sfgpetclinic.model.Owner;
import com.brunom24.sfgpetclinic.model.Pet;
import com.brunom24.sfgpetclinic.model.PetType;
import com.brunom24.sfgpetclinic.services.OwnerService;
import com.brunom24.sfgpetclinic.services.PetService;
import com.brunom24.sfgpetclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    @Mock
    OwnerService ownerService;

    @Mock
    PetService petService;

    @Mock
    PetTypeService petTypeService;

    @InjectMocks
    PetController controller;

    Owner owner;

    Set<PetType> petTypes;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        owner = Owner.builder().id(1L).build();

        petTypes = new HashSet<>();
        petTypes.add(PetType.builder().id(1L).name("Dog").build());
        petTypes.add(PetType.builder().id(2L).name("Cat").build());

        owner.addPet(Pet.builder().id(1L).build());

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void initCreationForm() throws Exception {
        //when
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        //then
        mockMvc.perform(get("/owners/1/pets/new"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("owner"))
        .andExpect(model().attributeExists("petTypes"))
        .andExpect(model().attributeExists("pet"))
        .andExpect(view().name("pets/createOrUpdatePetForm"));

        verify(ownerService).findById(anyLong());
        verify(petTypeService).findAll();
    }

    @Test
    void processCreationForm() throws Exception {
        //when
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        //then
        mockMvc.perform(post("/owners/1/pets/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

        verify(ownerService).findById(anyLong());
        verify(petTypeService).findAll();
        verify(petService).save(any());
    }

    @Test
    @Disabled //NOT IMPLEMENTED
    void processCreationFormHasErrors() throws Exception {
        //when
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        //then
        mockMvc.perform(post("/owners/1/pets/new"))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(view().name("pets/createOrUpdatePetForm"));

        verify(ownerService).findById(anyLong());
        verify(petTypeService).findAll();
        verifyZeroInteractions(petService);
    }

    @Test
    void initUpdateForm() throws Exception {
        //when
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);
        when(petService.findById(anyLong())).thenReturn(Pet.builder().id(1L).build());

        mockMvc.perform(get("/owners/1/pets/1/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("petTypes"))
                .andExpect(model().attributeExists("pet"))
                .andExpect(view().name("pets/createOrUpdatePetForm"));

        verify(ownerService).findById(anyLong());
        verify(petTypeService).findAll();
    }

    @Test
    void processUpdateForm() throws Exception {
        //when
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(post("/owners/1/pets/1/update"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

        verify(ownerService).findById(anyLong());
        verify(petTypeService).findAll();
        verify(petService).save(any());
    }

}