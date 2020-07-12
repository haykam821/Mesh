/*
 * Mesh
 * Copyright (C) 2019-2020 GlassPane
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; If not, see <https://www.gnu.org/licenses>.
 */
package io.github.glasspane.mesh.util.reflection;

import io.github.glasspane.mesh.Mesh;
import net.fabricmc.loader.api.FabricLoader;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionHelper {

    private static final boolean devEnv;

    static {
        devEnv = MappingFormats.NAMED.equals(FabricLoader.getInstance().getMappingResolver().getCurrentRuntimeNamespace());
        if (devEnv) {
            Mesh.getLogger().info("Detected NAMED mappings, we are in a development environment!");
        }
    }

    public static <T> MethodInvoker<T> getMethodInvoker(Class<T> clazz, String obfName, @Nullable String yarnName, Class<?>... parameters) {
        return new MethodInvoker<>(getMethod(clazz, obfName, yarnName, parameters));
    }

    public static <T> Method getMethod(Class<T> clazz, String obfName, @Nullable String yarnName, Class<?>... parameters) {
        Method method = null;
        try {
            if (devEnv && yarnName != null) {
                method = clazz.getDeclaredMethod(yarnName, parameters);
            } else {
                method = clazz.getDeclaredMethod(obfName, parameters);
            }
        } catch (NoSuchMethodException e) {
            Mesh.getLogger().fatal(String.format("unable to find method %s (%s) in class %s", obfName, yarnName, clazz.getCanonicalName()), e);
        }
        if (method != null) {
            method.setAccessible(true);
            return method;
        }
        throw new IllegalStateException("reflection error: method");
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public static <T> T getField(Class<T> clazz, @Nullable T instance, String obfName, @Nullable String yarnName) {
        Field f = null;
        try {
            if (devEnv && yarnName != null) {
                f = clazz.getDeclaredField(yarnName);
            } else {
                f = clazz.getDeclaredField(obfName);
            }
        } catch (NoSuchFieldException e) {
            Mesh.getLogger().fatal(String.format("unable to find field %s (%s) in class %s", obfName, yarnName, clazz.getCanonicalName()), e);
        }
        if (f != null) {
            f.setAccessible(true);
            try {
                return (T) f.get(instance);
            } catch (IllegalAccessException | ClassCastException e) {
                Mesh.getLogger().fatal(String.format("unable to access field %s in class %s", f.getName(), clazz.getCanonicalName()), e);
            }
        }
        throw new IllegalStateException("reflection error: field");
    }

    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            Mesh.getLogger().fatal("unable to instantiate " + clazz.getCanonicalName(), e);
            throw new IllegalStateException("reflection error: instance");
        }
    }
}
