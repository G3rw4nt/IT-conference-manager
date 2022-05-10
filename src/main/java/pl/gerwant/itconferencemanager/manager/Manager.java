package pl.gerwant.itconferencemanager.manager;

import org.decimal4j.util.DoubleRounder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.gerwant.itconferencemanager.dao.ReservationRepo;
import pl.gerwant.itconferencemanager.dao.UserRepo;
import pl.gerwant.itconferencemanager.dao.entities.Reservation;
import pl.gerwant.itconferencemanager.dao.entities.User;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
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

    private int findRes(String login, Integer hour)
    {
        List<Reservation> list = reservationRepo.findAll();
        int count = 0;
        for(int i = 0; i < list.size(); i++)
        {
            if(list.get(i).getLogin().equals(login) && hour.equals(list.get(i).getStarthour()))
            {
                count++;
            }
        }
        return count;
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
            List<Reservation> temp = reservationRepo.findAll();
            System.out.println("--------------------------------------");
            reservation.setLecturetopic(Character.getNumericValue(reservation.getLectureid().charAt(3)));
            reservation.setStarthour(Integer.parseInt(reservation.getLectureid().substring(0,2)));
            System.out.println(reservation.getLogin());
            System.out.println(reservation.getStarthour());
            System.out.println(findRes(reservation.getLogin(),reservation.getStarthour()));
            if (findRes(reservation.getLogin(),reservation.getStarthour()) > 0) {
                return "NA PODANĄ GODZINĘ I NAZWISKO ZOSTAŁA JUŻ ZŁOŻONA REZERWACJA";
            }
            else
            {

                File file = new File("messages.txt");
                PrintWriter messages = new PrintWriter(new FileWriter(file,true));
                messages.println("Data: " + LocalDate.now());
                messages.println("Do: " + reservation.getEmail());
                messages.println("Treść: Pomyślnie zarezerwowano miejsce na prelekcji: " + reservation.getLectureid() + " dla użytkownika: " + reservation.getLogin());
                messages.println("\n");
                messages.close();
                reservationRepo.save(reservation);
                return "REZERWACJA PRZYJĘTA POMYŚLNIE";
                //Reservation tempRes = reservationRepo.findByStarthourAndLogin(reservation.getStarthour(),reservation.getLogin());
                //return String.valueOf(reservationRepo.countByStarthourAndLogin(reservation.getStarthour(),reservation.getLogin()));

            }
        }
    }

    public Iterable<Reservation> getAllReservations(){return reservationRepo.findAll();}

    public Iterable<Reservation> getUsersReservations(String login){return reservationRepo.findByLogin(login);}

    public void deleteReservation(String id, String login){reservationRepo.deleteByLectureidAndLogin(id, login);}


    public Map<String, Double> resultsByLecture(){
       HashMap<String, Double> results = new HashMap<>();
       Iterable<Reservation> temp = reservationRepo.findAll();
       Iterator<Reservation> iter = temp.iterator();
       String key;
       while(iter.hasNext())
       {
           key = iter.next().getLectureid();
           results.merge(key,1.0d,Double::sum);
       }
       Double val;
       for (Map.Entry<String, Double> entry : results.entrySet())
       {
           val = entry.getValue();
           key = entry.getKey();
           results.put(key, DoubleRounder.round(val/5,2));
       }
        Map<String, Double> sortedMap = results.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, (Map.Entry::getValue),
                        (e1, e2) -> e1, LinkedHashMap::new));
       return sortedMap;

    }
    public Map<Integer,Double> resultsByTopic()
    {
        ArrayList<Double> topics = new ArrayList<>();
        Iterable<Reservation> temp = reservationRepo.findAll();
        Iterator<Reservation> iter = temp.iterator();
        for(int i = 0; i < 3; i++)
        {
            topics.add(0d);
        }
        while(iter.hasNext())
        {
            switch (iter.next().getLecturetopic())
            {
                case 1:
                    topics.set(0,topics.get(0) + 1);
                    break;
                case 2:
                    topics.set(1,topics.get(1) + 1);
                    break;
                case 3:
                    topics.set(2,topics.get(2) + 1);
                    break;
            }
        }
        Map<Integer,Double> topicResults = new HashMap<>();
        Double sum = topics.get(0) + topics.get(1) + topics.get(2);
        topicResults.put(1,DoubleRounder.round(topics.get(0)/sum,2));
        topicResults.put(2,DoubleRounder.round(topics.get(1)/sum,2));
        topicResults.put(3,DoubleRounder.round(topics.get(2)/sum,2));
        return topicResults;
    }

}
