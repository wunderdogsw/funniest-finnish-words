package hassutsanat;

import org.junit.Assert;
import org.junit.Test;

public class FunninessCounterTest {

  private FunninessCounter counter = new FunninessCounter("");

  @Test
  public void funninessScoreOfEmptyStringShouldBeZero() {
    Assert.assertEquals(0, counter.countFunninessScore(""));
  }

  @Test
  public void funninessScoreOfSingleVowelShouldBeTwo() {
    Assert.assertEquals(2, counter.countFunninessScore("a"));
  }

  @Test
  public void funninessScoreOfThreeVowelChainShouldBe24() {
    Assert.assertEquals(24, counter.countFunninessScore("aei"));
  }

  @Test
  public void funninessScoreOfSeveralVowelChainsShouldBeSumOfChainScores() {
    Assert.assertEquals(24 + 2, counter.countFunninessScore("aeixo"));
  }
}
