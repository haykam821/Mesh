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
package io.github.glasspane.mesh.impl.vanity;

import io.github.glasspane.mesh.Mesh;
import io.github.glasspane.mesh.api.annotation.AutoRegistry;
import io.github.glasspane.mesh.api.util.vanity.VanityFeature;
import io.github.glasspane.mesh.impl.vanity.feature.EnderCapeFeature;
import io.github.glasspane.mesh.impl.vanity.feature.TitleFeature;

@AutoRegistry.Register(value = VanityFeature.class, modid = Mesh.MODID, registry = "mesh:vanity_features")
public class RegisteredVanityFeatures {

    public static final VanityFeature<TitleFeature.Config> TITLE = new TitleFeature();
    public static final VanityFeature<EnderCapeFeature.Config> ENDER_CAPE = new EnderCapeFeature();
}
