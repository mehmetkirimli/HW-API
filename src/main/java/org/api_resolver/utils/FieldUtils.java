package org.api_resolver.utils;

import java.lang.reflect.Field;

public class FieldUtils {

    public static int countNonNullFields(Object dto)
    {
        int nonNullCount = 0;

        Field[] fields = dto.getClass().getDeclaredFields();
        //Note : Reflection , method ve field'a runtime esnasında erişebiliyo.

        for (Field field : fields)
        {
            field.setAccessible(true);
            try
            {
                Object value = field.get(dto);
                if (value != null)
                {
                    nonNullCount++;
                }
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
        return nonNullCount;
    }
}
