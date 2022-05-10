package pl.gerwant.itconferencemanager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Conference {
    private LocalDate date;
    private List<Lecture> lectures;


    public Conference() {
        this.date = LocalDate.of(2021, 01, 06);
        lectures = new ArrayList<>();
        lectures.add(new Lecture("10_1", LocalTime.of(10, 0), LocalTime.of(11, 45), 105));
        lectures.add(new Lecture("10_2", LocalTime.of(10, 0), LocalTime.of(11, 45), 105));
        lectures.add(new Lecture("10_3", LocalTime.of(10, 0), LocalTime.of(11, 45), 105));

        lectures.add(new Lecture("12_1", LocalTime.of(12, 0), LocalTime.of(13, 45), 105));
        lectures.add(new Lecture("12_2", LocalTime.of(12, 0), LocalTime.of(13, 45), 105));
        lectures.add(new Lecture("12_3", LocalTime.of(12, 0), LocalTime.of(13, 45), 105));

        lectures.add(new Lecture("14_1", LocalTime.of(14, 0), LocalTime.of(15, 45), 105));
        lectures.add(new Lecture("14_2", LocalTime.of(14, 0), LocalTime.of(15, 45), 105));
        lectures.add(new Lecture("14_3", LocalTime.of(14, 0), LocalTime.of(15, 45), 105));

    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }
}
