package presentation;
import java.lang.reflect.Field;

import bll.StudentBLL;
import model.Student;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ClientOperationsView extends JFrame {
    private JTextField nameField;
    private JTextField addressField;
    private JTextField emailField;
    private JTextField ageField;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton listButton;
    private JButton backButton; // New button
    private JTable clientTable;
    private JTextField idField;

    private StudentBLL studentBLL;

    public ClientOperationsView() {
        setTitle("Client Operations");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        studentBLL = new StudentBLL();

        nameField = new JTextField(20);
        addressField = new JTextField(20);
        emailField = new JTextField(20);
        ageField = new JTextField(20);
        idField = new JTextField(20);



        addButton = new JButton("Add Client");
        editButton = new JButton("Edit Client");
        deleteButton = new JButton("Delete Client");
        listButton = new JButton("List Clients");
        backButton = new JButton("Back"); // Initialize backButton
        clientTable = new JTable();


        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Set layout to BoxLayout

        panel.add(new JLabel("ID:"));
        panel.add(idField);

        panel.add(new JLabel("Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Address:"));
        panel.add(addressField);

        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        panel.add(new JLabel("Age:"));
        panel.add(ageField);

        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);
        panel.add(listButton);
        panel.add(backButton); // Add backButton to inputPanel

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(clientTable), BorderLayout.CENTER);

        deleteButton.addActionListener(e -> {
            int id = Integer.parseInt(idField.getText());
            Student student = studentBLL.findStudentById(id);
            if (student != null) {
                studentBLL.deleteStudent(student.getId());
                updateTable();
            }
        });

        addButton.addActionListener(e -> {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String address = addressField.getText();
            String email = emailField.getText();
            int age = Integer.parseInt(ageField.getText());


            Student student = new Student(id, name, address, email, age);
            studentBLL.insertStudent(student);
            updateTable();
        });

        editButton.addActionListener(e -> {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String address = addressField.getText();
            String email = emailField.getText();
            int age = Integer.parseInt(ageField.getText());

            Student student = new Student(id, name, address, email, age);
            studentBLL.updateStudent(student);
            updateTable();
        });

        listButton.addActionListener(e -> showStudentList());

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Dispose the current window
                dispose();
            }
        });
    }

    private void showStudentList() {
        JFrame studentListFrame = new JFrame("List of Students");
        studentListFrame.setSize(800, 600);
        studentListFrame.setLocationRelativeTo(null);

        JTable studentTable = new JTable();
        studentListFrame.add(new JScrollPane(studentTable), BorderLayout.CENTER);



        List<Student> students = studentBLL.findAllStudents();
        DefaultTableModel model = createTableModelFromList(students);
        studentTable.setModel(model);
        studentListFrame.setVisible(true);
    }

    public DefaultTableModel createTableModelFromList(List<?> list) {
        DefaultTableModel model = new DefaultTableModel();
        if (!list.isEmpty()) {
            Object firstObject = list.get(0);
            Field[] fields = firstObject.getClass().getDeclaredFields();
            // Create table columns
            for (Field field : fields) {
                model.addColumn(field.getName());
            }
            // Create table rows
            for (Object object : list) {
                Object[] row = new Object[fields.length];
                for (int i = 0; i < fields.length; i++) {
                    try {
                        fields[i].setAccessible(true);
                        row[i] = fields[i].get(object);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                model.addRow(row);
            }
        }
        return model;
    }

    public void updateTable() {
        List<Student> students = studentBLL.findAllStudents();
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Address");
        model.addColumn("Email");
        model.addColumn("Age");

        for (Student student : students) {
            model.addRow(new Object[] {
                    student.getId(),
                    student.getName(),
                    student.getAddress(),
                    student.getEmail(),
                    student.getAge()
            });
        }
        clientTable.setModel(model);
    }

    public JButton getListButton() {
        return listButton;
    }

    public void setListButton(JButton listButton) {
        this.listButton = listButton;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public void setNameField(JTextField nameField) {
        this.nameField = nameField;
    }

    public JTextField getAddressField() {
        return addressField;
    }

    public void setAddressField(JTextField addressField) {
        this.addressField = addressField;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public void setEmailField(JTextField emailField) {
        this.emailField = emailField;
    }

    public JTextField getAgeField() {
        return ageField;
    }

    public void setAgeField(JTextField ageField) {
        this.ageField = ageField;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public void setAddButton(JButton addButton) {
        this.addButton = addButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public void setEditButton(JButton editButton) {
        this.editButton = editButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(JButton deleteButton) {
        this.deleteButton = deleteButton;
    }

    public JTable getClientTable() {
        return clientTable;
    }

    public void setClientTable(JTable clientTable) {
        this.clientTable = clientTable;
    }
    public JTextField getIdField() {
        return idField;
    }

    public void setIdField(JTextField idField) {
        this.idField = idField;
    }
}