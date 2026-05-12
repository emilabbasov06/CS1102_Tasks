import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CourseManagement {
    private static List<Course> courses = new ArrayList<>();

    public static void addCourse(String code, String name, int capacity) {
        Course newCourse = new Course(code, name, capacity);
        courses.add(newCourse);
    }

    public static void enrollStudent(Student student, Course course) {
        if (course.hasSpace()) {
            student.enrollInCourse(course);
            course.addStudent();
        } else {
            System.out.println("Error: Course reached maximum capacity.");
        }
    }

    public static void assignGrade(Student student, Course course, int grade) {
        student.assignGrade(course, grade);
    }

    public static double calculateOverallGrade(Student student) {
        Map<Course, Integer> grades = student.getGrades();
        if (grades.isEmpty()) {
            return 0.0;
        }
        double sum = 0;
        for (int grade : grades.values()) {
            sum += grade;
        }
        return sum / grades.size();
    }

    public static List<Course> getCourses() {
        return courses;
    }
}