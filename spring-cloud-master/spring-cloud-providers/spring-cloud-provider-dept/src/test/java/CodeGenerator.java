import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: FengJie
 * @Date: 2019/2/28 13:31
 * @Description: 代码生成器
 * 可以快速生成 Entity、Mapper、Mapper XML、Service、Controller 等各个模块的代码，
 * 极大的提升了开发效率
 */
public class CodeGenerator {
    /**
     * 生成代码的目录
     */
    private static String outDirPath = "E:\\Generator";
    /**
     * 代码生成器
     */
    private static  AutoGenerator autoGenerator = new AutoGenerator();

    public static void main(String[] args) throws FileNotFoundException {
        autoGenerator.setGlobalConfig(globalConfig("Fengjie"))
                .setDataSource(dataSourceConfig())
                .setPackageInfo(packageConfig("com.company.dept",null))
                .setCfg(injectionConfig(null))
                .setStrategy(strategyConfig(
                        //设置表名
                        new String[]{
                                "dept"
                        }
                )).execute();
        System.out.println(autoGenerator.getCfg().getMap().get("abc"));
    }


    /**
     * 全局配置
     * @param author 作者
     * @return
     */
    private static GlobalConfig globalConfig(String author){
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(outDirPath)
                //设置作者
                .setAuthor(author).setOpen(true)
                //是否覆盖
                .setFileOverride(true)
                // 不需要ActiveRecord特性的请改为false
                .setActiveRecord(true)
                // XML 二级缓存
                .setEnableCache(false)
                // XML ResultMap
                .setBaseResultMap(true)
                // XML columList
                .setBaseColumnList(true);
        return gc;
    }

    /**
     * 配置数据源
     * @return
     */
    private static DataSourceConfig dataSourceConfig(){
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("Cpic1234");
        dsc.setUrl("jdbc:mysql://localhost:3306/cloud_db01?useSSL=false&useUnicode=true&characterEncoding=utf8&serverTimezone=UTC");
        dsc.setTypeConvert(new MySqlTypeConvert());
        return dsc;
    }

    /**
     * 策略配置
     * @param tables 需要生成实体的表
     * @return
     */
    private static StrategyConfig strategyConfig(String[] tables){
        StrategyConfig strategy = new StrategyConfig();
        // 表名生成策略 下划线转驼峰命名
        strategy.setNaming(NamingStrategy.underline_to_camel)
                // 需要生成的表,RestController lombok 实体
                .setInclude(tables).setRestControllerStyle(true).setEntityLombokModel(true)
                //字段注解
                .setEntityTableFieldAnnotationEnable(true)
                //乐观锁字段名称
                .setVersionFieldName("version").setNaming(NamingStrategy.underline_to_camel)
                //是否为构建者模型
                .setEntityBuilderModel(true);
        return strategy;
    }

    /**
     * 配置包
     * @param parent
     * @param module
     * @return
     */
    private static PackageConfig packageConfig(String parent,String module){
        PackageConfig packageConfig = new PackageConfig();
        //设置父目录
        packageConfig.setParent(parent);
        if(StrUtil.isNotEmpty(module)){
            //实体
            packageConfig.setEntity("entities"+"."+module)
                    //Mapper 接口
                    .setMapper("mapper"+"."+module)
                    .setXml("mapperXml"+"."+module)
                    //Service 接口
                    .setService("service"+"."+module)
                    //ServiceImpl
                    .setServiceImpl("serviceipml"+"."+module)
                    //Controller
                    .setController("controller"+"."+module);
        }else {
            packageConfig.setEntity("entities").setMapper("mapper").setXml("mapperXml")
                    .setService("service").setServiceImpl("serviceipml").setController("controller");
        }
        return packageConfig;
    }

    /**
     * 自定义配置
     * @param module
     * @return
     */
    private static InjectionConfig injectionConfig(String module){
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
            }
        };
        return cfg;
    }
}