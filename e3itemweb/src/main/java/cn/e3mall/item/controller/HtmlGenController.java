package cn.e3mall.item.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Generate static page test controller
 * @author Darwin
 * @date 2018/4/20
 */
@Controller
public class HtmlGenController {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @RequestMapping("/genhtml")
    @ResponseBody
    public String genHtml() throws Exception {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        // Load the template obj.
        Template template = configuration.getTemplate("hello.ftl");
        // create a data set.
        Map data = new HashMap<>();
        data.put("hello", "12341234");
        // Specify the directory and filename of output file.
        Writer out = new FileWriter("F:\\tmp\\freemarker\\hello2.html");
        // output file.
        template.process(data, out);
        // close stream
        out.close();
        return "OK";
    }

}
