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
    private final Manager manager;
    private final Conference conference;

    @Autowired
    public Api(Manager manager) { //API constructor

        this.manager = manager;
        this.conference = new Conference();
    }

    @GetMapping("/agenda")  // Return whole conference agenda
    public List<Lecture> getAgenda() {
        return conference.getLectures();
    }

    @GetMapping("/users/all")   // Return all registered users
    public Iterable<User> getAllUsers() {
        return manager.getAllUsers();
    }

    @GetMapping("/reservations") // Return all reservations made by created by a specific user
    public Iterable<Reservation> getUsersReservations(@RequestParam String login) {
        return manager.getUsersReservations(login);
    }

    @PatchMapping   // Update email of a specific user
    public User updateEmail(@RequestBody User user) {
        return manager.updateEmail(user);
    }

    @PostMapping    // Add reservation
    public String addReservation(@RequestBody Reservation reservation) throws IOException {
        return manager.addReservation(reservation);
    }

    @Transactional
    @DeleteMapping  // Delete reservation
    public void deleteReservation(@RequestParam String id, String login) {
        manager.deleteReservation(id, login);
    }

    @GetMapping("/results/lecture") // Return summary of results by Lecture ID
    public Map<String, Double> getLectureIdResults() {
        return manager.resultsByLecture();
    }

    @GetMapping("/results/lecturetopic") // Return summary of results by Topic
    public Map<Integer, Double> getLectureTopicResults() {
        return manager.resultsByTopic();
    }
}
