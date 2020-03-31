import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyDir extends SimpleFileVisitor<Path> {

  private Path sourceDir;
  private Path targetDir;

  public CopyDir(Path sourceDir, Path targetDir) {
    this.sourceDir = sourceDir;
    this.targetDir = targetDir;
  }

  @Override
  public FileVisitResult visitFile(Path file,
      BasicFileAttributes attributes) {
    try {
      Path targetFile = targetDir.resolve(sourceDir.relativize(file));
      Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException ex) {
      System.err.println(ex);
    }
    return FileVisitResult.CONTINUE;
  }

  @Override
  public FileVisitResult preVisitDirectory(Path dir,
      BasicFileAttributes attributes) {
    try {
        Path newDir = targetDir.resolve(sourceDir.relativize(dir));
      if (dir.equals(targetDir)) {
        return FileVisitResult.TERMINATE;
      } else {
        Files.createDirectories(newDir);
      }
    } catch (IOException ex) {
      System.err.println(ex);
    }
    return FileVisitResult.CONTINUE;
  }

  public static void main(String[] args) throws IOException {
    Path sourceDir = Paths.get("dir1/");
    Path targetDir = Paths.get("dir2/");
    Files.walkFileTree(sourceDir, new CopyDir(sourceDir, targetDir));
  }
}