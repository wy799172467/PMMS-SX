package com.geno.pm.pmms_sx.util;


import com.geno.pm.pmms_sx.Bean.FieldMeta;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Parent<T> {

    Parent() {
    }

    @SuppressWarnings("unchecked")
    public List<SortableField> init() {
        List<SortableField> list = new ArrayList<>();
        /**getClass().getGenericSuperclass()返回表示此 Class 所表示的实体（类、接口、基本类型或 void）
         * 的直接超类的 Type(Class<T>泛型中的类型)，然后将其转换ParameterizedType。。
         *  getActualTypeArguments()返回表示此类型实际类型参数的 Type 对象的数组。
         *  [0]就是这个数组中第一个了。。
         *  简而言之就是获得超类的泛型参数的实际类型。。*/
        Class<? extends Parent> aClass = this.getClass();
        Type genericSuperclass = aClass.getGenericSuperclass();
        Class<T> entity;
        if (genericSuperclass instanceof ParameterizedType) {
            //参数化类型
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            //返回表示此类型实际类型参数的 Type 对象的数组
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            entity = (Class<T>) actualTypeArguments[0];
        } else {
            entity = (Class<T>) genericSuperclass;
        }
//        entity = (Class<T>)((ParameterizedType)this.getClass().getGenericSuperclass())
//                .getActualTypeArguments()[0];
//      FieldMeta filed = entity.getAnnotation(FieldMeta.class);

        if (entity != null) {

            /**返回类中所有字段，包括公共、保护、默认（包）访问和私有字段，但不包括继承的字段
             * entity.getFields();只返回对象所表示的类或接口的所有可访问公共字段
             * 在class中getDeclared**()方法返回的都是所有访问权限的字段、方法等；
             * 可看API
             * */
            Field[] fields = entity.getDeclaredFields();
//
            for (Field f : fields) {
                //获取字段中包含fieldMeta的注解
                @SuppressWarnings("ReflectionForUnavailableAnnotation")
                FieldMeta meta = f.getAnnotation(FieldMeta.class);
                if (meta != null) {
                    SortableField sf = new SortableField(meta, f);
                    list.add(sf);
                }
            }

            //返回对象所表示的类或接口的所有可访问公共方法
            Method[] methods = entity.getMethods();

            for (Method m : methods) {
                @SuppressWarnings("ReflectionForUnavailableAnnotation")
                FieldMeta meta = m.getAnnotation(FieldMeta.class);
                if (meta != null) {
                    SortableField sf = new SortableField(meta, m.getName(), m.getReturnType());
                    list.add(sf);
                }
            }
            //这种方法是新建FieldSortCom类实现Comparator接口，来重写compare方法实现排序
//          Collections.sort(list, new FieldSortCom());
            Collections.sort(list, new Comparator<SortableField>() {
                @Override
                public int compare(SortableField s1, SortableField s2) {
                    return s1.getMeta().value() - s2.getMeta().value();
//                  return s1.getName().compareTo(s2.getName());//也可以用compare来比较
                }

            });
        }
        return list;

    }

}
