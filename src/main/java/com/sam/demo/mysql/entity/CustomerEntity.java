package com.sam.demo.mysql.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sam.demo.enums.SexEnum;
import com.sam.demo.enums.SexEnumConvert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
@Builder
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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public SexEnum getSex() {
        return sex;
    }
    public void Sex(SexEnum sex) {
        this.sex = sex;
    }



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
