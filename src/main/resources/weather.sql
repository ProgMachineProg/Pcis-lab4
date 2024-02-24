CREATE DATABASE weather;

CREATE TABLE PopulationTypes (
                                 population_type_id SERIAL PRIMARY KEY,
                                 name VARCHAR(255) NOT NULL,
                                 language VARCHAR(255) NOT NULL
);

CREATE TABLE Regions (
                         region_id SERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         area DOUBLE PRECISION NOT NULL,
                         population_type_id INT,
                         FOREIGN KEY (population_type_id) REFERENCES PopulationTypes(population_type_id)
);

CREATE TABLE Weather (
                         weather_id SERIAL PRIMARY KEY,
                         region_id INT,
                         date DATE NOT NULL,
                         temperature DOUBLE PRECISION NOT NULL,
                         precipitation VARCHAR(255) NOT NULL,
                         FOREIGN KEY (region_id) REFERENCES Regions(region_id)
);

INSERT INTO PopulationTypes (name, language) VALUES
                                                 ('Urban', 'English'),
                                                 ('Suburban', 'Spanish'),
                                                 ('Rural', 'French');

INSERT INTO Regions (name, area, population_type_id) VALUES
                                                         ('City A', 1000.0, 1),
                                                         ('City B', 800.0, 2),
                                                         ('Village C', 500.0, 3);

INSERT INTO Weather (region_id, date, temperature, precipitation) VALUES
                                                                      (1, '2024-02-22', 25.0, 'Clear'),
                                                                      (1, '2024-02-23', 23.5, 'Snow'),
                                                                      (2, '2024-02-22', 20.0, 'Rain'),
                                                                      (2, '2024-02-23', 18.0, 'Snow'),
                                                                      (3, '2024-02-22', 15.0, 'Clear'),
                                                                      (3, '2024-02-23', 14.0, 'Snow');
