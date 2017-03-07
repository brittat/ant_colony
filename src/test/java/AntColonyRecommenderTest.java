import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by brittathornblom1 on 3/6/17.
 */
public class AntColonyRecommenderTest {
  private AntGraph antGraph;

  @Before
  public void setUp() throws Exception {
    this.antGraph = new AntGraph("shortdataset.csv");
  }

  @Test
  public void getEdgeSum() throws Exception {
    List<String> currentCart = new ArrayList<String>(Arrays.asList("1", "3"));
    AntColonyRecommender antColonyRecommender = new AntColonyRecommender(new PairComparator(), this.antGraph);
    int edgeSum = antColonyRecommender.getEdgeSum(this.antGraph.getGraphMap(), "2", currentCart);
    Assert.assertEquals(5, edgeSum);
  }

  @Test
  public void recommend() throws Exception {
    AntColonyRecommender antColonyRecommender = new AntColonyRecommender(new PairComparator(), this.antGraph);
    List<String> currentCart = new ArrayList<String>(Arrays.asList("2"));
    List<Pair<String, Integer>> recommendedList = antColonyRecommender.recommend(currentCart, 2);
    Pair<String, Integer> firstRecommended = recommendedList.remove(0);
    Pair<String, Integer> secondRecommended = recommendedList.remove(0);
    List<String> itemIds = new ArrayList<String>(Arrays.asList(firstRecommended.getKey(), secondRecommended.getKey()));
    Assert.assertTrue(itemIds.contains("1"));
    Assert.assertTrue(itemIds.contains("3"));
  }

  @Test
  public void recommendCatLady() throws Exception {
    AntGraph bigAntGraph = new AntGraph("user_data.csv");
    OccurrenceBasedPairComparator occurrenceBasedPairComparator = new OccurrenceBasedPairComparator(this.antGraph.getItemMap());
    AntColonyRecommender antColonyRecommender = new AntColonyRecommender(occurrenceBasedPairComparator, bigAntGraph);
    List<String> currentCart = new ArrayList<String>(Arrays.asList(
            "47017",
            "58628",
            "46827",
            "55041",
            "55026",
            "42217",
            "54674",
            "41586",
            "41983"));
    List<Pair<String, Integer>> recommendedList = antColonyRecommender.recommend(currentCart, 5);
    List<String> itemIds = new ArrayList<String>();
    Iterator<Pair<String, Integer>> recomIterator = recommendedList.iterator();
    while (recomIterator.hasNext()) {
      Pair<String, Integer> currentPair = recomIterator.next();
      itemIds.add(currentPair.getKey());
    }
    System.out.println(itemIds);
  }

  @Test
  public void recommendBbq() throws Exception {
    AntGraph antGraph = new AntGraph("user_data.csv");
    OccurrenceBasedPairComparator occurrenceBasedPairComparator = new OccurrenceBasedPairComparator(antGraph.getItemMap());
    AntColonyRecommender antColonyRecommender = new AntColonyRecommender(occurrenceBasedPairComparator, antGraph);
    List<String> currentCart = new ArrayList<String>(Arrays.asList(
            "43556",
            "61722",
            "52389",
            "53477",
            "61529",
            "61465" ));
    List<Pair<String, Integer>> recommendedList = antColonyRecommender.recommend(currentCart, 10);
    List<String> itemIds = new ArrayList<String>();
    Iterator<Pair<String, Integer>> recomIterator = recommendedList.iterator();
    while (recomIterator.hasNext()) {
      Pair<String, Integer> currentPair = recomIterator.next();
      itemIds.add(currentPair.getKey());
    }
    System.out.println(itemIds);
  }
}
