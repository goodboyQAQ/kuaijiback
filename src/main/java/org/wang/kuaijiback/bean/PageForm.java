package org.wang.kuaijiback.bean;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class PageForm {
    private Page page;
    private Map<String,String> form;
}
