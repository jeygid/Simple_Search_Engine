package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String file = "";

        for (int i = 0; i < args.length; i++) {
            if (args[0].equals("--data")) file = args[1];
        }

        if (file.equals("")) return;

        File peopleFile = new File(file);

        List<String> people = new LinkedList<>();

        try (Scanner scanner = new Scanner(peopleFile)) {
            while (scanner.hasNext()) {
                people.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        SearchEngine se = new SearchEngine(people);
        se.run();


    }
}
