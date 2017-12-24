package cn.com.pool.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;

/**
 * Created by yinchao on 2017/12/24.
 */
public class DBConnectionPool extends Timer{
    private Connection conn;
    private int inUsed;//使用的连接数
    private ArrayList<Connection> freeConnection;//空闲连接
    private int minConn;//最小连接数
    private int maxConn;//最大连接数
    private String name;//连接池名字
    private String password;//连接池密码
    private String url;//数据库链接地址
    private String driver;//数据库连接驱动
    private String user;//用户名
    private Timer timer;//定时


    public DBConnectionPool(){

    }

    /**
     * 初始化连接池信息
     */
    public DBConnectionPool(String name,String driver,String URL,String user,String password,int maxConn ){
        this.name=name;
        this.driver=driver;
        this.url=URL;
        this.user=user;
        this.password=password;
        this.maxConn=maxConn;
    }

    /**
     * 释放连接
     * @param  conn
     */
    public synchronized void freeConnection(Connection conn){
        this.freeConnection.add(conn);
        this.inUsed--;
    }

    /**
     * 通过timeout获取连接
     * @param timeout
     * @return conn
     */
    public synchronized Connection getConnection(long timeout){
        Connection conn=null;
        if(this.freeConnection.size()>0){
            conn=this.freeConnection.get(0);
            if(conn==null){
                conn=getConnection(timeout);
            }
        }else{
            conn=newConnection();

        }
        if(this.inUsed>this.maxConn||this.maxConn==0){
            conn=null;
        }
        if(conn!=null){
            this.inUsed++;
        }
        return conn;
    }

    /**
     * 从连接池中获取连接
     * @return conn
     */
    public Connection getConnection(){
        Connection conn=null;
        if(this.freeConnection.size()>0){
            conn=this.freeConnection.get(0);
            this.freeConnection.remove(0);
            if(conn==null){
                conn=getConnection();
            }
        }else{
            conn=newConnection();

        }
        if(this.inUsed>this.maxConn||this.maxConn==0){
            conn=null;
        }
        if(conn!=null){
            this.inUsed++;
        }
        return conn;
    }

    /**
     * 释放全部的连接
     */
    public synchronized void release(){
        Iterator<Connection> iterator=this.freeConnection.iterator();
        while(iterator.hasNext()){
            Connection conn=iterator.next();
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                if(conn!=null){
                    conn=null;
                }
            }
        }
        this.freeConnection.clear();
    }


    /**
     * 创建新的连接
     * @return conn
     */
    public Connection newConnection(){
        try {
            Class.forName(driver);
            conn= DriverManager.getConnection(url,user,password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 定时处理函数
     */
    public void TimerEvent(){

    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public int getInUsed() {
        return inUsed;
    }

    public void setInUsed(int inUsed) {
        this.inUsed = inUsed;
    }

    public int getMinConn() {
        return minConn;
    }

    public void setMinConn(int minConn) {
        this.minConn = minConn;
    }

    public int getMaxConn() {
        return maxConn;
    }

    public void setMaxConn(int maxConn) {
        this.maxConn = maxConn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
