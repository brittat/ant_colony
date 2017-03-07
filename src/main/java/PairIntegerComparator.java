import javafx.util.Pair;

import java.util.Comparator;

/**
 * Created by brittathornblom1 on 3/6/17.
 */
public class PairIntegerComparator implements Comparator<Pair<String, Integer>> {

  public PairIntegerComparator() {
  }

  public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
    Integer firstValue = o1.getValue();
    Integer secondValue = o2.getValue();
    int returnInt;

    if (firstValue > secondValue) {
      returnInt = 1;
    } else if (secondValue > firstValue) {
      returnInt = -1;
    } else {
      returnInt = 0;
    }

    return returnInt;
  }
}
