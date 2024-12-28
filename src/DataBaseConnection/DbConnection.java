package DataBaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
        private static final String url= "jdbc:mysql://localhost:3306/weatherapp";
        private static final String username="root";
        private static final String pass="Boobathi776##";
        public static Connection getConnection()   // this method going to return the connection object
        {                                                          // so return type  Connection class
            try {
                return DriverManager.getConnection(url,username,pass);
            } catch (SQLException e) {
                System.out.println("Some issue in Dbconnection class ");
                return null;
            }

        }
    }

