package steyn91.Schedule.models;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id @GeneratedValue(strategy = GenerationType.AUTO) int id;
    String fio;
    @ManyToOne @JoinColumn(name = "groupID") Group group;

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



    public Student(String fio, Group group) {
        this.fio = fio;
        this.group = group;
    }

    public Student(){}
}
