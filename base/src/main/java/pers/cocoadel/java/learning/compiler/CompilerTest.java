package pers.cocoadel.java.learning.compiler;

import javax.swing.*;
import javax.tools.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class CompilerTest {
    public static void main(String[] args)
            throws Exception {
        //获取 java 系统编译器
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        //构建文件管理器
        List<ByteArrayClass> classFileObjects = new ArrayList<>();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        JavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
        fileManager = new ForwardingJavaFileManager<JavaFileManager>(fileManager) {

            @Override
            public JavaFileObject getJavaFileForOutput(Location location, String className,
                                                       JavaFileObject.Kind kind, FileObject sibling) throws IOException {
                if (kind == JavaFileObject.Kind.CLASS) {
                    ByteArrayClass byteArrayClass = new ByteArrayClass(className);
                    classFileObjects.add(byteArrayClass);
                    return byteArrayClass;
                }
                return super.getJavaFileForOutput(location, className, kind, sibling);
            }
        };

        //获取父类名称
        String supperFrameClassName = "pers.cocoadel.java.learning.compiler.ButtonFrame";
        //通过父类名称构造子类的字符串
        JavaFileObject source = buildSource(supperFrameClassName);
        //构建编译任务
        JavaCompiler.CompilationTask task = compiler
                .getTask(null, fileManager, diagnostics, null, null, Collections.singletonList(source));
        //执行编译任务
        Boolean result = task.call();
        diagnostics.getDiagnostics()
                .forEach(diagnostic -> System.out.println("diagnostic = " + diagnostic.getMessage(null)));
        if (!result) {
            System.out.println("Compilation Failure!!");
            System.exit(1);
        }

        //加载 子类 Class 到内存
        ByteArrayClassLoader loader = new ByteArrayClassLoader(classFileObjects);
        //通过反射构建 子类对象
        JFrame frame = (JFrame) loader.loadClass("x.Frame").getConstructor().newInstance();
        //添加子类对象到窗口
        EventQueue.invokeLater(() -> {
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("CompilerTest");
            frame.setVisible(true);
        });
    }

    private static JavaFileObject buildSource(String supperClassName) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("package x;\n\n");
        sb.append("public class Frame extends ").append(supperClassName).append("{\n");
        sb.append("protected void addEventHandlers() {\n");
        Properties properties = new Properties();
//        properties.load(Files.newInputStream(
//                Paths.get(supperClassName.replace('.', '/'))
//                        .getParent().resolve("action.properties")));
        properties.load(Files.newInputStream(Paths.get("E:\\Projects\\java\\java-learning\\base" +
                "\\src\\main\\resources\\pers\\cocoadel\\java\\learning\\compiler\\action.properties")));
        properties.forEach((beanName,eventCode) ->{
            sb.append(beanName).append(".addActionListener(event -> {\n");
            sb.append(eventCode).append("\n");
            sb.append("});\n");
        });
        sb.append("}\n");
        sb.append("}");
        return new StringSource("x.Frame",sb.toString());
    }
}
