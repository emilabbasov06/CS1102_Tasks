public class Course {
    private String courseCode;
    private String name;
    private int maxCapacity;
    private int currentEnrollment;
    private static int totalEnrolledStudents = 0;

    public Course(String courseCode, String name, int maxCapacity) {
        this.courseCode = courseCode;
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.currentEnrollment = 0;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getName() {
        return name;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getCurrentEnrollment() {
        return currentEnrollment;
    }

    public boolean hasSpace() {
        return currentEnrollment < maxCapacity;
    }

    public void addStudent() {
        currentEnrollment++;
    }

    public static void incrementTotalEnrolled() {
        totalEnrolledStudents++;
    }

    public static int getTotalEnrolledStudents() {
        return totalEnrolledStudents;
    }
}