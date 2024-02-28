import jdk.nashorn.api.scripting.JSObject;
import lombok.Cleanup;
import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class Main {
//    @SneakyThrows(IOException.class)
    public static void main(String[] args) {
        Student student = new Student();

        System.out.println(student);

//        @Cleanup
//        try (FileInputStream inputStream = new FileInputStream("")) {
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        HashMap<String, Object> hashMap = new HashMap<>();
        System.out.println(hashMap.get("aa"));

    }
}
