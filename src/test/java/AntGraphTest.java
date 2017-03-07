import org.junit.Test;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by brittathornblom1 on 3/6/17.
 */
public class AntGraphTest {

  @Test
  public void readFile() throws Exception {
    AntGraph antGraph = new AntGraph();
    antGraph.readFile("shortdataset.csv");
    Map<String, Integer> graphMap = antGraph.getGraphMap();
    System.out.println(Arrays.toString(graphMap.entrySet().toArray()));
  }
}
