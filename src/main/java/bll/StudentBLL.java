package bll;

import dao.StudentDAO;
import model.Student;

import java.util.List;

public class StudentBLL {

    private StudentDAO studentDAO;

    public StudentBLL() {
        this.studentDAO = new StudentDAO();
    }

    public Student findStudentById(int id) {
        return studentDAO.find(id);
    }

    public void insertStudent(Student student) {
        studentDAO.insert(student);
    }

    public Student findByName(String studentName) {
        return studentDAO.findByName(studentName);
    }
    public void updateStudent(Student student) {
        studentDAO.update(student);
    }

    public void deleteStudent(int id) {
        studentDAO.delete(id);
    }

    public List<Student> findAllStudents() {
        return studentDAO.findAll();
    }
}