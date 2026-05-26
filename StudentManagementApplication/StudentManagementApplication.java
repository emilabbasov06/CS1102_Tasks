package com.management.system;

import javafx.application.Application;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class StudentManagementApplication extends Application {

    public static class Student {
        private final StringProperty studentId;
        private final StringProperty studentName;
        private final ObservableList<Course> enrolledCourses;
        private final Map<String, DoubleProperty> courseGrades;

        public Student(String studentId, String studentName) {
            this.studentId = new SimpleStringProperty(this, "studentId", studentId);
            this.studentName = new SimpleStringProperty(this, "studentName", studentName);
            this.enrolledCourses = FXCollections.observableArrayList();
            this.courseGrades = new HashMap<>();
        }

        public StringProperty studentIdProperty() { return studentId; }
        public StringProperty studentNameProperty() { return studentName; }
        public String getStudentId() { return studentId.get(); }
        public String getStudentName() { return studentName.get(); }
        public ObservableList<Course> getEnrolledCourses() { return enrolledCourses; }
        public Map<String, DoubleProperty> getCourseGrades() { return courseGrades; }

        public void enrollInCourse(Course course) {
            if (!enrolledCourses.contains(course)) {
                enrolledCourses.add(course);
                courseGrades.put(course.getCourseCode(), new SimpleDoubleProperty(this, "grade", -1.0));
            }
        }

        public void assignGrade(String courseCode, double grade) {
            if (courseGrades.containsKey(courseCode)) {
                courseGrades.get(courseCode).set(grade);
            }
        }

        @Override
        public String toString() { return studentName.get() + " (" + studentId.get() + ")"; }
    }

    public static class Course {
        private final StringProperty courseCode;
        private final StringProperty courseName;
        private final ObservableList<Student> enrolledStudents;

        public Course(String courseCode, String courseName) {
            this.courseCode = new SimpleStringProperty(this, "courseCode", courseCode);
            this.courseName = new SimpleStringProperty(this, "courseName", courseName);
            this.enrolledStudents = FXCollections.observableArrayList();
        }

        public StringProperty courseCodeProperty() { return courseCode; }
        public StringProperty courseNameProperty() { return courseName; }
        public String getCourseCode() { return courseCode.get(); }
        public String getCourseName() { return courseName.get(); }
        public ObservableList<Student> getEnrolledStudents() { return enrolledStudents; }

        public void addStudent(Student student) {
            if (!enrolledStudents.contains(student)) {
                enrolledStudents.add(student);
            }
        }

        @Override
        public String toString() { return courseName.get() + " (" + courseCode.get() + ")"; }
    }

    private final ObservableList<Student> referenceStudentMasterList = FXCollections.observableArrayList();
    private final ObservableList<Course> referenceCourseMasterList = FXCollections.observableArrayList();

    private TableView<Student> mainDashboardTableView;
    private ListView<Student> courseEligibilityListView;
    private ListView<Course> gradeOverviewListView;

    private ComboBox<Course> generalCourseSelectionComboBox;
    private ComboBox<Student> generalStudentSelectionComboBox;
    private ComboBox<Course> transactionalGradeCourseComboBox;

    private TextField inputFieldStudentId;
    private TextField inputFieldStudentName;
    private TextField inputFieldUpdateName;
    private TextField inputFieldGradeValue;

    @Override
    public void start(Stage primaryStage) {
        initializeMockDatabaseLayer();

        BorderPane containerRootNode = new BorderPane();
        containerRootNode.setPadding(new Insets(15));

        HBox topNavigationBarControlLayout = createTopOperationalTabBar();
        containerRootNode.setTop(topNavigationBarControlLayout);

        VBox leftDataEntryFormLayout = createUnifiedFormInputColumn();
        containerRootNode.setLeft(leftDataEntryFormLayout);

        VBox centerDataDisplayLayout = createCentralDashboardVisualizer();
        containerRootNode.setCenter(centerDataDisplayLayout);

        Scene primaryApplicationScene = new Scene(containerRootNode, 1150, 700);
        primaryStage.setTitle("Student Management System Architecture");
        primaryStage.setScene(primaryApplicationScene);
        primaryStage.show();
    }

    private void initializeMockDatabaseLayer() {
        Course infoTechCourse = new Course("IT-202", "Information Technologies");
        Course compSciCourse = new Course("CS-110", "Computer Science");
        referenceCourseMasterList.addAll(infoTechCourse, compSciCourse);

        Student studentAlpha = new Student("2026001", "Farid Aliyev");
        Student studentBeta = new Student("2026002", "Emin Mammadov");
        
        referenceStudentMasterList.addAll(studentAlpha, studentBeta);
    }

    private HBox createTopOperationalTabBar() {
        HBox structuralTopContainer = new HBox(15);
        structuralTopContainer.setPadding(new Insets(0, 0, 15, 0));

        Label applicationTitleHeaderLabel = new Label("Administrator Dashboard System");
        applicationTitleHeaderLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        structuralTopContainer.getChildren().add(applicationTitleHeaderLabel);
        return structuralTopContainer;
    }

    private VBox createUnifiedFormInputColumn() {
        VBox columnVerticalContainer = new VBox(20);
        columnVerticalContainer.setPadding(new Insets(0, 15, 0, 0));
        columnVerticalContainer.setPrefWidth(350);

        TitledPane panelAddStudentForm = new TitledPane("Register New Student", createRegistrationGridForm());
        panelAddStudentForm.setCollapsible(false);

        TitledPane panelUpdateStudentForm = new TitledPane("Modify Existing Record", createModificationGridForm());
        panelUpdateStudentForm.setCollapsible(false);

        TitledPane panelCourseEnrollmentForm = new TitledPane("Course Enrollment Pipeline", createEnrollmentGridForm());
        panelCourseEnrollmentForm.setCollapsible(false);

        TitledPane panelGradeManagementForm = new TitledPane("Academic Grade Assignment", createGradeGridForm());
        panelGradeManagementForm.setCollapsible(false);

        columnVerticalContainer.getChildren().addAll(
                panelAddStudentForm, 
                panelUpdateStudentForm, 
                panelCourseEnrollmentForm, 
                panelGradeManagementForm
        );
        return columnVerticalContainer;
    }

    private GridPane createRegistrationGridForm() {
        GridPane elementGridContainer = new GridPane();
        elementGridContainer.setHgap(8);
        elementGridContainer.setVgap(8);
        elementGridContainer.setPadding(new Insets(5));

        inputFieldStudentId = new TextField();
        inputFieldStudentName = new TextField();
        Button actionButtonSubmitRegistration = new Button("Add Student");

        elementGridContainer.add(new Label("Student ID:"), 0, 0);
        elementGridContainer.add(inputFieldStudentId, 1, 0);
        elementGridContainer.add(new Label("Full Name:"), 0, 1);
        elementGridContainer.add(inputFieldStudentName, 1, 1);
        elementGridContainer.add(actionButtonSubmitRegistration, 1, 2);

        actionButtonSubmitRegistration.setOnAction(e -> executeStudentRegistrationTransaction());
        return elementGridContainer;
    }

    private GridPane createModificationGridForm() {
        GridPane elementGridContainer = new GridPane();
        elementGridContainer.setHgap(8);
        elementGridContainer.setVgap(8);
        elementGridContainer.setPadding(new Insets(5));

        generalStudentSelectionComboBox = new ComboBox<>(referenceStudentMasterList);
        generalStudentSelectionComboBox.setPromptText("Select Student");
        inputFieldUpdateName = new TextField();
        Button actionButtonSubmitModification = new Button("Update Name");

        elementGridContainer.add(new Label("Target:"), 0, 0);
        elementGridContainer.add(generalStudentSelectionComboBox, 1, 0);
        elementGridContainer.add(new Label("New Name:"), 0, 1);
        elementGridContainer.add(inputFieldUpdateName, 1, 1);
        elementGridContainer.add(actionButtonSubmitModification, 1, 2);

        actionButtonSubmitModification.setOnAction(e -> executeStudentModificationTransaction());
        return elementGridContainer;
    }

    private GridPane createEnrollmentGridForm() {
        GridPane elementGridContainer = new GridPane();
        elementGridContainer.setHgap(8);
        elementGridContainer.setVgap(8);
        elementGridContainer.setPadding(new Insets(5));

        generalCourseSelectionComboBox = new ComboBox<>(referenceCourseMasterList);
        generalCourseSelectionComboBox.setPromptText("Select Target Course");
        courseEligibilityListView = new ListView<>();
        courseEligibilityListView.setPrefHeight(100);
        Button actionButtonSubmitEnrollment = new Button("Enroll Selected Student");

        elementGridContainer.add(new Label("Course:"), 0, 0);
        elementGridContainer.add(generalCourseSelectionComboBox, 1, 0);
        elementGridContainer.add(new Label("Eligible:"), 0, 1);
        elementGridContainer.add(courseEligibilityListView, 1, 1);
        elementGridContainer.add(actionButtonSubmitEnrollment, 1, 2);

        generalCourseSelectionComboBox.setOnAction(e -> refreshEligibilitySelectionMatrix());
        actionButtonSubmitEnrollment.setOnAction(e -> executeCourseEnrollmentTransaction());
        return elementGridContainer;
    }

    private GridPane createGradeGridForm() {
        GridPane elementGridContainer = new GridPane();
        elementGridContainer.setHgap(8);
        elementGridContainer.setVgap(8);
        elementGridContainer.setPadding(new Insets(5));

        ComboBox<Student> academicGradeStudentComboBox = new ComboBox<>(referenceStudentMasterList);
        academicGradeStudentComboBox.setPromptText("Select Student");
        transactionalGradeCourseComboBox = new ComboBox<>();
        transactionalGradeCourseComboBox.setPromptText("Select Course");
        inputFieldGradeValue = new TextField();
        Button actionButtonSubmitGradeAllocation = new Button("Assign Grade");

        elementGridContainer.add(new Label("Student:"), 0, 0);
        elementGridContainer.add(academicGradeStudentComboBox, 1, 0);
        elementGridContainer.add(new Label("Course:"), 0, 1);
        elementGridContainer.add(transactionalGradeCourseComboBox, 1, 1);
        elementGridContainer.add(new Label("Score:"), 0, 2);
        elementGridContainer.add(inputFieldGradeValue, 1, 2);
        elementGridContainer.add(actionButtonSubmitGradeAllocation, 1, 3);

        academicGradeStudentComboBox.setOnAction(e -> {
            Student selectedStudent = academicGradeStudentComboBox.getSelectionModel().getSelectedItem();
            if (selectedStudent != null) {
                transactionalGradeCourseComboBox.setItems(selectedStudent.getEnrolledCourses());
                gradeOverviewListView.setItems(selectedStudent.getEnrolledCourses());
            }
        });

        actionButtonSubmitGradeAllocation.setOnAction(e -> executeGradeAllocationTransaction(academicGradeStudentComboBox.getSelectionModel().getSelectedItem()));
        return elementGridContainer;
    }

    private VBox createCentralDashboardVisualizer() {
        VBox dashboardVerticalLayoutContainer = new VBox(15);
        dashboardVerticalLayoutContainer.setPadding(new Insets(0, 0, 0, 10));

        Label tableTitleLabel = new Label("Master Student Directory View");
        tableTitleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        mainDashboardTableView = new TableView<>(referenceStudentMasterList);
        mainDashboardTableView.setPrefHeight(300);

        TableColumn<Student, String> operationalColumnId = new TableColumn<>("Registration ID");
        operationalColumnId.setCellValueFactory(cellData -> cellData.getValue().studentIdProperty());
        operationalColumnId.setMinWidth(120);

        TableColumn<Student, String> operationalColumnName = new TableColumn<>("Student Full Name");
        operationalColumnName.setCellValueFactory(cellData -> cellData.getValue().studentNameProperty());
        operationalColumnName.setMinWidth(250);

        mainDashboardTableView.getColumns().add(operationalColumnId);
        mainDashboardTableView.getColumns().add(operationalColumnName);

        Label gradesTitleLabel = new Label("Selected Student Course & Grade Manifest");
        gradesTitleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        gradeOverviewListView = new ListView<>();
        gradeOverviewListView.setPrefHeight(180);
        setCustomGradeCellFactory();

        mainDashboardTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                gradeOverviewListView.setItems(newSelection.getEnrolledCourses());
            }
        });

        dashboardVerticalLayoutContainer.getChildren().addAll(
                tableTitleLabel, 
                mainDashboardTableView, 
                gradesTitleLabel, 
                gradeOverviewListView
        );
        return dashboardVerticalLayoutContainer;
    }

    private void setCustomGradeCellFactory() {
        gradeOverviewListView.setCellFactory(columnParameter -> new ListCell<>() {
            @Override
            protected void updateItem(Course targetCourseItem, boolean logicalEmptyFlag) {
                super.updateItem(targetCourseItem, logicalEmptyFlag);
                if (logicalEmptyFlag || targetCourseItem == null) {
                    setText(null);
                } else {
                    Student activeStudentFocus = mainDashboardTableView.getSelectionModel().getSelectedItem();
                    if (activeStudentFocus != null) {
                        double extractedGrade = activeStudentFocus.getCourseGrades().get(targetCourseItem.getCourseCode()).get();
                        String formattedGradeString = (extractedGrade < 0) ? "Ungraded" : String.format("%.2f", extractedGrade);
                        setText(targetCourseItem.getCourseName() + " [" + targetCourseItem.getCourseCode() + "] -> Grade: " + formattedGradeString);
                    }
                }
            }
        });
    }

    private void refreshEligibilitySelectionMatrix() {
        Course currentlySelectedCourse = generalCourseSelectionComboBox.getSelectionModel().getSelectedItem();
        if (currentlySelectedCourse == null) {
            courseEligibilityListView.setItems(FXCollections.emptyObservableList());
            return;
        }
        ObservableList<Student> conditionalEligibleStudents = FXCollections.observableArrayList();
        for (Student singleStudentIterationNode : referenceStudentMasterList) {
            if (!singleStudentIterationNode.getEnrolledCourses().contains(currentlySelectedCourse)) {
                conditionalEligibleStudents.add(singleStudentIterationNode);
            }
        }
        courseEligibilityListView.setItems(conditionalEligibleStudents);
    }

    private void executeStudentRegistrationTransaction() {
        String inputIdString = inputFieldStudentId.getText().trim();
        String inputNameString = inputFieldStudentName.getText().trim();

        if (inputIdString.isEmpty() || inputNameString.isEmpty()) {
            triggerSystemWarningAlert("Registration Failure", "Input Field Violation Detected", "All input text entries must be filled.");
            return;
        }

        for (Student trackingNode : referenceStudentMasterList) {
            if (trackingNode.getStudentId().equals(inputIdString)) {
                triggerSystemWarningAlert("Registration Failure", "Primary Key Collision", "A student record already exists with ID: " + inputIdString);
                return;
            }
        }

        Student unifiedNewStudentInstance = new Student(inputIdString, inputNameString);
        referenceStudentMasterList.add(unifiedNewStudentInstance);
        
        inputFieldStudentId.clear();
        inputFieldStudentName.clear();
        refreshEligibilitySelectionMatrix();
    }

    private void executeStudentModificationTransaction() {
        Student focusedTargetStudent = generalStudentSelectionComboBox.getSelectionModel().getSelectedItem();
        String targetRevisedName = inputFieldUpdateName.getText().trim();

        if (focusedTargetStudent == null || targetRevisedName.isEmpty()) {
            triggerSystemWarningAlert("Modification Failure", "Execution Dependency Omission", "Specify a valid student target and enter a modified name.");
            return;
        }

        focusedTargetStudent.studentNameProperty().set(targetRevisedName);
        inputFieldUpdateName.clear();
        mainDashboardTableView.refresh();
        gradeOverviewListView.refresh();
    }

    private void executeCourseEnrollmentTransaction() {
        Course activeTargetCourse = generalCourseSelectionComboBox.getSelectionModel().getSelectedItem();
        Student activeTargetStudent = courseEligibilityListView.getSelectionModel().getSelectedItem();

        if (activeTargetCourse == null || activeTargetStudent == null) {
            triggerSystemWarningAlert("Enrollment Failure", "Invalid Entity Selection", "Select an active course alongside an eligible candidate.");
            return;
        }

        activeTargetStudent.enrollInCourse(activeTargetCourse);
        activeTargetCourse.addStudent(activeTargetStudent);
        
        refreshEligibilitySelectionMatrix();
        gradeOverviewListView.refresh();
    }

    private void executeGradeAllocationTransaction(Student activeTargetStudent) {
        Course activeTargetCourse = transactionalGradeCourseComboBox.getSelectionModel().getSelectedItem();
        String continuousRawGradeString = inputFieldGradeValue.getText().trim();

        if (activeTargetStudent == null || activeTargetCourse == null || continuousRawGradeString.isEmpty()) {
            triggerSystemWarningAlert("Grade Input Violation", "Missing Parameter Dependencies", "Ensure target fields contain references.");
            return;
        }

        try {
            double parsedGradeMetricValue = Double.parseDouble(continuousRawGradeString);
            if (parsedGradeMetricValue < 0.0 || parsedGradeMetricValue > 100.0) {
                throw new IllegalArgumentException("Numeric range limit exceeded.");
            }
            activeTargetStudent.assignGrade(activeTargetCourse.getCourseCode(), parsedGradeMetricValue);
            inputFieldGradeValue.clear();
            gradeOverviewListView.refresh();
        } catch (NumberFormatException exceptionContext) {
            triggerSystemWarningAlert("Format Exception Check", "Invalid Character Ingestion", "Grades must be numeric values.");
        } catch (IllegalArgumentException boundaryExceptionContext) {
            triggerSystemWarningAlert("Boundary Exception Check", "Value Constraint Violated", "Grades must fall within the range [0.0 - 100.0].");
        }
    }

    private void triggerSystemWarningAlert(String boxTitle, String internalHeader, String inlineMessage) {
        Alert diagnosticAlertBox = new Alert(Alert.AlertType.WARNING);
        diagnosticAlertBox.setTitle(boxTitle);
        diagnosticAlertBox.setHeaderText(internalHeader);
        diagnosticAlertBox.setContentText(inlineMessage);
        diagnosticAlertBox.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}