import models.*;
import services.*;

import java.util.*;

public class Main {
    private static List<Student> students = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);
    private static MatchService matchService = new MatchService();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("\nStudy Buddy Menu:");
            System.out.println("1. Create Profile");
            System.out.println("2. Add Course");
            System.out.println("3. Add Availability");
            System.out.println("4. Find Matches");
            System.out.println("5. Exit");
            System.out.print("Choice: ");
            int choice = sc.nextInt(); sc.nextLine();

            switch (choice) {
                case 1 -> createProfile();
                case 2 -> addCourse();
                case 3 -> addAvailability();
                case 4 -> findMatches();
                case 5 -> running = false;
            }
        }
    }

    private static void createProfile() {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        students.add(new Student(name, email));
        System.out.println("Profile created.");
    }

    private static Student pickStudent() {
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i+1) + ". " + students.get(i));
        }
        System.out.print("Select student #: ");
        int idx = sc.nextInt(); sc.nextLine();
        return students.get(idx - 1);
    }

    private static void addCourse() {
        Student s = pickStudent();
        System.out.print("Enter course code: ");
        String course = sc.nextLine();
        s.addCourse(course);
        System.out.println("Course added.");
    }

    private static void addAvailability() {
        Student s = pickStudent();
        System.out.print("Enter day: ");
        String day = sc.nextLine();
        System.out.print("Enter start time (HH:MM): ");
        String start = sc.nextLine();
        System.out.print("Enter end time (HH:MM): ");
        String end = sc.nextLine();
        s.addAvailability(day, start, end);
        System.out.println("Availability added.");
    }

    private static void findMatches() {
        Student s = pickStudent();
        List<Student> matches = matchService.findMatches(s, students);
        System.out.println("Matches for " + s.getName() + ":");
        for (Student m : matches) {
            System.out.println(" - " + m.getName() + " (" + m.getCourses() + ")");
        }
    }
}
