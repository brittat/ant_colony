import javafx.util.Pair;
import javafx.util.converter.DoubleStringConverter;

import java.util.*;

import static java.util.Collections.max;
import static java.util.Collections.sort;

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
    double edgeSum;
    PriorityQueue<Pair<String, Double>> priorityQueue = new PriorityQueue(numRecommend, this.comparator);
    int addedItems = 0;

    for (String item : this.itemMap.keySet()) {
      if (itemsToCheck.contains(item)) {
        itemsToCheck.remove(item);
      } else {
        edgeSum = getEdgeSum(this.graphMap, item, currentCart);

        if (addedItems != numRecommend) {
          priorityQueue.add(new Pair<String, Double>(item, edgeSum));
          addedItems ++;
        } else {
          Double lowestValue = priorityQueue.peek().getValue();
          if (edgeSum > lowestValue) {
            priorityQueue.poll();
            priorityQueue.add(new Pair<String, Double>(item, edgeSum));
          }
        }
      }
    }
    return new ArrayList(priorityQueue);
  }

  public ArrayList recommendMvp(List<String> currentCart, int numRecommend) {
    List<String> itemsToCheck = new ArrayList<String>(currentCart);
    double edgeSum;
    PriorityQueue<Pair<String, Double>> priorityQueue = new PriorityQueue(numRecommend, this.comparator);
    int addedItems = 0;

    for (String item : this.itemMap.keySet()) {
      if (itemsToCheck.contains(item)) {
        itemsToCheck.remove(item);
      } else {
        edgeSum = getWeightedEdgeSum(item, currentCart);

        if (addedItems != numRecommend) {
          priorityQueue.add(new Pair<String, Double>(item, edgeSum));
          addedItems ++;
        } else {
          Double lowestValue = priorityQueue.peek().getValue();
          if (edgeSum > lowestValue) {
            priorityQueue.poll();
            priorityQueue.add(new Pair<String, Double>(item, edgeSum));
          }
        }
      }
    }
    return new ArrayList(priorityQueue);
  }

  public List<Pair<String, Double>> itemBasedRecommendations(List<String> currentCart, int numRecommend) {
    Double edgeValue;
    List<String> neighborhood;
    List<Pair<String, Double>> recommendedList = new ArrayList<Pair<String, Double>>();
    List<Pair<String, Double>> bestInNeighborhood = new ArrayList<Pair<String, Double>>();
    PairDoubleComparator pairDoubleComparator = new PairDoubleComparator();

    for (String item : currentCart) {
      neighborhood = getItemNeighborhood(item);
      bestInNeighborhood.clear();
      Integer itemOccurrences = itemMap.get(item);
      if (itemOccurrences == null) {
        itemOccurrences = 1;
      }
      for (String neighbor : neighborhood) {
        edgeValue = (double) getEdgeValue(item, neighbor)/itemOccurrences;
        if (edgeValue != null) {
          bestInNeighborhood.add(new Pair<String, Double>(neighbor, edgeValue));
        }
      }
      if (!bestInNeighborhood.isEmpty()) {
        Pair<String, Double> best = Collections.max(bestInNeighborhood, pairDoubleComparator);
        recommendedList.add(best);
      }
    }

    if (currentCart.size() < numRecommend) {
      return fillOutRecommendationList(currentCart, numRecommend, recommendedList);
    } else if (currentCart.size() > numRecommend) {
      Collections.sort(recommendedList, pairDoubleComparator);
      return recommendedList.subList(recommendedList.size()-numRecommend, recommendedList.size());
    } else {
      return recommendedList;
    }
  }

  public List<Pair<String, Double>> fillOutRecommendationList(List<String> currentCart,
                                                              int numRecommend,
                                                              List<Pair<String, Double>> returnList) {
    List<String> itemIds = new ArrayList<String>();
    Iterator<Pair<String, Double>> recomIterator = returnList.iterator();
    while (recomIterator.hasNext()) {
      Pair<String, Double> currentPair = recomIterator.next();
      itemIds.add(currentPair.getKey());
    }

    List<Pair<String, Double>> genRecommend = recommend(currentCart, numRecommend);
    while (returnList.size() < numRecommend) {
      Pair<String, Double> vanillaRecommended = genRecommend.remove(0);
      if (!itemIds.contains(vanillaRecommended.getKey())) {
        returnList.add(vanillaRecommended);
      }
    }
    return returnList;
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

  public double getWeightedEdgeSum(String item, List<String> currentCart) {
    double edgeSum = 0;
    for (String cartItem : currentCart) {
      Integer cartWeight = this.itemMap.get(cartItem);
      if (cartWeight == null) {
        cartWeight = 1;
      }

      Integer edgeValue = getEdgeValue(item, cartItem);
      if(edgeValue != null) {
        edgeSum += (double) edgeValue/cartWeight;
      }
    }
    return edgeSum;
  }

  public int getEdgeValue(String firstItem, String secondItem) {
    Pair<String, String> graphKey = GraphUtilities.getGraphKeyPair(firstItem, secondItem);
    Integer edgeValue = this.graphMap.get(graphKey);
    if (edgeValue == null) {
      return 0;
    } else {
      return edgeValue;
    }
  }

  public List<String> getItemNeighborhood(String item) {
    Set<Pair<String, String>> keys = graphMap.keySet();
    List<String> neighbors = new ArrayList<String>();
    for (Pair<String, String> key : keys) {
      if (item.equals(key.getKey())) {
        neighbors.add(key.getValue());
      } else if (item.equals(key.getValue())) {
        neighbors.add(key.getKey());
      }
    }
    return neighbors;
  }
}