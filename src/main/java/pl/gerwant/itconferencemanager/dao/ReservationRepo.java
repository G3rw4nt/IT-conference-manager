package pl.gerwant.itconferencemanager.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.gerwant.itconferencemanager.dao.entities.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepo extends JpaRepository<Reservation, String> {
    Iterable<Reservation> findByLogin(String login);
    int countByLectureid(String id);
    void deleteByLectureidAndLogin(String id, String login);
    Reservation findByStarthourAndLogin(int starthour, String login);
}
