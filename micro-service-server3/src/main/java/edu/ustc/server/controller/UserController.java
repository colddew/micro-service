package edu.ustc.server.controller;

import edu.ustc.server.domain.User;
import edu.ustc.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

//import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by colddew on 2019/5/7.
 */
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

//    @RequestMapping(method = RequestMethod.POST, path = "/user")
    @PostMapping(path = "/user")
    public User addUser(String name) {

        User user = new User();
        user.setName(name);

        return userRepository.save(user);
    }
}
