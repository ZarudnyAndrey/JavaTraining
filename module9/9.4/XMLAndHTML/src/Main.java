import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

  private static final String REGEX_FORMAT = "img[src~=(?i)\\.(png|jpe?g|gif)]";

  public static void main(String[] args) throws IOException {
    Document document = Jsoup.connect("http://lenta.ru/").get();
    Elements images = document.select(REGEX_FORMAT);

    for (Element element : images) {
      String absUrl = element.absUrl("src");
      System.out.println(absUrl);
      getImages(absUrl);
    }
  }

  private static void getImages(String absUrl) throws IOException {
    String folder = "pictures";
    InputStream inputStream = null;
    OutputStream outputStream = null;
    int indexname = absUrl.lastIndexOf("/");

    if (indexname == absUrl.length()) {
      absUrl = absUrl.substring(1, indexname);
    }

    indexname = absUrl.lastIndexOf("/");
    String name = absUrl.substring(indexname, absUrl.length());

    System.out.println(name);

    try {
      URL url = new URL(absUrl);
      inputStream = url.openStream();
      outputStream = new BufferedOutputStream(new FileOutputStream(folder + name ));
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }

    for (int i; (i = inputStream.read()) != -1;) {
      outputStream.write(i);
    }
    outputStream.close();
    inputStream.close();
  }
}
