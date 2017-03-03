package net.ion.service;

import java.util.UUID;

@FunctionalInterface
public interface UUIDGenerator {

    UUID generate();
}