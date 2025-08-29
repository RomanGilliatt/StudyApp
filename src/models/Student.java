import java.util.ArrayList;
import java.util.List;

public class Student {
    private String name;
    private String email;
    private List<Course> courses;
    private List<Availability> availability;
    private List<Session> sessions;

    public Student(String name, String email) {
        this.name = name;
        this.email = email;
        this.courses = new ArrayList<>();
        this.availability = new ArrayList<>();
        this.sessions = new ArrayList<>();
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public List<Course> getCourses() { return courses; }
    public List<Availability> getAvailability() { return availability; }
    public List<Session> getSessions() { return sessions; }

    // Profile Management
    public void addCourse(String courseCode) {
        courses.add(new Course(courseCode));
    }

    public void removeCourse(String courseCode) {
        courses.removeIf(c -> c.getCourseCode().equalsIgnoreCase(courseCode));
    }

    // Availability Management
    public void addAvailability(String day, String start, String end) {
        availability.add(new Availability(day, start, end));
    }

    public void removeAvailability(String day, String start, String end) {
        availability.removeIf(a -> a.getDayOfWeek().equalsIgnoreCase(day) &&
                a.getStartTime().equals(start) &&
                a.getEndTime().equals(end));
    }

    // Session Management
    public void addSession(Session s) {
        sessions.add(s);
    }

    public void cancelSession(Session s) {
        sessions.remove(s);
    }

    @Override
    public String toString() {
        return name + " (" + email + "), Courses: " + courses;
    }
}