package pers.cocoadel.java.learning.io.file;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;

public class ZipFileDemo {
    public static void main(String[] args) throws IOException {
        String path = "D:\\tmp.zip";
        //解压到指定的文件夹
        Path targetPath = Paths.get("D:\\");
        FileSystem fs = FileSystems.newFileSystem(Paths.get(path), null);
        Files.walkFileTree(fs.getPath("/"),new SimpleFileVisitor<Path>(){

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                //如果不是压缩文件的根路径
                if (!dir.equals(dir.getRoot())) {
                    Path p = targetPath.resolve(dir.toString());
                    System.out.println("p = " + p);
                    Files.createDirectory(p);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println(file);
                Files.copy(fs.getPath(file.toString()),targetPath.resolve(file.toString()));
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
