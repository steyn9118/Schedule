package steyn91.Schedule.models;

import jakarta.persistence.*;

@Entity
@Table(name = "`groups`")
public class Group {

    @Id @GeneratedValue(strategy = GenerationType.AUTO) private int id;
    private String name;
    @Column(name = "studentsAmount") private Integer studentsAmount;

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

    public int getStudentsAmount() {
        return studentsAmount;
    }

    public void setStudentsAmount(int studentsAmount) {
        this.studentsAmount = studentsAmount;
    }

    public Group(String name){
        this.name = name;
        studentsAmount = 0;
    }

    public Group(){}
}
