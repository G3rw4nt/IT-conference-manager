package pl.gerwant.itconferencemanager.dao;

import org.springframework.data.repository.CrudRepository;
import pl.gerwant.itconferencemanager.dao.entities.Reservation;

public interface ReservationRepo extends CrudRepository<Reservation, String> {
    
}
