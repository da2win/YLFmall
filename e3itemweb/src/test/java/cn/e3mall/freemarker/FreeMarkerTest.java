package cn.e3mall.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * Created by Darwin on 2018/4/20.
 */
public class FreeMarkerTest {

    @Test
    public void testFreeMarker() throws Exception {
        // Create a template file.
        // Create a Configuration obj.
        Configuration conf = new Configuration(Configuration.getVersion());
        // Setting the directory saved by the template file.
        conf.setDirectoryForTemplateLoading(new File("F:\\LearningSpace\\Java\\YLF\\e3itemweb\\src\\main\\webapp\\WEB-INF\\ftl"));
        // Setting the encoding of the template file, It's generally UTF-8.
        conf.setDefaultEncoding("utf-8");
        // Load a template file, create a template file.
        //Template template = conf.getTemplate("hello.ftl");
        Template template = conf.getTemplate("student.ftl");
        // Create a data set, It can be POJO, or Map. It's recommended to use Map.
        Map data = new HashMap<>();
        data.put("hello", "hello freemarker");
        // Create a POJO obj.
        Student student = new Student(1, "小明", 18, "CD");
        // Add a list.
        List<Student> list = new ArrayList<>();
        list.add(new Student(1, "小明", 18, "CD"));
        list.add(new Student(2, "小刚", 12, "RB"));
        list.add(new Student(3, "小b", 14, "BJ"));
        list.add(new Student(4, "无情贱", 13, "SH"));
        list.add(new Student(5, "小贱", 11, "Dubbo"));
        data.put("student", student);
        data.put("stuList", list);
        // Add a date.
        data.put("date", new Date());
        // Test the null value.
        data.put("val", 123);
        // Create a writer obj, specify the directory and filename of the output file.
        Writer out = new FileWriter("F:\\tmp\\freemarker\\student.html");
        // Generating static pages.
        template.process(data, out);
        // Close stream.
        out.close();
    }
}
