package io.fdlessard.codebites.hystrix.controllers;


import io.fdlessard.codebites.hystrix.domain.Customer;
import io.fdlessard.codebites.hystrix.domain.Product;
import io.fdlessard.codebites.hystrix.gateways.CustomerGateway;
import io.fdlessard.codebites.hystrix.gateways.ProductGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HystrixController {

    @Autowired
    private CustomerGateway customerGateway;

    @Autowired
    private ProductGateway productGateway;

    @GetMapping(path = "/customer/{id}", produces = "application/json")
    public Customer getCustomer(@PathVariable String id) {

        log.debug("CustomerController.getCustomer({})", id);

        Product product = productGateway.getProduct("12");

        return customerGateway.getCustomer(id);
    }
}
