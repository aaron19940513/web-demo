package com.sam.demo.mysql.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "customer_tradetype_relation", schema = "test")
public class CustomerTradetypeRelationEntity {
    private String id;
    private String customerId;
    private String tradeTypeId;

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "CUSTOMER_ID")
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Basic
    @Column(name = "TRADE_TYPE_ID")
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

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, tradeTypeId);
    }
}
