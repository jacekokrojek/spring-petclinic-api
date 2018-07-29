package org.springframework.samples.petclinic.soap.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

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
            Collection<Pet> pets = mapPets(Pets);
            response.getPet().addAll(pets);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addPetRequest")
    @ResponsePayload
    public AddPetResponse addPet(@RequestPayload AddPetRequest petRequest) {
        Pet pet = petRequest.getPet();
        if (pet != null) {
            org.springframework.samples.petclinic.model.Pet p = mapToModelPet(pet);
            this.clinicService.savePet(p);
        }
        AddPetResponse response = new AddPetResponse();
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updatePetRequest")
    @ResponsePayload
    public UpdatePetResponse updatePet(@RequestPayload UpdatePetRequest PetRequest) {
        Pet pet = PetRequest.getPet();
        if (pet != null) {
            org.springframework.samples.petclinic.model.Pet p = mapToModelPet(pet);
            this.clinicService.savePet(p);
        }
        UpdatePetResponse response = new UpdatePetResponse();
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPetRequest")
    @ResponsePayload
    public GetPetResponse getPet(@RequestPayload GetPetRequest petRequest) {
        GetPetResponse response = new GetPetResponse();
        org.springframework.samples.petclinic.model.Pet p = this.clinicService.findPetById(petRequest.getId());
        if (p != null) {
            Pet pet = mapFromModelPet(p);
            response.getPet().add(pet);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPetTypesRequest")
    @ResponsePayload
    public GetPetTypesResponse getPetTypes(@RequestPayload GetPetTypesRequest petRequest) {
        GetPetTypesResponse response = new GetPetTypesResponse();
        Collection<org.springframework.samples.petclinic.model.PetType> ptc = this.clinicService.findAllPetTypes();
        Collection<PetType> list = new LinkedList<PetType>();
        for (org.springframework.samples.petclinic.model.PetType pt : ptc) {
            PetType p = new PetType();
            p.setId(pt.getId());
            p.setName(pt.getName());
            list.add(p);
        }
        response.getPetType().addAll(list);
        return response;
    }

    private Pet mapFromModelPet(org.springframework.samples.petclinic.model.Pet p){
        Pet pet = new Pet();
        pet.setName(p.getName());
        pet.setTypeId(p.getType().getId());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(p.getBirthDate());
        pet.setBirthDate(formattedDate);
        pet.setOwnerId(p.getOwner().getId());
        return pet;
    }

    private org.springframework.samples.petclinic.model.Pet mapToModelPet( Pet p){
        org.springframework.samples.petclinic.model.Pet pet = new org.springframework.samples.petclinic.model.Pet();
        pet.setName(p.getName());
        org.springframework.samples.petclinic.model.PetType petType = this.clinicService.findPetTypeById(p.getTypeId());
        pet.setType(petType);
        try {
            SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
            Date date = parser.parse(p.getBirthDate());
            pet.setBirthDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        org.springframework.samples.petclinic.model.Owner o = this.clinicService.findOwnerById(p.getOwnerId());
        pet.setOwner(o);
        return pet;
    }

    private Collection<Pet> mapPets(Collection<org.springframework.samples.petclinic.model.Pet> Pets) {
        Collection<Pet> list = new LinkedList<Pet>();
        for (org.springframework.samples.petclinic.model.Pet p : Pets) {
            Pet pet = mapFromModelPet(p);
            list.add(pet);
        }
        return list;
    }

}
