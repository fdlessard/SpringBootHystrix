package io.fdlessard.codebites.hystrix.controllers;


import io.fdlessard.codebites.hystrix.domain.Customer;
import io.fdlessard.codebites.hystrix.domain.Product;
import io.fdlessard.codebites.hystrix.gateways.FallbackMethodCustomerGateway;
import io.fdlessard.codebites.hystrix.gateways.FallbackMethodProductGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class FallbackMethodController {

    @Autowired
    private FallbackMethodCustomerGateway fallbackMethodCustomerGateway;

    @Autowired
    private FallbackMethodProductGateway fallbackMethodProductGateway;

    @GetMapping(path = "/fallback/customer/{id}", produces = "application/json")
    public Customer getCustomer(@PathVariable String id) {

        log.debug("FallbackMethodController.getCustomer({})", id);

        Product product = fallbackMethodProductGateway.getProduct("12");

        return fallbackMethodCustomerGateway.getCustomer(id);
    }
}
