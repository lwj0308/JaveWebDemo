package entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cynicism
 * @version 1.0
 * @project JavaWeb
 * @description 分页
 * @date 2024/3/4 19:49:25
 */

@Data
@NoArgsConstructor
public class Page<T> {
    private int pageIndex;//第几页
    private int rowCount;//总行数
    private int pageSize = 5;//每页显示行数
    private int pageCount;//总页数

    private List<T> item = new ArrayList<>();
}
