package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.exceptions.PetNotFoundException;
import com.udacity.jdnd.course3.critter.domain.entity.Pet;
import com.udacity.jdnd.course3.critter.domain.dto.PetDTO;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.domain.entity.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {
    private final PetRepository petRepository;
    private final UserService userService;

    public PetService(PetRepository petRepository, UserService userService) {
        this.petRepository = petRepository;
        this.userService = userService;
    }

    public Pet savePet(Pet pet){
        return petRepository.save(pet);
    }

    public Pet getPet(long petId) {
        Optional<Pet> optionalPet = petRepository.findById(petId);
        return optionalPet.orElseThrow(PetNotFoundException::new);
    }

    public List<Pet> getPets() {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwner(long ownerId) {
        Customer customer = userService.getCustomer(ownerId);
        List<Pet> pets = customer.getPets();
        return pets;
    }

    public Pet convertPetDTOtoPet(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        Long l = petDTO.getOwnerId();
        Customer c = userService.getCustomer(l);
        pet.setCustomer(c);
        return pet;
    }

    public PetDTO convertPetToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        Long l = pet.getCustomer().getId();
        petDTO.setOwnerId(l);
        return petDTO;
    }

    public List<PetDTO> convertPetsToPetsDTO(List<Pet> pets) {
        List<PetDTO> petsDTO = new ArrayList<>();
        for(Pet pet: pets) {
            petsDTO.add(convertPetToPetDTO(pet));
        }
        return petsDTO;
    }
}
