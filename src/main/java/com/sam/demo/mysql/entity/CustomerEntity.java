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
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "NAME")
    private String name;
    @Basic
    @Column(name = "COUNTRY")
    private String country;
    @Basic
    @Column(name = "AGE")
    private Integer age;
    @Basic
    @Column(name = "SEX")
    // @Enumerated(EnumType.STRING)
    @Convert(converter = SexEnumConvert.class)
    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
    //@JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingName)
    private SexEnum sex;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "CUSTOMER_ID")
    private List<CustomerTradetypeRelationEntity> customerTradetypeRelationEntities;



    @Override
    public int hashCode() {
        return Objects.hash(id, name, country, age, sex);
    }
}
