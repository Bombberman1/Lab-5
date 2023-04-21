package ua.lviv.iot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public final class LettersCounter {
    public void findVowelsInConsole(File name) {
        try (Scanner scanner = new Scanner(Files.newInputStream(
                name.toPath()), StandardCharsets.UTF_8)) {
            scanner.useDelimiter("(?<=\\.)|(?<=\\?)|(?<=!)");
            List<Character> list = new LinkedList<Character>() {{
                add('a'); add('e'); add('i'); add('o'); add('u'); add('y');
            }};
            int iterator = 0;
            while (scanner.hasNext()) {
                AtomicInteger vowels = new AtomicInteger(0);
                String sentence = scanner.next();
                String words = Arrays.stream(sentence.split(" |(?<=,)"))
                        .filter(s -> (s != null && s.length() > 0))
                        .peek(s -> {
                            for (Character letter : s.toLowerCase()
                                                    .toCharArray()) {
                                if (list.contains(letter)) {
                                    vowels.incrementAndGet();
                                }
                            }
                        })
                        .collect(Collectors.joining(" "));
                System.out.println(words);
                iterator++;
                System.out.printf("%d Sentence: %d vowels%n",
                        iterator, vowels.get());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void findVowelsInFile(File name) {
        try (Scanner scanner = new Scanner(Files.newInputStream(
                name.toPath()), StandardCharsets.UTF_8);
             FileWriter writer = new FileWriter("Vowels.txt",
                     StandardCharsets.UTF_8)) {
            scanner.useDelimiter("(?<=\\.)|(?<=\\?)|(?<=!)");
            List<Character> list = new LinkedList<Character>() {{
                add('a'); add('e'); add('i'); add('o'); add('u'); add('y');
            }};
            int iterator = 0;
            while (scanner.hasNext()) {
                AtomicInteger vowels = new AtomicInteger(0);
                String sentence = scanner.next();
                String words = Arrays.stream(sentence.split(" |(?<=,)"))
                        .filter(s -> (s != null && s.length() > 0))
                        .peek(s -> {
                            for (Character letter : s.toLowerCase()
                                                    .toCharArray()) {
                                if (list.contains(letter)) {
                                    vowels.incrementAndGet();
                                }
                            }
                        })
                        .collect(Collectors.joining(" "));
                iterator++;
                writer.write(String.format("%s%n%d Sentence: %d vowels%n",
                        words, iterator, vowels.get()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        LettersCounter counter = new LettersCounter();
        counter.findVowelsInConsole(new File("Sentences.txt"));
        counter.findVowelsInFile(new File("Sentences.txt"));
    }
}
