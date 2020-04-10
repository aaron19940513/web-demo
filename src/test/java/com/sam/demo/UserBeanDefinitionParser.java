// package com.sam.demo;
//
//
// import com.sam.demo.bean.User;
// import org.springframework.beans.factory.support.BeanDefinitionBuilder;
// import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
// import org.w3c.dom.Element;
//
// /**
//  * @author sam
//  * @date 11/01/19 13:51
//  */
// public class UserBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
//     protected Class getBeanClass(Element element) {
//         return User.class;
//     }
//
//     @Override
//     protected void doParse(Element element, BeanDefinitionBuilder builder) {
//         String userName = element.getAttribute("userName");
//         String email = element.getAttribute("email");
//         builder.addPropertyValue("userName", userName);
//         builder.addPropertyValue("email", email);
//     }
// }
