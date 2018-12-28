package org.wang.kuaijiback.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Deprecated
//mybatis分页插件需要
public class Page<T> {
    private T bean;
    private int pageNum;
    private int pageSize;
}
