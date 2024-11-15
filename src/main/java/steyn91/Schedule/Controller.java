package steyn91.Schedule;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import steyn91.Schedule.models.*;
import steyn91.Schedule.repos.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@org.springframework.stereotype.Controller
public class Controller {

    // REPOS

    @Autowired
    private GroupsRepo groupsRepo;
    @Autowired
    private LessonsRepo lessonsRepo;
    @Autowired
    private ProfessorsRepo professorsRepo;
    @Autowired
    private RoomsRepo roomsRepo;
    @Autowired
    private StudentsRepo studentsRepo;
    @Autowired
    private SubjectsRepo subjectsRepo;


    // GET

    @PostMapping("/")
    public String getHome() {
        return "home";
    }

    @GetMapping("/schedule/group/{id}")
    public String getScheduleByGroup(@PathVariable(value = "id") Integer id, Model model) {
        Iterable<Lesson> lessons = StreamSupport.stream(lessonsRepo.findAll().spliterator(), false).filter(lesson -> {
            return lesson.getGroup().getId() == id;
        }).toList();

        int i = 1;
        for (LocalDate day : getDaysOfTheWeek()){
            List<Lesson> currentDayLessons = new ArrayList<>();
            currentDayLessons.addAll(StreamSupport.stream(lessons.spliterator(), false).filter(lesson -> {
                return lesson.getDateTime().getDayOfWeek().equals(day.getDayOfWeek());
            }).toList());
            model.addAttribute("lessons" + i, currentDayLessons);
            model.addAttribute("date" + i, day.toString());
            i++;
        }

        model.addAttribute("group", groupsRepo.findById(id).orElseThrow());

        return "group_schedule";
    }

    @GetMapping("/schedule/professor/{id}")
    public String getScheduleByProfessor(@PathVariable(value = "id") Integer id, Model model) {
        Iterable<Lesson> lessons = StreamSupport.stream(lessonsRepo.findAll().spliterator(), false).filter(lesson -> {
            return lesson.getProfessor().getId() == id;
        }).toList();

        int i = 1;
        for (LocalDate day : getDaysOfTheWeek()){
            List<Lesson> currentDayLessons = new ArrayList<>();
            currentDayLessons.addAll(StreamSupport.stream(lessons.spliterator(), false).filter(lesson -> {
                return lesson.getDateTime().getDayOfWeek().equals(day.getDayOfWeek());
            }).toList());
            model.addAttribute("lessons" + i, currentDayLessons);
            model.addAttribute("date" + i, day.toString());
            i++;
        }

        model.addAttribute("professor", professorsRepo.findById(id).orElseThrow());

        return "professor_schedule";
    }

    @GetMapping("/groups")
    public String getGroups(Model model) {
        Iterable<Group> groups = groupsRepo.findAll();
        model.addAttribute("groups", groups);
        return "groups";
    }

    @GetMapping("/professors")
    public String getProfessors(Model model) {
        Iterable<Professor> professors = professorsRepo.findAll();
        model.addAttribute("professors", professors);
        return "professors";
    }

    @GetMapping("/adder")
    public String getAdder(Model model) {
        Iterable<Subject> subjects = subjectsRepo.findAll();
        Iterable<Professor> professors = professorsRepo.findAll();
        Iterable<Group> groups = groupsRepo.findAll();
        Iterable<Room> rooms = StreamSupport.stream(roomsRepo.findAll().spliterator(), false).filter(Room::isAvailable).toList();
        model.addAttribute("rooms", rooms);
        model.addAttribute("subjects", subjects);
        model.addAttribute("professors", professors);
        model.addAttribute("groups", groups);
        return "adder";
    }

    @GetMapping("/remover")
    public String getRemover(Model model) {
        Iterable<Subject> subjects = subjectsRepo.findAll();
        Iterable<Professor> professors = professorsRepo.findAll();
        Iterable<Group> groups = groupsRepo.findAll();
        Iterable<Room> rooms = roomsRepo.findAll();
        Iterable<Lesson> lessons = lessonsRepo.findAll();
        model.addAttribute("rooms", rooms);
        model.addAttribute("subjects", subjects);
        model.addAttribute("professors", professors);
        model.addAttribute("groups", groups);
        model.addAttribute("lessons", lessons);
        return "remover";
    }

    @GetMapping("/editor")
    public String getEditor(Model model) {
        Iterable<Subject> subjects = subjectsRepo.findAll();
        Iterable<Professor> professors = professorsRepo.findAll();
        Iterable<Group> groups = groupsRepo.findAll();
        Iterable<Room> rooms = roomsRepo.findAll();
        Iterable<Lesson> lessons = lessonsRepo.findAll();
        model.addAttribute("rooms", rooms);
        model.addAttribute("subjects", subjects);
        model.addAttribute("professors", professors);
        model.addAttribute("groups", groups);
        model.addAttribute("lessons", lessons);
        return "editor";
    }

    // POST

    @PostMapping("/add/group")
    public String addGroup(@RequestParam String name){
        Group group = new Group(name);
        groupsRepo.save(group);
        return "redirect:/adder";
    }

    @PostMapping("/add/professor")
    public String addProfessor(@RequestParam String fio){
        Professor professor = new Professor(fio);
        professorsRepo.save(professor);
        return "redirect:/adder";
    }

    @PostMapping("/add/room")
    public String addRoom(@RequestParam Integer audienceLimit, @RequestParam String name, @RequestParam(required = false) String available){
        boolean realAvailable;
        if (available == null) realAvailable = false;
        else realAvailable = true;
        Room room = new Room(audienceLimit, name, realAvailable);
        roomsRepo.save(room);
        return "redirect:/adder";
    }

    @PostMapping("/add/subject")
    public String addSubject(@RequestParam String name){
        Subject subject = new Subject(name);
        subjectsRepo.save(subject);
        return "redirect:/adder";
    }

    @PostMapping("/add/lesson")
    public String addLesson(@RequestParam Integer professorId, @RequestParam Integer roomId, @RequestParam Integer subjectId, @RequestParam Integer groupId, @RequestParam String dateTime){
        Lesson lesson = new Lesson(
                groupsRepo.findById(groupId).orElseThrow(),
                subjectsRepo.findById(subjectId).orElseThrow(),
                roomsRepo.findById(roomId).orElseThrow(),
                professorsRepo.findById(professorId).orElseThrow(),
                LocalDateTime.parse(dateTime)
        );
        lessonsRepo.save(lesson);
        return "redirect:/adder";
    }

    // Удаление

    @PostMapping("/remove/group")
    public String removeGroup(@RequestParam Integer id){
        groupsRepo.deleteById(id);
        return "redirect:/remover";
    }

    @PostMapping("/remove/subject")
    public String removeSubject(@RequestParam Integer id){
        subjectsRepo.deleteById(id);
        return "redirect:/remover";
    }

    @PostMapping("/remove/professor")
    public String removeProfessor(@RequestParam Integer id){
        professorsRepo.deleteById(id);
        return "redirect:/remover";
    }

    @PostMapping("/remove/room")
    public String removeRoom(@RequestParam Integer id){
        roomsRepo.deleteById(id);
        return "redirect:/remover";
    }

    @PostMapping("/remove/lesson")
    public String removeLesson(@RequestParam Integer id){
        lessonsRepo.deleteById(id);
        return "redirect:/remover";
    }

    // Редактирование

    @PostMapping("/update/group")
    public String updateGroup(@RequestParam Integer id, @RequestParam String name){
        Group group = groupsRepo.findById(id).orElseThrow();
        group.setName(name);
        groupsRepo.save(group);
        return "redirect:/editor";
    }

    @PostMapping("/update/professor")
    public String updateProfessor(@RequestParam Integer id, @RequestParam String fio){
        Professor professor = professorsRepo.findById(id).orElseThrow();
        professor.setFio(fio);
        professorsRepo.save(professor);
        return "redirect:/editor";
    }

    @PostMapping("/update/room")
    public String updateRoom(@RequestParam Integer id, @RequestParam Integer audienceLimit, @RequestParam String name, @RequestParam(required = false) String available){
        boolean realAvailable = available != null;
        Room room = roomsRepo.findById(id).orElseThrow();
        room.setAvailable(realAvailable);
        room.setAudienceLimit(audienceLimit);
        room.setName(name);
        roomsRepo.save(room);
        return "redirect:/editor";
    }

    @PostMapping("/update/subject")
    public String updateSubject(@RequestParam Integer id, @RequestParam String name){
        Subject subject = subjectsRepo.findById(id).orElseThrow();
        subject.setName(name);
        subjectsRepo.save(subject);
        return "redirect:/editor";
    }

    @PostMapping("/update/lesson")
    public String updateLesson(@RequestParam Integer id, @RequestParam Integer professorId, @RequestParam Integer roomId, @RequestParam Integer subjectId, @RequestParam Integer groupId, @RequestParam String dateTime){
        Lesson lesson = lessonsRepo.findById(id).orElseThrow();
        lesson.setGroup(groupsRepo.findById(groupId).orElseThrow());
        lesson.setRoom(roomsRepo.findById(roomId).orElseThrow());
        lesson.setProfessor(professorsRepo.findById(professorId).orElseThrow());
        lesson.setSubject(subjectsRepo.findById(subjectId).orElseThrow());
        lesson.setDateTime(LocalDateTime.parse(dateTime));
        lessonsRepo.save(lesson);
        return "redirect:/editor";
    }

    // Utils

    private List<LocalDate> getDaysOfTheWeek(){
        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(DayOfWeek.MONDAY);
        List<LocalDate> weekDays = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            weekDays.add(monday.plusDays(i));
        }
        return weekDays;
    }
}
