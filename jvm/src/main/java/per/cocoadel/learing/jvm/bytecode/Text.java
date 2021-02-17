package per.cocoadel.learing.jvm.bytecode;


import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class Text {

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        String str = "package date20200321.sorm.utils;\n" +
                "\n" +
                "/**\n" +
                " * @description:\n" +
                " * @Author: YaoDong\n" +
                " * @Date: 2021/1/15 2:45 下午\n" +
                " */\n" +
                "public class t {\n" +
                "    private int age;\n" +
                "\n" +
                "    static {\n" +
                "        System.out.println(\"t-----------\");\n" +
                "\n" +
                "    }"
                +
                "    public t(int age) {\n" +
                "        this.age = age;\n" +
                "    }\n" +
                "\n" +
                "    public int getAge() {\n" +
                "        return age;\n" +
                "    }\n" +
                "\n" +
                "    public void setAge(int age) {\n" +
                "        this.age = age;\n" +
                "    }\n" +
                "}\n";

        String classPath
        File file = new File("/Users/huxiao/Documents/yaodong/JAVA-myself-code/java-learning/src/date20200321/sorm/utils/t.java");
        if (!file.exists()) {
            file.createNewFile();

        }
        System.out.println("纯在：" + file.exists());
        BufferedWriter writer=null;
        writer = new BufferedWriter(new FileWriter(file));
        writer.write(str);
        writer.flush();
        writer.close();

        //编译新java文件
        Map<String, byte[]> results;
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager stdManager = compiler.getStandardFileManager(null, null, null);
        try (MemoryJavaFileManager manager = new MemoryJavaFileManager(stdManager)) {
            JavaFileObject javaFileObject = manager.makeStringSource(fileName, source);
            CompilationTask task = compiler.getTask(null, manager, null, null, null, Arrays.asList(javaFileObject));
            if (task.call()) {
                results = manager.getClassBytes();
            }
        }

        Class c=Class.forName("date20200321.sorm.utils.t");
    }
}