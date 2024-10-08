package com.fashionmall.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutingConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        //eureka 서버 연결용
//        return builder.routes()
//                .route("CART-SERVICE", r -> r.path("/api/cart/**")
//                        .uri("lb://module-cart-service"))
//                .route("COUPON-SERVICE", r -> r.path("/api/coupon/**")
//                        .uri("lb://module-coupon-service"))
//                .route("ITEM-SERVICE", r -> r.path("/api/item/**")
//                        .uri("lb://module-item-service"))
//                .route("ORDER-SERVICE", r -> r.path("/api/order/**")
//                        .uri("lb://module-order-service"))
//                .route("REVIEW-SERVICE", r -> r.path("/api/review/**")
//                        .uri("lb://module-review-service"))
//                .route("USER-SERVICE", r -> r.path("/api/user/**")
//                        .uri("lb://module-user-service"))
//                .build();
        return builder.routes()
                .route("CART-SERVICE", r -> r.path("/api/cart/**")
                        .uri("http://localhost:8080"))
                .route("COUPON-SERVICE", r -> r.path("/api/coupon/**")
                        .uri("http://localhost:8081"))
                .route("ITEM-SERVICE", r -> r.path("/api/item/**")
                        .uri("http://localhost:8082"))
                .route("ORDER-SERVICE", r -> r.path("/api/order/**")
                        .uri("http://localhost:8083"))
                .route("REVIEW-SERVICE", r -> r.path("/api/review/**")
                        .uri("http://localhost:8084"))
                .route("USER-SERVICE", r -> r.path("/api/user/**")
                        .uri("http://localhost:8085"))
                .build();
    }

}
