package com.project;

import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;

class Student {
    private int studentId;
    private String studentName;
    private List<Course> courses;

    public Student(int studentId, String studentName, List<Course> courses) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.courses = courses;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Student [ID=" + studentId + ", Name=" + studentName + ", Courses=" + courses + "]";
    }
}

class Course {
    private int courseId;
    private String courseName;

    public Course(int courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    @Override
    public String toString() {
        return "Course [ID=" + courseId + ", Name=" + courseName + "]";
    }
}

class StudentUtil {
    public static Comparator<Student> sortByName = new Comparator<Student>() {
        @Override
        public int compare(Student s1, Student s2) {
            return s1.getStudentName().compareTo(s2.getStudentName());
        }
    };

    public static Comparator<Student> sortById = new Comparator<Student>() {
        @Override
        public int compare(Student s1, Student s2) {
            return Integer.compare(s1.getStudentId(), s2.getStudentId());
        }
    };
}

class StudentDB {
    private List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
    }

    public void updateStudent(int id, String name, List<Course> courses) {
        try {
            for (Student student : students) {
                if (student.getStudentId() == id) {
                    student.setStudentName(name);
                    student.setCourses(courses);
                    return;
                }
            }
            throw new Exception("Student with ID " + id + " not found.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public Student getStudentById(int id) {
        try {
            for (Student student : students) {
                if (student.getStudentId() == id) {
                    return student;
                }
            }
            throw new Exception("Student with ID " + id + " not found.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public List<Student> searchStudentByName(String name) {
        List<Student> result = new ArrayList<>();
        try {
            for (Student student : students) {
                if (student.getStudentName().equalsIgnoreCase(name)) {
                    result.add(student);
                }
            }
            if (result.isEmpty()) {
                throw new Exception("No students found with name " + name);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    public void sortStudentsByName() {
        Collections.sort(students, StudentUtil.sortByName);
    }

    public void sortStudentsById() {
        Collections.sort(students, StudentUtil.sortById);
    }

    public void deleteStudent(int id) {
        try {
            boolean removed = students.removeIf(student -> student.getStudentId() == id);
            if (!removed) {
                throw new Exception("Student with ID " + id + " not found.");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}

public class StudentManagementSystem {
    public static void main(String[] args) {
        StudentDB studentDB = new StudentDB();

        Course course1 = new Course(101, "CSE");
        Course course2 = new Course(102, "ECE");
        Course course3 = new Course(103, "EEE");

        List<Course> courses1 = new ArrayList<>();
        courses1.add(course1);
        courses1.add(course2);
        studentDB.addStudent(new Student(1, "Sravya", courses1));

        List<Course> courses2 = new ArrayList<>();
        courses2.add(course2);
        courses2.add(course3);
        studentDB.addStudent(new Student(2, "Ravi", courses2));

        // Display all students
        System.out.println("All Students: " + studentDB.getAllStudents());

        // Search student by name
        System.out.println("Search by name 'Sravya': " + studentDB.searchStudentByName("Sravya"));

        // Sort students by name
        studentDB.sortStudentsByName();
        System.out.println("Students sorted by name: " + studentDB.getAllStudents());

        // Sort students by ID
        studentDB.sortStudentsById();
        System.out.println("Students sorted by ID: " + studentDB.getAllStudents());

        // Update student
        List<Course> newCourses = new ArrayList<>();
        newCourses.add(course1);
        studentDB.updateStudent(1, "Sravya Reddy", newCourses);
        System.out.println("Updated student: " + studentDB.getStudentById(1));

        // Delete student
        studentDB.deleteStudent(2);
        System.out.println("All Students after deletion: " + studentDB.getAllStudents());
    }
}
