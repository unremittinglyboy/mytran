package com.example.boke.Util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

//返回blog在数据传递过程中为null的属性的名字组成的数组。
public class MyBeanUtils {
    public static String[] getNullPropertyNames(Object obj){
        BeanWrapper beanWrapper = new BeanWrapperImpl(obj);
        PropertyDescriptor[] pds = beanWrapper.getPropertyDescriptors();
        List<String> nullPropertyNames = new ArrayList<>();
        for(PropertyDescriptor pd : pds){
            String propertyName = pd.getName();
            if(beanWrapper.getPropertyValue(propertyName) == null){
                nullPropertyNames.add(propertyName);
            }
        }
        return nullPropertyNames.toArray(new String[nullPropertyNames.size()]);
    }
}
