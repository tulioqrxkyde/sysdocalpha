package br.sysdoc.factories;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class PathFactory {

    private static final String PATH_CONST = "sysdoc/";
    private static final String PATH_FINANCE = "ficha_financeira/";
    private static final String PATH_DOCUMENTS = "documentos_pessoais/";
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

    public int countFilesInFolder(Path path) {
        Iterator<Path> it = path.getFileSystem().getRootDirectories().iterator();

        int size = 0;
        while (it.hasNext()) {
            it.next();
            size++;
        }
        return size - 2;
    }
    
    public Set<Path> getFiles(Path path) {
        Iterator<Path> it = path.getFileSystem().getRootDirectories().iterator();
        Set<Path> files = new HashSet<>();
        while(it.hasNext()) {
            files.add(it.next());
        }
        return files;
    }

    /**
     * @return the PATH_CONST
     */
    public static String getPATH_CONST() {
        return PATH_CONST;
    }

    /**
     * @return the PATH_FINANCE
     */
    public static String getPATH_FINANCE() {
        return PATH_FINANCE;
    }

    /**
     * @return the PATH_DOCUMENTS
     */
    public static String getPATH_DOCUMENTS() {
        return PATH_DOCUMENTS;
    }
}
