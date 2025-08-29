public class Course {
    private String courseCode; // e.g., "CPSC 2120"

    public Course(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseCode() {
        return courseCode;
    }

    @Override
    public String toString() {
        return courseCode;
    }
}