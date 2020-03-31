import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Queue;
import javax.imageio.ImageIO;

public class ImageResizer implements Runnable {
  private Queue<File> queue;
  private int newWidth;
  private String dstFolder;
  private long start;

  public ImageResizer(Queue<File> queue, int newWidth, String dstFolder, long start) {
    this.queue = queue;
    this.newWidth = newWidth;
    this.dstFolder = dstFolder;
    this.start = start;
  }

  @Override
  public void run() {
    while (!queue.isEmpty()) {
      try {
        File file = queue.poll();
        BufferedImage image = ImageIO.read(file);
        if (image == null) {
          continue;
        }
        int newHeight = (int) Math
            .round(image.getHeight() / (image.getWidth() / (double) newWidth));
        BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

        int widthStep = image.getWidth() / newWidth;
        int heightStep = image.getHeight() / newHeight;

        for (int x = 0; x < newWidth; x++) {
          for (int y = 0; y < newHeight; y++) {
            int rgb = image.getRGB(x * widthStep, y * heightStep);
            newImage.setRGB(x, y, rgb);
          }
        }

        Graphics2D graphics2D = newImage.createGraphics();
        graphics2D.setComposite(AlphaComposite.Src);
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(image, 0, 0, newWidth, newHeight, null);
        graphics2D.dispose();

        File newFile = new File(dstFolder + "/" + file.getName());
        ImageIO.write(newImage, "jpg", newFile);
        System.out.println(file.getName() + " recorded");
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    System.out.println("Duration: " + (System.currentTimeMillis() - start) + " ms");
  }
}