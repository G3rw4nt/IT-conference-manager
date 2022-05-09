package pl.gerwant.itconferencemanager.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.gerwant.itconferencemanager.Conference;
import pl.gerwant.itconferencemanager.Lecture;
import pl.gerwant.itconferencemanager.dao.entities.Reservation;
import pl.gerwant.itconferencemanager.dao.entities.User;
import pl.gerwant.itconferencemanager.manager.Manager;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class Api {
    private Manager manager;
    private Conference conference;

    @Autowired
    public Api(Manager manager) {

        this.manager = manager;
        this.conference = new Conference();
    } // constructor

    @GetMapping("/agenda")
    public List<Lecture> getAgenda() {
        return conference.getLectures();
    }
    @GetMapping("/reservations/all")
    public Iterable<Reservation> getAllReservations(){return manager.getAllReservations();}
    @GetMapping("/users/all")
    public Iterable<User> getAllUsers(){ return manager.getAllUsers(); } // return all registered users
    @GetMapping("/reservations")
    public Iterable<Reservation> getUsersReservations(@RequestParam String login){return manager.getUsersReservations(login);}

    @PatchMapping
    public User updateEmail(@RequestBody User user) { //update email of a user
        return manager.updateEmail(user);
    }

    @PostMapping
    public String addReservation(@RequestBody Reservation reservation) throws IOException { return manager.addReservation(reservation); }

    @Transactional
    @DeleteMapping
    public void deleteReservation(@RequestParam String id, String login){manager.deleteReservation(id, login);}

    @GetMapping("/results/lecture")
    public Map<String,Double> getLectureIdResults(){return manager.resultsByLecture();}

    @GetMapping("/results/lecturetopic")
    public Map<Integer,Double> getLectureTobicResults(){return manager.resultsByTopic();}
}
