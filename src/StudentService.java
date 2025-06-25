import java.util.*;

/**
 * Service class to handle all student-related operations
 * Contains business logic for CRUD operations and data management
 */
public class StudentService {
    private ArrayList<Student> students;
    private int nextId;

    public StudentService() {
        this.students = new ArrayList<>();
        this.nextId = 1001; // Starting ID for students
        initializeSampleData();
    }

    /**
     * Initialize system with sample student data
     */
    private void initializeSampleData() {
        try {
            students.add(new Student(nextId++, "Alice Johnson", 92.5, "Computer Science"));
            students.add(new Student(nextId++, "Bob Smith", 78.0, "Mathematics"));
            students.add(new Student(nextId++, "Carol Davis", 85.5, "Physics"));
            students.add(new Student(nextId++, "David Wilson", 67.0, "Chemistry"));
            students.add(new Student(nextId++, "Emma Brown", 44.5, "Biology"));
        } catch (Exception e) {
            System.err.println("Error initializing sample data: " + e.getMessage());
        }
    }

    /**
     * Add a new student to the system
     * @param name Student name
     * @param course Student course
     * @param marks Student marks
     * @return The created Student object
     * @throws IllegalArgumentException if invalid data provided
     */
    public Student addStudent(String name, String course, double marks) {
        // Validation is now handled in Student constructor
        Student student = new Student(nextId++, name, marks, course);
        students.add(student);
        return student;
    }

    /**
     * Get all students (defensive copy)
     * @return List of all students
     */
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    /**
     * Find student by ID
     * @param id Student ID
     * @return Student object if found, null otherwise
     */
    public Student findStudentById(int id) {
        if (id <= 0) return null; // Invalid ID

        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    /**
     * Update student information
     * @param id Student ID
     * @param name New name (null to keep existing)
     * @param course New course (null to keep existing)
     * @param marks New marks (null to keep existing)
     * @return true if updated successfully, false if student not found
     * @throws IllegalArgumentException if invalid data provided
     */
    public boolean updateStudent(int id, String name, String course, Double marks) {
        Student student = findStudentById(id);
        if (student == null) {
            return false;
        }

        try {
            // Use setters which have validation
            if (name != null && !name.trim().isEmpty()) {
                student.setName(name);
            }
            if (course != null && !course.trim().isEmpty()) {
                student.setCourse(course);
            }
            if (marks != null) {
                student.setMarks(marks);
            }
            return true;
        } catch (IllegalArgumentException e) {
            throw e; // Re-throw validation errors
        }
    }

    /**
     * Delete student by ID
     * @param id Student ID
     * @return true if deleted successfully, false if student not found
     */
    public boolean deleteStudent(int id) {
        Student student = findStudentById(id);
        if (student != null) {
            students.remove(student);
            return true;
        }
        return false;
    }

    /**
     * Search students by name (case-insensitive partial match)
     * @param name Name to search for
     * @return List of matching students
     */
    public List<Student> searchByName(String name) {
        List<Student> results = new ArrayList<>();

        if (name == null || name.trim().isEmpty()) {
            return results; // Return empty list for invalid search
        }

        String searchName = name.toLowerCase().trim();

        for (Student student : students) {
            if (student.getName().toLowerCase().contains(searchName)) {
                results.add(student);
            }
        }
        return results;
    }

    /**
     * Search students by course (case-insensitive partial match)
     * @param course Course to search for
     * @return List of matching students
     */
    public List<Student> searchByCourse(String course) {
        List<Student> results = new ArrayList<>();

        if (course == null || course.trim().isEmpty()) {
            return results; // Return empty list for invalid search
        }

        String searchCourse = course.toLowerCase().trim();

        for (Student student : students) {
            if (student.getCourse().toLowerCase().contains(searchCourse)) {
                results.add(student);
            }
        }
        return results;
    }

    /**
     * Search students by grade (exact match, case-insensitive)
     * @param grade Grade to search for
     * @return List of matching students
     */
    public List<Student> searchByGrade(String grade) {
        List<Student> results = new ArrayList<>();

        if (grade == null || grade.trim().isEmpty()) {
            return results; // Return empty list for invalid search
        }

        String searchGrade = grade.toUpperCase().trim();

        // Validate grade format
        Set<String> validGrades = new HashSet<>(Arrays.asList("A+", "A", "B", "C", "D", "F"));
        if (!validGrades.contains(searchGrade)) {
            return results; // Return empty for invalid grade
        }

        for (Student student : students) {
            if (student.getGrade().equals(searchGrade)) {
                results.add(student);
            }
        }
        return results;
    }

    /**
     * Get system statistics
     * @return StudentStatistics object containing system stats
     */
    public StudentStatistics getStatistics() {
        if (students.isEmpty()) {
            return new StudentStatistics();
        }

        double totalMarks = 0;
        double highestMarks = students.get(0).getMarks();
        double lowestMarks = students.get(0).getMarks();
        Student topStudent = students.get(0);

        Map<String, Integer> gradeDistribution = new HashMap<>();
        int passingCount = 0;

        for (Student student : students) {
            double marks = student.getMarks();
            totalMarks += marks;

            if (marks > highestMarks) {
                highestMarks = marks;
                topStudent = student;
            }

            if (marks < lowestMarks) {
                lowestMarks = marks;
            }

            if (student.isPassing()) {
                passingCount++;
            }

            String grade = student.getGrade();
            gradeDistribution.put(grade, gradeDistribution.getOrDefault(grade, 0) + 1);
        }

        double averageMarks = totalMarks / students.size();

        return new StudentStatistics(
                students.size(),
                Math.round(averageMarks * 100.0) / 100.0, // Round to 2 decimal places
                highestMarks,
                lowestMarks,
                topStudent,
                gradeDistribution,
                passingCount
        );
    }

    /**
     * Check if system has any students
     * @return true if system is empty, false otherwise
     */
    public boolean isEmpty() {
        return students.isEmpty();
    }

    /**
     * Get total number of students
     * @return Total student count
     */
    public int getStudentCount() {
        return students.size();
    }

    /**
     * Get students sorted by marks (descending)
     * @return List of students sorted by marks
     */
    public List<Student> getStudentsSortedByMarks() {
        List<Student> sortedStudents = new ArrayList<>(students);
        sortedStudents.sort((s1, s2) -> Double.compare(s2.getMarks(), s1.getMarks()));
        return sortedStudents;
    }

    /**
     * Get students sorted by name (ascending)
     * @return List of students sorted by name
     */
    public List<Student> getStudentsSortedByName() {
        List<Student> sortedStudents = new ArrayList<>(students);
        sortedStudents.sort((s1, s2) -> s1.getName().compareToIgnoreCase(s2.getName()));
        return sortedStudents;
    }

    /**
     * Check if a student ID already exists
     * @param id Student ID to check
     * @return true if ID exists, false otherwise
     */
    public boolean studentExists(int id) {
        return findStudentById(id) != null;
    }

    /**
     * Get next available student ID
     * @return Next student ID
     */
    public int getNextStudentId() {
        return nextId;
    }
}