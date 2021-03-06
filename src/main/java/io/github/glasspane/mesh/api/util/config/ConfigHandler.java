/*
 * Mesh
 * Copyright (C) 2019-2021 GlassPane
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
package io.github.glasspane.mesh.api.util.config;

import io.github.fablabsmc.fablabs.api.fiber.v1.annotation.AnnotatedSettings;
import io.github.fablabsmc.fablabs.api.fiber.v1.annotation.SettingNamingConvention;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.JanksonValueSerializer;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.ConfigBranch;
import io.github.glasspane.mesh.util.config.ConfigHandlerImpl;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.function.Supplier;

public interface ConfigHandler {

    JanksonValueSerializer SERIALIZER = new JanksonValueSerializer(false);

    AnnotatedSettings DEFAULT_CONFIG_SETTINGS = AnnotatedSettings.builder().registerTypeMapping(Identifier.class, DefaultFiberConfigTypes.IDENTIFIER).useNamingConvention(SettingNamingConvention.SNAKE_CASE).build();

    static <T> T getConfig(Class<T> configClass) {
        return ConfigHandlerImpl.getConfig(configClass);
    }

    static ConfigBranch getConfigBranch(Class<?> configClass) {
        return ConfigHandlerImpl.getConfigBranch(configClass);
    }

    /**
     * Convenience overload of {@linkplain ConfigHandler#registerConfig(String, String, Class, Supplier)}
     */
    static void registerConfig(String modid, String configPath, Class<?> configClass) {
        registerConfig(modid, configPath, configClass, () -> DEFAULT_CONFIG_SETTINGS);
    }

    /**
     * @param modid           the mod ID for which to register the config. there can only be one config per mod ID.
     * @param configPath      the filename of the config (without .json5 extension)
     * @param configClass     the POJO representation of the configuration
     * @param settingsFactory a factory for providing an instance of {@linkplain AnnotatedSettings}; if not provided {@linkplain ConfigHandler#DEFAULT_CONFIG_SETTINGS} will be used
     * @see AnnotatedSettings.Builder
     * @since 0.5.3
     */
    static void registerConfig(String modid, String configPath, Class<?> configClass, Supplier<AnnotatedSettings> settingsFactory) {
        ConfigHandlerImpl.registerConfig(modid, configPath, configClass, settingsFactory);
    }

    /**
     * @param modid the mod ID for which to register the config. there can only be one config per mod ID.
     */
    static void registerConfig(String modid, Class<?> configClass) {
        registerConfig(modid, modid, configClass);
    }

    static void saveConfig(Class<?> configClass) {
        saveConfig(configClass, getConfigBranch(configClass));
    }

    static void saveConfig(Class<?> configClass, ConfigBranch configBranch) {
        ConfigHandlerImpl.saveConfig(configClass, configBranch);
    }

    static <T> void reloadConfig(Class<T> configClass) {
        ConfigHandlerImpl.reloadConfig(configClass);
    }

    static Map<String, Class<?>> getRegisteredConfigs() {
        return ConfigHandlerImpl.getRegisteredConfigs();
    }

    static void reloadAll() {
        ConfigHandlerImpl.reloadAll();
    }

}
