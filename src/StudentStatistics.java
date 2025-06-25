import java.util.Map;
import java.util.HashMap;

/**
 * Data class to hold student statistics
 * Used for displaying system analytics and performance metrics
 */
public class StudentStatistics {
    private int totalStudents;
    private double averageMarks;
    private double highestMarks;
    private double lowestMarks;
    private Student topStudent;
    private Map<String, Integer> gradeDistribution;
    private int passingStudents;

    // Default constructor for empty system
    public StudentStatistics() {
        this.totalStudents = 0;
        this.averageMarks = 0.0;
        this.highestMarks = 0.0;
        this.lowestMarks = 0.0;
        this.topStudent = null;
        this.gradeDistribution = new HashMap<>();
        this.passingStudents = 0;
    }

    // Constructor with all parameters
    public StudentStatistics(int totalStudents, double averageMarks, double highestMarks,
                             double lowestMarks, Student topStudent, Map<String, Integer> gradeDistribution,
                             int passingStudents) {
        // Validation for parameters
        if (totalStudents < 0) {
            throw new IllegalArgumentException("Total students cannot be negative");
        }
        if (passingStudents < 0 || passingStudents > totalStudents) {
            throw new IllegalArgumentException("Passing students count is invalid");
        }
        if (gradeDistribution == null) {
            throw new IllegalArgumentException("Grade distribution cannot be null");
        }

        this.totalStudents = totalStudents;
        this.averageMarks = averageMarks;
        this.highestMarks = highestMarks;
        this.lowestMarks = lowestMarks;
        this.topStudent = topStudent;
        this.gradeDistribution = new HashMap<>(gradeDistribution); // Defensive copy
        this.passingStudents = passingStudents;
    }

    // Getters
    public int getTotalStudents() {
        return totalStudents;
    }

    public double getAverageMarks() {
        return averageMarks;
    }

    public double getHighestMarks() {
        return highestMarks;
    }

    public double getLowestMarks() {
        return lowestMarks;
    }

    public Student getTopStudent() {
        return topStudent;
    }

    public Map<String, Integer> getGradeDistribution() {
        return new HashMap<>(gradeDistribution); // Return defensive copy
    }

    public int getPassingStudents() {
        return passingStudents;
    }

    public int getFailingStudents() {
        return totalStudents - passingStudents;
    }

    public double getPassingPercentage() {
        if (totalStudents == 0) return 0.0;
        return Math.round((passingStudents * 100.0) / totalStudents * 10.0) / 10.0; // Round to 1 decimal
    }

    public double getFailingPercentage() {
        if (totalStudents == 0) return 0.0;
        return Math.round((getFailingStudents() * 100.0) / totalStudents * 10.0) / 10.0; // Round to 1 decimal
    }

    /**
     * Get percentage for a specific grade
     * @param grade Grade to get percentage for
     * @return Percentage of students with that grade
     */
    public double getGradePercentage(String grade) {
        if (totalStudents == 0 || grade == null) return 0.0;
        int count = gradeDistribution.getOrDefault(grade, 0);
        return Math.round((count * 100.0) / totalStudents * 10.0) / 10.0; // Round to 1 decimal
    }

    /**
     * Check if system has any data
     * @return true if no students in system
     */
    public boolean isEmpty() {
        return totalStudents == 0;
    }

    /**
     * Get the most common grade
     * @return Most common grade or null if no students
     */
    public String getMostCommonGrade() {
        if (gradeDistribution.isEmpty()) return null;

        String mostCommon = null;
        int maxCount = 0;

        for (Map.Entry<String, Integer> entry : gradeDistribution.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostCommon = entry.getKey();
            }
        }

        return mostCommon;
    }

    /**
     * String representation of statistics
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "No statistics available - system is empty.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Student Statistics Summary:\n");
        sb.append(String.format("Total Students: %d\n", totalStudents));
        sb.append(String.format("Average Marks: %.2f\n", averageMarks));
        sb.append(String.format("Highest Marks: %.2f\n", highestMarks));
        sb.append(String.format("Lowest Marks: %.2f\n", lowestMarks));
        sb.append(String.format("Passing: %d (%.1f%%)\n", passingStudents, getPassingPercentage()));
        sb.append(String.format("Failing: %d (%.1f%%)", getFailingStudents(), getFailingPercentage()));

        return sb.toString();
    }
}
