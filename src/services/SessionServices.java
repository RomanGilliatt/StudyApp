package services;

import models.*;

import java.util.ArrayList;
import java.util.List;

public class SessionServices {
    private List<Session> sessions;

    public SessionServices() {
        this.sessions = new ArrayList<>();
    }

    //Schedule a new session between two students
    public Session scheduleSession(Student student1, Student student2, String dayOfWeek, String timeSlot) {
        //Check if both students are available at the given time
        boolean student1Available = student1.getAvailability().stream()
                .anyMatch(a -> a.getDayOfWeek().equalsIgnoreCase(dayOfWeek) &&
                        timeSlotOverlaps(a.getStartTime(), a.getEndTime(), timeSlot));

        boolean student2Available = student2.getAvailability().stream()
                .anyMatch(a -> a.getDayOfWeek().equalsIgnoreCase(dayOfWeek) &&
                        timeSlotOverlaps(a.getStartTime(), a.getEndTime(), timeSlot));

        if (student1Available && student2Available) {
            Session session = new Session(student1, student2, dayOfWeek, timeSlot);
            sessions.add(session);
            student1.addSession(session);
            student2.addSession(session);
            return session;
        } else {
            throw new IllegalArgumentException("One or both students are not available at the specified time.");
        }
    }

    //Cancel an existing session
    public void cancelSession(Session session) {
        sessions.remove(session);
        session.student1.cancelSession(session);
        session.student2.cancelSession(session);
    }

    //Get all sessions
    public List<Session> getAllSessions() {
        return new ArrayList<>(sessions);
    }

    //Helper method to check if a time slot overlaps with availability
    private boolean timeSlotOverlaps(String start, String end, String timeSlot) {
        String[] timeParts = timeSlot.split("-");
        String slotStart = timeParts[0];
        String slotEnd = timeParts[1];

        return (slotStart.compareTo(end) < 0 && slotEnd.compareTo(start) > 0);
    }
}