package cn.e3mall.pagehelper;

import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by Darwin on 2018/4/3.
 */
public class PageHelperTest {

    @Test
    public void testPageHelper() {
        // 初始化spring容器
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");

        TbItemMapper bean = context.getBean(TbItemMapper.class);
        // 执行sql之前设置分页信息使用PageHelper的startPage方法
        // 执行查询
        PageHelper.startPage(1, 10);
        TbItemExample example = new TbItemExample();
        List<TbItem> list = bean.selectByExample(example);
        // 分页信息 pageinfo, 总记录数, 总页数, 当前页码
        PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
        System.out.println(pageInfo.getTotal());
        System.out.println(pageInfo.getPages());
        System.out.println(list.size());

    }
}
