import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Student {
    private String name;
    private int rollNumber;
    private String grade;

    public Student(String name, int rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public String getGrade() {
        return grade;
    }
}

class StudentManagementSystem {
    private List<Student> students;
    private static final String FILE_NAME = "students.txt";

    public StudentManagementSystem() {
        students = new ArrayList<>();
        loadStudentsFromFile();
    }

    public void addStudent(Student student) {
        students.add(student);
        saveStudentsToFile();
    }

    public void removeStudent(int rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                students.remove(student);
                saveStudentsToFile();
                break;
            }
        }
    }

    public Student searchStudent(int rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                return student;
            }
        }
        return null;
    }

    public List<Student> getAllStudents() {
        return students;
    }

    private void loadStudentsFromFile() {
        try {
            FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            students = (ArrayList<Student>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            // Ignore if file doesn't exist or cannot be read
        }
    }

    private void saveStudentsToFile() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(students);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class StudentManagementSystemUI {
    private static StudentManagementSystem sms;

    public static void main(String[] args) {
        sms = new StudentManagementSystem();

        // Start the user interface
        showMenu();
    }

    public static void showMenu() {
        System.out.println("Student Management System");
        System.out.println("1. Add Student");
        System.out.println("2. Remove Student");
        System.out.println("3. Search Student");
        System.out.println("4. Display All Students");
        System.out.println("5. Exit");

        int choice = getUserChoice();

        switch (choice) {
            case 1:
                addStudent();
                break;
            case 2:
                removeStudent();
                break;
            case 3:
                searchStudent();
                break;
            case 4:
                displayAllStudents();
                break;
            case 5:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                showMenu();
                break;
        }
    }

    public static int getUserChoice() {
        int choice = 0;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            choice = Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            getUserChoice();
        }
        return choice;
    }

    public static void addStudent() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Enter student name:");
            String name = reader.readLine();

            System.out.println("Enter student roll number:");
            int rollNumber = Integer.parseInt(reader.readLine());

            System.out.println("Enter student grade:");
            String grade = reader.readLine();

            Student student = new Student(name, rollNumber, grade);
            sms.addStudent(student);

            System.out.println("Student added successfully.");

            showMenu();
        } catch (IOException | NumberFormatException e) {
            System.out.println("Invalid input. Please try again.");
            addStudent();
        }
    }

    public static void removeStudent() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Enter student roll number:");
            int rollNumber = Integer.parseInt(reader.readLine());

            sms.removeStudent(rollNumber);

            System.out.println("Student removed successfully.");

            showMenu();
        } catch (IOException | NumberFormatException e) {
            System.out.println("Invalid input. Please try again.");
            removeStudent();
        }
    }

    public static void searchStudent() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Enter student roll number:");
            int rollNumber = Integer.parseInt(reader.readLine());

            Student student = sms.searchStudent(rollNumber);

            if (student != null) {
                System.out.println("Student found:");
                System.out.println("Name: " + student.getName());
                System.out.println("Roll Number: " + student.getRollNumber());
                System.out.println("Grade: " + student.getGrade());
            } else {
                System.out.println("Student not found.");
            }

            showMenu();
        } catch (IOException | NumberFormatException e) {
            System.out.println("Invalid input. Please try again.");
            searchStudent();
        }
    }

    public static void displayAllStudents() {
        List<Student> students = sms.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("All Students:");

            for (Student student : students) {
                System.out.println("Name: " + student.getName());
                System.out.println("Roll Number: " + student.getRollNumber());
                System.out.println("Grade: " + student.getGrade());
                System.out.println("------------------------");
            }
        }

        showMenu();
    }
}
