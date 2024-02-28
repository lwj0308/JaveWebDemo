import com.alibaba.fastjson2.JSON;
import entity.User;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;

@Log
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        User user = new User("John","1111");
        String jsonString = JSON.toJSONString(user);
        log.info(jsonString);

        User user1 = JSON.parseObject(jsonString, User.class);
        log.info(user1.toString());

        log.info("------------------");
        List<User> users = new ArrayList<>();

        users.add(new User("John","1111"));
        users.add(new User("ammm","2222"));

        String jsonString1 = JSON.toJSONString(users);
        log.info(jsonString1);

        List<User> users1 = JSON.parseArray(jsonString1, User.class);
        log.info(users1.toString());
    }
}