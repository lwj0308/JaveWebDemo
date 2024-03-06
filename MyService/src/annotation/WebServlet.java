package annotation;

import javax.annotation.Resource;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Cynicism
 * @version 1.0
 * @project JavaWeb
 * @description
 * @date 2024/3/6 11:29:05
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)//虚拟机什么时候找他
public @interface WebServlet {
    String value() default "/";//默认属性
}
