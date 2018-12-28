package org.wang.kuaijiback.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ApplyMaterial {
    private Integer id;
    private Integer materialId;
    private String materialName;
    private String suppilerId;
    private String suppiler;
    private int number;
    private Date applyTime;
    private int realNumber;
    private Date realTime;
    private String status;

}
