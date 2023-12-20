package Objetos;

import java.sql.*;

public class ConexaoMySQL {
    private static String URL = "jdbc:mysql://localhost:3307/sisteminha_produtos_2";
    private static String USER = "root";
    private static String PASSWORD = "root";
    private Connection dbconn = null;
    private Statement sqlmgr = null;

    public void openDatabase() {
        try {
            dbconn = DriverManager.getConnection(URL, USER, PASSWORD);
            sqlmgr = dbconn.createStatement();
        } catch (Exception error) {
            error.printStackTrace();
        } 
    }

    public void closeDatabase() {
        try {
            if (sqlmgr != null) {
                sqlmgr.close();
            }
            if (dbconn != null) {
                dbconn.close();
            }
        } catch (Exception error) {
            error.printStackTrace();
        } 
    }    

    public int executeQuery(String sql) {
        try {
            return sqlmgr.executeUpdate(sql); // insert/delete/update/create
        } catch (Exception error) {
            error.printStackTrace();
        } 
        return -1;
    }

    public ResultSet executeSelectQuery(String sql) {
        ResultSet conjuntoDeResultados = null;
        try {
            conjuntoDeResultados = sqlmgr.executeQuery(sql);
        } catch (Exception error) {
            error.printStackTrace();
        }
        return conjuntoDeResultados;
    }

    public void closeResultSet(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (Exception error) {
            error.printStackTrace();
        } 
    }
}