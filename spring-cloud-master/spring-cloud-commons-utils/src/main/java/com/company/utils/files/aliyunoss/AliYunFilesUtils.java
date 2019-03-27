package com.company.utils.files.aliyunoss;

import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.*;
import com.google.common.io.Files;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author FengJie
 * @Date: 2018/10/15 15:45
 * @Description: 阿里oss 文件管理工具类
 */
public class AliYunFilesUtils {
    /**
     * 对象存储客户端
     */
    public volatile OSSClient oSSClient;

    public AliYunFilesUtils(OSSClient oSSClient){
        this.oSSClient = oSSClient;
    }

    /**
     *
     *  文件上传使用本地文件作为OSS文件的数据源
     * @param file  上传的文件
     * @param bucketName 存储空间
     * @param key   存储到oss的路径 eg：images/2018/10/30/aaaaaa.jpg
     * @return  返回文件访问地址（适合上传图片）
     */
    public  Map<String, String> uploadFileReturnPath(File file, String bucketName, String key){
        Map<String, String> result = new HashMap<>(2);
        try {
            //上传文件
            PutObjectResult putResult = uploadFile(file, bucketName, key);
            if(null != putResult){
                //MD5值
                result.put("ETag", putResult.getETag());
                //文件在oss上的存储路径
                //oss 文件路径 + 文件名 /test/test.png
                result.put("OSSFilePathName", getUrl(key,bucketName,null));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 上传本地文件到oss
     * @param file  需要上传的文件
     * @param bucketName  存储空间名称
     * @param key  存储到oss的路径 eg：images/2018/10/30/aaaaaa.jpg
     */
    public  PutObjectResult uploadFile(File file, String bucketName, String key){
        try {
            //以输入流的形式上传文件
            InputStream is = new FileInputStream(file);
            //上传后的文件名
            // String fileName = String.format("%s.%s", UUID.randomUUID().toString(),FileUtils.getFileExtension(file.getName()) );
            String fileName = file.getName();
            //文件大小
            Long fileSize = file.length();

            String md5 = BinaryUtil.toBase64String(BinaryUtil.calculateMd5(Files.toByteArray(file)));
            //创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            //设置md5值校验
            metadata.setContentMD5(md5);
            //上传的文件的长度
            metadata.setContentLength(is.available());
            //指定该Object被下载时的网页的缓存行为
            metadata.setCacheControl("no-cache");
            //指定该Object下设置Header
            metadata.setHeader("Pragma", "no-cache");
            //指定该Object被下载时的内容编码格式
            metadata.setContentEncoding("utf-8");
            //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
            //如果没有扩展名则填默认值application/octet-stream
            //metadata.setContentType(getContentType(fileName));
            //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
            //上传文件   (上传文件流的形式)
            // 带进度条的上传。
            /*PutObjectResult putResult = client.putObject(new PutObjectRequest(bucketName, key, is,metadata).
                    <PutObjectRequest>withProgressListener(new PutObjectProgressListener()));*/
            //上传
            PutObjectResult putResult = this.oSSClient.putObject(bucketName, key, is, metadata);
            return putResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     *    流式上传使用InputStream作为文件的数据源
     * @param baos  ByteArrayOutputStream
     * @param bucketName  存储空间
     * @param key  存储到oss的路径 eg：images/2018/10/30/aaaaaa.jpg
     * @return   返回文件访问地址（适合上传图片）
     */
    public  Map<String, String> uploadStreamReturnPath(ByteArrayOutputStream baos, String bucketName, String key){
        Map<String, String> result = new HashMap<>(2);
        try {
            PutObjectResult putResult = uploadStream(baos, bucketName, key);
            if(null != putResult){
                //解析结果
                //MD5值
                result.put("ETag", putResult.getETag());
                //oss 文件路径 + 文件名 /test/test.png
                result.put("OSSFilePathName", getUrl(key,bucketName,null));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public  PutObjectResult uploadStream(ByteArrayOutputStream baos, String bucketName, String key){
        try {

            byte[] bytes = baos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

            String md5 = BinaryUtil.toBase64String(BinaryUtil.calculateMd5(bytes));
            //创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            //设置md5值校验
            metadata.setContentMD5(md5);
            //上传的文件的长度
            metadata.setContentLength(bais.available());
            //指定该Object被下载时的网页的缓存行为
            metadata.setCacheControl("no-cache");
            //指定该Object下设置Header
            metadata.setHeader("Pragma", "no-cache");
            //指定该Object被下载时的内容编码格式
            metadata.setContentEncoding("utf-8");
            //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
            //如果没有扩展名则填默认值application/octet-stream
            //metadata.setContentType(getContentType(fileName));
            //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
            //metadata.setContentDisposition("filename/filesize=" + fileName + "/" + bytes.length + "Byte.");
            //上传文件   (上传文件流的形式)
            //存储路径

            // 带进度条的上传。
            /*PutObjectResult putResult = client.putObject(new PutObjectRequest(bucketName, key, bais,metadata).
                    <PutObjectRequest>withProgressListener(new PutObjectProgressListener()));*/

            PutObjectResult putResult = this.oSSClient.putObject(bucketName, key, bais, metadata);
            return putResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }





    /**
     * 下载到本地文件
     * @param bucketName    Bucket名
     * @param key  OSS上的文件名称
     * @param filename     文件下载到本地保存的路径
     */
    public void downloadFile( String bucketName, String key, String filename) {

        // 带进度条的下载。
        /*AliYunOssClientUtils.getOSSClient().getObject(
                new GetObjectRequest(bucketName, key).
                        <GetObjectRequest>withProgressListener(new GetObjectProgressListener()),
                new File(filename));*/
        this.oSSClient.getObject(new GetObjectRequest(bucketName, key), new File(filename));
    }


    /**
     *  流式下载   (数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作)
     * @param bucketName Bucket名
     * @param key  OSS上的文件名称
     * @return
     */
    public  InputStream downloadStream( String bucketName, String key) {
        OSSObject object = this.oSSClient.getObject(bucketName, key);
        return object.getObjectContent();
    }




    /**
     * 根据key删除OSS服务器上的文件
     * @param bucketName  存储空间
     * @param key  eg:  images/2018/10/16/11a513dd-6389-4230-9aed-0f301c01c63f.JPG
     */
    public void deleteFile(String bucketName,  String key) {
            this.oSSClient.deleteObject(bucketName, key);
           // log.info("删除" + bucketName + "下的文件" + folder + key + "成功");
    }

    /**
     * 删除多个文件
     * @param bucketName
     * @param keys
     */
    public void deleteFile(String bucketName,  List<String> keys) {
        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName).withKeys(keys);
        DeleteObjectsResult deleteObjectsResult =  this.oSSClient.deleteObjects(deleteObjectsRequest);
        List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
        // log.info("删除" + bucketName + "下的文件" + folder + key + "成功");
    }

    /**
     * 拷贝文件
     * @param sourceBucketName  源存储空间
     * @param sourceKey         源文件名称
     * @param destinationBucketName  目标空间
     * @param destinationKey         目标文件名称
     * @return
     */
    public CopyObjectResult copyFile(
            String sourceBucketName,String sourceKey,
            String destinationBucketName,String destinationKey){
        return this.oSSClient.copyObject(sourceBucketName,sourceKey,destinationBucketName,destinationKey);

    }



    /**
     * 获取文件访问地址
     * @param key   文件路径
     * @param bucketName
     * @param activeTime
     * @return
     */
    public String getUrl(String key,  String bucketName, String activeTime) {
        // 设置URL过期时间为10年  3600l* 1000*24*365*10
        long time = 5 * 60000;
        if (StrUtil.isNotBlank(activeTime)) {
            time = Long.parseLong(activeTime) * 60000;
        }
        Date expiration = new Date(System.currentTimeMillis() + time);
        // 生成URL
        URL url = this.oSSClient.generatePresignedUrl(bucketName, key, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }


    /**
     * 判断文件是否存在
     * @param bucketName  存储空间名称
     * @param key        文件路径 eg:  images/2018/10/16/11a513dd-6389-4230-9aed-0f301c01c63f.JPG
     * @return
     */
    public boolean fileExist(String bucketName,String key){

        return this.oSSClient.doesObjectExist(bucketName,key);
    }

    /**
     * 设置文件的访问权限
     * @param bucketName  存储空间名称
     * @param key         文件路径 eg:  images/2018/10/16/11a513dd-6389-4230-9aed-0f301c01c63f.JPG
     * @param control     权限枚举
     */
    public void setObjectAcl(String bucketName, String key, CannedAccessControlList control){
       this.oSSClient.setObjectAcl(bucketName,key,control);
    }

}
