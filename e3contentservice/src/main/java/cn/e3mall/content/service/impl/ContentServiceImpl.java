package cn.e3mall.content.service.impl;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.util.E3Result;
import cn.e3mall.common.util.JsonUtils;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.mapper.TbContentMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *
 * 内容管理Service
 * @author Darwin
 * @date 2018/4/10
 */
@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private TbContentMapper contentMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${CONTENT_LIST}")
    private String CONTENT_LIST;

    @Override
    public EasyUIDataGridResult getContentList(long categoryId, int page, int rows) {
        // 设置分页信息
        PageHelper.startPage(page, rows);
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> contentsList = contentMapper.selectByExample(example);
        // 创建一个返回对象
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(contentsList);
        // 取分页结果
        PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(contentsList);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public E3Result addContent(TbContent content) {
        // 将内容插入到数据库
        content.setCreated(new Date());
        content.setUpdated(new Date());
        contentMapper.insert(content);
        // 缓存同步, 删除缓存中对应的数据
        jedisClient.hdel(CONTENT_LIST, content.getCategoryId() + "");
        return E3Result.ok();
    }

    /**
     * 根据内容分类id查询内容列表
     * @param cid
     * @return
     */
    @Override
    public List<TbContent> getContentListByCid(long cid) {
        // 查询缓存
        try {
            // 如果缓存中有直接响应结果
            String json = jedisClient.hget(CONTENT_LIST, cid + "");
            if (StringUtils.isNotBlank(json)) {
                List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 如果没有查询数据库
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        // 执行查询
        List<TbContent> contentList = contentMapper.selectByExampleWithBLOBs(example);
        // 把结果添加到缓存中
        try {
            jedisClient.hset(CONTENT_LIST, cid + "", JsonUtils.objectToJson(contentList));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentList;
    }
}
