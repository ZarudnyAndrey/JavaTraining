package core;

import java.io.IOException;
import java.util.Collection;

public interface Node {
  Collection<Node> getChildren() throws IOException;
  String getLink();
}
