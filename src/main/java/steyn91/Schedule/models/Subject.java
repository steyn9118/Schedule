package steyn91.Schedule.models;

import jakarta.persistence.*;

@Entity
@Table(name = "`subjects`")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Subject(String name) {
        this.name = name;
    }

    public Subject(){}
}
