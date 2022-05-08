package pl.gerwant.itconferencemanager.dao.entities;

import javax.persistence.*;

@Entity
public class Reservation {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    private String lectureid;
    private String login;
    private String email;
    private int lecturetopic;

    public Reservation() {
    }

    public Reservation(int id, String lectureid, String login, String email, int lecturetopic) {
        this.id = id;
        this.lectureid = lectureid;
        this.login = login;
        this.email = email;
        this.lecturetopic = lecturetopic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLectureid() {
        return lectureid;
    }

    public void setLectureid(String lectureid) {
        this.lectureid = lectureid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLecturetopic() {
        return lecturetopic;
    }

    public void setLecturetopic(int lecturetopic) {
        this.lecturetopic = lecturetopic;
    }
}
