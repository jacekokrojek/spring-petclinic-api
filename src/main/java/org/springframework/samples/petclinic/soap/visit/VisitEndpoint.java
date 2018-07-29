package org.springframework.samples.petclinic.soap.visit;

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
public class VisitEndpoint {
    private static final String NAMESPACE_URI = "http://petclinic.samples.springframework.org/soap/visit";

    private ClinicService clinicService;

    @Autowired
    public VisitEndpoint(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getVisitsRequest")
    @ResponsePayload
    public GetVisitsResponse getVisits(@RequestPayload GetVisitsRequest VisitsRequest) {
        GetVisitsResponse response = new GetVisitsResponse();
        Collection<org.springframework.samples.petclinic.model.Visit> Visits = this.clinicService.findAllVisits();
        if (!Visits.isEmpty()) {
            Collection<Visit> visits = mapVisits(Visits);
            response.getVisit().addAll(visits);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addVisitRequest")
    @ResponsePayload
    public AddVisitResponse addVisit(@RequestPayload AddVisitRequest petRequest) {
        Visit pet = petRequest.getVisit();
        if (pet != null) {
            org.springframework.samples.petclinic.model.Visit p = mapToModel(pet);
            this.clinicService.saveVisit(p);
        }
        AddVisitResponse response = new AddVisitResponse();
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateVisitRequest")
    @ResponsePayload
    public UpdateVisitResponse updateVisit(@RequestPayload UpdateVisitRequest VisitRequest) {
        Visit pet = VisitRequest.getVisit();
        if (pet != null) {
            org.springframework.samples.petclinic.model.Visit p = mapToModel(pet);
            this.clinicService.saveVisit(p);
        }
        UpdateVisitResponse response = new UpdateVisitResponse();
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getVisitRequest")
    @ResponsePayload
    public GetVisitResponse getVisit(@RequestPayload GetVisitRequest petRequest) {
        GetVisitResponse response = new GetVisitResponse();
        org.springframework.samples.petclinic.model.Visit p = this.clinicService.findVisitById(petRequest.getId());
        if (p != null) {
            Visit pet = mapFromModel(p);
            pet.setDescription("");
            response.getVisit().add(pet);
        }
        return response;
    }

    private Visit mapFromModel(org.springframework.samples.petclinic.model.Visit v){
        Visit visit = new Visit();
        visit.setDescription(v.getDescription());
        visit.setId(v.getId());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(v.getDate());
        visit.setDate(formattedDate);
        visit.setPetId(v.getPet().getId());
        return visit;
    }

    private org.springframework.samples.petclinic.model.Visit mapToModel(Visit v){
        org.springframework.samples.petclinic.model.Visit visit = new org.springframework.samples.petclinic.model.Visit();
        visit.setId(v.getId());
        visit.setDescription(v.getDescription());
        org.springframework.samples.petclinic.model.Pet pet = this.clinicService.findPetById(v.getPetId());
        visit.setPet(pet);
        try {
            SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
            Date date = parser.parse(v.getDate());
            visit.setDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return visit;
    }

    private Collection<Visit> mapVisits(Collection<org.springframework.samples.petclinic.model.Visit> Visits) {
        Collection<Visit> list = new LinkedList<Visit>();
        for (org.springframework.samples.petclinic.model.Visit p : Visits) {
            Visit pet = mapFromModel(p);
            list.add(pet);
        }
        return list;
    }

}
