package com.company.utils.files.aliyunoss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.AccessControlList;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.BucketInfo;
import com.aliyun.oss.model.CannedAccessControlList;

import java.util.List;

/**
 * @author FengJie
 * @Date: 2018/10/15 14:15
 * @Description:  阿里OssClient 工具类
 */
public class AliYunOssClientUtils {

    /**
     * 对象存储客户端
     */
    public volatile  OSSClient oSSClient;

    /**
     * 通过构造函数传递参数
     */
    public AliYunOssClientUtils(OSSClient oSSClient) {
        this.oSSClient = oSSClient;
    }

    /**
     * 单例
     * @return  OSS客户端实例
     */
   /* public static OSSClient getOSSClient(String endPoint,String accessKeyId,String accessKeySecret) {
        if (instance == null) {
            synchronized (AliYunOssClientUtils.class) {
                if (instance == null) {
                    ClientConfiguration conf = new ClientConfiguration();
                    conf.setConnectionTimeout(5000);
                    conf.setMaxErrorRetry(10);
                    instance = new OSSClient(endPoint,
                            accessKeyId,
                            accessKeySecret,
                            conf);
                }
            }
        }
        return instance;
    }*/

    /**
     * 关闭Client实例，并释放所有正在使用的资源。
     * @param client
     */
    public  void shutdown(OSSClient client){
        client.shutdown();
    }

    /**
     * 创建存储空间
     * @param bucketName 存储空间名称
     * @return
     */
    public String createBucketName(String bucketName){

            if(!this.oSSClient.doesBucketExist(bucketName)){
                //创建存储空间
                Bucket bucket=this.oSSClient.createBucket(bucketName);
                return bucket.getName();
            }
        return bucketName;
    }

    /**
     * 判断存储空间是否存在
     * @param bucketName
     * @return
     */
    public  boolean doesBucketExist(String bucketName){
            return this.oSSClient.doesBucketExist(bucketName);
    }

    /**
     * 删除存储空间
     * @param bucketName
     */
    public void deleteBucket(String bucketName){
        this.oSSClient.deleteBucket(bucketName);
    }

    /**
     * 列举存储空间
     * @return
     */
    public  List<Bucket> listBuckets(){
        return this.oSSClient.listBuckets();
    }

    /**
     * 设置存储空间的访问权限
     * @param bucketName  存储空间
     * @param controlList  权限参数
     */
    public  void setBucketAcl(String bucketName, CannedAccessControlList controlList ){
          this.oSSClient.setBucketAcl(bucketName,controlList);
    }

    /**
     * 获取存储空间的访问权限
     * @param bucketName
     * @return
     */
    public  AccessControlList getBucketAcl(String bucketName){
          return this.oSSClient.getBucketAcl(bucketName);
    }

    /**
     * 获取存储空间的地域
     * @param bucketName
     * @return
     */
    public  String getBucketLocation(String bucketName){
        return  this.oSSClient.getBucketLocation(bucketName);
    }

    /**
     * 获取存储空间的信息
     * @param bucketName
     * @return
     */
    public  BucketInfo getBucketInfo(String bucketName) {
        return this.oSSClient.getBucketInfo(bucketName);
    }

}
