package org.springframework.samples.petclinic.soap.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.soap.*;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@Endpoint
public class PetEndpoint {
    private static final String NAMESPACE_URI = "http://petclinic.samples.springframework.org/soap/pet";

    private ClinicService clinicService;

    @Autowired
    public PetEndpoint(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPetsRequest")
    @ResponsePayload
    public GetPetsResponse getPets(@RequestPayload GetPetsRequest PetsRequest) {
        GetPetsResponse response = new GetPetsResponse();
        Collection<org.springframework.samples.petclinic.model.Pet> Pets = this.clinicService.findAllPets();
        if (!Pets.isEmpty()) {
            response = mapPets(Pets);

        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addPetRequest")
    @ResponsePayload
    public AddPetResponse addPet(@RequestPayload AddPetRequest petRequest) {
        org.springframework.samples.petclinic.model.Pet Pet = new org.springframework.samples.petclinic.model.Pet();
        Pet own = petRequest.getPet();
        if (own != null) {
            Pet.setName(own.getName());
            PetType petType = new PetType();
            petType.setName(own.getType());
            Pet.setType(petType);
            try {
                SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
                Date date = parser.parse(own.getBirthDate());
                Pet.setBirthDate(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            org.springframework.samples.petclinic.model.Owner o = this.clinicService.findOwnerById(petRequest.getPet().getId());
            Pet.setOwner(o);
            this.clinicService.savePet(Pet);
        }

        AddPetResponse response = new AddPetResponse();
        return response;
    }

//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updatePetRequest")
//    @ResponsePayload
//    public UpdatePetResponse updatePet(@RequestPayload UpdatePetRequest PetRequest) {
//        Pet Pet = PetRequest.getPet();
//        org.springframework.samples.petclinic.model.Pet currentPet = this.clinicService.findPetById(Pet.id);
//        currentPet.setAddress(Pet.getAddress());
//        currentPet.setCity(Pet.getCity());
//        currentPet.setFirstName(Pet.getFirstName());
//        currentPet.setLastName(Pet.getLastName());
//        currentPet.setTelephone(Pet.getTelephone());
//        this.clinicService.savePet(currentPet);
//        UpdatePetResponse response = new UpdatePetResponse();
//        return response;
//    }
//
//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findPetsRequest")
//    @ResponsePayload
//    public FindPetsResponse findPets(@RequestPayload FindPetsRequest PetsRequest) {
//        FindPetsResponse response = new FindPetsResponse();
//        String lastName = PetsRequest.getLastName();
//        Collection<org.springframework.samples.petclinic.model.Pet> Pets = this.clinicService.findPetByLastName(lastName);
//        if (!Pets.isEmpty()) {
//            mapFindPets(response, Pets);
//        }
//        return response;
//    }
//
//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPetRequest")
//    @ResponsePayload
//    public GetPetResponse getPet(@RequestPayload GetPetRequest PetRequest) {
//        GetPetResponse response = new GetPetResponse();
//        int id = PetRequest.getId();
//        org.springframework.samples.petclinic.model.Pet own = this.clinicService.findPetById(id);
//        if (own != null) {
//            Pet Pet = new Pet();
//            Pet.setFirstName(own.getFirstName());
//            Pet.setLastName(own.getLastName());
//            Pet.setTelephone(own.getTelephone());
//            Pet.setCity(own.getCity());
//            Pet.setAddress(own.getAddress());
//            response.getPet().add(Pet);
//        }
//        return response;
//    }
//
    private GetPetsResponse mapPets(Collection<org.springframework.samples.petclinic.model.Pet> Pets) {
        GetPetsResponse response = new GetPetsResponse();
        for (org.springframework.samples.petclinic.model.Pet p : Pets) {
            Pet pet = new Pet();
            pet.setName(p.getName());
            pet.setType(p.getType().getName());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = formatter.format(p.getBirthDate());
            pet.setBirthDate(formattedDate);
            pet.setOwnerId(p.getOwner().getId());
            response.getPet().add(pet);
        }
        return response;
    }
//
//    private void mapFindPets(FindPetsResponse response, Collection<org.springframework.samples.petclinic.model.Pet> Pets) {
//        for (org.springframework.samples.petclinic.model.Pet own : Pets) {
//            Pet Pet = new Pet();
//            Pet.setFirstName(own.getFirstName());
//            Pet.setLastName(own.getLastName());
//            Pet.setTelephone(own.getTelephone());
//            Pet.setCity(own.getCity());
//            Pet.setAddress(own.getAddress());
//            response.getPet().add(Pet);
//        }
//    }

}
