package ru.otus.jdbc.mapper;

import ru.otus.annotation.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> clazz;
    private final List<Field> allFields;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
        this.allFields = extractFields();
    }

    private List<Field> extractFields() {
        return Arrays.stream(clazz.getDeclaredFields())
                .map(field -> {
                    field.setAccessible(true);
                    return field;
                })
                .toList();
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        try {
            return clazz.getConstructor();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Field getIdField() {
        return allFields.stream()
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public List<Field> getAllFields() {
        return this.allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return allFields.stream()
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .toList();
    }

}