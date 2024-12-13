package Model.simulation.filewriter;

import Model.simulation.framework.Event;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A class for writing simulation data to CSV files.
 */
public class CSVWriter {
    private static String simulationFileName;
    private static String resultFileName;
    private static long seed;

    private static boolean firstWrite = true;
    private static boolean firstTicketResultWrite = true;
    private static boolean firstStationResultWrite = true;

    /**
     * Default constructor.
     */
    public CSVWriter() {}

    /**
     * Constructor with seed.
     * Initializes the file names based on the seed and creates the necessary directories.
     *
     * @param seed the seed for the simulation
     */
    public CSVWriter(long seed) {
        CSVWriter.seed = seed;

        String baseDirectory = System.getProperty("user.dir");

        simulationFileName = Paths.get(baseDirectory, "src", "main", "java", "Model", "simulation", "files", seed + "simulation.csv").toString();
        resultFileName = Paths.get(baseDirectory, "src", "main", "java", "Model", "simulation", "files", seed + "result.csv").toString();

        createDirectoryIfNotExists(Paths.get(baseDirectory, "src", "main", "java", "Model", "simulation", "files").toString());

        if (firstWrite) {
            write();
            firstWrite = false;
        }
    }

    /**
     * Creates the directory if it does not exist.
     *
     * @param directoryPath the path of the directory
     */
    private void createDirectoryIfNotExists(String directoryPath) {
        Path path = Paths.get(directoryPath);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Writes the header line to the simulation file.
     * This method writes the header "Event;Event Time" to the simulation file.
     * If an IOException occurs, it is caught and the stack trace is printed.
     */
    public void write() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(simulationFileName, true))) {
            String line = "Event;Event Time";
            bufferedWriter.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes the header line for ticket results to the result file.
     * This method writes the header ";Queue Size;Served;Service Time;" to the result file.
     * If an IOException occurs, it is caught and the stack trace is printed.
     */
    public void writeTicketResult() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(resultFileName, true))) {
            String line = ";Queue Size;Served;Service Time;\n";
            bufferedWriter.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes the header line for station results to the result file.
     * This method writes the header "Station;Passengers At Station;Served;Average Station Load Time;Stopped;Average Capacity;Average Train Travel Time;Average Loaded Capacity" to the result file.
     * If an IOException occurs, it is caught and the stack trace is printed.
     */
    public void writeStation() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(resultFileName, true))) {
            String line = "Station;Passengers At Station;Served;Average Station Load Time;Stopped;Average Capacity;Average Train Travel Time;Average Loaded Capacity\n";
            bufferedWriter.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes an event to the simulation file.
     * This method writes the event type and time to the simulation file.
     *
     * @param e the event to write
     */
    public void writeEvent(Event e) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(simulationFileName, true))) {
            String line = e.getType() + ";" + e.getTime() + "\n";
            bufferedWriter.write(line);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Writes the result of a ticket check to the result file.
     * This method writes the name, queue size, served count, and service time to the result file.
     *
     * @param name the name of the ticket check
     * @param queueSize the size of the queue
     * @param served the number of served passengers
     * @param serviceTime the service time
     */
    public void writeResultTicketCheck(String name, int queueSize, int served, double serviceTime) {
        if (firstTicketResultWrite) {
            writeTicketResult();
            firstTicketResultWrite = false;
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(resultFileName, true))) {
            String formattedServiceTime = String.format("%.2f", serviceTime);
            String line = name + ";" + queueSize + ";" + served + ";" + formattedServiceTime + "\n";
            bufferedWriter.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes the result of a train station to the result file.
     * This method writes the name, queue size, served count, service time, total trains, average capacity, average travel time, and average loaded capacity to the result file.
     *
     * @param name the name of the train station
     * @param queueSize the size of the queue
     * @param served the number of served passengers
     * @param serviceTime the service time
     * @param totalTrains the total number of trains
     * @param averageCapacity the average capacity
     * @param averageTravelTime the average travel time
     * @param averageLoadedCapacity the average loaded capacity
     */
    public void writeResultTrainStation(String name, int queueSize, int served, double serviceTime, int totalTrains, double averageCapacity, double averageTravelTime, double averageLoadedCapacity) {
        if (firstStationResultWrite) {
            writeStation();
            firstStationResultWrite = false;
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(resultFileName, true))) {
            String formattedServiceTime = String.format("%.2f", serviceTime);
            String formattedAverageCapacity = String.format("%.2f", averageCapacity);
            String formattedAverageTravelTime = String.format("%.2f", averageTravelTime);
            String formattedAverageLoadedCapacity = String.format("%.2f", averageLoadedCapacity);
            String line = name + ";" + queueSize + ";" + served + ";" + formattedServiceTime + ";" + totalTrains + ";" + formattedAverageCapacity + ";" + formattedAverageTravelTime + ";" + formattedAverageLoadedCapacity + "\n";
            bufferedWriter.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}