package com.borrowboss.apigateway.Config;

import com.borrowboss.apigateway.Service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;



import org.springframework.http.HttpHeaders;


@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config>{
	
	@Autowired
	private RouteValidator routeValidator;
	@Autowired
	private JwtService jwtService;
	
	public AuthenticationFilter() {
		super(Config.class);
	}
	
	public static class Config{
		
	}

	@Override
	public GatewayFilter apply(Config config) {
		
		return ((exchange,chain)->{
			
			if(routeValidator.isSecured.test(exchange.getRequest())) {
				//header contains token or not
				if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
					throw new RuntimeException("missing authorization header");
				}
				
				String authHeader=exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
				if(authHeader != null && authHeader.startsWith("Bearer ")) {
					authHeader=authHeader.substring(7);
				}
				
				try {
					jwtService.validateToken(authHeader);
				}catch(Exception e){
					throw new RuntimeException("Un-authorised access");
					
				}
			}
			return chain.filter(exchange);
		});
	}

}
