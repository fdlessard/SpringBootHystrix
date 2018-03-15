package io.fdlessard.codebites.hystrix.gateways;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.fdlessard.codebites.hystrix.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

@Slf4j
@Service
public class FallbackMethodCustomerGateway {

    @Qualifier("customerRestTemplate")
    private RestOperations restOperations;

    public FallbackMethodCustomerGateway(@Qualifier("customerRestTemplate") RestOperations restOperations) {
        this.restOperations = restOperations;
    }

    @HystrixCommand(fallbackMethod = "reliable")
    public Customer getCustomer(String id) {

        log.debug("FallbackMethodCustomerGateway.getCustomer({})", id);

        return restOperations.getForObject("http://localhost:8090/customer/12", Customer.class);
    }

    public Customer reliable(String id) {
        return Customer.builder()
                .id("fallBackCustomerId")
                .company("fallBackCustomerCompany")
                .firstName("fallBackCustomerFirstName")
                .lastName("fallBackCustomerLastName")
                .build();

    }

}