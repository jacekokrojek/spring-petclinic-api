package org.springframework.samples.petclinic.soap;

import io.spring.owner.owner_web_service.GetOwnersRequest;
import io.spring.owner.owner_web_service.GetOwnersResponse;
import io.spring.owner.owner_web_service.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.Collection;

@Endpoint
public class OwnerEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/owner/owner-web-service";

    private ClinicService clinicService;

    @Autowired
    public OwnerEndpoint(ClinicService clinicService) {
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
