package io.fdlessard.codebites.hystrix.gateways;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.fdlessard.codebites.hystrix.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import static com.netflix.hystrix.contrib.javanica.annotation.HystrixException.RUNTIME_EXCEPTION;

@Slf4j
@Service
public class RaiseExceptionCustomerGateway {

    @Qualifier("customerRestTemplate")
    private RestOperations restOperations;

    public RaiseExceptionCustomerGateway(@Qualifier("customerRestTemplate") RestOperations restOperations) {
        this.restOperations = restOperations;
    }

    @HystrixCommand(raiseHystrixExceptions = RUNTIME_EXCEPTION)
    public Customer getCustomerRaise(String id) {

        log.debug("RaiseExceptionCustomerGateway.getCustomer({})", id);

        return restOperations.getForObject("http://localhost:8090/customer/12", Customer.class);
    }
}