import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by brittathornblom1 on 3/6/17.
 */
public class GraphUtilitiesTest {

  @Test
  public void getEdgeKeyRightOrder() throws Exception {
    String edgeKey = GraphUtilities.getGraphKey("30", "1000");
    Assert.assertEquals("30+1000", edgeKey);
  }

  @Test
  public void getEdgeKeyWrongOrder() throws Exception {
    String edgeKey = GraphUtilities.getGraphKey("90", "10");
    Assert.assertEquals("10+90", edgeKey);
  }

  @Test
  public void getEdgeKeyPairRightOrder() throws Exception {
    Pair<String, String> edgeKey = GraphUtilities.getGraphKeyPair("30", "1000");
    Pair<String, String> key = new Pair<String, String>("30", "1000");
    Assert.assertEquals(key, edgeKey);
  }

  @Test
  public void getEdgeKeyPairWrongOrder() throws Exception {
    Pair<String, String> edgeKey = GraphUtilities.getGraphKeyPair("90", "10");
    Pair<String, String> key = new Pair<String, String>("10", "90");
    Assert.assertEquals(key, edgeKey);
  }

}
