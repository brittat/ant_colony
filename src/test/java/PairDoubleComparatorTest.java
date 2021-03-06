import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by brittathornblom1 on 3/6/17.
 */
public class PairDoubleComparatorTest {
  private PairDoubleComparator pairDoubleComparator = new PairDoubleComparator();
  private Pair firstPair = new Pair<String, Integer>("first", 10);
  private Pair secondPair = new Pair<String, Integer>("second", 100);

  @Test
  public void compareNegative() throws Exception {
    int compared = pairDoubleComparator.compare(this.firstPair, this.secondPair);
    Assert.assertEquals(-1, compared);
  }

  @Test
  public void comparePositive() throws Exception {
    int compared = pairDoubleComparator.compare(this.secondPair, this.firstPair);
    Assert.assertEquals(1, compared);
  }

  @Test
  public void compareZero() throws Exception {
    Pair thirdPair = new Pair<String, Integer>("third", 10);
    int compared = pairDoubleComparator.compare(this.firstPair, thirdPair);
    Assert.assertEquals(0, compared);
  }
}
