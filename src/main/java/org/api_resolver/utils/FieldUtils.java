package org.api_resolver.utils;

import java.lang.reflect.Field;

public class FieldUtils {

    public static long countNonNullFields(Object dto)
    {
        long nonNullCount = 0;

        Field[] fields = dto.getClass().getDeclaredFields(); // Note Reflection İle Runtime'da Class yapısına göre Obje Oluşturulup

        for (Field field : fields) {
            field.setAccessible(true);

            try {
                Object value = field.get(dto);

                if (value != null)
                {
                    nonNullCount++;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return nonNullCount;
    }
}
