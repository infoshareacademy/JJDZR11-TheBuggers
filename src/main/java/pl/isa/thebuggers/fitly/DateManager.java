package pl.isa.thebuggers.fitly;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DateManager {
    private List<LocalDateTime> dates;
    public DateManager() {
        this.dates = new ArrayList<>();
    }
    public void addDate(LocalDateTime date) {
        dates.add(date);
    }
    public void removeOldDates() {
        LocalDateTime now = LocalDateTime.now();
        Iterator<LocalDateTime> iterator = dates.iterator();
        while (iterator.hasNext()) {
            LocalDateTime date = iterator.next();
            if (date.isBefore(now)) {
                iterator.remove();
            }
        }
    }
    public void saveDatesToFile(String fileName) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (LocalDateTime date : dates) {
                writer.println(date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            }
        }
    }
    public void loadDatesFromFile(String fileName) throws IOException {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNext()) {
                LocalDateTime date = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            }
        }
    }
    public void printDates() {
        for (LocalDateTime date : dates) {
            System.out.println(date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }


    }

    public void deleteDateFromFile(String fileName, LocalDateTime date) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }
            reader.close();

            String modifiedContent = content.toString().replace(date.toString(), "");

            FileWriter writer = new FileWriter(fileName);
            writer.write(modifiedContent);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    public static void main(String[] args) throws IOException {
        DateManager dateManager = new DateManager();
        // Dodajemy przykłładowe daty
        dateManager.addDate(LocalDateTime.of(2023, 6, 25, 10, 30));
        dateManager.addDate(LocalDateTime.of(2023, 3, 26, 15, 45));
        dateManager.addDate(LocalDateTime.of(2023, 8, 27, 8, 0));
        // Usuwamy daty starsze niż obecna data i godzina
        dateManager.removeOldDates();
        // Zapisujemy daty do pliku
        dateManager.saveDatesToFile("dates.txt");

        //Niestety nie mam pojęcia dlaczego nie usuwa sekund :00, przez co się wywala :/
        //dateManager.deleteDateFromFile("dates.txt",LocalDateTime.of(2023,6,25,10,30,00));

        // Wczytujemy daty z pliku
        dateManager.loadDatesFromFile("dates.txt");



        // Wyświetlamy daty
        dateManager.printDates();


    }
}