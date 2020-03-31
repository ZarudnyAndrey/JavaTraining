package core;

import static core.Main.LOGGER;
import static org.apache.logging.log4j.spi.AbstractLogger.EXCEPTION_MARKER;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LinkThief implements Node {
  private final String URL;
  private String link;
  private Collection<Node> children;

  public LinkThief(String link, String startURL) {
    this.link = link;
    URL = startURL;
    children = new HashSet<>();
  }

  @Override
  public Collection<Node> getChildren() {
    Document document = null;
    try {
      document = Jsoup.connect(getLink()).maxBodySize(0).get();
    } catch (IOException ex) {
      LOGGER.error(EXCEPTION_MARKER, "Приложение вызвало исключение: " + ex.getMessage());
    }
    Elements elements = document.select("a");

    for (Element element : elements) {
      LinkThief newLink = new LinkThief(element.absUrl("href"), URL);

      try {
        Thread.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      if (!LinkCollection.linkStorage.contains(newLink.getLink())
          && newLink.getLink().contains(URL)
          && !newLink.getLink().endsWith(".pdf")
          && !newLink.getLink().contains("#")
          && !newLink.getLink().contains("?")) {

        children.add(newLink);
      }
    }

    return children;
  }

  @Override
  public String getLink() {
    return link;
  }
}