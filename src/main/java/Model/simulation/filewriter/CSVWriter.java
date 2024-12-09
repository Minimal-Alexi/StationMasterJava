package Model.simulation.filewriter;

import Model.simulation.framework.Event;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CSVWriter {
    private String simulationFileName;
    private String resultFileName;

    private static boolean firstWrite = true;
    private static boolean firstTicketResultWrite = true;
    private static boolean firstStationResultWrite = true;

    public CSVWriter() {
        String baseDirectory = System.getProperty("user.dir");

        simulationFileName = Paths.get(baseDirectory, "src", "main", "java", "Model", "simulation", "files", "simulation.csv").toString();
        resultFileName = Paths.get(baseDirectory, "src", "main", "java", "Model", "simulation", "files", "result.csv").toString();

        createDirectoryIfNotExists(Paths.get(baseDirectory, "src", "main", "java", "Model", "simulation", "files").toString());

        if (firstWrite) {
            write();
            firstWrite = false;
        }
    }

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

    public void write() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(simulationFileName, true))) {
            String line = "Event;Event Time";
            bufferedWriter.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeTicketResult() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(resultFileName, true))) {
            String line = ";Queue Size;Served;Service Time;\n";
            bufferedWriter.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeStation() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(resultFileName, true))) {
            String line = "Station;Passengers At Station;Served;Average Station Load Time;Stopped;Average Capacity;Average Train Travel Time;Average Loaded Capacity\n";
            bufferedWriter.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeEvent(Event e) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(simulationFileName, true))) {
            String line = e.getType() + ";" + e.getTime() + "\n";
            bufferedWriter.write(line);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

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
