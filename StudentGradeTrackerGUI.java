import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Student {
    String name;
    double grade;

    Student(String name, double grade) {
        this.name = name;
        this.grade = grade;
    }
}

public class StudentGradeTrackerGUI extends JFrame {

    private JTextField nameField, gradeField;
    private JTextArea displayArea;

    private ArrayList<Student> students;

    public StudentGradeTrackerGUI() {
        students = new ArrayList<>();

        setTitle("Student Grade Tracker");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Panel (Input)
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));

        inputPanel.add(new JLabel("Student Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Grade:"));
        gradeField = new JTextField();
        inputPanel.add(gradeField);

        JButton addButton = new JButton("Add Student");
        JButton summaryButton = new JButton("Show Summary");

        inputPanel.add(addButton);
        inputPanel.add(summaryButton);

        add(inputPanel, BorderLayout.NORTH);

        // Center Panel (Display)
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        // Button Actions
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        summaryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showSummary();
            }
        });

        setVisible(true);
    }

    private void addStudent() {
        String name = nameField.getText();
        String gradeText = gradeField.getText();

        if (name.isEmpty() || gradeText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter both name and grade.");
            return;
        }

        try {
            double grade = Double.parseDouble(gradeText);
            students.add(new Student(name, grade));

            displayArea.append("Added: " + name + " - " + grade + "\n");

            nameField.setText("");
            gradeField.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid number for grade.");
        }
    }

    private void showSummary() {
        if (students.isEmpty()) {
            displayArea.setText("No student data available.\n");
            return;
        }

        double total = 0;
        double highest = students.get(0).grade;
        double lowest = students.get(0).grade;

        StringBuilder report = new StringBuilder();
        report.append("---- Student Summary ----\n");

        for (Student s : students) {
            report.append(s.name).append(" - ").append(s.grade).append("\n");
            total += s.grade;

            if (s.grade > highest) highest = s.grade;
            if (s.grade < lowest) lowest = s.grade;
        }

        double average = total / students.size();

        report.append("\nAverage: ").append(average);
        report.append("\nHighest: ").append(highest);
        report.append("\nLowest: ").append(lowest);

        displayArea.setText(report.toString());
    }

    public static void main(String[] args) {
        new StudentGradeTrackerGUI();
    }
}