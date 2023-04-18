package ua.lviv.iot;

import com.google.common.io.Files;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class LettersCounterTest {
    public LettersCounter counter = new LettersCounter();
    public ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    @BeforeEach
    public void initialize() {
        System.setOut(new PrintStream(outputStream));
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
