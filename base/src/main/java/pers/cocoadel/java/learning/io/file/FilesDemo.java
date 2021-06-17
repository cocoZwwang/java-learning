package pers.cocoadel.java.learning.io.file;


import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FilesDemo {

    public static void main(String[] args) throws IOException {
        String path = "D:\\tmp";
        deleteDir1(path);
        visitDir(path);
        deleteDir(path);
    }

    private static void visitDir(String path) throws IOException {
        Files.walkFileTree(Paths.get(path),new SimpleFileVisitor<Path>(){
            //开始遍历一个目录前 回调
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("preVisitDirectory: " + dir.getFileName());
                return FileVisitResult.CONTINUE;
            }

            //遍历完毕一个目录后回调
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                System.out.println("postVisitDirectory: " + dir.getFileName());
                return FileVisitResult.CONTINUE;
            }

            //文件访问失败 回调
            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                System.out.println("visitFileFailed: " + file.getFileName());
                return FileVisitResult.SKIP_SUBTREE;
            }

            //文件访问成功回调
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println("visitFile: " + file.getFileName());
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * 删除一个目录
     */
    private static void deleteDir(String path) throws IOException {
        Files.walkFileTree(Paths.get(path),new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                System.out.println("delete file: " + file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (exc != null) {
                    throw exc;
                }
                Files.delete(dir);
                System.out.println("delete dir: " + dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private static void deleteDir1(String path) {
        File file = new File(path);
        System.out.println("file.exists: " + file.exists());
        if (file.exists()) {
            boolean delete = file.delete();
            System.out.println("delete = " + delete);
            if (delete) {
                System.out.println("删除成功");
            }
        }
    }
}
