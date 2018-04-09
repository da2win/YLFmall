package cn.e3mall.fast;

import cn.e3mall.common.util.FastDFSClient;
import org.csource.fastdfs.*;
import org.junit.Test;

/**
 * Created by Darwin on 2018/4/8.
 */
public class FastDfsTest {

    @Test
    public void testUpload() throws Exception {
        // 创建一个配置文件, 文件名随意, tracker服务器的地址
        // 使用全局对象加载配置文件.
        ClientGlobal.init("F:/LearningSpace/Java/YLF/e3managerweb/src/main/resources/conf/client.conf");
        // 创建一个TrackerClient对象
        TrackerClient trackerClient = new TrackerClient();
        // 通过TrackerClient获取TrackerServer
        TrackerServer trackerServer = trackerClient.getConnection();
        // 创建一个StorageServer的引用, 可以为null
        StorageServer storageServer = null;
        // 创建一个StorageClient, 参数需要TrackerServer和StorageServer
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        // 使用StorageClient上传文件
        String[] strings = storageClient.upload_file("C:\\Users\\Darwin\\Pictures\\夜斗.jpg", "jpg", null);
        for (String string : strings) {
            System.out.println(string);
        }
    }

    @Test
    public void testFastDfsClient() throws Exception {
        FastDFSClient fastDFSClient = new FastDFSClient("F:/LearningSpace/Java/YLF/e3managerweb/src/main/resources/conf/client.conf");
        String string = fastDFSClient.uploadFile("C:\\Users\\Darwin\\Pictures\\Lighthouse.jpg");
        System.out.println(string);

    }
}
