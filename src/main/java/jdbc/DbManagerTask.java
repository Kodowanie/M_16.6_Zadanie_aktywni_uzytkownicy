package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public enum DbManagerTask {
    INSTANCE;
    private Connection conn;
    DbManagerTask(){
        Properties connectionProps = new Properties();
        connectionProps.put("user", "userAdmin");
        connectionProps.put("password", "Pass123");
        try{
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/task" +
                            "?serverTimezone=Europe/Warsaw" +
                            "&useSSL=False",
                    connectionProps);
        } catch (SQLException e){
            throw new ExceptionInInitializerError(e);
        }
    }
    public static DbManagerTask getInstance(){return INSTANCE;}
    public Connection getConnection(){ return conn;}

}
