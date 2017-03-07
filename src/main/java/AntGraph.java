import au.com.bytecode.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by brittathornblom1 on 3/6/17.
 */
public class AntGraph {
  private Map graphMap = new HashMap<String, Integer>();
  private Map<String, Integer> itemMap = new HashMap<String, Integer>();

  public AntGraph() {
  }

  public AntGraph(String fileName) {
    readFile(fileName);
  }

  public Map<String, Integer> getGraphMap() {
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
    String edgeKey = GraphUtilities.getGraphKey(firstItem, secondItem);
    Integer currentEdge = (Integer) graphMap.get(edgeKey);
    if (currentEdge == null) {
      graphMap.put(edgeKey, 1);
    } else {
      graphMap.put(edgeKey, currentEdge + 1);
    }
  }
}
