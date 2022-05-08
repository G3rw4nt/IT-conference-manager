package pl.gerwant.itconferencemanager.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.gerwant.itconferencemanager.dao.entities.User;
import pl.gerwant.itconferencemanager.manager.Manager;

@RestController
@RequestMapping("/api")
public class Api {
    private Manager manager;

    @Autowired
    public Api(Manager manager) {
        this.manager = manager;
    } // constructor

    @GetMapping("/users/all")
    public Iterable<User> getAllUsers(){
        return manager.getAllUsers();
    } // return all registered users

    @PatchMapping
    public User updateEmail(@RequestBody User user) { //update email of a user
        return manager.updateEmail(user);
    }

}
