package org.homework;

import java.sql.*;
import java.util.Date;
public class WeatherQueryExecutor {

    public static void displayWeatherInfoByRegion(String regionName) {
        String sql = "SELECT w.*, r.name as region_name, p.name as population_type_name " +
                "FROM Weather w " +
                "JOIN Regions r ON w.region_id = r.region_id " +
                "JOIN PopulationTypes p ON r.population_type_id = p.population_type_id " +
                "WHERE r.name = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, regionName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int weatherId = resultSet.getInt("weather_id");
                    int regionId = resultSet.getInt("region_id");
                    Date date = resultSet.getDate("date");
                    double temperature = resultSet.getDouble("temperature");
                    String precipitation = resultSet.getString("precipitation");
                    String regionNameFromDB = resultSet.getString("region_name");
                    String populationTypeName = resultSet.getString("population_type_name");

                    // Виведення інформації про погоду в заданому регіоні
                    System.out.println("ID погоди: " + weatherId);
                    System.out.println("ID регіону: " + regionId);
                    System.out.println("Назва регіону: " + regionNameFromDB);
                    System.out.println("Тип населення: " + populationTypeName);
                    System.out.println("Дата: " + date);
                    System.out.println("Температура: " + temperature);
                    System.out.println("Опади: " + precipitation);
                    System.out.println("---------------------------");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void displaySnowyDays(String regionName, double temperatureThreshold) {
        String sql = "SELECT w.date " +
                "FROM Weather w " +
                "JOIN Regions r ON w.region_id = r.region_id " +
                "WHERE r.name = ? AND w.precipitation = 'Snow' AND w.temperature < ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, regionName);
            preparedStatement.setDouble(2, temperatureThreshold);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("Снігові дні в " + regionName + " з температурою нижче " + temperatureThreshold + ":");
                while (resultSet.next()) {
                    Date date = resultSet.getDate("date");
                    System.out.println(date);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void displayWeatherInfoByLanguage(String language) {
        String sql = "SELECT w.*, r.name as region_name " +
                "FROM Weather w " +
                "JOIN Regions r ON w.region_id = r.region_id " +
                "JOIN PopulationTypes p ON r.population_type_id = p.population_type_id " +
                "WHERE p.language = ? AND w.date >= current_date - interval '7 days'";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, language);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("Інформація про погоду по регіонах, де говорять жителі " + language + " минулого тижня:");
                while (resultSet.next()) {
                    int weatherId = resultSet.getInt("weather_id");
                    int regionId = resultSet.getInt("region_id");
                    Date date = resultSet.getDate("date");
                    double temperature = resultSet.getDouble("temperature");
                    String precipitation = resultSet.getString("precipitation");
                    String regionName = resultSet.getString("region_name");

                    // Вывод информации о погоде
                    System.out.println("ID погоди: " + weatherId);
                    System.out.println("ID регіону: " + regionId);
                    System.out.println("Назва регіону: " + regionName);
                    System.out.println("Дата: " + date);
                    System.out.println("Температура: " + temperature);
                    System.out.println("Опади: " + precipitation);
                    System.out.println("---------------------------");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void displayAverageTemperatureByArea(double areaThreshold) {
        String sql = "SELECT AVG(w.temperature) as avg_temperature, r.name as region_name " +
                "FROM Weather w " +
                "JOIN Regions r ON w.region_id = r.region_id " +
                "WHERE r.area > ? AND w.date >= current_date - interval '7 days' " +
                "GROUP BY r.name";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setDouble(1, areaThreshold);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("Середня температура в регіонах, площа яких перевищує " + areaThreshold + " минулого тижня:");
                while (resultSet.next()) {
                    String regionName = resultSet.getString("region_name");
                    double avgTemperature = resultSet.getDouble("avg_temperature");

                    // Вывод информации о средней температуре
                    System.out.println("Назва регіону: " + regionName);
                    System.out.println("Середня температура: " + avgTemperature);
                    System.out.println("---------------------------");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
