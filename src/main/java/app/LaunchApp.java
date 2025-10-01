package app;

import app.database.MySQL;
import app.javafx.VisorCitasApp;

import java.sql.Connection;

public class LaunchApp {
    public static void main(String[] args) {
        try {
            forceInitDatabase();
        } catch (Exception e) {
            System.out.println("Base de datos ya inicializada");
        }
        VisorCitasApp.launchApp();
    }

    private static void forceInitDatabase(){
        MySQL mySQL = MySQL.getInstance("localhost", "3306", "root", "toor", "");
        Connection mySQLConnection = mySQL.getConnection();

        mySQL.execute(mySQLConnection, "DROP DATABASE P1DBCESAR");

        initDatabase();
    }

    private static void initDatabase(){
        MySQL mySQL = MySQL.getInstance("localhost", "3306", "root", "toor", "");
        Connection mySQLConnection = mySQL.getConnection();

        mySQL.execute(mySQLConnection, "CREATE DATABASE IF NOT EXISTS P1DBCESAR");
        mySQL.execute(mySQLConnection, "USE P1DBCESAR");
        mySQL.execute(mySQLConnection, "CREATE TABLE IF NOT EXISTS USUARIO(email VARCHAR(40) PRIMARY KEY, password VARCHAR(40), idUsuario INT)");
        mySQL.execute(mySQLConnection, "CREATE TABLE IF NOT EXISTS PACIENTE(idPaciente INT PRIMARY KEY, dni VARCHAR(9), nombre VARCHAR(60), direccion VARCHAR(100), telefono INT)");
        mySQL.execute(mySQLConnection, "CREATE TABLE IF NOT EXISTS ESPECIALIDAD(idEspecialidad INT PRIMARY KEY, nombre VARCHAR(40))");
        mySQL.execute(mySQLConnection, "CREATE TABLE IF NOT EXISTS CITA(idCita INT AUTO_INCREMENT PRIMARY KEY, idPaciente INT, idEspecialidad INT, fecha Date)");

        mySQL.execute(mySQLConnection, "INSERT INTO USUARIO VALUES('usuario1@example.com', 'admin', 1)");
        mySQL.execute(mySQLConnection, "INSERT INTO USUARIO VALUES('usuario2@example.com', 'root', 2)");
        mySQL.execute(mySQLConnection, "INSERT INTO USUARIO VALUES('usuario3@example.com', 'user', 3)");

        mySQL.execute(mySQLConnection, "INSERT INTO PACIENTE VALUES(1, '12431897Y', 'César Prieto Calvo', 'C/Paseo Zorrilla 1', 655234612)");
        mySQL.execute(mySQLConnection, "INSERT INTO PACIENTE VALUES(2, '15623256X', 'Juan Diego Gomez Mejilla', 'C/Mirabel 1', 684178653)");
        mySQL.execute(mySQLConnection, "INSERT INTO PACIENTE VALUES(3, '12052353T', 'Marta Lopez Vargas', 'C/Lopez Gomez 1', 603893412)");

        mySQL.execute(mySQLConnection, "INSERT INTO ESPECIALIDAD VALUES(1, 'Cirugia')");
        mySQL.execute(mySQLConnection, "INSERT INTO ESPECIALIDAD VALUES(2, 'Medicina')");
        mySQL.execute(mySQLConnection, "INSERT INTO ESPECIALIDAD VALUES(3, 'Enfermeria')");
        mySQL.execute(mySQLConnection, "INSERT INTO ESPECIALIDAD VALUES(4, 'Matrona')");
        mySQL.execute(mySQLConnection, "INSERT INTO ESPECIALIDAD VALUES(5, 'Alergologia')");

        mySQL.execute(mySQLConnection, "INSERT INTO CITA(idPaciente, idEspecialidad, fecha) VALUES(1, 5, '2025-09-22')");
        mySQL.execute(mySQLConnection, "INSERT INTO CITA(idPaciente, idEspecialidad, fecha) VALUES(1, 5, '2025-09-22')");
        mySQL.execute(mySQLConnection, "INSERT INTO CITA(idPaciente, idEspecialidad, fecha) VALUES(2, 5, '2025-09-22')");
        mySQL.execute(mySQLConnection, "INSERT INTO CITA(idPaciente, idEspecialidad, fecha) VALUES(2, 5, '2025-09-22')");
        mySQL.execute(mySQLConnection, "INSERT INTO CITA(idPaciente, idEspecialidad, fecha) VALUES(3, 5, '2025-09-22')");
        mySQL.execute(mySQLConnection, "INSERT INTO CITA(idPaciente, idEspecialidad, fecha) VALUES(3, 5, '2025-09-22')");


    }
}
