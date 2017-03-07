import javafx.util.Pair;

import java.util.Comparator;

/**
 * Created by brittathornblom1 on 3/6/17.
 */
public class PairDoubleComparator implements Comparator<Pair<String, Double>> {

  public PairDoubleComparator() {
  }

  public int compare(Pair<String, Double> o1, Pair<String, Double> o2) {
    Double firstValue = o1.getValue();
    Double secondValue = o2.getValue();
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
