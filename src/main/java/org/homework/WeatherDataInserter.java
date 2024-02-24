package org.homework;

import java.sql.*;
import java.util.Date;

public class WeatherDataInserter {

    public static void insertWeatherData(int regionId, Date date, double temperature, String precipitation) {
        String sql = "INSERT INTO Weather (region_id, date, temperature, precipitation) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, regionId);
            preparedStatement.setDate(2, new java.sql.Date(date.getTime()));
            preparedStatement.setDouble(3, temperature);
            preparedStatement.setString(4, precipitation);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Дані про погоду успішно вставлено.");
            } else {
                System.out.println("Не вдалося вставити дані про погоду.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
