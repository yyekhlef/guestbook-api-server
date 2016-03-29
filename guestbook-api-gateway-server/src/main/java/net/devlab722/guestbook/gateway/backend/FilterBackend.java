package net.devlab722.guestbook.gateway.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Maps;
import com.netflix.client.DefaultLoadBalancerRetryHandler;
import com.netflix.client.RetryHandler;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.reactive.LoadBalancerCommand;

import net.devlab722.guestbook.api.Message;
import rx.Observable;

@Component
public class FilterBackend {

    private final ILoadBalancer loadBalancer;

    private final RestTemplate restTemplate;

    // https://github.com/Netflix/ribbon/blob/master/ribbon-core/src/main/java/com/netflix/client/DefaultLoadBalancerRetryHandler.java
    // retrySameServer=0, retryNextServer=1, retryEnabled=true
    public static final RetryHandler DEFAULT_RETRY_HANDLER = new DefaultLoadBalancerRetryHandler(1, 1, true);

    @Autowired
    FilterBackend(FilterBackendLoadBalancer loadBalancerConfiguration, RestTemplate restTemplate) {
        this.loadBalancer = loadBalancerConfiguration.getLoadBalancer();
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<Message> blockingFilter(Message input) {
        return rxFilter(input).toBlocking().first();
    }

    public Observable<ResponseEntity<Message>> rxFilter(Message input) {
        return LoadBalancerCommand.<ResponseEntity<Message>>builder()
                .withLoadBalancer(loadBalancer)
                .withRetryHandler(DEFAULT_RETRY_HANDLER)
                .build()
                .submit(server -> Observable.just(callRemoteService(input, server)));
    }

    ResponseEntity<Message> callRemoteService(Message message, Server server) {
        return restTemplate.exchange(
                "http://" + server.getHost() + ":" + server.getPort() + "/api/v1/sanity/filter",
                HttpMethod.POST,
                new HttpEntity<>(message),
                Message.class,
                Maps.newHashMap());
    }

    public static void testDirectCall(Message message) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Message> requestEntity = new HttpEntity<>(message);
        ResponseEntity<Message> responseEntity = restTemplate.exchange(
                "http://localhost:8080/api/v1/sanity/filter",
                HttpMethod.POST,
                requestEntity,
                Message.class,
                Maps.newHashMap());
        System.out.println("==========================");
        System.out.println(responseEntity.getBody());
        System.out.println("==========================");
    }

    public static void testRxCall(Message message) {
        FilterBackend filterBackend = new FilterBackend(
                new FilterBackendLoadBalancer("localhost:8080"),
                new RestTemplate()
        );
        Observable<ResponseEntity<Message>> responseEntityObservable = filterBackend.rxFilter(message);
        System.out.println("===================");
        System.out.println(responseEntityObservable.toBlocking().first().getBody());
        System.out.println("===================");
    }

    public static void main(String[] args) {
        Message message = Message.builder().content("ceci est un test avec des microservices et des bières").build();
        System.out.println("Testing the rxCall");
        testRxCall(message);
        System.out.println("**********************");
        System.out.println("Testing the directCall");
        testDirectCall(message);
    }
}
