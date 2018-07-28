package org.springframework.samples.petclinic.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.rest.BindingErrorsResponse;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.validation.Valid;
import java.util.Collection;

@Endpoint
public class PetClinicEndpoint {
    private static final String NAMESPACE_URI = "http://petclinic.samples.springframework.org/soap";

    private ClinicService clinicService;

    @Autowired
    public PetClinicEndpoint(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getOwnersRequest")
    @ResponsePayload
    public GetOwnersResponse getOwners(@RequestPayload GetOwnersRequest ownersRequest) {
        GetOwnersResponse response = new GetOwnersResponse();
        Collection<org.springframework.samples.petclinic.model.Owner> owners = this.clinicService.findAllOwners();
        if (!owners.isEmpty()) {
            mapOwners(response, owners);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addOwnerRequest")
    @ResponsePayload
    public AddOwnerResponse addOwner(@RequestPayload AddOwnerRequest ownerRequest) {
        org.springframework.samples.petclinic.model.Owner owner = new org.springframework.samples.petclinic.model.Owner();
        Owner own = ownerRequest.getOwner();
        if (own != null) {
            owner.setFirstName(own.getFirstName());
            owner.setLastName(own.getLastName());
            owner.setTelephone(own.getTelephone());
            owner.setCity(own.getCity());
            owner.setAddress(own.getAddress());
            this.clinicService.saveOwner(owner);
        }

        AddOwnerResponse response = new AddOwnerResponse();
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateOwnerRequest")
    @ResponsePayload
    public UpdateOwnerResponse updateOwner(@RequestPayload UpdateOwnerRequest ownerRequest) {
        Owner owner = ownerRequest.getOwner();
        org.springframework.samples.petclinic.model.Owner currentOwner = this.clinicService.findOwnerById(owner.id);
        currentOwner.setAddress(owner.getAddress());
        currentOwner.setCity(owner.getCity());
        currentOwner.setFirstName(owner.getFirstName());
        currentOwner.setLastName(owner.getLastName());
        currentOwner.setTelephone(owner.getTelephone());
        this.clinicService.saveOwner(currentOwner);
        UpdateOwnerResponse response = new UpdateOwnerResponse();
        return response;

    }


    private void mapOwners(GetOwnersResponse response, Collection<org.springframework.samples.petclinic.model.Owner> owners) {
        for (org.springframework.samples.petclinic.model.Owner own : owners) {
            Owner owner = new Owner();
            owner.setFirstName(own.getFirstName());
            owner.setLastName(own.getLastName());
            owner.setTelephone(own.getTelephone());
            owner.setCity(own.getCity());
            owner.setAddress(own.getAddress());
            response.getOwner().add(owner);
        }
    }
}
