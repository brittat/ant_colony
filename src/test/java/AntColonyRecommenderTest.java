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
  private AntColonyRecommender antColonyRecommender;
  @Before
  public void setUp() throws Exception {
    this.antGraph = new AntGraph("shortdataset.csv");
    this.antColonyRecommender = new AntColonyRecommender();
  }

  @Test
  public void getEdgeSum() throws Exception {
    List<String> currentCart = new ArrayList<String>(Arrays.asList("1", "3"));
    int edgeSum = this.antColonyRecommender.getEdgeSum(this.antGraph.getGraphMap(), "2", currentCart);
    Assert.assertEquals(5, edgeSum);
  }

  @Test
  public void recommend() throws Exception {
    List<String> currentCart = new ArrayList<String>(Arrays.asList("2"));
    List<Pair<String, Integer>> recommendedList = this.antColonyRecommender.recommend(this.antGraph, currentCart, 2);
    Pair<String, Integer> firstRecommended = recommendedList.remove(0);
    Pair<String, Integer> secondRecommended = recommendedList.remove(0);
    List<String> itemIds = new ArrayList<String>(Arrays.asList(firstRecommended.getKey(), secondRecommended.getKey()));
    Assert.assertTrue(itemIds.contains("1"));
    Assert.assertTrue(itemIds.contains("3"));
  }

  @Test
  public void recommendCatLady() throws Exception {
    AntGraph bigAntGraph = new AntGraph("user_data.csv");
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
    List<Pair<String, Integer>> recommendedList = this.antColonyRecommender.recommend(bigAntGraph, currentCart, 5);
    List<String> itemIds = new ArrayList<String>();
    Iterator<Pair<String, Integer>> recomIterator = recommendedList.iterator();
    while (recomIterator.hasNext()) {
      Pair<String, Integer> currentPair = recomIterator.next();
      itemIds.add(currentPair.getKey());
    }
    System.out.println(itemIds);
  }
}
