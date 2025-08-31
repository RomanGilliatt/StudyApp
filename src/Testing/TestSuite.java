package Testing;

import models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestSuite {

    private Student alice;
    private Student bob;
    private MatchService matchService;
    private SessionServices sessionServices;

    @BeforeEach
    void setUp() {
        alice = new Student("Alice", "alice@clemson.edu");
        bob = new Student("Bob", "bob@clemson.edu");
        matchService = new MatchService();
        sessionServices = new SessionServices();
    }

    // ---------- Student/Profile ----------
    @Test
    void testProfileCreation() {
        assertEquals("Alice", alice.getName());
        assertEquals("alice@clemson.edu", alice.getEmail());
        assertTrue(alice.getCourses().isEmpty());
    }

    @Test
    void testAddAndRemoveCourse() {
        alice.addCourse("CPSC 2120");
        assertEquals(1, alice.getCourses().size());

        alice.removeCourse("CPSC 2120");
        assertTrue(alice.getCourses().isEmpty());
    }

    @Test
    void testAddAndRemoveAvailability() {
        alice.addAvailability("Monday", "15:00", "17:00");
        assertEquals(1, alice.getAvailability().size());

        alice.removeAvailability("Monday", "15:00", "17:00");
        assertTrue(alice.getAvailability().isEmpty());
    }

    // ---------- Course ----------
    @Test
    void testCourseCreation() {
        Course c = new Course("CPSC 2120");
        assertEquals("CPSC 2120", c.getCourseCode());
        assertEquals("CPSC 2120", c.toString());
    }

    // ---------- Availability ----------
    @Test
    void testAvailabilityCreationAndToString() {
        Availability a = new Availability("Tuesday", "10:00", "12:00");
        assertEquals("Tuesday", a.getDayOfWeek());
        assertEquals("10:00", a.getStartTime());
        assertEquals("12:00", a.getEndTime());
        assertEquals("Tuesday 10:00-12:00", a.toString());
    }

    // ---------- MatchService ----------
    @Test
    void testFindMatchesWithSharedCourseAndOverlap() {
        alice.addCourse("CPSC 2120");
        alice.addAvailability("Monday", "15:00", "17:00");

        bob.addCourse("CPSC 2120");
        bob.addAvailability("Monday", "16:00", "18:00");

        List<Student> matches = matchService.findMatches(alice, List.of(alice, bob));
        assertTrue(matches.contains(bob), "Bob should be suggested as a match");
    }

    @Test
    void testFindMatchesFailsWhenNoSharedCourse() {
        alice.addCourse("CPSC 2120");
        alice.addAvailability("Monday", "15:00", "17:00");

        bob.addCourse("CPSC 2310"); // different course
        bob.addAvailability("Monday", "15:00", "17:00");

        List<Student> matches = matchService.findMatches(alice, List.of(alice, bob));
        assertFalse(matches.contains(bob), "Bob should NOT match without a shared course");
    }

    @Test
    void testFindMatchesFailsWhenNoAvailabilityOverlap() {
        alice.addCourse("CPSC 2120");
        alice.addAvailability("Monday", "15:00", "17:00");

        bob.addCourse("CPSC 2120");
        bob.addAvailability("Tuesday", "15:00", "17:00"); // different day

        List<Student> matches = matchService.findMatches(alice, List.of(alice, bob));
        assertFalse(matches.contains(bob), "Bob should NOT match without overlapping availability");
    }

    // ---------- Session ----------
    @Test
    void testSessionCreationAndConfirmation() {
        Session s = new Session(alice, bob, "Wednesday", "14:00-16:00");
        assertFalse(s.isConfirmed());
        s.confirm();
        assertTrue(s.isConfirmed());
        assertTrue(s.toString().contains("[CONFIRMED]"));
    }

    // ---------- SessionServices ----------
    @Test
    void testScheduleSessionSuccess() {
        alice.addAvailability("Friday", "10:00", "12:00");
        bob.addAvailability("Friday", "10:30", "11:30");

        Session s = sessionServices.scheduleSession(alice, bob, "Friday", "10:30-11:00");
        assertNotNull(s);
        assertEquals(1, sessionServices.getAllSessions().size());
        assertTrue(alice.getSessions().contains(s));
        assertTrue(bob.getSessions().contains(s));
    }

    @Test
    void testScheduleSessionFailsWhenUnavailable() {
        alice.addAvailability("Friday", "10:00", "12:00");
        // Bob has no availability

        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                sessionServices.scheduleSession(alice, bob, "Friday", "10:30-11:00")
        );
        assertEquals("One or both students are not available at the specified time.", ex.getMessage());
    }

    @Test
    void testCancelSessionRemovesFromBothStudents() {
        alice.addAvailability("Friday", "10:00", "12:00");
        bob.addAvailability("Friday", "10:00", "12:00");

        Session s = sessionServices.scheduleSession(alice, bob, "Friday", "10:00-11:00");
        sessionServices.cancelSession(s);

        assertFalse(alice.getSessions().contains(s));
        assertFalse(bob.getSessions().contains(s));
        assertTrue(sessionServices.getAllSessions().isEmpty());
    }
}
