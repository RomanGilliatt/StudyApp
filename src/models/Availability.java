public class Availability {
    private String dayOfWeek;  // e.g., "Monday"
    private String startTime;  // e.g., "15:00"
    private String endTime;    // e.g., "17:00"

    public Availability(String dayOfWeek, String startTime, String endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDayOfWeek() { return dayOfWeek; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }

    @Override
    public String toString() {
        return dayOfWeek + " " + startTime + "-" + endTime;
    }
}