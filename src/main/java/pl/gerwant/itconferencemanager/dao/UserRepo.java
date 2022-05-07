package pl.gerwant.itconferencemanager.dao;

import org.springframework.data.repository.CrudRepository;
import pl.gerwant.itconferencemanager.dao.entities.User;

public interface UserRepo extends CrudRepository<User, String> {
}
