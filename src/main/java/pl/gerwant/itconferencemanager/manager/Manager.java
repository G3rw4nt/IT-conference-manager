package pl.gerwant.itconferencemanager.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.gerwant.itconferencemanager.dao.ReservationRepo;
import pl.gerwant.itconferencemanager.dao.UserRepo;
import pl.gerwant.itconferencemanager.dao.entities.Reservation;
import pl.gerwant.itconferencemanager.dao.entities.User;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class Manager {
    private UserRepo userRepo;
    private ReservationRepo reservationRepo;

    @Autowired
    public Manager(UserRepo userRepo, ReservationRepo reservationRepo){  //constructor
        this.reservationRepo = reservationRepo;
        this.userRepo = userRepo;}


    public Iterable<User> getAllUsers(){
        return userRepo.findAll();
    }

    public User updateEmail(User user){
        return userRepo.save(user);
    }

    public Reservation addReservation(Reservation reservation) throws IOException {
        if(reservationRepo.countByLectureid(reservation.getLectureid()) >= 5)
        {
            return null;
        }
        else
        {
            return reservationRepo.save(reservation);
        }
    }

    public Iterable<Reservation> getAllReservations(){return reservationRepo.findAll();}

    public Iterable<Reservation> getUsersReservations(String login){return reservationRepo.findByLogin(login);}

    public void deleteReservation(String id, String login){reservationRepo.deleteByLectureidAndLogin(id, login);}

}
