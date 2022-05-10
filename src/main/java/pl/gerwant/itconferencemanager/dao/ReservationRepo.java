package pl.gerwant.itconferencemanager.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.gerwant.itconferencemanager.dao.entities.Reservation;

public interface ReservationRepo extends JpaRepository<Reservation, String> {
    Iterable<Reservation> findByLogin(String login); //find specific reservation by login

    int countByLectureid(String id);    //count reservations by lecture id

    void deleteByLectureidAndLogin(String id, String login); //delete specific reservation
}
