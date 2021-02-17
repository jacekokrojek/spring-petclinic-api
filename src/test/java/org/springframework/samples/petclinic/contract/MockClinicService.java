package org.springframework.samples.petclinic.contract;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public class MockClinicService implements ClinicService {
    @Override
    public Pet findPetById(int id) throws DataAccessException {
        return null;
    }

    @Override
    public Collection<Pet> findAllPets() throws DataAccessException {
        return null;
    }

    @Override
    public void savePet(Pet pet) throws DataAccessException {

    }

    @Override
    public void deletePet(Pet pet) throws DataAccessException {

    }

    @Override
    public Collection<Visit> findVisitsByPetId(int petId) {
        return null;
    }

    @Override
    public Visit findVisitById(int visitId) throws DataAccessException {
        return null;
    }

    @Override
    public Collection<Visit> findAllVisits() throws DataAccessException {
        return null;
    }

    @Override
    public void saveVisit(Visit visit) throws DataAccessException {

    }

    @Override
    public void deleteVisit(Visit visit) throws DataAccessException {

    }

    @Override
    public Vet findVetById(int id) throws DataAccessException {
        Vet vet = new Vet();
        Specialty dentistrySpeciality = new Specialty();
        dentistrySpeciality.setId(1);
        dentistrySpeciality.setName("dentistry");
        vet.setId(123);
        vet.setFirstName("John");
        vet.setLastName("Down");
        vet.addSpecialty(dentistrySpeciality);
        return vet;
    }

    @Override
    public Collection<Vet> findVets() throws DataAccessException {
        return null;
    }

    @Override
    public Collection<Vet> findAllVets() throws DataAccessException {
        return null;
    }

    @Override
    public void saveVet(Vet vet) throws DataAccessException {

    }

    @Override
    public void deleteVet(Vet vet) throws DataAccessException {

    }

    @Override
    public Owner findOwnerById(int id) throws DataAccessException {
        return null;
    }

    @Override
    public Collection<Owner> findAllOwners() throws DataAccessException {
        return null;
    }

    @Override
    public void saveOwner(Owner owner) throws DataAccessException {

    }

    @Override
    public void deleteOwner(Owner owner) throws DataAccessException {

    }

    @Override
    public Collection<Owner> findOwnerByLastName(String lastName) throws DataAccessException {
        return null;
    }

    @Override
    public PetType findPetTypeById(int petTypeId) {
        return null;
    }

    @Override
    public Collection<PetType> findAllPetTypes() throws DataAccessException {
        return null;
    }

    @Override
    public Collection<PetType> findPetTypes() throws DataAccessException {
        return null;
    }

    @Override
    public void savePetType(PetType petType) throws DataAccessException {

    }

    @Override
    public void deletePetType(PetType petType) throws DataAccessException {

    }

    @Override
    public Specialty findSpecialtyById(int specialtyId) {
        return null;
    }

    @Override
    public Collection<Specialty> findAllSpecialties() throws DataAccessException {
        return null;
    }

    @Override
    public void saveSpecialty(Specialty specialty) throws DataAccessException {

    }

    @Override
    public void deleteSpecialty(Specialty specialty) throws DataAccessException {

    }

    @Override
    public String addPetPhoto(int petId, MultipartFile file) {
        return null;
    }

    @Override
    public byte[] loadPetImage(String fileName) {
        return new byte[0];
    }
}
