import java.io.*;
import java.util.Scanner;

/**
 * Created by Jimmy on 5/5/2015 in PACKAGE_NAME
 * 109259420
 * Homework 5
 * jimmy.ji@stonybrook.edu
 * Recitation 3: Sun Lin
 */
public class SigmaAirDriver implements Serializable {
    public static void main(String[] args) throws ClassNotFoundException {
        SigmaAir runner;
        runner = readFromDisk();

        while(true) {
            try {
                run(runner);
                System.out.println();
            } catch (FileNotFoundException ex) {
                System.out.println("\nFile is not found\n");
            } catch (IOException ex) {
                System.out.println("\nInvalid input/output\n");
            } catch (IllegalArgumentException ex) {
                System.out.println("\nInvalid Input\n");
            } catch (NullPointerException ex) {
                System.out.println("\nInvalid Input\n");
            } catch (ClassNotFoundException ex) {
                System.out.println("\nInvalid Input\n");
            }
        }
    }

    /**
     * Reads from an existing sigma_air.obj
     * @return the SigmaAir object written on sigma_air.obj
     * @throws ClassNotFoundException if the file cannot be found
     */
    public static SigmaAir readFromDisk() throws ClassNotFoundException{
        SigmaAir myLibrary;
        try {
            FileInputStream file = new FileInputStream("sigma_air.obj");
            ObjectInputStream fin  = new ObjectInputStream(file);
            myLibrary = (SigmaAir) fin.readObject(); //readObject() returns Object, so must typecast to HashedLibrary
            System.out.println("Found file sigma_air.obj");
            fin.close();
        } catch(IOException ex){
            myLibrary = new SigmaAir();
            System.out.println("File not found. New SigmaAir object will be created");
        }

        return myLibrary;
    }

    /**
     * Writes the SigmaAir object to sigma_air.obj
     * @param myLibrary is the SigmaAir object to be written from.
     */
    public static void writeToDisk(SigmaAir myLibrary) {
        try {
            FileOutputStream file = new FileOutputStream("sigma_air.obj");
            ObjectOutputStream fout = new ObjectOutputStream(file);
            fout.writeObject(myLibrary); //Writes myLibrary to sigma_air.obj
            fout.close();
        } catch (IOException e){
            System.out.println("Invalid input");
        }
    }

    /**
     * Runs the menu of  the SigmaAirDriver
     * @param airport is the SigmaAir object used to run
     * @throws IOException when the city doesn't exist
     * @throws FileNotFoundException when the file doesn't exist
     * @throws ClassNotFoundException when something is wrong with the reading from disk
     */
    public static void run(SigmaAir airport) throws IOException, FileNotFoundException, ClassNotFoundException {
        Scanner input = new Scanner(System.in);
        System.out.print("(A) Add City\n" +
                "(B) Add Connection\n" +
                "(C) Load all Cities\n" +
                "(D) Load all Connections\n" +
                "(E) Print all Cities\n" +
                "(F) Print all Connections\n" +
                "(G) Remove Connection\n" +
                "(H) Find Shortest Path\n" +
                "(Q) Quit \n" + "Enter a selection:");

        char c = Character.toLowerCase(input.nextLine().charAt(0));
        switch(c) {
            case 'a':
                System.out.print("Enter the name of the city:");
                String city = input.nextLine();
                City cityCreator = new City(city);
                City.setCityCount(airport.getCities().size());
                cityCreator.setIndexPos(airport.getCities().size());
                airport.addCity(cityCreator); break;
            case 'b':
                System.out.print("Enter source city:");
                String source = input.nextLine();
                System.out.print("Enter destination city:");
                String destination = input.nextLine();
                airport.addConnection(airport.obtainCity(source), airport.obtainCity(destination)); break;
            case 'c':
                System.out.print("Enter the file name to load cities:");
                String filename = input.nextLine();
                City cityCreator1 = new City();
                City.setCityCount(airport.getCities().size());
                cityCreator1.setIndexPos(airport.getCities().size());
                airport.loadAllCities(filename); break;
            case 'd':
                System.out.print("Enter the file name to load connections:");
                String filename1 = input.nextLine();
                airport.loadAllConnections(filename1); break;
            case 'e':
                String selection = "";
                while (!selection.equalsIgnoreCase("Q")) {
                    System.out.println("(EA) Sort Cities by Name\n" +
                            "(EB) Sort Cities by Latitude\n" +
                            "(EC) Sort Cities by Longitude\n" +
                            "(Q) Quit");
                    System.out.print("Enter a selection:");
                    selection = input.nextLine();
                    if (selection.equalsIgnoreCase("EA"))
                        airport.printAllCities(new NameComparator());
                    else if (selection.equalsIgnoreCase("EB"))
                        airport.printAllCities(new LatComparator());
                    else if (selection.equalsIgnoreCase("EC"))
                        airport.printAllCities(new LngComparator());
                    else if (selection.equalsIgnoreCase("Q"))
                        System.out.println("Quitting print menu...");
                    else
                        throw new IllegalArgumentException("Invalid input");
                } break;
            case 'f':
                airport.printAllConnections(); break;
            case 'g':
                System.out.print("Enter a source city:");
                String source1 = input.nextLine();
                System.out.print("Enter a destination city:");
                String destination1 = input.nextLine();
                airport.removeConnection(airport.obtainCity(source1), airport.obtainCity(destination1)); break;
            case 'h':
                System.out.print("Enter a source city:");
                String source2 = input.nextLine();
                System.out.print("Enter a destination city:");
                String destination2 = input.nextLine();
                airport.shortestPath(airport.obtainCity(source2), airport.obtainCity(destination2));
                break;
            case 'q':
                System.out.println("Saving files...");
                writeToDisk(airport);
                System.exit(1); break;
        }
    }
}
