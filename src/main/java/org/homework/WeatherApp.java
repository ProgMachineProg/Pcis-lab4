package org.homework;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class WeatherApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final WeatherQueryExecutor queryExecutor = new WeatherQueryExecutor();
    private static final WeatherDataInserter dataInserter = new WeatherDataInserter();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== Меню програми погоди =====");
            System.out.println("1. Вивести інформацію про погоду в заданому регіоні");
            System.out.println("2. Вивести дати, коли в заданому регіоні йшов сніг і температура була нижчою від заданої негативної");
            System.out.println("3. Відображення інформації про погоду за мовою");
            System.out.println("4. Відображення середньої температури за областями");
            System.out.println("5. Добавити дані про погоду");
            System.out.println("0. Вихід");

            System.out.print("Введіть свій вибір: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayWeatherInfoByRegion();
                    break;
                case 2:
                    displaySnowyDays();
                    break;
                case 3:
                    displayWeatherInfoByLanguage();
                    break;
                case 4:
                    displayAverageTemperatureByArea();
                    break;
                case 5:
                    insertWeatherData();
                    break;
                case 0:
                    System.out.println("Вихід із програми Погода. До побачення!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Невірний вибір. Введіть правильний варіант.");
            }
        }
    }

    private static void displayWeatherInfoByRegion() {
        System.out.print("Введіть назву регіону: ");
        String regionName = scanner.next();
        queryExecutor.displayWeatherInfoByRegion(regionName);
    }

    private static void displaySnowyDays() {
        System.out.print("Введіть назву регіону: ");
        String regionName = scanner.next();
        System.out.print("Введіть температурний поріг: ");
        double temperatureThreshold = scanner.nextDouble();
        queryExecutor.displaySnowyDays(regionName, temperatureThreshold);
    }

    private static void displayWeatherInfoByLanguage() {
        System.out.print("Введіть мову: ");
        String language = scanner.next();
        queryExecutor.displayWeatherInfoByLanguage(language);
    }

    private static void displayAverageTemperatureByArea() {
        System.out.print("Введіть порогове значення області: ");
        double areaThreshold = scanner.nextDouble();
        queryExecutor.displayAverageTemperatureByArea(areaThreshold);
    }

    private static void insertWeatherData() {
        System.out.print("Введіть ідентифікатор регіону: ");
        int regionId = scanner.nextInt();
        System.out.print("Введіть дату (2024-02-22): ");
        String dateString = scanner.next();
        Date date = parseDate(dateString);
        System.out.print("Введіть температуру: ");
        double temperature = scanner.nextDouble();
        System.out.print("Введіть опади: ");
        String precipitation = scanner.next();

        dataInserter.insertWeatherData(regionId, date, temperature, precipitation);
    }

    private static Date parseDate(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Помилка аналізу дати. Будь ласка, використовуйте формат 2024-02-22.");
            return null;
        }
    }
}
