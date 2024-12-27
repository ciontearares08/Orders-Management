package presentation;

import bll.StudentBLL;
import model.Student;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientController {
    private ClientOperationsView view;
    private StudentBLL studentBLL;

    public ClientController(ClientOperationsView view) {
        this.view = view;
        this.studentBLL = new StudentBLL();

        view.getAddButton().addActionListener(new AddButtonListener());
        view.getEditButton().addActionListener(new EditButtonListener());
        view.getDeleteButton().addActionListener(new DeleteButtonListener());
        view.getListButton().addActionListener(new ListButtonListener());
    }

    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = view.getNameField().getText();
            String address = view.getAddressField().getText();
            String email = view.getEmailField().getText();
            int age = Integer.parseInt(view.getAgeField().getText());

            Student student = new Student(name, address, email, age);
            studentBLL.insertStudent(student);

            view.updateTable();
        }
    }

    private class EditButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = view.getClientTable().getSelectedRow();
            if (selectedRow != -1) {
                String name = view.getNameField().getText();
                String address = view.getAddressField().getText();
                String email = view.getEmailField().getText();
                int age = Integer.parseInt(view.getAgeField().getText());

                Student student = new Student(name, address, email, age);
                studentBLL.updateStudent(student);

                view.updateTable();
            }
        }
    }

    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = view.getClientTable().getSelectedRow();
            if (selectedRow != -1) {
                studentBLL.deleteStudent(selectedRow);

                view.updateTable();
            }
        }
    }

    private class ListButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.updateTable();
        }
    }
}