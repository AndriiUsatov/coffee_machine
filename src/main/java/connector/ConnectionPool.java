package connector;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

public class ConnectionPool {

    private static ConnectionPool connector;
    private InitialContext initialContext;
    private Context context;
    private DataSource dataSource;
    private static final Logger logger = Logger.getLogger(ConnectionPool.class);

    private ConnectionPool() {
        init();
    }

    private void init() {
        try {
            initialContext = new InitialContext();
            context = (Context) initialContext.lookup("java:comp/env");
            dataSource = (DataSource) context.lookup("jdbc/coffee_machine");
        } catch (NamingException e) {
            logger.log(Level.ERROR, "Exception", e);
            e.printStackTrace();
        }
    }

    public static ConnectionPool getConnector() {
        if (connector == null) {
            synchronized (ConnectionPool.class) {
                if (connector == null) {
                    connector = new ConnectionPool();
                }
            }
        }
        return connector;
    }

    public synchronized Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception", e);
            e.printStackTrace();
        }
        return connection;
    }
}
