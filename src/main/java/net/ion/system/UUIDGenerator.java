package net.ion.system;

import java.util.UUID;

@FunctionalInterface
public interface UUIDGenerator {
    UUID generate();
}
