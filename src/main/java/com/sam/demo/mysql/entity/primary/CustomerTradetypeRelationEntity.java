package com.sam.demo.mysql.entity.primary;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "customer_tradetype_relation", schema = "test")
public class CustomerTradetypeRelationEntity {
    @Id
    @Column(name = "ID")
    private String id;

    @Basic
    @Column(name = "CUSTOMER_ID")
    private String customerId;

    @Basic
    @Column(name = "TRADE_TYPE_ID")
    private String tradeTypeId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }


    public String getTradeTypeId() {
        return tradeTypeId;
    }

    public void setTradeTypeId(String tradeTypeId) {
        this.tradeTypeId = tradeTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerTradetypeRelationEntity that = (CustomerTradetypeRelationEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(customerId, that.customerId) &&
                Objects.equals(tradeTypeId, that.tradeTypeId);
    }

}
