import au.com.bytecode.opencsv.CSVReader;
import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by brittathornblom1 on 3/6/17.
 */
public class AntGraph {
  /* Maps pairs of products to the number of times they occur together in orders */
  private Map<Pair<String, String>, Integer> graphMap = new HashMap<Pair<String, String>, Integer>();
  /* Keeps track of which items have occurred and how many times */
  private Map<String, Integer> itemMap = new HashMap<String, Integer>();

  public AntGraph() {
  }

  public AntGraph(String fileName) {
    readFile(fileName);
  }

  public Map<Pair<String, String>, Integer> getGraphMap() {
    return this.graphMap;
  }

  public Map<String, Integer> getItemMap() {
    return this.itemMap;
  }

  public void readFile(String fileName) {
    CSVReader reader;
    try {
      reader = new CSVReader(new FileReader(fileName));
      String [] nextLine;
      while ((nextLine = reader.readNext()) != null) {
        addOrder(nextLine);
      }
    } catch(FileNotFoundException e) {
      System.out.println("No file named " + fileName + " can be found");
    } catch(IOException e) {
      System.out.println("No file named " + fileName + " can be found");
    }
  }

  public void addOrder(String [] orderLineItems) {
    List<String> properItemList = cleanUpArray(orderLineItems);
    for (int i = 0; i < properItemList.size() - 1; i ++) {
      String firstItem = properItemList.get(i);
      Integer firstOccurrences = itemMap.get(firstItem);
      if (firstOccurrences == null) {
        itemMap.put(firstItem, 1);
      } else {
        itemMap.put(firstItem, firstOccurrences + 1);
      }
      for (int j = i + 1; j < properItemList.size(); j ++) {
        String secondItem = properItemList.get(j);
        addToEdge(firstItem, secondItem);
        Integer secondOccurrences = itemMap.get(secondItem);
        if (secondOccurrences == null) {
          itemMap.put(secondItem, 1);
        } else {
          itemMap.put(secondItem, secondOccurrences + 1);
        }
      }
    }
  }

  public List<String> cleanUpArray(String[] orderLineItems) {
    List<String> list = new ArrayList<String>(Arrays.asList(orderLineItems));
    list.removeAll(Arrays.asList("", null));
    return list;
  }

  public void addToEdge(String firstItem, String secondItem) {
    Pair<String, String> edgeKey = GraphUtilities.getGraphKeyPair(firstItem, secondItem);
    Integer currentEdge = graphMap.get(edgeKey);
    if (currentEdge == null) {
      graphMap.put(edgeKey, 1);
    } else {
      graphMap.put(edgeKey, currentEdge + 1);
    }
  }
}
