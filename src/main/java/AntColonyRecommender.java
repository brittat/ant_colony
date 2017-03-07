import javafx.util.Pair;

import java.util.*;

/**
 * Created by brittathornblom1 on 3/6/17.
 */
public class AntColonyRecommender {

  public AntColonyRecommender() {
  }

  public ArrayList recommend(AntGraph antGraph, List<String> currentCart, int numRecommend) {
    Map<String, Integer> graphMap = antGraph.getGraphMap();
    Map<String, Integer> itemMap = antGraph.getItemMap();
    List<String> itemsToCheck = new ArrayList<String>(currentCart);
    int edgeSum;
    PriorityQueue<Pair<String, Integer>> priorityQueue = new PriorityQueue(numRecommend, new PairComparator());
    int addedItems = 0;

    for (String item : itemMap.keySet()) {
      if (itemsToCheck.contains(item)) {
        itemsToCheck.remove(item);
      } else {
        edgeSum = getEdgeSum(graphMap, item, currentCart);

        if (addedItems != numRecommend) {
          priorityQueue.add(new Pair<String, Integer>(item, edgeSum));
          addedItems ++;
        } else {
          Integer lowestValue = priorityQueue.peek().getValue();
          if (edgeSum > lowestValue) {
            priorityQueue.poll();
            priorityQueue.add(new Pair<String, Integer>(item, edgeSum));
          }
        }
      }
    }
    return new ArrayList(priorityQueue);
  }

  public int getEdgeSum(Map<String, Integer> graphMap, String item, List<String> currentCart) {
    Integer edgeSum = 0;
    for (String cartItem : currentCart) {
      String graphKey = GraphUtilities.getGraphKey(item, cartItem);
      Integer edgeValue = graphMap.get(graphKey);
      if(edgeValue != null) {
        edgeSum += edgeValue;
      }
    }
    return edgeSum;
  }
}
