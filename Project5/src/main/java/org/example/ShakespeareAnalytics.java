package org.example;

// Author: Khushi Bhuwania
// Email: kbhuwani@andrew.cmu.edu
// This application conducts various analyses on William Shakespeare's "All's Well That Ends Well"
//It computes the number of lines, words, symbols, distinct words, distinct symbols, and distinct letters. It also provides an interactive feature to search for lines containing a specific word.

import java.util.Arrays;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.FlatMapFunction;

public class ShakespeareAnalytics {
    public static void main(String[] args) {
        // Initialize Spark context with the application name "Shakespeare Analytics"
        JavaSparkContext sc = new JavaSparkContext("local", "Shakespeare Analytics");

        // Load the text file into an RDD where each element represents a line from the text
        JavaRDD<String> lines = sc.textFile("AllsWellThatEndsWell.txt");

        // Task 0
        // Calculate the total number of lines in the text
        long lineCount = lines.count();
        System.out.println("Number of lines: " + lineCount);

        // Task 1
        // Split each line into words using a regex delimiter that targets non-letter characters
        JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterable<String> call(String line) {
                // Split the line based on non-alphabetic characters
                String[] splitWords = line.split("[^a-zA-Z]+");
                return Arrays.asList(splitWords);
            }
        });

        // Remove any empty strings resulting from the split operation
        JavaRDD<String> filteredWords = words.filter(new Function<String, Boolean>() {
            @Override
            public Boolean call(String word) {
                // Retain the word only if it's not empty
                Boolean isWordEmpty = !word.isEmpty();
                return isWordEmpty;
            }
        });

        // Compute the total number of words
        long wordCount = filteredWords.count();
        System.out.println("Number of words: " + wordCount);

        // Task 2
        // Determine the number of unique words
        long distinctWordCount = filteredWords.distinct().count();
        System.out.println("Number of distinct words: " + distinctWordCount);

        // Task 3
        // Split each line into individual symbols (characters), including handling empty lines
        JavaRDD<String> symbols = lines.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterable<String> call(String line) {
                List<String> symbolList = new ArrayList<String>();
                if (line.length() == 0) {
                    // Represent empty lines with a newline character
                    symbolList.add("\n");
                } else {
                    // Iterate over each character in the line and add it to the list
                    for (int i = 0; i < line.length(); i++) {
                        symbolList.add(String.valueOf(line.charAt(i)));
                    }
                }
                return symbolList;
            }
        });

        // Calculate the total number of symbols
        long symbolCount = symbols.count();
        System.out.println("Number of symbols: " + symbolCount);

        // Task 4
        // Find the number of distinct symbols
        long distinctSymbolCount = symbols.distinct().count();
        System.out.println("Number of distinct symbols: " + distinctSymbolCount);

        // Task 5
        // Filter symbols to include only alphabetic characters (case-sensitive)
        JavaRDD<String> letters = symbols.filter(new Function<String, Boolean>() {
            @Override
            public Boolean call(String symbol) {
                // Check if the symbol is a letter
                Boolean filterSymbols = symbol.matches("[a-zA-Z]");
                return filterSymbols;
            }
        });

        // Determine the number of unique letters
        long distinctLetterCount = letters.distinct().count();
        System.out.println("Number of distinct letters: " + distinctLetterCount);

        // Task 6
        // Interactive feature to search for lines containing a specific word (case-sensitive)
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a word to search for: ");
        final String searchWord = scanner.nextLine();

        // Filter lines that contain the specified search word
        JavaRDD<String> matchingLines = lines.filter(new Function<String, Boolean>() {
            @Override
            public Boolean call(String line) {
                // Determine if the line includes the search word
                boolean containsWord = line.contains(searchWord);
                return containsWord;
            }
        });

        // Collect the matching lines into a list
        List<String> foundLines = matchingLines.collect();

        // Iterate through the list using a traditional indexed for loop and print each line
        System.out.println("Lines containing \"" + searchWord + "\":");
        for (int i = 0; i < foundLines.size(); i++) {
            System.out.println(foundLines.get(i));
        }

        // Close the scanner to free up resources
        scanner.close();

        // Terminate the Spark context
        sc.stop();
    }
}
