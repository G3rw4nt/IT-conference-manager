package pl.gerwant.itconferencemanager.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.gerwant.itconferencemanager.dao.ReservationRepo;
import pl.gerwant.itconferencemanager.dao.UserRepo;
import pl.gerwant.itconferencemanager.dao.entities.User;

@Service
public class Manager {
    private UserRepo userRepo;
    private ReservationRepo reservationRepo;

    @Autowired
    public Manager(UserRepo userRepo, ReservationRepo reservationRepo) {  //constructor
        this.reservationRepo = reservationRepo;
        this.userRepo = userRepo; }


    public Iterable<User> getAllUsers(){
        return userRepo.findAll();
    }

    public User updateEmail(User user){
        return userRepo.save(user);
    }


}
