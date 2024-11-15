package steyn91.Schedule.models;

import jakarta.persistence.*;

@Entity
@Table(name = "teachers")
public class Professor {

    @Id @GeneratedValue(strategy = GenerationType.AUTO) int id;
    String fio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public Professor(String fio) {
        this.fio = fio;
    }

    public Professor() {
    }
}
