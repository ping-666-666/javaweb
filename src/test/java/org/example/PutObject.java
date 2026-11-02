package org.example;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.PutObjectRequest;
import com.aliyun.sdk.service.oss2.models.PutObjectResult;
import com.aliyun.sdk.service.oss2.transport.BinaryData;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 阿里云 OSS Java SDK V2 —— 上传一张图片的示例程序
 *
 * AK/SK 从环境变量读取，不写死在代码里：
 *     OSS_ACCESS_KEY_ID
 *     OSS_ACCESS_KEY_SECRET
 * 配好后运行；没配的话程序会直接提示。
 *
 * 运行方式：在 IDEA 里右键本文件 → Run 'PutObject.main()'
 */
public class PutObject {

    // ==================== 改成你自己的 ====================
    /** 地域节点，格式 https://oss-cn-{地域}.aliyuncs.com */
    private static final String ENDPOINT = "https://oss-cn-beijing.aliyuncs.com";
    /** 地域 ID，如 cn-hangzhou */
    private static final String REGION = "cn-beijing";
    /** Bucket 名称 */
    private static final String BUCKET = "p-java-ai";
    /** 要上传的本地图片路径（Windows 路径要写双反斜杠） */
    private static final String IMAGE_PATH = "D:/T/1.png";
    // =====================================================

    public static void main(String[] args) throws Exception {
        File image = new File(IMAGE_PATH);
        if (!image.exists()) {
            System.out.println("图片不存在，请先修改 IMAGE_PATH: " + IMAGE_PATH);
            return;
        }

        // 先检查环境变量有没有读到，免得后面报个看不懂的错
        if (System.getenv("OSS_ACCESS_KEY_ID") == null || System.getenv("OSS_ACCESS_KEY_SECRET") == null) {
            System.out.println("没有读到环境变量 OSS_ACCESS_KEY_ID / OSS_ACCESS_KEY_SECRET ！");
            System.out.println("IDEA 里的配法：Run → Edit Configurations → Environment variables");
            System.out.println("    OSS_ACCESS_KEY_ID=你的AccessKeyId;OSS_ACCESS_KEY_SECRET=你的AccessKeySecret");
            System.out.println("如果用的是 setx 设的系统环境变量，必须完全重启 IDEA 才生效。");
            return;
        }

        // 对象名(key)一定要带后缀，否则浏览器访问时会当成附件下载而不是预览
        String ext = IMAGE_PATH.contains(".")
                ? IMAGE_PATH.substring(IMAGE_PATH.lastIndexOf("."))
                : "";
        String key = "images/" + System.currentTimeMillis() + ext;

        // 凭证从环境变量 OSS_ACCESS_KEY_ID / OSS_ACCESS_KEY_SECRET 读取
        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();

        // OSSClient 实现了 AutoCloseable，try-with-resources 结束会自动关闭；
        // V2 里已经没有 V1 的 ossClient.shutdown() 了。
        try (OSSClient client = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(REGION)
                .endpoint(ENDPOINT)
                .build();
             InputStream in = new FileInputStream(image)) {

            PutObjectResult result = client.putObject(PutObjectRequest.newBuilder()
                    .bucket(BUCKET)
                    .key(key)
                    // 流式上传需要显式给出长度，image.length() 就是文件字节数
                    .body(BinaryData.fromStream(in, image.length()))
                    // 让浏览器能直接预览图片；png 图片就写 image/png
                    .contentType("image/jpeg")
                    .build());

            System.out.printf("status code:%d, request id:%s, eTag:%s%n",
                    result.statusCode(), result.requestId(), result.eTag());
            System.out.println("图片地址: https://" + BUCKET + "."
                    + ENDPOINT.replaceFirst("^https?://", "") + "/" + key);
        }
    }
}
