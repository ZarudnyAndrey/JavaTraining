package core;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

public class LinkCollection extends RecursiveTask<Set<String>> {
  private final Node node;
  public static Set<String> linkStorage = new HashSet<>();

  public LinkCollection(Node node) {
    this.node = node;
  }

  @Override
  protected Set<String> compute() {
    linkStorage.add(node.getLink());
    List<LinkCollection> subTusks = new LinkedList<>();

    try {
      for (Node child : node.getChildren()) {
        LinkCollection task = new LinkCollection(child);
        task.fork();
        subTusks.add(task);
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    for (LinkCollection task : subTusks) {
      linkStorage.addAll(task.join());
    }

    return linkStorage;
  }
}