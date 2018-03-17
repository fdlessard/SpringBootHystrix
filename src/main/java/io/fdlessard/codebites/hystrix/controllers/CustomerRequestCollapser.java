package io.fdlessard.codebites.hystrix.controllers;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import io.fdlessard.codebites.hystrix.domain.Customer;
import io.fdlessard.codebites.hystrix.gateways.FallbackMethodCustomerGateway;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomerRequestCollapser extends HystrixCollapser<List<Customer>, Customer, String> {

    private final String customerId;

    private final FallbackMethodCustomerGateway fallbackMethodCustomerGateway;

    public CustomerRequestCollapser(FallbackMethodCustomerGateway fallbackMethodCustomerGateway, String customerId) {
        this.fallbackMethodCustomerGateway = fallbackMethodCustomerGateway;
        this.customerId = customerId;
    }

    @Override
    public String getRequestArgument() {
        return customerId;
    }

    @Override
    protected HystrixCommand<List<Customer>> createCommand(Collection<CollapsedRequest<Customer, String>> requests) {
        return new BatchCommand(fallbackMethodCustomerGateway, requests);
    }

    @Override
    protected void mapResponseToRequests(List<Customer> batchResponse, Collection<CollapsedRequest<Customer, String>> requests) {
        int count = 0;
        for (CollapsedRequest<Customer, String> request : requests) {
            request.setResponse(batchResponse.get(count++));
        }
    }

    private static final class BatchCommand extends HystrixCommand<List<Customer>> {

        private final FallbackMethodCustomerGateway fallbackMethodCustomerGateway;

        private final Collection<CollapsedRequest<Customer, String>> requests;

        private BatchCommand(FallbackMethodCustomerGateway fallbackMethodCustomerGateway, Collection<CollapsedRequest<Customer, String>> requests) {
            super(
                    Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("CustomerGroup"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("CustomerRequestCollapser")));
            this.fallbackMethodCustomerGateway = fallbackMethodCustomerGateway;
            this.requests = requests;
        }

        @Override
        protected List<Customer> run() {
            ArrayList<Customer> response = new ArrayList<>();
            for (CollapsedRequest<Customer, String> request : requests) {

                Customer customer = fallbackMethodCustomerGateway.getCustomer(request.getArgument());

                response.add(customer);
            }
            return response;
        }
    }
}