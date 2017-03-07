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
}
