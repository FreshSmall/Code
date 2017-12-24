package cn.com.pool;

import cn.com.pool.demo.DSConfigBean;
import cn.com.pool.demo.ParseDSConfig;

/**
 * Created by yinchao on 2017/12/22.
 *
 */
public class DataPoolUtil {
    public static void main(String[] args){
//        System.out.println("测试数据库连接池！");

        ParseDSConfig pd=new ParseDSConfig();
        String path="config.xml";
        pd.readConfigInfo(path);
        //pd.delConfigInfo(path, "tj012006");
        DSConfigBean dsb=new DSConfigBean();
        dsb.setType("oracle");
        dsb.setName("yyy004");
        dsb.setDriver("org.oracle.jdbc");
        dsb.setUrl("jdbc:oracle://localhost");
        dsb.setUsername("sa");
        dsb.setPassword("");
        dsb.setMaxconn(1000);
        pd.addConfigInfo(path, dsb);
        pd.delConfigInfo(path, "yyy001");
    }


}
