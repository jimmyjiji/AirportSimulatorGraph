import latlng.LatLng;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;


/**
 * Created by Jimmy on 5/4/2015 in PACKAGE_NAME
 * 109259420
 * Homework 5
 * jimmy.ji@stonybrook.edu
 * Recitation 3: Sun Lin
 */
public class SigmaAir implements Serializable {
    private ArrayList<City> cities = new ArrayList<City>(100);
    public static final int MAX_CITIES = 100;
    private double [][] connections;
    private double[][] dist;

    /**
     * Constructor that initializes the adjacency matrix when created.
     */
    public SigmaAir() {
        connections = new double[MAX_CITIES][MAX_CITIES];
        for(int i = 0; i < MAX_CITIES; i++) {
            for (int j = 0; j < MAX_CITIES; j++) {
                connections[i][j] = Double.POSITIVE_INFINITY;
            }
        }
    }

    /**
     * returns the cities list
     * @return the cities list
     */
    public ArrayList<City> getCities() {
        return cities;
    }

    /**
     * Adds a city if it doesn't already exist in the array list
     * @param city is the city to be added
     */
    public void addCity(City city) {
        int position = city.getIndexPos();
        if (connections[position][position] == Double.POSITIVE_INFINITY && !cityExists(city.getName())) {
            connections[position][position] = 0;
            cities.add(city.getIndexPos(), city);
            System.out.println(city.getName()+ " has been added: ("+city.getLocation().getLat()+ ", " +city.getLocation().getLng()+ ")");
            //System.out.println(city.cityCount);
        } else {
            System.out.println("This city already exists");
            city.cityCount--;
        }
    }

    /**
     * Adds a connection between two cities if they exist in the matrix
     * @param cityFrom is the source city
     * @param cityTo is the destination city
     * @throws IOException when the city cannot be found
     */
    public void addConnection(City cityFrom, City cityTo) throws IOException {
        if (connections[cityFrom.getIndexPos()][cityFrom.getIndexPos()] == 0 && connections[cityTo.getIndexPos()][cityTo.getIndexPos()] == 0) {
            double distance = LatLng.calculateDistance(cityFrom.getLocation(), cityTo.getLocation());
            connections[cityFrom.getIndexPos()][cityTo.getIndexPos()] = distance;
            System.out.println(cityFrom.getName()+ " --> " +cityTo.getName() + " has been added!: " +distance);
        } else {
            System.out.println("One of these cities does not exist in the matrix. User must add the city to add a connection");
        }
    }

    /**
     * Removes the connection between two cities if it exists
     * @param cityFrom is the source city
     * @param cityTo is the destination city
     */
    public void removeConnection(City cityFrom, City cityTo) {
        if (connections[cityFrom.getIndexPos()][cityTo.getIndexPos()] != Double.POSITIVE_INFINITY ) {
            connections[cityFrom.getIndexPos()][cityTo.getIndexPos()] = Double.POSITIVE_INFINITY;
            System.out.println("Connection from " +cityFrom.getName()+ " to " +cityTo.getName()+ " has been removed!");
        } else {
            System.out.println("The connection does not exist in the matrix");
        }

    }

    /**
     * Prints all the cities
     * @param comp is the comparator that is used to print all the cities
     */
    public void printAllCities(Comparator<City> comp) {
        ArrayList<City> clone = (ArrayList<City>) cities.clone();
        Collections.sort(cities, comp);
        System.out.println("Cities: \n" +
                "City Name                  Latitude        Longitude");
        System.out.println("----------------------------------------------------------");
        for (City city : cities) {
            System.out.println(city);
        }
        cities = clone;
    }

    /**
     * Prints all the connections present in the matrix
     */
    public void printAllConnections() {
        System.out.println("Connections:\n" +
                "Route                               Distance\n" +
                "----------------------------------------------------------");
        for(int i = 0; i < MAX_CITIES; i++) {
            for (int j = 0; j < MAX_CITIES; j++) {
                if (connections[i][j] > 0 && connections [i][j] != Double.POSITIVE_INFINITY) {
                    String print = cities.get(i).getName()+ " --> " +cities.get(j).getName();
                    System.out.printf("%-35s%-10f",print, connections[i][j]);
                    System.out.println();
                }
            }
        }
    }

    /**
     * Loads the cities from a file
     * @param filename is the file to be loaded from
     * @throws FileNotFoundException when the file cannot be found
     * @throws IOException when the city doesn't exist
     */
    public void loadAllCities(String filename) throws FileNotFoundException, IOException {
        Scanner input = new Scanner(new File(filename));
        while(input.hasNext()) {
            addCity(new City(input.nextLine()));
        }
    }

    /**
     * checks the array list if the city exists
     * @param city is the city to be checked in the list
     * @return true if the city exists. false otherwise.
     */
    public boolean cityExists(String city) {
        for (City city1 : cities) {
            if (city.equalsIgnoreCase(city1.getName()))
                return true;
        }
        return false;
    }

    /**
     * Obtains city through string
     * @param city is string variable that is searched in the array list
     * @return city object if found
     */
    public City obtainCity(String city) {
        for (City city1 : cities) {
            if (city.equalsIgnoreCase(city1.getName()))
                return city1;
        }
        return null;
    }

    /**
     * Loads all connections of cities from a file
     * @param filename is the file to be loaded from
     * @throws FileNotFoundException when the file cannot be found
     * @throws IOException when the city does not exist
     */
    public void loadAllConnections(String filename) throws FileNotFoundException, IOException {
        Scanner input = new Scanner(new File(filename));
        input.useDelimiter(",");
        while (input.hasNext()) {
            String city = input.next();
            String city1 = input.nextLine().substring(1);
            if (cityExists(city) && cityExists(city1)) {
                addConnection(obtainCity(city), obtainCity(city1));
            } else {
                System.out.println("Error adding connection: " +city+ " --> " +city1);
            }
        }
    }

    /**
     * Finds the shortest path between two cities
     * @param cityFrom is the source city
     * @param cityTo is the destination city
     */
    public void shortestPath(City cityFrom, City cityTo) {
        int v = cities.size();
        dist = new double[v][v];
        int[][] next = new int[v][v];

        int from = cityFrom.getIndexPos();
        int to = cityTo.getIndexPos();

        if (from == -1 || to == -1) {
            System.out.println("Shortest path from " + cityFrom + " to " + cityTo + " does not exist\n");
            return;
        }
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < v; j++) {
                dist[i][j] = connections[i][j];
            }
        }

        for (int k = 0; k < v; k++) {
            for (int i = 0; i < v; i++) {
                for (int j = 0; j < v; j++) {
                    if(dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = k;
                    }
                }
            }
        }

        int middle = next[from][to];
        String mid = "";
        while (!cities.get(from).getName().equals(cities.get(middle).getName())) {
            mid += (cities.get(middle).getName() + "#");
            middle = next[from][middle];
        }

        String s = cities.get(to).getName() + "#" + mid + cities.get(from).getName() + "#" + dist[from][to];
        System.out.print("The shortest path from " +cityFrom.getName()+ " to " +cityTo.getName()+ " is: ");
        String []array = s.split("#");
        for (int i = array.length-2; i > 0; i--) {
            System.out.print(array[i] + "--> ");
        }
        System.out.printf(array[0] + ": " + array[array.length-1] + "\n");
    }
}
