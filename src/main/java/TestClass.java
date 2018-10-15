import java.util.Scanner;

public class TestClass {
    public static void main(String[] args){
        DBManager dbManager=new DBManager();
        dbManager.makeConnection();
        CityDAO cityDAO=new CityDAO(dbManager);
        System.out.println("The cities which begin with \"En\" are:"+cityDAO.getCityNames("En")+".");
        System.out.println("Give the string that you want the city to begin with: ");
        Scanner scanner = new Scanner(System.in);
        String city = scanner.nextLine();
        System.out.println("The cities which begin with \""+city+"\" are:"+cityDAO.getCityNames(city)+".");
        System.out.println("Enter a country: ");
        String country=scanner.nextLine();
        System.out.println("The cities which are in "+ country+" are:"+cityDAO.getCityNamesbyCountry(country)+".");
    }
}
