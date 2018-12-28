package org.wang.kuaijiback.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class InventoryAction {
    private Long id;
    private Long inventoryId;
    private String inventoryName;
    private String invenrotyType;
    private String in_out;
    private Integer number;
    private Date actionTime;
    private String status;
}
