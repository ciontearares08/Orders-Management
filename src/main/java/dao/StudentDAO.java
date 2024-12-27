package dao;

import java.util.logging.Logger;
import java.util.logging.Level;
import connection.ConnectionFactory;
import model.Student;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class StudentDAO {
    private static final Logger LOGGER = Logger.getLogger(StudentDAO.class.getName());

    public Student find(int id) {
        Student student = null;
        String FIND_BY_ID_SQL = "SELECT * FROM Student WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    student = extractStudent(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error finding student by id: " + e.getMessage());
        }
        return student;
    }

    public void insert(Student student) {
        String INSERT_SQL = "INSERT INTO Student (id, name, address, email, age) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {
            statement.setInt(1, student.getId());
            statement.setString(2, student.getName());
            statement.setString(3, student.getAddress());
            statement.setString(4, student.getEmail());
            statement.setInt(5, student.getAge());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error inserting student: " + e.getMessage());
        }
    }

    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        String FIND_ALL_SQL = "SELECT * FROM Student";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                students.add(extractStudent(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error finding all students: " + e.getMessage());
        }
        return students;
    }

    public void update(Student student) {
        String UPDATE_SQL = "UPDATE Student SET name = ?, address = ?, email = ?, age = ? WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, student.getName());
            statement.setString(2, student.getAddress());
            statement.setString(3, student.getEmail());
            statement.setInt(4, student.getAge());
            statement.setInt(5, student.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error updating student: " + e.getMessage());
        }
    }

    public void delete(int studentId) {
        String DELETE_SQL = "DELETE FROM Student WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setInt(1, studentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error deleting student: " + e.getMessage());
        }
    }

    public Student findByName(String studentName) {
        Student student = null;
        String FIND_BY_NAME_SQL = "SELECT * FROM Student WHERE name = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME_SQL)) {
            statement.setString(1, studentName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    student = extractStudent(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error finding student by name: " + e.getMessage());
        }
        return student;
    }

    private Student extractStudent(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String address = resultSet.getString("address");
        String email = resultSet.getString("email");
        int age = resultSet.getInt("age");
        return new Student(id, name, address, email, age);
    }
}