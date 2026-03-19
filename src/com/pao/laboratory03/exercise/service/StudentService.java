package com.pao.laboratory03.exercise.service;
import com.pao.laboratory03.exercise.model.Student;
import com.pao.laboratory03.exercise.model.Subject;
import com.pao.laboratory03.exercise.exception.StudentNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StudentService {
    private static StudentService instance;
    private List<Student> students;

    private StudentService() {
        students = new ArrayList<>();
    }

    public static StudentService getInstance() {
        if (instance == null) {
            instance = new StudentService();
        }
        return instance;
    }

    public void addStudent(String name, int age) {
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(name)) {
                throw new RuntimeException("Studentul exista deja: " + name);
            }
        }
        students.add(new Student(name, age));
    }

    public Student findByName(String name) {
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        throw new StudentNotFoundException("Studentul nu exista " + name);
    }

    public void addGrade(String studentName, Subject subject, double grade) {
        Student s = findByName(studentName);
        s.addGrade(subject, grade);
    }

    public void printAllStudents() {
        if (students.isEmpty()) {
            System.out.println("Nu exista studenti");
            return;
        }
        for (Student s : students) {
            System.out.println(s);
            System.out.println(" Note: " + s.getGrades());
        }
    }

    public void printTopStudents() {
        if (students.isEmpty()) {
            System.out.println("Nu exista studenti");
            return;
        }
        List<Student> sortat = new ArrayList<>(students);
        sortat.sort((a, b) -> Double.compare(b.getAverage(), a.getAverage()));
        for (Student s : sortat) {
            System.out.println(s);
        }
    }

    public Map<Subject, Double> getAveragePerSubject() {
        Map<Subject, Double> sum = new HashMap<>();
        Map<Subject, Integer> count = new HashMap<>();
        for (Student s : students) {
            for (Map.Entry<Subject, Double> entry : s.getGrades().entrySet()) {
                Subject materie = entry.getKey();
                double nota = entry.getValue();

                sum.put(materie, sum.getOrDefault(materie, 0.0) + nota);
                count.put(materie, count.getOrDefault(materie, 0) + 1);
            }
        }
        Map<Subject, Double> result = new HashMap<>();
        for (Subject sub : sum.keySet()) {
            result.put(sub, sum.get(sub) / count.get(sub));
        }
        return result;
    }

}
