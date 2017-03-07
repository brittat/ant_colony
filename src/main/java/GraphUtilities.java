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
}
