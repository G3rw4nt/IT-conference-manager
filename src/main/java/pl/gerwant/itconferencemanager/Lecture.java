package pl.gerwant.itconferencemanager;

import java.time.LocalTime;

public class Lecture {
    private String id;
    private LocalTime beginning;
    private LocalTime ending;
    private int duration;

    public Lecture(String id, LocalTime beginning, LocalTime ending, int duration) {
        this.id = id;
        this.beginning = beginning;
        this.ending = ending;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalTime getBeginning() {
        return beginning;
    }

    public void setBeginning(LocalTime beginning) {
        this.beginning = beginning;
    }

    public LocalTime getEnding() {
        return ending;
    }

    public void setEnding(LocalTime ending) {
        this.ending = ending;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
