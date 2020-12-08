package com.microfocus.jdbc;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;


/**
 * This program demonstrates how to establish database connection to Microsoft SQL Server.
 *
 */
public class JdbcSQLServerConnection {

    public static void main(String[] args) {

        Connection conn = null;

        try {

            String jdbcUrl = null;
            String username = null;
            String password = null;

            String defaultDbURL = "jdbc:sqlserver://localhost\\sqlexpress";
            String defaultUser = "sa";
            String defaultPassword = "secret";

            Console console = System.console();
            if (console == null) {
                // we are in IDE
                try {
                    jdbcUrl = getInputInIDE("Enter database JDBC URL: ");
                    username = getInputInIDE("Enter username: ");
                    System.out.println("LOG: Running within IDE...");
                    System.out.println("LOG: Password will not be masked");
                    password = getInputInIDE("Enter password: ");
                    System.out.println("LOG: Password entered");
                } catch (IOException e) {
                    System.err.println("Error getting password" + e.getMessage());
                    System.exit(1);
                }
            } else {
                jdbcUrl = getJdbcUrl(console, "Enter database JDBC URL:");
                username = getUsername(console, "Enter username:");
                password = getPasswordMasked(console, "Enter password:");
            }

            conn = DriverManager.getConnection(jdbcUrl, username, password);
            if (conn != null) {
                DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static String getJdbcUrl(Console cons, String msg)
    {
        String url;
        while (true) {
            url = cons.readLine("%s", msg);
            if (url != null) {
                if (url.length() > 0) {
                    return url;
                } else {
                    System.out.println("Invalid input\n");
                }
            }
        }
    }

    public static String getUsername(Console cons, String msg)
    {
        String username;
        while (true) {
            username = cons.readLine("%s", msg);
            if (username != null) {
                if (username.length() > 0) {
                    return username;
                } else {
                    System.out.println("Invalid input\n");
                }
            }
        }
    }

    public static String getPasswordMasked(Console cons, String msg)
    {
        char[] passwd;
        while (true) {
            passwd = cons.readPassword("%s", msg);
            if (passwd != null) {
                if (passwd.length > 0) {
                    return new String(passwd);
                } else {
                    System.out.println("Invalid input\n");
                }
            }
        }
    }

    public static String getInputInIDE(String msg) throws IOException
    {
        System.out.print(msg);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                System.in));
        String input = reader.readLine();
        if (input != null) {
            if (input.length() <= 0) {
                System.out.println("Invalid input\n");
                throw new IOException("Error reading input");
            }
        }
        return input;
    }
}
