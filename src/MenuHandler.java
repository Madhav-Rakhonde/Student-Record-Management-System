import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Handles all user interface operations and menu interactions
 * Separates UI logic from business logic
 */
public class MenuHandler {
    private StudentService studentService;
    private Scanner scanner;

    public MenuHandler() {
        this.studentService = new StudentService();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Display welcome message and start main menu loop
     */
    public void start() {
        displayWelcome();

        while (true) {
            displayMainMenu();
            int choice = getValidChoice(1, 8);

            if (!handleMenuChoice(choice)) {
                break; // Exit application
            }

            pauseForUser();
        }
    }

    /**
     * Display welcome message
     */
    private void displayWelcome() {
        System.out.println("=".repeat(60));
        System.out.println("       STUDENT RECORD MANAGEMENT SYSTEM");
        System.out.println("=".repeat(60));
    }

    /**
     * Display main menu options
     */
    private void displayMainMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("           MAIN MENU");
        System.out.println("=".repeat(40));
        System.out.println("1. Add New Student");
        System.out.println("2. View All Students");
        System.out.println("3. View Student by ID");
        System.out.println("4. Update Student");
        System.out.println("5. Delete Student");
        System.out.println("6. Search Students");
        System.out.println("7. Display Statistics");
        System.out.println("8. Exit");
        System.out.println("=".repeat(40));
        System.out.print("Enter your choice (1-8): ");
    }

    /**
     * Handle user menu choice
     * @param choice Menu option selected
     * @return true to continue, false to exit
     */
    private boolean handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                handleAddStudent();
                break;
            case 2:
                handleViewAllStudents();
                break;
            case 3:
                handleViewStudentById();
                break;
            case 4:
                handleUpdateStudent();
                break;
            case 5:
                handleDeleteStudent();
                break;
            case 6:
                handleSearchStudents();
                break;
            case 7:
                handleDisplayStatistics();
                break;
            case 8:
                displayExitMessage();
                return false;
        }
        return true;
    }

    /**
     * Handle adding new student
     */
    private void handleAddStudent() {
        System.out.println("\n" + "-".repeat(30));
        System.out.println("      ADD NEW STUDENT");
        System.out.println("-".repeat(30));

        try {
            System.out.print("Enter student name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter course: ");
            String course = scanner.nextLine().trim();

            System.out.print("Enter marks (0-100): ");
            double marks = Double.parseDouble(scanner.nextLine());

            Student student = studentService.addStudent(name, course, marks);

            System.out.println("\n✓ Student added successfully!");
            System.out.println("Student ID: " + student.getId());
            System.out.println("Details: " + student);

        } catch (NumberFormatException e) {
            System.out.println("Invalid marks! Please enter a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Handle viewing all students
     */
    private void handleViewAllStudents() {
        System.out.println("\n" + "-".repeat(80));
        System.out.println("                           ALL STUDENTS");
        System.out.println("-".repeat(80));

        List<Student> students = studentService.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No students found in the system.");
            return;
        }

        displayStudentTable(students);
        System.out.println("Total students: " + students.size());
    }

    /**
     * Handle viewing student by ID
     */
    private void handleViewStudentById() {
        System.out.println("\n" + "-".repeat(30));
        System.out.println("    VIEW STUDENT BY ID");
        System.out.println("-".repeat(30));

        try {
            System.out.print("Enter student ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            Student student = studentService.findStudentById(id);
            if (student != null) {
                displayStudentDetails(student);
            } else {
                System.out.println("Student with ID " + id + " not found!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID! Please enter a valid number.");
        }
    }

    /**
     * Handle updating student information
     */
    private void handleUpdateStudent() {
        System.out.println("\n" + "-".repeat(30));
        System.out.println("     UPDATE STUDENT");
        System.out.println("-".repeat(30));

        try {
            System.out.print("Enter student ID to update: ");
            int id = Integer.parseInt(scanner.nextLine());

            Student student = studentService.findStudentById(id);
            if (student == null) {
                System.out.println("Student with ID " + id + " not found!");
                return;
            }

            System.out.println("\nCurrent details:");
            System.out.println(student);

            System.out.println("\nWhat would you like to update?");
            System.out.println("1. Name");
            System.out.println("2. Course");
            System.out.println("3. Marks");
            System.out.println("4. All details");
            System.out.print("Enter choice (1-4): ");

            int updateChoice = getValidChoice(1, 4);
            handleUpdateChoice(id, updateChoice);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter valid numbers.");
        }
    }

    /**
     * Handle specific update choice
     */
    private void handleUpdateChoice(int studentId, int choice) {
        try {
            String newName = null;
            String newCourse = null;
            Double newMarks = null;

            switch (choice) {
                case 1:
                    System.out.print("Enter new name: ");
                    newName = scanner.nextLine().trim();
                    break;
                case 2:
                    System.out.print("Enter new course: ");
                    newCourse = scanner.nextLine().trim();
                    break;
                case 3:
                    System.out.print("Enter new marks (0-100): ");
                    newMarks = Double.parseDouble(scanner.nextLine());
                    break;
                case 4:
                    System.out.print("Enter new name: ");
                    newName = scanner.nextLine().trim();
                    System.out.print("Enter new course: ");
                    newCourse = scanner.nextLine().trim();
                    System.out.print("Enter new marks (0-100): ");
                    newMarks = Double.parseDouble(scanner.nextLine());
                    break;
            }

            boolean updated = studentService.updateStudent(studentId, newName, newCourse, newMarks);
            if (updated) {
                System.out.println("✓ Student updated successfully!");
                Student updatedStudent = studentService.findStudentById(studentId);
                System.out.println("Updated details: " + updatedStudent);
            } else {
                System.out.println("Failed to update student.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid marks! Please enter a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Handle deleting student
     */
    private void handleDeleteStudent() {
        System.out.println("\n" + "-".repeat(30));
        System.out.println("     DELETE STUDENT");
        System.out.println("-".repeat(30));

        try {
            System.out.print("Enter student ID to delete: ");
            int id = Integer.parseInt(scanner.nextLine());

            Student student = studentService.findStudentById(id);
            if (student == null) {
                System.out.println("Student with ID " + id + " not found!");
                return;
            }

            System.out.println("\nStudent to delete:");
            System.out.println(student);

            System.out.print("\nAre you sure you want to delete this student? (y/n): ");
            String confirm = scanner.nextLine().trim().toLowerCase();

            if (confirm.equals("y") || confirm.equals("yes")) {
                boolean deleted = studentService.deleteStudent(id);
                if (deleted) {
                    System.out.println("✓ Student deleted successfully!");
                } else {
                    System.out.println("Failed to delete student.");
                }
            } else {
                System.out.println("Delete operation cancelled.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid ID! Please enter a valid number.");
        }
    }

    /**
     * Handle searching students
     */
    private void handleSearchStudents() {
        System.out.println("\n" + "-".repeat(30));
        System.out.println("     SEARCH STUDENTS");
        System.out.println("-".repeat(30));

        System.out.println("Search by:");
        System.out.println("1. Name");
        System.out.println("2. Course");
        System.out.println("3. Grade");
        System.out.print("Enter choice (1-3): ");

        int searchChoice = getValidChoice(1, 3);
        List<Student> results = null;

        switch (searchChoice) {
            case 1:
                System.out.print("Enter name to search: ");
                String searchName = scanner.nextLine().trim();
                results = studentService.searchByName(searchName);
                break;
            case 2:
                System.out.print("Enter course to search: ");
                String searchCourse = scanner.nextLine().trim();
                results = studentService.searchByCourse(searchCourse);
                break;
            case 3:
                System.out.print("Enter grade to search (A+, A, B, C, D, F): ");
                String searchGrade = scanner.nextLine().trim();
                results = studentService.searchByGrade(searchGrade);
                break;
        }

        if (results != null) {
            displaySearchResults(results);
        }
    }

    /**
     * Handle displaying statistics
     */
    private void handleDisplayStatistics() {
        System.out.println("\n" + "-".repeat(40));
        System.out.println("         STUDENT STATISTICS");
        System.out.println("-".repeat(40));

        StudentStatistics stats = studentService.getStatistics();

        if (stats.isEmpty()) {
            System.out.println("No students in the system.");
            return;
        }

        displayStatistics(stats);
    }

    /**
     * Display student table
     */
    private void displayStudentTable(List<Student> students) {
        System.out.printf("%-5s | %-20s | %-15s | %-6s | %-5s%n",
                "ID", "NAME", "COURSE", "MARKS", "GRADE");
        System.out.println("-".repeat(80));

        for (Student student : students) {
            System.out.println(student);
        }
        System.out.println("-".repeat(80));
    }

    /**
     * Display detailed student information
     */
    private void displayStudentDetails(Student student) {
        System.out.println("\nStudent found:");
        System.out.println("-".repeat(50));
        System.out.println("ID: " + student.getId());
        System.out.println("Name: " + student.getName());
        System.out.println("Course: " + student.getCourse());
        System.out.println("Marks: " + student.getMarks());
        System.out.println("Grade: " + student.getGrade());
        System.out.println("Status: " + (student.isPassing() ? "PASSING" : "FAILING"));
        System.out.println("-".repeat(50));
    }

    /**
     * Display search results
     */
    private void displaySearchResults(List<Student> results) {
        if (results.isEmpty()) {
            System.out.println("No students found matching your search criteria.");
        } else {
            System.out.println("\nSearch results:");
            System.out.println("-".repeat(80));
            displayStudentTable(results);
            System.out.println("Found " + results.size() + " student(s).");
        }
    }

    /**
     * Display comprehensive statistics
     */
    private void displayStatistics(StudentStatistics stats) {
        System.out.println("Total Students: " + stats.getTotalStudents());
        System.out.println("Average Marks: " + String.format("%.2f", stats.getAverageMarks()));
        System.out.println("Highest Marks: " + String.format("%.2f", stats.getHighestMarks()));
        System.out.println("Lowest Marks: " + String.format("%.2f", stats.getLowestMarks()));

        Student topStudent = stats.getTopStudent();
        if (topStudent != null) {
            System.out.println("\nTop Performer:");
            System.out.println("  " + topStudent.getName() + " (" + topStudent.getCourse() + ") - " +
                    String.format("%.2f", topStudent.getMarks()));
        }

        System.out.println("\nPass/Fail Statistics:");
        System.out.println("  Passing: " + stats.getPassingStudents() + " (" +
                String.format("%.1f", stats.getPassingPercentage()) + "%)");
        System.out.println("  Failing: " + stats.getFailingStudents() + " (" +
                String.format("%.1f", stats.getFailingPercentage()) + "%)");

        System.out.println("\nGrade Distribution:");
        Map<String, Integer> gradeDistribution = stats.getGradeDistribution();
        String[] grades = {"A+", "A", "B", "C", "D", "F"};

        for (String grade : grades) {
            int count = gradeDistribution.getOrDefault(grade, 0);
            if (count > 0) {
                double percentage = stats.getGradePercentage(grade);
                System.out.println("  " + grade + ": " + count + " student(s) (" +
                        String.format("%.1f", percentage) + "%)");
            }
        }
    }

    /**
     * Get valid choice within range
     */
    private int getValidChoice(int min, int max) {
        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    System.out.print("Please enter a number between " + min + "-" + max + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    /**
     * Pause for user to read output
     */
    private void pauseForUser() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Display exit message
     */
    private void displayExitMessage() {
        System.out.println("\nThank you for using Student Management System!");
        System.out.println("Goodbye!");
    }
}
