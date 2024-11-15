package steyn91.Schedule.models;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "classes")
public class Lesson {

    @Id @GeneratedValue(strategy = GenerationType.AUTO) int id;
    @ManyToOne @OnDelete(action = OnDeleteAction.CASCADE) @JoinColumn(name = "groupID") Group group;
    @ManyToOne @OnDelete(action = OnDeleteAction.CASCADE) @JoinColumn(name = "subjectID") Subject subject;
    @ManyToOne @OnDelete(action = OnDeleteAction.CASCADE) @JoinColumn(name = "roomID") Room room;
    @ManyToOne @OnDelete(action = OnDeleteAction.CASCADE) @JoinColumn(name = "teacherID") Professor professor;
    @Column(name = "dateTime") LocalDateTime dateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getTime(){
        return dateTime.getHour() + ":" + dateTime.getMinute();
    }

    public String asFormattedString(){
        return room.name + " " + subject.name + " " + group.getName() + " (Преподаватель: " + professor.fio + ", Дата: " + dateTime + ")";
    }

    public Lesson(){}

    public Lesson(Group group, Subject subject, Room room, Professor professor, LocalDateTime dateTime) {
        this.group = group;
        this.subject = subject;
        this.room = room;
        this.professor = professor;
        this.dateTime = dateTime;
    }
}
