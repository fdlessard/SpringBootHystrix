package io.fdlessard.codebites.hystrix.controllers;


import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import io.fdlessard.codebites.hystrix.domain.Customer;
import io.fdlessard.codebites.hystrix.gateways.FallbackMethodCustomerGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@Slf4j
@RestController
public class RequestCollapsingController {

    @Autowired
    private FallbackMethodCustomerGateway fallbackMethodCustomerGateway;

    @GetMapping(path = "/collapsing/customers", produces = "application/json")
    public List<Customer> getCustomers() {


        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        Future<Customer> f1 = new CustomerRequestCollapser(fallbackMethodCustomerGateway, "1").queue();
        Future<Customer> f2 = new CustomerRequestCollapser(fallbackMethodCustomerGateway, "2").queue();
        Future<Customer> f3 = new CustomerRequestCollapser(fallbackMethodCustomerGateway, "3").queue();
        Future<Customer> f4 = new CustomerRequestCollapser(fallbackMethodCustomerGateway, "4").queue();

        List<Customer> customers = new ArrayList<>();


        try {
            customers.add(f1.get());
            customers.add(f2.get());
            customers.add(f3.get());
            customers.add(f4.get());

        } catch (Exception e) {

        }

        return customers;
    }
}
