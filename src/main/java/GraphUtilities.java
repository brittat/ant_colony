import javafx.util.Pair;

/**
 * Created by brittathornblom1 on 3/6/17.
 */
public class GraphUtilities {

  public static String getGraphKey(String firstItem, String secondItem) {
    String edgeKey;
    if (Integer.parseInt(firstItem) < Integer.parseInt(secondItem)) {
      edgeKey = firstItem + "+" + secondItem;
    } else {
      edgeKey = secondItem + "+"  + firstItem;
    }
    return edgeKey;
  }

  public static Pair<String, String> getGraphKeyPair(String firstItem, String secondItem) {
    Pair<String, String> edgeKey;
    if (Integer.parseInt(firstItem) < Integer.parseInt(secondItem)) {
      edgeKey = new Pair<String, String>(firstItem, secondItem);
    } else {
      edgeKey = new Pair<String, String>(secondItem, firstItem);
    }
    return edgeKey;
  }

}
