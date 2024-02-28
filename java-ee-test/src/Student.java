import lombok.*;
import lombok.experimental.Accessors;


@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Accessors(fluent = true,makeFinal = true)

public class Student {
    private String name;
    private int age;
    @ToString.Include(name = "set")
    private String sex;
}
@EqualsAndHashCode(callSuper = true)
class SubStu extends Student{
    protected String id;
}