package com.sam.demo;
import java.util.List;

import com.google.common.collect.Lists;
import com.sam.demo.VO.MyTestBean;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * @author sam
 * @date 10/10/19 11:09
 */
public class SpringTest {
    @Test
    public void testSimpleLoad() {
        BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("src/test/application.xml"));
        MyTestBean myTestBean = (MyTestBean) beanFactory.getBean("myTestBean");
        System.out.println(myTestBean.getTestStr());
    }

    @Test
    public void test() {
        int a = getresult();
    }

    private int getresult() {
        List<Integer> a = Lists.newArrayList(1, 2, 3, 4, 5);
        int b = 0;
        a.forEach(element -> {
            if (element == 3) {
                return;
            }
        });

        return b;
    }
}
