package io.fdlessard.codebites.hystrix.gateways;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.fdlessard.codebites.hystrix.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;


@Slf4j
@Service
public class FallbackMethodProductGateway {

    @Qualifier("productRestTemplate")
    private RestOperations restOperations;

    public FallbackMethodProductGateway(@Qualifier("productRestTemplate") RestOperations restOperations) {
        this.restOperations = restOperations;
    }

    @HystrixCommand(fallbackMethod = "reliable")
    public Product getProduct(String id) {

        log.debug("FallbackMethodProductGateway.getProduct({})", id);

        return restOperations.getForObject("http://localhost:8090/product/12", Product.class);
    }

    public Product reliable(String id) {
        return Product.builder()
                .id("fallBackProductId")
                .name("fallBackProductName")
                .cost(10)
                .quantityAvailable(150)
                .build();

    }

}