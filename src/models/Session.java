public class Session {
    private Student student1;
    private Student student2;
    private String dayOfWeek;
    private String timeSlot;
    private boolean confirmed;

    public Session(Student student1, Student student2, String dayOfWeek, String timeSlot) {
        this.student1 = student1;
        this.student2 = student2;
        this.dayOfWeek = dayOfWeek;
        this.timeSlot = timeSlot;
        this.confirmed = false;
    }

    public void confirm() {
        this.confirmed = true;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    @Override
    public String toString() {
        return "Session: " + student1.getName() + " & " + student2.getName() +
                " on " + dayOfWeek + " at " + timeSlot +
                (confirmed ? " [CONFIRMED]" : " [PENDING]");
    }
}