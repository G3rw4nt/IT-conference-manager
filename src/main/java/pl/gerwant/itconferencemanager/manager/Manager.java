package pl.gerwant.itconferencemanager.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.gerwant.itconferencemanager.dao.UserRepo;
import pl.gerwant.itconferencemanager.dao.entities.User;

import java.util.Optional;

@Service
public class Manager {
    private UserRepo userRepo;

    @Autowired
    public Manager(UserRepo userRepo) { this.userRepo = userRepo; } //constructor


    public Iterable<User> findAll(){
        return userRepo.findAll();
    }

    public User updateEmail(User user){
        return userRepo.save(user);
    }


}
