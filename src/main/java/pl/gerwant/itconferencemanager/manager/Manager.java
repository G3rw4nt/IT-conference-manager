package pl.gerwant.itconferencemanager.manager;

import org.decimal4j.util.DoubleRounder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.gerwant.itconferencemanager.dao.ReservationRepo;
import pl.gerwant.itconferencemanager.dao.UserRepo;
import pl.gerwant.itconferencemanager.dao.entities.Reservation;
import pl.gerwant.itconferencemanager.dao.entities.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class Manager {
    private final UserRepo userRepo;
    private final ReservationRepo reservationRepo;

    @Autowired
    public Manager(UserRepo userRepo, ReservationRepo reservationRepo) {  //constructor
        this.reservationRepo = reservationRepo;
        this.userRepo = userRepo;
    }


    public Iterable<User> getAllUsers() {    // return all users
        return userRepo.findAll();
    }

    public User updateEmail(User user) { // update specific user email
        return userRepo.save(user);
    }

    private int countRes(String login, Integer hour)     //count reservations of specific login and start hour
    {
        List<Reservation> list = reservationRepo.findAll();
        int count = 0;
        for (Reservation reservation : list) {
            if (reservation.getLogin().equals(login) && hour.equals(reservation.getStarthour())) {
                count++;
            }
        }
        return count;
    }

    public String addReservation(Reservation reservation) throws IOException {  //add reservation
        if (reservationRepo.countByLectureid(reservation.getLectureid()) >= 5) //if there's already booked 5 persons on lecture
        {                                                                     //it returns statement
            return "NA TEJ PRELEKCJI SKOŃCZYŁY SIĘ JUŻ MIEJSCA.";
        } else {

            if (userRepo.findById(reservation.getLogin()).isEmpty()) {   //check if user exists in database
                userRepo.save(new User(reservation.getLogin(), reservation.getEmail()));
            } else {
                User temp = userRepo.findById(reservation.getLogin()).get();    //check if login is busy
                if (temp.getLogin().equals(reservation.getLogin()) && (!temp.getEmail().equals(reservation.getEmail()))) {
                    return "PODANY LOGIN JEST JUŻ ZAJĘTY. WYBIERZ INNY LOGIN I ZŁÓŻ PONOWNIE REZERWACJĘ.";
                }
            }
            reservation.setLecturetopic(Character.getNumericValue(reservation.getLectureid().charAt(3))); //extract and set start hour and lecture topic id
            reservation.setStarthour(Integer.parseInt(reservation.getLectureid().substring(0, 2)));
            if (countRes(reservation.getLogin(), reservation.getStarthour()) > 0) {
                return "NA PODANĄ GODZINĘ I NAZWISKO ZOSTAŁA JUŻ ZŁOŻONA REZERWACJA";
            } else //if everything is good it saves a reservation and "sends" email
            {

                File file = new File("messages.txt");
                PrintWriter messages = new PrintWriter(new FileWriter(file, true));
                messages.println("Data: " + LocalDate.now());
                messages.println("Do: " + reservation.getEmail());
                messages.println("Treść: Pomyślnie zarezerwowano miejsce na prelekcji: " + reservation.getLectureid() + " dla użytkownika: " + reservation.getLogin());
                messages.println("\n");
                messages.close();
                reservationRepo.save(reservation);
                return "REZERWACJA PRZYJĘTA POMYŚLNIE";

            }
        }
    }

    public Iterable<Reservation> getUsersReservations(String login) {
        return reservationRepo.findByLogin(login);
    } //return all users

    public void deleteReservation(String id, String login) {
        reservationRepo.deleteByLectureidAndLogin(id, login);
    } //delete reservation


    public Map<String, Double> resultsByLecture() { // Return summary of results by Lecture ID
        HashMap<String, Double> results = new HashMap<>();
        Iterable<Reservation> temp = reservationRepo.findAll();
        Iterator<Reservation> iter = temp.iterator();
        String key;
        while (iter.hasNext())        //count all lectures
        {
            key = iter.next().getLectureid();
            results.merge(key, 1.0d, Double::sum);
        }
        Double val;
        for (Map.Entry<String, Double> entry : results.entrySet())   //divide by 5 to make percents
        {
            val = entry.getValue();
            key = entry.getKey();
            results.put(key, DoubleRounder.round(val / 5, 2));
        }
        return results.entrySet().stream()      //sort
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, (Map.Entry::getValue),
                        (e1, e2) -> e1, LinkedHashMap::new));

    }

    public Map<Integer, Double> resultsByTopic() // Return summary of results by Topic
    {
        ArrayList<Double> topics = new ArrayList<>();
        Iterable<Reservation> temp = reservationRepo.findAll();
        Iterator<Reservation> iter = temp.iterator();
        for (int i = 0; i < 3; i++)  //set 3 array elements
        {
            topics.add(0d);
        }
        while (iter.hasNext()) {
            switch (iter.next().getLecturetopic()) {
                case 1:
                    topics.set(0, topics.get(0) + 1);    //lecture topic 1
                    break;
                case 2:
                    topics.set(1, topics.get(1) + 1);    //lecture topic 2
                    break;
                case 3:
                    topics.set(2, topics.get(2) + 1);    //lecture topic 3
                    break;
            }
        }
        Map<Integer, Double> topicResults = new HashMap<>();             //convert to percent and round
        Double sum = topics.get(0) + topics.get(1) + topics.get(2);
        topicResults.put(1, DoubleRounder.round(topics.get(0) / sum, 2));
        topicResults.put(2, DoubleRounder.round(topics.get(1) / sum, 2));
        topicResults.put(3, DoubleRounder.round(topics.get(2) / sum, 2));
        return topicResults;
    }

}
