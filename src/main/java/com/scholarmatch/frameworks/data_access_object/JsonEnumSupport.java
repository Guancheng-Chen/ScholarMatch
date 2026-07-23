package com.scholarmatch.frameworks.data_access_object;

/**
 * Tiny shared helper for parsing a server-sent enum value, used by both ScholarUserMapper
 * (academicLevel, lookingFor, researchField, fundingStatus) and PostingGateway
 * (researchField, collaborationType, status) — kept as one static method rather than
 * duplicated in each, since it's identical logic in both places.
 */
final class JsonEnumSupport {

    private JsonEnumSupport() {
    }

    static <T extends Enum<T>> T safeParseEnum(
            final Class<T> enumClass, final String value, final T defaultValue) {
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        try {
            return Enum.valueOf(enumClass, value);
        } catch (final IllegalArgumentException ignored) {
            return defaultValue;
        }
    }
}
