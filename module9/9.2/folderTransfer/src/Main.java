import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Main {

  public static void main(String[] args) {
    File source = new File("dir1");
    File destination = new File("dir2");
    copyFolder(source, destination);
  }

  public static void copyFolder (File source, File destination) {
    if (source.isDirectory()) {
      if (!destination.exists()) {
        destination.mkdir();
      }

      String files[] = source.list();

      for (String file : files) {
        File srcFile = new File(source, file);
        File destFile = new File(destination, file);

        copyFolder(srcFile, destFile);
      }
    } else {
      FileInputStream in;
      FileOutputStream out;

      try {
        in = new FileInputStream(source);
        out = new FileOutputStream(destination);

        byte[] buffer = new byte[1024];
        int length;

        while ((length = in.read(buffer)) > 0) {
          out.write(buffer, 0, length);
        }

        in.close();
        out.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}