package pl.gerwant.itconferencemanager.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.gerwant.itconferencemanager.dao.entities.Reservation;

import javax.transaction.Transactional;

public interface ReservationRepo extends JpaRepository<Reservation, String> {
    Iterable<Reservation> findByLogin(String login);
    int countByLectureid(String id);
    void deleteByLectureidAndLogin(String id, String login);

    int countByStarthourAndLogin(int starthour, String login);
}
