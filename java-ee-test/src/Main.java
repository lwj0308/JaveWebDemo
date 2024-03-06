import entity.Student;
import jdk.nashorn.api.scripting.JSObject;
import lombok.Cleanup;
import lombok.SneakyThrows;
import mapper.TestMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class Main {
//    @SneakyThrows(IOException.class)

    public static void main(String[] args) throws FileNotFoundException {
//        Student student = new Student();
//
//        System.out.println(student);
//
////        @Cleanup
////        try (FileInputStream inputStream = new FileInputStream("")) {
////        } catch (IOException e) {
////            throw new RuntimeException(e);
////        }
//
//        HashMap<String, Object> hashMap = new HashMap<>();
//        System.out.println(hashMap.get("aa"));

//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(new FileInputStream("java-ee-test/mybatis-config.xml"));
        try(SqlSession session =MybatisUtil.getSession(true)){
//            List<Student> students = session.selectList("selectStudent");
//            students.forEach(System.out::println);
//            System.out.println(Optional.ofNullable(session.selectOne("selectStudent", 3)));
            TestMapper mapper = session.getMapper(TestMapper.class);
            mapper.selectStudent().forEach(System.out::println);
        }

    }
}
