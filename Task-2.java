import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class WordCounter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputText = "";

        System.out.println("Word Counting Program");

        while (true) {
            System.out.println("\nEnter '1' to enter a text or '2' to provide a file:");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter the text: ");
                    inputText = scanner.nextLine();
                    break;
                case 2:
                    System.out.print("Enter the file path: ");
                    String filePath = scanner.nextLine();
                    try {
                        inputText = readFile(filePath);
                    } catch (FileNotFoundException e) {
                        System.out.println("Error: File not found. Please try again.");
                        continue;
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    continue;
            }

            // Split the input text into an array of words using space or punctuation as delimiters
            String[] words = inputText.split("[\\s.,!?\":;()\\[\\]{}]+");

            // Initialize a counter variable to keep track of the number of words
            int wordCount = 0;

            // Create a map to store the frequency of each word
            Map<String, Integer> wordFrequency = new HashMap<>();

            // Iterate through the array of words and count the occurrences of each word
            for (String word : words) {
                if (!word.isBlank()) {
                    word = word.toLowerCase(); // Convert word to lowercase to treat "Word" and "word" as the same
                    wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
                    wordCount++;
                }
            }

            // Display the total count of words to the user
            System.out.println("\nTotal word count: " + wordCount);

            // Display the number of unique words
            System.out.println("Number of unique words: " + wordFrequency.size());

            // Display the frequency of each word
            System.out.println("\nWord Frequency:");
            for (String word : wordFrequency.keySet()) {
                System.out.println(word + ": " + wordFrequency.get(word));
            }

            // Prompt the user to play again or exit the program
            System.out.print("\nDo you want to continue? (yes/no): ");
            String playAgainResponse = scanner.nextLine();
            if (!playAgainResponse.equalsIgnoreCase("yes")) {
                break;
            }
        }

        System.out.println("Exiting the Word Counting Program.");
        scanner.close();
    }

    private static String readFile(String filePath) throws FileNotFoundException {
        StringBuilder content = new StringBuilder();
        File file = new File(filePath);
        Scanner fileScanner = new Scanner(file);

        while (fileScanner.hasNextLine()) {
            content.append(fileScanner.nextLine()).append("\n");
        }

        fileScanner.close();
        return content.toString();
    }
}
