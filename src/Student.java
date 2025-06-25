public class Student {
    private int id;
    private String name;
    private double marks;
    private String course;

    // Constructor with input validation
    public Student(int id, String name, double marks, String course) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (course == null || course.trim().isEmpty()) {
            throw new IllegalArgumentException("Course cannot be empty");
        }
        if (marks < 0 || marks > 100) {
            throw new IllegalArgumentException("Marks must be between 0 and 100");
        }
        this.id = id;
        this.name = name;
        this.marks = marks;
        this.course = course;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public double getMarks() { return marks; }
    public String getCourse() { return course; }

    // Setters with validation
    public void setId(int id) { this.id = id; }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }

    public void setMarks(double marks) {
        if (marks < 0 || marks > 100) {
            throw new IllegalArgumentException("Marks must be between 0 and 100");
        }
        this.marks = marks;
    }

    public void setCourse(String course) {
        if (course == null || course.trim().isEmpty()) {
            throw new IllegalArgumentException("Course cannot be empty");
        }
        this.course = course;
    }

    // Grade calculation
    public String getGrade() {
        if (marks >= 90) return "A+";
        else if (marks >= 80) return "A";
        else if (marks >= 70) return "B";
        else if (marks >= 60) return "C";
        else if (marks >= 50) return "D";
        else return "F";
    }

    // Passing check
    public boolean isPassing() {
        return marks >= 50.0;
    }

    // String representation
    @Override
    public String toString() {
        return String.format("ID: %-5d | Name: %-20s | Course: %-15s | Marks: %-6.2f | Grade: %s",
                id, name, course, marks, getGrade());
    }

    // Equality based on ID
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return id == student.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
