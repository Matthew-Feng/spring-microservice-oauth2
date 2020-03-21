package com.example.edgeservice;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import feign.RequestInterceptor;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;;import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@EnableOAuth2Sso
@EnableFeignClients
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableZuulProxy
@SpringBootApplication
public class EdgeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdgeServiceApplication.class, args);
	}

	@Bean
	public RequestInterceptor getUserFeignClientInterceptor() {
		return new UserFeignClientInterceptor();
	}

}

@Data
 class Beer {
	private String name;
}

@FeignClient("beer-catalog-service")
interface BeerClient {

	
	@GetMapping("/beers")
	CollectionModel<Beer> readBeers();
}

@RestController
class GoodBeerApiAdapterRestController {

	private final BeerClient beerClient;

	public GoodBeerApiAdapterRestController(BeerClient beerClient) {
		this.beerClient = beerClient;
	}

	@HystrixCommand(fallbackMethod = "fallback")
	@GetMapping("/good-beers")
	@CrossOrigin(origins = "*")
	public Collection<Beer> goodBeers() {
		return beerClient.readBeers()
				.getContent()
				.stream()
				.filter(this::isGreat)
				.collect(Collectors.toList());
	}
	public Collection<Beer> fallback() {
		return new ArrayList<>();
	}

	private boolean isGreat(Beer beer) {
		return !beer.getName().equals("Budweiser") &&
				!beer.getName().equals("Coors Light") &&
				!beer.getName().equals("PBR");
	}
}
