package org.springframework.samples.petclinic.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("oauth")
public class OauthController {

    private static final Logger log = LoggerFactory.getLogger(OauthController.class);

    @RequestMapping(value = "/check_token")
    public Map<String, String> user(Principal principal) {
        log.info("Checking token");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("name", principal.getName());
        return map;
    }

}
