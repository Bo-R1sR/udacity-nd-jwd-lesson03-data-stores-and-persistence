package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.domain.dto.PetDTO;
import com.udacity.jdnd.course3.critter.domain.entity.Customer;
import com.udacity.jdnd.course3.critter.domain.entity.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;
    private final UserService userService;

    public PetController(PetService petService, UserService userService) {
        this.petService = petService;
        this.userService = userService;
    }

    @GetMapping
    public List<PetDTO> getPets() {
        List<Pet> pets = petService.getPets();
        List<PetDTO> petsDTO = petService.convertPetsToPetsDTO(pets);
        return petsDTO;
        }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = petService.convertPetDTOtoPet(petDTO);
        Customer customer = userService.getCustomer(petDTO.getOwnerId());
        pet.setCustomer(customer);
        if (customer.getPets() == null)
            customer.setPets(new ArrayList<>());
        customer.getPets().add(pet);
        Pet savedPet = petService.savePet(pet);
        PetDTO returnPetDTO = petService.convertPetToPetDTO(savedPet);
        return returnPetDTO;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPet(petId);
        PetDTO returnPetDTO = petService.convertPetToPetDTO(pet);
        return returnPetDTO;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.getPetsByOwner(ownerId);
        List<PetDTO> petsDTO = petService.convertPetsToPetsDTO(pets);
        return petsDTO;
    }
}
