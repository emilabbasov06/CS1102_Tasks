import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class UniversitySystem {
    private static List<Student> students = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- University Management Menu ---");
            System.out.println("1. Add New Course");
            System.out.println("2. Enroll Student");
            System.out.println("3. Assign Grade");
            System.out.println("4. Calculate Overall Grade");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addNewCourse();
                    break;
                case "2":
                    enrollStudent();
                    break;
                case "3":
                    assignGrade();
                    break;
                case "4":
                    calculateGrade();
                    break;
                case "5":
                    System.out.println("Exiting system.");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void addNewCourse() {
        System.out.print("Enter course code: ");
        String code = scanner.nextLine();
        System.out.print("Enter course name: ");
        String name = scanner.nextLine();
        System.out.print("Enter max capacity: ");
        try {
            int cap = Integer.parseInt(scanner.nextLine());
            CourseManagement.addCourse(code, name, cap);
            System.out.println("Course added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid capacity input.");
        }
    }

    private static void enrollStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        
        Student student = findOrCreateStudent(name, id);
        
        System.out.print("Enter course code: ");
        String code = scanner.nextLine();
        Course course = findCourse(code);

        if (course != null) {
            CourseManagement.enrollStudent(student, course);
            System.out.println("Enrollment processed.");
        } else {
            System.out.println("Course not found.");
        }
    }

    private static void assignGrade() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        Student student = findStudentById(id);

        System.out.print("Enter course code: ");
        String code = scanner.nextLine();
        Course course = findCourse(code);

        if (student != null && course != null) {
            System.out.print("Enter grade: ");
            try {
                int grade = Integer.parseInt(scanner.nextLine());
                CourseManagement.assignGrade(student, course, grade);
                System.out.println("Grade assigned.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid grade input.");
            }
        } else {
            System.out.println("Student or course not found.");
        }
    }

    private static void calculateGrade() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        Student student = findStudentById(id);

        if (student != null) {
            double avg = CourseManagement.calculateOverallGrade(student);
            System.out.println("Overall grade for " + student.getName() + ": " + avg);
        } else {
            System.out.println("Student not found.");
        }
    }

    private static Student findOrCreateStudent(String name, String id) {
        for (Student s : students) {
            if (s.getId().equals(id)) return s;
        }
        Student newStudent = new Student(name, id);
        students.add(newStudent);
        return newStudent;
    }

    private static Student findStudentById(String id) {
        for (Student s : students) {
            if (s.getId().equals(id)) return s;
        }
        return null;
    }

    private static Course findCourse(String code) {
        for (Course c : CourseManagement.getCourses()) {
            if (c.getCourseCode().equals(code)) return c;
        }
        return null;
    }
}