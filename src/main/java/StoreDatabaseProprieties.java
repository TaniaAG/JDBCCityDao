import java.io.FileOutputStream;
import java.util.Properties;

public class StoreDatabaseProprieties {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("jdbc.username", "tania3");
        props.setProperty("jdbc.password", "1234");
        props.setProperty("jdbc.db_url", "jdbc:mysql://localhost/world");
        props.setProperty("jdbc.db_params","?autoReconnect=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");

        FileOutputStream out = null;
        try {
            out = new FileOutputStream("database.properties");
            props.store(out, "Database info");
            out.close();
        }
        catch (Exception e) {

        }
    }
}
