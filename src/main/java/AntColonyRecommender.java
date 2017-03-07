import javafx.util.Pair;

import java.util.*;

/**
 * Created by brittathornblom1 on 3/6/17.
 */
public class AntColonyRecommender {
  private Comparator comparator;
  private Map<Pair<String, String>, Integer> graphMap;
  private Map<String, Integer> itemMap;

  public AntColonyRecommender(Comparator comparator, AntGraph antGraph) {
    this.comparator = comparator;
    this.graphMap = antGraph.getGraphMap();
    this.itemMap = antGraph.getItemMap();
  }


  public ArrayList recommend(List<String> currentCart, int numRecommend) {
    List<String> itemsToCheck = new ArrayList<String>(currentCart);
    int edgeSum;
    PriorityQueue<Pair<String, Integer>> priorityQueue = new PriorityQueue(numRecommend, this.comparator);
    int addedItems = 0;

    for (String item : this.itemMap.keySet()) {
      if (itemsToCheck.contains(item)) {
        itemsToCheck.remove(item);
      } else {
        edgeSum = getEdgeSum(this.graphMap, item, currentCart);

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

  public ArrayList recommendMvp(List<String> currentCart, int numRecommend) {
    List<String> itemsToCheck = new ArrayList<String>(currentCart);
    int edgeSum;
    PriorityQueue<Pair<String, Integer>> priorityQueue = new PriorityQueue(numRecommend, this.comparator);
    int addedItems = 0;

    for (String item : itemMap.keySet()) {
      if (itemsToCheck.contains(item)) {
        itemsToCheck.remove(item);
      } else {
        edgeSum = (int) getWeightedEdgeSum(graphMap, item, currentCart);

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

  public int getEdgeSum(Map<Pair<String, String>, Integer> graphMap, String item, List<String> currentCart) {
    Integer edgeSum = 0;
    for (String cartItem : currentCart) {
      Pair<String, String> graphKey = GraphUtilities.getGraphKeyPair(item, cartItem);
      Integer edgeValue = graphMap.get(graphKey);
      if(edgeValue != null) {
        edgeSum += edgeValue;
      }
    }
    return edgeSum;
  }

  public double getWeightedEdgeSum(Map<Pair<String, String>, Integer> graphMap, String item, List<String> currentCart) {
    double edgeSum = 0;
    for (String cartItem : currentCart) {
      Integer cartWeight = this.itemMap.get(cartItem);
      if (cartWeight == null) {
        cartWeight = 1;
      }
      Pair<String, String> graphKey = GraphUtilities.getGraphKeyPair(item, cartItem);
      Integer edgeValue = graphMap.get(graphKey);
      if(edgeValue != null) {
        edgeSum += (double) edgeValue/cartWeight;
      }
    }
    return edgeSum;
  }
}