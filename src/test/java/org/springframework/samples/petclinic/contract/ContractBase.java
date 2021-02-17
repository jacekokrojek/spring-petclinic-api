package org.springframework.samples.petclinic.contract;

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.springframework.samples.petclinic.rest.VetRestController;

public class ContractBase {
    @Before
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(new VetRestController(new MockClinicService()));
    }
}
