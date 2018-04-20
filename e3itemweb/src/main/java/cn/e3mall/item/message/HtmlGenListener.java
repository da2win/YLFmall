package cn.e3mall.item.message;

import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Listening to the added message of the goods.Generate the corresponding static page.
 * @author Darwin
 * @date 2018/4/20
 */
public class HtmlGenListener implements MessageListener {
    @Autowired
    private ItemService itemService;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${HTML_GEN_PATH}")
    private String HTML_GEN_PATH;

    @Override
    public void onMessage(Message message) {

        try {
            // Create a template, reference pojo.
            // Get the ID from message.
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            Long itemId = new Long(text);

            // Waiting for the transaction submission.
            Thread.sleep(1000);
            // Inquire commodity info, commodity basic info and desc according to commodity ID.
            TbItem tbItem = itemService.getItemById(itemId);
            Item item = new Item(tbItem);
            // Get commodity desc.
            TbItemDesc itemDesc = itemService.getItemDescById(itemId);
            // Create a data set.
            Map data = new HashMap<>();
            data.put("item", item);
            data.put("itemDesc", itemDesc);
            // Load the template obj.
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template template = configuration.getTemplate("item.ftl");
            // Create a output stream, specify directory and filename of the output file.
            Writer out = new FileWriter(HTML_GEN_PATH + itemId + ".html");
            // Generate page.
            template.process(data, out);
            // Close stream.
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
