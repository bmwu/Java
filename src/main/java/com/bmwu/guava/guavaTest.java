package com.bmwu.guava;

import com.google.common.base.Joiner;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author bmwu.hz
 * @date 2018-05-19
 */
public class guavaTest {

    @Data
    public class Inventory implements Serializable {


        private static final long serialVersionUID = 6727896701288330152L;
        /**
         * entity id
         */
        private String entityId;

        /**
         * 类型：SkuId(1), SkuCode(2)
         */
        private Integer entityType;

        /**
         * 唯一键，用于幂等
         */
        private String uniqueCode;

        /**
         * 仓库编码
         */
        private String warehouseCode;

        /**
         * 仓库类型
         */
        private Integer warehouseType;

        /**
         * 真实库存(物理库存)
         */
        private Long realQuantity;

        /**
         * 安全库存
         */
        private Long safeQuantity;

        /**
         * 在途库存(JIT)
         */
        private Long preorderQuantity;

        /**
         * 锁定库存
         */
        private Long withholdQuantity;

        /**
         * 占用库存
         */
        private Long occupyQuantity;

        /**
         * 状态：Active(1), Inactive(0)
         */
        private Integer status;

        /**
         * sku code
         */
        private String skuCode;


        /**
         * 版本号，乐观锁
         */
        private Integer version;

        /**
         * 创建时间
         */
        private Date createdAt;

        /**
         * 更新时间
         */
        private Date updatedAt;
    }

    @Data
    @EqualsAndHashCode(of = {"warehouseCode", "warehouseType"})
    public class Warehouse implements Serializable {

        private static final long serialVersionUID = 217532931481970726L;

        /**
         * 编码
         */
        private String warehouseCode;

        /**
         * 名称
         */
        private String warehouseName;

        /**
         * 类型
         */
        private Integer warehouseType;

        /**
         * priority
         */
        private Integer priority;

        /**
         * 地址
         */
        private String address;

        /**
         * 是否默认仓库
         */
        private Integer isDefault;

        /**
         * 省市区Id
         */
        private String divisionId;

        /**
         * 区域
         */
        private String regionId;


        /**
         * 创建时间
         */
        private Date createdAt;

        /**
         * 更新时间
         */
        private Date updatedAt;


        public String getCacheKey() {
            StringBuffer stringBuffer = new StringBuffer();
            return stringBuffer.append(warehouseCode).
                    append("&_&").append(warehouseType).toString();
        }

        public Warehouse(String warehouseCode, Integer warehouseType) {
            this.warehouseCode = warehouseCode;
            this.warehouseType = warehouseType;
        }

    }




    @Test
    public void findWarehousesByEntity() {

        int count = 0;

        if (++count == 0) {
            System.out.println("AA");
        }

        // TODO: 16/05/2018 校验逻辑 entityId, entityType

        List<Inventory> inventories = new ArrayList<>();
        for (int i = 0; i<10;i++) {
            Inventory inventory = new Inventory();
            inventory.setEntityId("entityId" + i);
            inventory.setEntityType(1);
            inventories.add(inventory);
        }

        String result = "";

        if(!CollectionUtils.isEmpty(inventories)){
            List<String> warehouseKeys = inventories.stream().
                    map(inv -> new Warehouse(inv.getWarehouseCode(), inv.getWarehouseType())).
                    map(Warehouse::getCacheKey).collect(Collectors.toList());
            result = Joiner
                    .on(",")
                    .skipNulls()
                    .join(warehouseKeys);
        }
        System.out.println(result);
    }

}
