package edu.ustc.server.config;

import edu.ustc.server.domain.User;
import edu.ustc.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import java.util.Collection;

/**
 * Created by colddew on 2019/5/7.
 */
@Configuration
public class RouterFunctionConfiguration {

    @Bean
    @Autowired
    public RouterFunction<ServerResponse> listUsers(UserRepository userRepository) {
        return RouterFunctions.route(RequestPredicates.GET("/users"),
                request -> {
                    Collection<User> users = userRepository.list();
                    Flux<User> userFlux = Flux.fromIterable(users);
                    return ServerResponse.ok().body(userFlux, User.class);
                });
    }
}
