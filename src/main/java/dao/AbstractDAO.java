package dao;

import connection.ConnectionFactory;

import java.lang.reflect.Field;
import java.sql.*;

public abstract class AbstractDAO<T> {
    private final Class<T> type;

    protected AbstractDAO(Class<T> type) {
        this.type = type;
    }

    public T find(int id) throws SQLException, IllegalAccessException, InstantiationException {
        String query = "SELECT * FROM " + type.getSimpleName() + " WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    T instance = type.newInstance();
                    for (Field field : type.getDeclaredFields()) {
                        field.setAccessible(true);
                        field.set(instance, resultSet.getObject(field.getName()));
                    }
                    return instance;
                }
            }
        }
        return null;
    }

    public void insert(T instance) throws SQLException, IllegalAccessException {
        StringBuilder query = new StringBuilder("INSERT INTO " + type.getSimpleName() + " (");
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            query.append(field.getName()).append(",");
        }
        query.deleteCharAt(query.length() - 1);
        query.append(") VALUES (");
        for (Field field : fields) {
            query.append("?,");
        }
        query.deleteCharAt(query.length() - 1);
        query.append(")");

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query.toString())) {
            int index = 1;
            for (Field field : fields) {
                field.setAccessible(true);
                statement.setObject(index++, field.get(instance));
            }
            statement.executeUpdate();
        }
    }

    public void update(T instance) throws SQLException, IllegalAccessException {
        StringBuilder query = new StringBuilder("UPDATE " + type.getSimpleName() + " SET ");
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            if (!field.getName().equals("id")) {
                query.append(field.getName()).append(" = ?,");
            }
        }
        query.deleteCharAt(query.length() - 1);
        Field idField = null;
        try {
            idField = type.getDeclaredField("id");
            query.append(" WHERE id = ?");
        } catch (NoSuchFieldException e) {
            // Handle the case where the class does not have a field named "id"
        }

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query.toString())) {
            int index = 1;
            for (Field field : fields) {
                field.setAccessible(true);
                if (!field.getName().equals("id")) {
                    statement.setObject(index++, field.get(instance));
                }
            }
            if (idField != null) {
                idField.setAccessible(true);
                statement.setObject(index, idField.get(instance));
            }
            statement.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM " + type.getSimpleName() + " WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}