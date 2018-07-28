package org.springframework.samples.petclinic.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

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

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "setOwnersRequest")
    @ResponsePayload
    public SetOwnerResponse getOwners(@RequestPayload SetOwnerRequest ownerRequest) {
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

        SetOwnerResponse response = new SetOwnerResponse();
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
