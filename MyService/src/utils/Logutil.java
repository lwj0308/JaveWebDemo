package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

/**
 * @description: TODO
 * @author Linweijun
 * @date 2024/1/6 14:37
 * @version 1.0
 */
public class Logutil {
    private final Logger logger = Logger.getLogger("fly");
    private boolean isDebug = true;

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
        if (isDebug) {
            logger.setLevel(Level.ALL);
        } else {
            logger.setLevel(Level.OFF);
        }
    }

    public Logger getLogger() {
        return logger;
    }

    private Logutil() {

        logger.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler() {{
            setOutputStream(System.out);
        }};
        handler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                String time = format.format(new Date(record.getMillis()));  //格式化日志时间
                String level = record.getLevel().getName();  // 获取日志级别名称
                // String level = record.getLevel().getLocalizedName();   // 获取本地化名称（语言跟随系统）
                String thread = String.format("%10s", Thread.currentThread().getName());  //线程名称（做了格式化处理，留出10格空间）
                long threadID = record.getThreadID();   //线程ID
                String className = String.format("%-20s", record.getSourceClassName());  //发送日志的类名
                String msg = record.getMessage();   //日志消息

                //\033[33m作为颜色代码，30~37都有对应的颜色，38是没有颜色，IDEA能显示，但是某些地方可能不支持
                return "\033[38m" + time + "  \033[33m" + level + " \033[35m" + threadID
                        + "\033[38m --- [" + thread + "] \033[36m" + className + "\033[38m : " + msg + "\n";
            }
        });

        logger.addHandler(handler);
    }


    private static class Builder {
        private static final Logutil INSTANCE = new Logutil();
    }

    public static Logutil getInstance() {
        return Builder.INSTANCE;
    }
}
