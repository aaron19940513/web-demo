package com.sam.demo.mysql.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sam.demo.enums.SexEnum;
import com.sam.demo.enums.SexEnumConvert;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer", schema = "test")
public class CustomerEntity implements Serializable {

    @Id
    @Column(name = "ID")
    private int id;
    @Basic
    @Column(name = "NAME")
    private String name;
    @Basic
    @Column(name = "COUNTRY")
    private String country;
    @Basic
    @Column(name = "AGE")
    private int age;
    @Basic
    @Column(name = "SEX")
    // @Enumerated(EnumType.STRING)
    @Convert(converter = SexEnumConvert.class)
    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
    //@JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingName)
    private SexEnum sex;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    private List<CustomerTradetypeRelationEntity> customerTradetypeRelationEntities;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerEntity that = (CustomerEntity) o;
        return id == that.id &&
                age == that.age &&
                Objects.equals(name, that.name) &&
                Objects.equals(country, that.country) &&
                Objects.equals(sex, that.sex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, country, age, sex);
    }
}
