package com.sam.demo.util;


import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.AbstractConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.StringUtils;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * DB table field reflection
 */
public class FieldUtils {
    /**
     * table column name and entity name reflection
     */
    private static Map<String, Map<String, String>> columnAndField = new HashMap<>();


    /**
     * set the field value by the field name
     *
     * @param object     object
     * @param fieldName  one table column name
     * @param fieldValue filed value
     * @throws Exception Thrown when get the write method
     */
    public static void setValueByField(Object object, String fieldName, Object fieldValue) throws Exception {
        if (object == null || StringUtils.isEmpty(fieldName)) {
            return;
        }
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, fieldValue);
        field.setAccessible(false);
    }

    /**
     * Get non-null String value by field name
     *
     * @param object    object
     * @param fieldName field name
     * @return string value
     * @throws Exception when static function throws exception
     */
    public static String getStringValueByField(Object object, String fieldName) throws Exception {
        String value = "";
        Object valueObj = getValueByField(object, fieldName);
        if (valueObj != null) {
            value = valueObj.toString();
        }

        return value;
    }

    /**
     * get the field value by the field name
     *
     * @param object    object
     * @param fieldName one table column name
     * @return file value
     * @throws Exception Thrown when get the read method
     */
    public static Object getValueByField(Object object, String fieldName) throws Exception {
        if (object == null || StringUtils.isEmpty(fieldName)) {
            return null;
        }
        // get the filed value
        // PropertyDescriptor pd = new PropertyDescriptor(fieldName, object.getClass());
        // return pd.getReadMethod().invoke(object);
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        Object obj = field.get(object);
        field.setAccessible(false);
        return obj;
    }


    /**
     * get field by field Name
     *
     * @param object    object
     * @param fieldName one field name
     * @return field type
     * @throws Exception Thrown when the class doesn't have a field of a specified name.
     */
    public static Class getFieldTypeByName(Object object, String fieldName) throws Exception {
        if (object == null || StringUtils.isEmpty(fieldName)) {
            return null;
        }
        return getFieldTypeByName(object.getClass(), fieldName);
    }

    /**
     * Gets field type by name.
     *
     * @param c         the c
     * @param fieldName the field name
     * @return the field type by name
     * @throws Exception the exception
     */
    public static Class getFieldTypeByName(Class c, String fieldName) throws Exception {
        if (c == null) {
            return null;
        }
        return c.getDeclaredField(fieldName).getType();
    }


    private static class CustomStringConverter extends AbstractConverter {
        /**
         * Instantiates a new Custom string converter.
         *
         * @param defaultValue the default value
         */
        public CustomStringConverter(Object defaultValue) {
            super(defaultValue);
        }

        @Override
        protected String convertToString(Object value) throws Throwable {
            String result;
            if (value instanceof Double) {
                Double doubleValue = (Double) value;
                if (doubleValue - doubleValue.intValue() == 0) {
                    result = doubleValue.intValue() + "";
                } else {
                    result = super.convertToString(value);
                }
            } else if (value instanceof Float) {
                Float floatValue = (Float) value;
                if (floatValue - floatValue.intValue() == 0) {
                    result = floatValue.intValue() + "";
                } else {
                    result = super.convertToString(value);
                }
            } else if (value instanceof String) {
                String strValue = (String) value;
                String floatPattern = "\\d+\\.\\d+";
                boolean isFloatString = Pattern.compile(floatPattern).matcher(strValue).matches();
                if (!isFloatString) {
                    result = super.convertToString(value);
                } else {
                    Double doubleValue = Double.parseDouble(strValue);
                    if (doubleValue - doubleValue.intValue() == 0) {
                        result = doubleValue.intValue() + "";
                    } else {
                        result = super.convertToString(value);
                    }
                }
            } else {
                result = super.convertToString(value);
            }
            return result;
        }

        @Override
        protected <T> T convertToType(Class<T> type, Object value) {
            // We have to support Object, too, because this class is sometimes
            // used for a standard to Object conversion
            if (String.class.equals(type) || Object.class.equals(type)) {
                return type.cast(value.toString());
            }
            throw conversionException(type, value);
        }

        @Override
        protected Class<?> getDefaultType() {
            return String.class;
        }
    }


    /**
     * Gets field annotation.
     *
     * @param <T>            the type parameter
     * @param c              the c
     * @param fieldName      the field name
     * @param anntationClass the anntation class
     * @return the field annotation
     * @throws NoSuchFieldException the no such field exception
     */
    public static <T extends Annotation> T getFieldAnnotation(Class c, String fieldName, Class<T> anntationClass) throws NoSuchFieldException {
        T t = null;
        Field field = c.getDeclaredField(fieldName);
        boolean fieldHasAnnotation = field.isAnnotationPresent(anntationClass);
        if (fieldHasAnnotation) {
            t = field.getAnnotation(anntationClass);
        }
        return t;
    }

}
