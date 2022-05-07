package pl.gerwant.itconferencemanager.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.gerwant.itconferencemanager.dao.entities.User;
import pl.gerwant.itconferencemanager.manager.Manager;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class Api {
    private Manager users;

    @Autowired
    public Api(Manager users) {
        this.users = users;
    } // constructor

    @GetMapping("/users/all")
    public Iterable<User> getAll(){
        return users.findAll();
    } // return all registered users

    @PatchMapping
    public User updateEmail(@RequestBody User user) { //update email of a user
        return users.updateEmail(user);
    }

}
