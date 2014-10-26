
package br.sysdoc.factories;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;


public class PathFactory {

    private static final String PATH_CONST = "sysdoc/";
    private Path path;

    private PathFactory() {

    }

    public static PathFactory getInstance() {
        return new PathFactory();
    }

    public Path newPath(String str) {
        return Paths.get(str);
    }

    public void createPath(Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectories(((isFile(path)) ? path.getParent() : path));
            if (isFile(path)) {
                Files.createFile(path);
            }
        }
    }

    public void deletePath(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            Iterator<Path> it = Files.newDirectoryStream(path).iterator();
            while (it.hasNext()) {
                Path pathItr = it.next();
                System.out.println(pathItr);
                Files.deleteIfExists(pathItr);
            }
        }
        Files.deleteIfExists(path);
    }

    public void copyPath(Path pathSource, Path pathTarget) throws IOException {
        if (!Files.exists(pathSource)) {
            Files.createFile(pathSource);
        }
        if (!Files.exists(pathTarget)) {
            Files.createFile(pathTarget);
        }
        Files.copy(pathSource, pathTarget, StandardCopyOption.REPLACE_EXISTING);
    }

    public String getRoot() {
        return FileSystems.getDefault().getRootDirectories().iterator().next().toString().replaceAll("[/\\\\]", "/");
    }

    public boolean isFile(Path path) {
        String pathT = path.toString();
        if (pathT.contains(".")) {
            String[] split = path.toString().split("\\.");
            return split[1].matches("[a-z]{3,4}");
        }
        return false;
    }

    /**
     * @return the PATH_CONST
     */
    public static String getPATH_CONST() {
        return PATH_CONST;
    }

}
