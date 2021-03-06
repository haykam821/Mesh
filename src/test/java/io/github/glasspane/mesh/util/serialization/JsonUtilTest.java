package io.github.glasspane.mesh.util.serialization;

import net.minecraft.util.Identifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonUtilTest {

    @Test
    void identifiersSerialize() {
        assertEquals("\"mesh:test\"", JsonUtil.GSON.toJson(new Identifier("mesh", "test")));
    }

    @Test
    void identifiersDeserialize() {
        assertEquals(new Identifier("hi"), JsonUtil.GSON.fromJson("\"minecraft:hi\"", Identifier.class));
    }
}
