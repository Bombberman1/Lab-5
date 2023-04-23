package ua.lviv.iot;

import lombok.Generated;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class LettersCounter {
    public static final Pattern SENTENCE_END_PATTERN =
            Pattern.compile("(\\b|\\.|!|\\?).*?(\\.|!|\\?)");
    public static final Pattern VOVELS_PATTERN =
            Pattern.compile("(?i)[aoueiy]");
    public Map<Integer, Integer> findVovelsInSentences(String text) {
        Map<Integer, Integer> resultMap = new HashMap<>();
        Matcher sentenceMatcher = SENTENCE_END_PATTERN.matcher(text);
        int sentencesCounter = 0;
        while (sentenceMatcher.find()) {
            String sentence = sentenceMatcher.group();
            Matcher vovelCountMantcher = VOVELS_PATTERN.matcher(sentence);
            int vowels = 0;
            while (vovelCountMantcher.find()) {
                vowels++;
            }
            resultMap.put(++sentencesCounter, vowels);
        }
        return resultMap;
    }
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
                        .filter(s -> {
                            if (s != null && s.length() > 0) {
                                for (Character letter : s.toLowerCase()
                                        .toCharArray()) {
                                    if (list.contains(letter)) {
                                        vowels.incrementAndGet();
                                    }
                                }
                                return true;
                            }
                            return false;
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
    @Generated
    public static void main(String[] args) {
        LettersCounter counter = new LettersCounter();
        counter.findVowelsInConsole(new File("Sentences.txt"));
        counter.findVowelsInFile(new File("Sentences.txt"));
        String sentence = "The sun was shining brightly in the clear blue sky, "
                + "and a gentle breeze rustled the leaves on the trees."
                + "Birds chirped merrily as they flitted from branch to branch, "
                + "and the scent of freshly cut grass filled the air."
                + "It was a perfect day for a picnic in the park.";
        System.out.println(counter.findVovelsInSentences(sentence));
    }
}
