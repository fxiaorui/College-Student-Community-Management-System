package com.example.college.student.community.management.system.common.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.college.student.community.management.system.common.annotation.QueryName;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

/**
 * 将自定义查询参数类转化为MyBatisPlus框架中的QueryWrapper对象的转换器工具类
 */
public class QueryToQueryWrapperConverter {

    /**
     * 将自定义查询参数类转化为MyBatisPlus框架中的QueryWrapper对象
     *
     * @param query 请求参数类(参数类要求所有属性必须为String类型,且属性名与数据库字段名相同)
     * @param <T>   数据库实体类类型
     * @return 包含了查询参数的QueryWrapper对象
     */
    public synchronized static <T> QueryWrapper<T> convert(Object query) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        try {
            Class<?> c = query.getClass();
            Field[] fields = c.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String o = null;
                if (field.get(query) instanceof String) {
                    o = (String) field.get(query);
                }
                if (StringUtils.hasText(o) && field.isAnnotationPresent(QueryName.class)) {
                    String column = field.getDeclaredAnnotation(QueryName.class).value();
                    if (field.getDeclaredAnnotation(QueryName.class).isFuzzy()) {
                        wrapper.like(column, o);
                    } else {
                        wrapper.eq(column, o);
                    }
                }
            }
        } catch (IllegalAccessException ignored) {
        }
        return wrapper;
    }
}
