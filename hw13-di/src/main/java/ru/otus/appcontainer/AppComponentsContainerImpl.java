package ru.otus.appcontainer;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.appcontainer.exception.ComponentNotFoundException;
import ru.otus.appcontainer.exception.ComponentCountException;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    private static final String ERROR_COMPONENT_NOT_FOUND = "Not found component with name: %s";
    private static final String ERROR_COMPONENT_COUNT = "More than one component was found with name: %s";
    private static final String ERROR_CONFIG_CLASS = "Class is not configuration: %s";

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        List<Object> list = appComponents.stream()
                .filter(component ->
                        componentClass.isAssignableFrom(component.getClass()) ||
                                component.getClass().equals(componentClass))
                .toList();
        if (list.isEmpty()) {
            throw new ComponentNotFoundException(String.format(ERROR_COMPONENT_NOT_FOUND, componentClass.getName()));
        }
        if (list.size() > 1) {
            throw new ComponentCountException(String.format(ERROR_COMPONENT_COUNT, componentClass.getName()));
        }
        return (C) list.getFirst();
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        var component = appComponentsByName.get(componentName);
        if (component == null) {
            throw new ComponentNotFoundException(String.format(ERROR_COMPONENT_NOT_FOUND, componentName));
        }
        return (C) component;
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        loadComponents(configClass);
    }

    private void loadComponents(Class<?> configClass) {
        try {
            var configInstance = configClass.getDeclaredConstructor().newInstance();
            Arrays.stream(configClass.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent((AppComponent.class)))
                    .sorted(Comparator.comparingInt(method -> method.getAnnotation(AppComponent.class).order()))
                    .forEach(method -> {
                        var args = getArgs(method);
                        createAppComponent(method, configInstance, args);
                    });
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private Object[] getArgs(Method method) {
        var params = method.getParameterTypes();
        var args = new Object[params.length];

        for (int i = 0; i < params.length; i++) {
            args[i] = getAppComponent(params[i]);
        }
        return args;
    }

    private void createAppComponent(Method method, Object configInstance, Object[] args) {
        try {
            var component = method.invoke(configInstance, args);
            var name = method.getAnnotation(AppComponent.class).name();
            if (!appComponentsByName.containsKey(name)) {
                appComponents.add(component);
                appComponentsByName.put(name, component);
            } else {
                throw new ComponentCountException(String.format(ERROR_COMPONENT_COUNT, name));
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format(ERROR_CONFIG_CLASS, configClass.getName()));
        }
    }

}