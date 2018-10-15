import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class CityDAO {
    private static CityDAO uniqueInstance = null;
    private static Connection connection = null;

    CityDAO(DBManager db) { //precondition dbExisis()
        if ((connection = db.getConnection()) == null) //connect to the city db
            System.err.println(">>> CityDAO: The database doesn't exist ...");
    }

    // apply singleton design pattern to CityDao
    public static synchronized CityDAO getInstance(DBManager db) {
        if (uniqueInstance == null)
            uniqueInstance = new CityDAO(db);
        return uniqueInstance;
    }

    public ArrayList<String> getCityNames(String cityName) {
        ArrayList<String> cityNames = new ArrayList<String>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM city WHERE name LIKE ?");
            preparedStatement.setString(1, cityName + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                cityNames.add(resultSet.getString("name"));
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }// nothing we can do
        }
        return cityNames;
    }
    public ArrayList<String> getCityNamesbyCountry(String country) {
        ArrayList<String> cityNames = new ArrayList<String>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("select City.name from City inner join Country on CountryCode=Country.Code && Country.Name like ?");
            preparedStatement.setString(1, country + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                cityNames.add(resultSet.getString("name"));
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }// nothing we can do
        }
        return cityNames;
    }
}