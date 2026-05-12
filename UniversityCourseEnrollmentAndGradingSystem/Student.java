import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student {
    private String name;
    private String id;
    private List<Course> enrolledCourses;
    private Map<Course, Integer> grades;

    public Student(String name, String id) {
        this.name = name;
        this.id = id;
        this.enrolledCourses = new ArrayList<>();
        this.grades = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void enrollInCourse(Course course) {
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
            Course.incrementTotalEnrolled();
        }
    }

    public void assignGrade(Course course, int grade) {
        if (enrolledCourses.contains(course)) {
            grades.put(course, grade);
        }
    }

    public Map<Course, Integer> getGrades() {
        return grades;
    }
}