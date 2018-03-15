package io.fdlessard.codebites.hystrix.controllers;


import io.fdlessard.codebites.hystrix.domain.Customer;
import io.fdlessard.codebites.hystrix.gateways.RaiseExceptionCustomerGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RaiseExceptionController {

    @Autowired
    private RaiseExceptionCustomerGateway raiseExceptionCustomerGateway;

    @GetMapping(path = "/raise/customer/{id}", produces = "application/json")
    public Customer getCustomerRaise(@PathVariable String id) {

        log.debug("RaiseExceptionCustomerGateway.getCustomerRaise({})", id);

        return raiseExceptionCustomerGateway.getCustomerRaise(id);
    }
}
