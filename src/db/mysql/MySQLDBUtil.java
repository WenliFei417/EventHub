package db.mysql;

public class MySQLDBUtil {
    private static final String HOSTNAME = "127.0.0.1";
    private static final String PORT_NUM = "3306";
    public static final String DB_NAME = "wlproject";

    // 从环境变量读取用户名和密码，如果没有设置则使用默认值
    public static final String USERNAME = System.getenv().getOrDefault("DB_USER", "root");
    public static final String PASSWORD = System.getenv().getOrDefault("DB_PASS", "");
    public static final String URL = "jdbc:mysql://"
            + HOSTNAME + ":" + PORT_NUM + "/" + DB_NAME
            + "?useSSL=false&serverTimezone=UTC";
}
