package wunderdog;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.hasItem;

/**
 * Unit/Integration test for calcalating funniest finnish words using wunderdog.FunnyFinnishWords
 *
 * @author Ville Komulainen
 */
public class FunnyFinnishWordsTest {

    @Test
    public void main_processAlastalonSalissa_printsSixFunnyWordsToStdOut() throws Exception {
        //  To keep the main code concise, we need a little hack here for testing purposes
        //  Redirect stdout to our own stream which we read for asserting the result
        ByteArrayOutputStream stdoutStream = new ByteArrayOutputStream();
        PrintStream stdoutRedirectStream = new PrintStream(stdoutStream);
        System.setOut(stdoutRedirectStream);

        //  Calculate funniest words
        FunnyFinnishWords.main(null);
        String funnyWordsOutput = stdoutStream.toString("UTF-8");
        List<String> funniestWords = Arrays.asList(funnyWordsOutput.split(System.lineSeparator()));

        //  Make sure we get the expected words
        assertThat(funniestWords.size(), is(6));
        assertThat(funniestWords, hasItem("seremoniioissa"));
        assertThat(funniestWords, hasItem("liioittelematta"));
        assertThat(funniestWords, hasItem("seremoniioilla"));
        assertThat(funniestWords, hasItem("leeaakaan"));
        assertThat(funniestWords, hasItem("puuaineen"));
        assertThat(funniestWords, hasItem("niiaamaan"));
    }

    @Test
    public void wordFunnyScore_emptyString_returns0() {
        assertThat(FunnyFinnishWords.wordFunnyScore(""), is(0));
    }

    @Test
    public void wordFunnyScore_noVowels_returns0() {
        assertThat(FunnyFinnishWords.wordFunnyScore("qwrtz"), is(0));
    }

    @Test
    public void wordFunnyScore_twoNonGroupedVowels_returns4() {
        assertThat(FunnyFinnishWords.wordFunnyScore("kala"), is(4));
    }

    @Test
    public void wordFunnyScore_twoNonGroupedUpperCaseVowels_returns4() {
        assertThat(FunnyFinnishWords.wordFunnyScore("KALA"), is(4));
    }

    @Test
    public void wordFunnyScore_twoGroupedVowels_returns8() {
        assertThat(FunnyFinnishWords.wordFunnyScore("ui"), is(8));
    }

    @Test
    public void wordFunnyScore_twoGroupedAndOneSingleVowel_returns10() {
        assertThat(FunnyFinnishWords.wordFunnyScore("koira"), is(10));
    }

    @Test
    public void wordFunnyScore_twoGroupedAndTwoSingleVowel_returns12() {
        assertThat(FunnyFinnishWords.wordFunnyScore("koirasi"), is(12));
    }

}
