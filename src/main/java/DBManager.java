import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBManager {
    final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static DBManager uniqueInstance=null;
    private static Connection connection = null ;
    DBManager(){
       if(!dbExists())
      {
          System.err.println(">>> DBManager: The database doesn't exist ...") ;
       }
   }
    public static synchronized DBManager getInstance() {
        if (uniqueInstance==null) {
            uniqueInstance = new DBManager();
        }
        return uniqueInstance;
    }
    private Boolean dbExists() {
        Boolean exists = false;
        Statement statement = null;
        try {
            if(connection == null) makeConnection();
            statement = connection.createStatement()
            ;
            statement.executeQuery( "USE world;" );
        }
        catch( SQLException se ) {
            se.printStackTrace();
        }
        finally {
            try {
                if(statement != null) {
                    statement.close();
                    exists = true;
                }
            }
            catch( SQLException se ) {
                se.printStackTrace();
                statement = null;
            }
        }
        return exists;
    }
    public void makeConnection()
    {
        FileInputStream in = null;
        try{
            Class.forName(JDBC_DRIVER);
            Properties props = new Properties();
            in = new FileInputStream("database.properties");
            props.load(in);
            in.close();
            String db_url = props.getProperty("jdbc.db_url");
            String db_params = props.getProperty("jdbc.db_params");
            String username = props.getProperty("jdbc.username");
            String password = props.getProperty("jdbc.password");

            connection = DriverManager.getConnection(db_url+db_params, username,
                    password);
        } catch( SQLException se ) {
            System.err.println("Connection error....") ;
        }catch ( Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null)
                    in.close();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            } // nothing we can do
        }
    }
    public void close() {
        try {

            connection.close();

            uniqueInstance=null;
            connection=null;
        } catch (SQLException
                e) {

            e.printStackTrace();
        }
    }
    public Connection
    getConnection() {
        return connection;
    }
}
