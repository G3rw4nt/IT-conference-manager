package pl.gerwant.itconferencemanager.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.gerwant.itconferencemanager.dao.ReservationRepo;
import pl.gerwant.itconferencemanager.dao.UserRepo;
import pl.gerwant.itconferencemanager.dao.entities.Reservation;
import pl.gerwant.itconferencemanager.dao.entities.User;

import java.io.*;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;


@Service
public class Manager {
    private final UserRepo userRepo;
    private final ReservationRepo reservationRepo;

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

    public String addReservation(Reservation reservation) throws IOException {
        if(reservationRepo.countByLectureid(reservation.getLectureid()) >= 5)
        {
            return "NA TEJ PRELEKCJI SKOŃCZYŁY SIĘ JUŻ MIEJSCA.";
        }

        else
        {

            if(userRepo.findById(reservation.getLogin()).isEmpty()) {
                userRepo.save(new User(reservation.getLogin(),reservation.getEmail()));
            }
            else
            {
                User temp = userRepo.findById(reservation.getLogin()).get();
                if(temp.getLogin().equals(reservation.getLogin()) && (!temp.getEmail().equals(reservation.getEmail())))
                {
                    return "PODANY LOGIN JEST JUŻ ZAJĘTY. WYBIERZ INNY LOGIN I ZŁÓŻ PONOWNIE REZERWACJĘ.";
                }
            }


                reservation.setLecturetopic(Character.getNumericValue(reservation.getLectureid().charAt(3)));
                reservation.setStarthour(Integer.parseInt(reservation.getLectureid().substring(0,2)));
                File file = new File("messages.txt");
                PrintWriter messages = new PrintWriter(new FileWriter(file,true));
                messages.println("Data: " + LocalDate.now());
                messages.println("Do: " + reservation.getEmail());
                messages.println("Treść: Pomyślnie zarezerwowano miejsce na prelekcji: " + reservation.getLectureid() + " dla użytkownika: " + reservation.getLogin());
                messages.println("\n");
                messages.close();
                reservationRepo.save(reservation);
                return "REZERWACJA PRZYJĘTA POMYŚLNIE";
        }
    }

    public Iterable<Reservation> getAllReservations(){return reservationRepo.findAll();}

    public Iterable<Reservation> getUsersReservations(String login){return reservationRepo.findByLogin(login);}

    public void deleteReservation(String id, String login){reservationRepo.deleteByLectureidAndLogin(id, login);}

    public Map<String,Integer> resultsByLecture(){
       Iterable<Reservation> temp = reservationRepo.findAll();
       
    }

}
