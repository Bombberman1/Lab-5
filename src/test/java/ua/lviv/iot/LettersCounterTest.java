package ua.lviv.iot;

import com.google.common.io.Files;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LettersCounterTest {
    public LettersCounter counter = new LettersCounter();
    public ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    @BeforeEach
    public void initialize() {
        System.setOut(new PrintStream(outputStream));
    }
    @Test
    public void InTextTest() {
        String sentence = "The sun was shining brightly in the clear blue sky, and a gentle breeze rustled the leaves on the trees."
                + "Birds chirped merrily as they flitted from branch to branch, and the scent of freshly cut grass filled the air."
                + "It was a perfect day for a picnic in the park.";
        Map<Integer, Integer> expect = new HashMap<>(){{
            put(1, 31); put(2, 28); put(3, 14);
        }};
        Assertions.assertEquals(expect, counter.findVovelsInSentences(sentence));
    }
    @Test
    public void unusualTextFormatInStringTest() {
        String sentence = "Sample text  .!? ? Another text...\nLineFeed text example     !";
        Map<Integer, Integer> expect = new HashMap<>(){{
            put(1, 3); put(2, 4); put(3, 8);
        }};
        Assertions.assertEquals(expect, counter.findVovelsInSentences(sentence));
    }
    @Test
    public void emptyStringInTextTest() {
        Assertions.assertNull(counter.findVovelsInSentences(""));
    }
    @Test
    public void zeroVowelsIgnoringIntegersInTextTest() {
        String sentence = "Tqw  zxn tklll.11212. Plz trg !";
        Map<Integer, Integer> expect = new HashMap<>(){{
            put(1, 0); put(2, 0);
        }};
        Assertions.assertEquals(expect, counter.findVovelsInSentences(sentence));
    }
    @Test
    public void InFileTest() throws IOException {
        counter.findVowelsInFile(new File("Sentences.txt"));
        Assertions.assertEquals(Files.toString(new File("Expect.txt"), StandardCharsets.UTF_8), Files.toString(new File("Vowels.txt"), StandardCharsets.UTF_8));
    }
    @Test
    public void InConsoleTest() {
        counter.findVowelsInConsole(new File("Sentences.txt"));
        String sentence1 = "The sun was shining brightly in the clear blue sky, and a gentle breeze rustled the leaves on the trees.";
        String sentence2 = "Birds chirped merrily as they flitted from branch to branch, and the scent of freshly cut grass filled the air.";
        String sentence3 = "It was a perfect day for a picnic in the park.";
        Assertions.assertEquals(sentence1 + "\r\n1 Sentence: 31 vowels\r\n" + sentence2 + "\r\n2 Sentence: 28 vowels\r\n" + sentence3 + "\r\n3 Sentence: 14 vowels\r\n", outputStream.toString());
    }
}
