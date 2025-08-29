package services;

import models.*;

import java.util.ArrayList;
import java.util.List;

public class MatchService {

    public List<Student> findMatches(Student current, List<Student> allStudents) {
        List<Student> matches = new ArrayList<>();

        for (Student other : allStudents) {
            if (other == current) continue;

            boolean sharedCourse = current.getCourses().stream()
                    .anyMatch(c1 -> other.getCourses().stream()
                            .anyMatch(c2 -> c1.getCourseCode().equalsIgnoreCase(c2.getCourseCode())));

            boolean overlap = current.getAvailability().stream()
                    .anyMatch(a1 -> other.getAvailability().stream()
                            .anyMatch(a2 -> a1.getDayOfWeek().equalsIgnoreCase(a2.getDayOfWeek())));

            if (sharedCourse && overlap) {
                matches.add(other);
            }
        }

        return matches;
    }
}