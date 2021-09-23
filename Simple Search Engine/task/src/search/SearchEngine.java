package search;

import java.util.*;

public class SearchEngine {

    private List<String> people;
    private Map<String, Set<Integer>> peopleInvertedIndex = new HashMap<>();

    public SearchEngine(List<String> people) {

        this.people = people;

        for (int i = 0; i < people.size(); i++) {

            String[] splitArr = people.get(i).split(" ");

            for (String value : splitArr) {

                Set<Integer> invertedIndexesSet = new HashSet<>();
                invertedIndexesSet.add(i);

                for (int j = 0; j < people.size(); j++) {
                    if (people.get(j).toLowerCase().contains(value.toLowerCase())) {
                        invertedIndexesSet.add(j);
                    }
                }

                peopleInvertedIndex.put(value.toLowerCase(), invertedIndexesSet);

            }

        }

        //System.out.println(peopleInvertedIndex);

    }

    public void run() {

        Scanner scanner = new Scanner(System.in);

        boolean exit = false;

        while (!exit) {

            System.out.println("=== Menu ===\n" +
                    "1. Find a person\n" +
                    "2. Print all people\n" +
                    "0. Exit");

            String option = scanner.nextLine();

            switch (option) {
                case "1":

                    String strategy = "";

                    while (true) {
                        System.out.println("Select a matching strategy: ALL, ANY, NONE");

                        strategy = scanner.nextLine();

                        if (strategy.matches("(ALL|ANY|NONE)")) {
                            break;
                        }
                    }

                    search(strategy);
                    break;

                case "2":

                    people.forEach(System.out::println);
                    break;

                case "0":

                    exit = true;
                    break;

                default:

                    System.out.println("Incorrect option! Try again.");
                    break;
            }


        }

    }

    public void search(String strategy) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a name or email to search all suitable people.");
        String searchString = scanner.nextLine().trim();
        String[] searchStringArray = searchString.split(" ");

        switch (strategy) {

            case "ALL":

                Set<Integer> intermediateResultSet;
                List<String> finalResultList = new LinkedList<>();

                for (String value : searchStringArray) {
                    if (!peopleInvertedIndex.containsKey(value.toLowerCase())) {
                        System.out.println("No matching people found.");
                        return;
                    }
                }

                intermediateResultSet = peopleInvertedIndex.get(searchStringArray[0].toLowerCase());

                for (Integer val : intermediateResultSet) {

                    int counter = 0;
                    String line = "";

                    for (String word : searchStringArray) {
                        line = people.get(val);
                        if (line.toLowerCase().contains(word.toLowerCase())) counter++;
                    }

                    if (counter == intermediateResultSet.size()) {
                        finalResultList.add(line);
                    }

                }

                System.out.println("\n" + finalResultList.size() + " persons found:");
                finalResultList.forEach(System.out::println);
                System.out.println();

                break;

            case "ANY":

                Set<String> anyResultSet = new HashSet<>();

                for (String value : searchStringArray) {
                    if (!peopleInvertedIndex.containsKey(value.toLowerCase())) {
                        System.out.println("No matching people found.");
                        return;
                    }
                }

                for (String word : searchStringArray) {

                    for (Integer value : peopleInvertedIndex.get(word.toLowerCase())) {
                        anyResultSet.add(people.get(value));
                    }

                }

                System.out.println("\n" + anyResultSet.size() + " persons found:");
                anyResultSet.forEach(System.out::println);
                System.out.println();

                break;


            case "NONE":

                Set<String> noneResult = new HashSet<>();

                for (Map.Entry entry : peopleInvertedIndex.entrySet()) {

                    for (String word : searchStringArray) {

                        if (!entry.getKey().equals(word.toLowerCase())) {

                            for (Integer val : (Set<Integer>) entry.getValue()) {
                                noneResult.add(people.get(val));
                            }

                        }
                    }
                }

                List<String> toDelete = new ArrayList<>();

                for (String line : noneResult) {
                    for (String word : searchStringArray) {
                        if (line.toLowerCase().contains(word.toLowerCase())) {
                            toDelete.add(line);
                        }
                    }
                }

                noneResult.removeAll(toDelete);

                if (noneResult.size() == 0) {
                    System.out.println("No matching people found.");
                    return;
                }

                System.out.println("\n" + noneResult.size() + " persons found:");
                noneResult.forEach(System.out::println);
                System.out.println();

                break;

        }


    }

}
