package wunderdog;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Calcalate funniest finnish words according to algorithm and data provided in http://wunderdog.fi/koodaus-hassuimmat-sanat/
 * Optimizing LOC (not performance, testability, or any other dimension)
 * Input data is Volter Kilpi's novel "Alastalon Salissa" : http://en.wikipedia.org/wiki/Alastalon_salissa
 *
 * @author Ville Komulainen
 */
public class FunnyFinnishWords {

    static final Pattern VOWEL_GROUP_PATTERN = Pattern.compile("([aeiouyöåäAEIOUYÖÅÄ]+)");
    static final String WORD_DELIMITER_REGEX = "[\\s+,\\.\\?]";
    static final String FINNISH_CLASSIC_NOVEL_ALASTALON_SALISSA = "src/test/resources/alastalon_salissa.txt";


    public static int wordFunnyScore(String word) {
        int wordFunnyScore = 0;
        Matcher m = VOWEL_GROUP_PATTERN.matcher(word);
        while (m.find()) {
            int vowelGroupLength = m.group(0).length();
            wordFunnyScore += (vowelGroupLength) * (int) Math.pow(2, vowelGroupLength);
        }
        return wordFunnyScore;
    }

    public static void main(String[] args) throws Exception {
        Files.lines(Paths.get(FINNISH_CLASSIC_NOVEL_ALASTALON_SALISSA))
                .map(line -> line.toLowerCase().split(WORD_DELIMITER_REGEX))
                .flatMap(Arrays::stream)
                .collect(Collectors.groupingBy(FunnyFinnishWords::wordFunnyScore))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(word -> -1 * word.getKey()))
                .findFirst()
                .get()
                .getValue()
                .forEach(System.out::println);
    }
}
