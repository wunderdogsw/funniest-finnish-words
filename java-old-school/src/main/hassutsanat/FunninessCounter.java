package hassutsanat;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FunninessCounter {

  private static class FunninessScoreResult {

    private final String word;
    private final int funninessScore;

    public FunninessScoreResult(String word, int funninessScore) {
      this.word = word;
      this.funninessScore = funninessScore;
    }

    @Override
    public String toString() {
      return word + " (" + funninessScore + ")";
    }
  }

  private static final String NON_VOWELS = "[^aAeEiIoOuUyYåÅäÄöÖ]";

  private final String filename;
  private int bestScore = -1;
  private List<FunninessScoreResult> funniestWords = Collections.emptyList();

  public FunninessCounter(String filename) {
    this.filename = filename;
  }

  private void findFunniestWord() {
    try {
      Path path = FileSystems.getDefault().getPath(filename);
      List<String> allLines = Files.readAllLines(path);
      for (String line : allLines) {
        String[] words = line.split("\\s");
        for (String word : words) {
          int score = countFunninessScore(word);
          if (score >= bestScore) {
            FunninessScoreResult res = new FunninessScoreResult(word, score);
            if (score > bestScore) {
              funniestWords = new ArrayList<FunninessCounter.FunninessScoreResult>();
              bestScore = score;
            }

            funniestWords.add(res);
          }
        }
      }
    } catch (IOException ioe) {
      System.err.println("Ongelma tiedoston luvussa: " + ioe.getMessage());
    }
  }

  int countFunninessScore(String word) {
    String[] vowelChains = word.split(NON_VOWELS);
    int totalScore = 0;

    for (String vowels : vowelChains) {
      int chainLength = vowels.length();
      totalScore += chainLength * Double.valueOf(Math.pow(2.0, chainLength)).intValue();
    }

    return totalScore;
  }

  private void printResults() {
    System.out.println("Found " + funniestWords.size() + " result(s).");
    for (FunninessScoreResult res : funniestWords) {
      System.out.println(res);
    }
  }

  public static void main(String[] args) {
    FunninessCounter counter = new FunninessCounter("alastalo.txt");
    counter.findFunniestWord();
    counter.printResults();
  }
}
