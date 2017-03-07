import javafx.util.Pair;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by brittathornblom1 on 3/7/17.
 */
public class OccurrenceBasedPairComparator implements Comparator<Pair<String, Integer>> {
  private Map<String, Integer> itemMap;

  public OccurrenceBasedPairComparator(Map<String, Integer> itemMap) {
    this.itemMap = itemMap;
  }

  public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
    Integer firstValue = o1.getValue();
    Integer firstOccurrences = this.itemMap.get(o1.getKey());
    Integer secondValue = o2.getValue();
    Integer secondOccurrences = this.itemMap.get(o2.getKey());
    int returnInt;


    if (firstValue == 0) {
      if (secondValue == 0) {
        return 0;
      } else {
        return -1;
      }
    } else {
      double firstScore = (double) firstValue/firstOccurrences;
      double secondScore = (double) secondValue/secondOccurrences;

      if (firstScore > secondScore) {
        returnInt = 1;
      } else if (secondScore > firstScore) {
        returnInt = -1;
      } else {
        returnInt = 0;
      }
      return returnInt;
    }
  }
}
