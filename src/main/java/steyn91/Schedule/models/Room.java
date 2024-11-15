package steyn91.Schedule.models;

import jakarta.persistence.*;

@Entity
@Table(name = "rooms")
public class Room {

    @Id @GeneratedValue(strategy = GenerationType.AUTO) int id;
    @Column(name = "audienceLimit") int audienceLimit;
    String name;
    boolean available;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAudienceLimit() {
        return audienceLimit;
    }

    public void setAudienceLimit(int audienceLimit) {
        this.audienceLimit = audienceLimit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable() {
        return available;
    }

    public String asFormattedString(){
        if (isAvailable()) return name + " (Доступность: Да, Вместимость: " + audienceLimit + ")";
        else return name + " (Доступность: Нет, Вместимость: " + audienceLimit + ")";
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Room(int audienceLimit, String name, boolean available) {
        this.audienceLimit = audienceLimit;
        this.name = name;
        this.available = available;
    }

    public Room(){}
}
