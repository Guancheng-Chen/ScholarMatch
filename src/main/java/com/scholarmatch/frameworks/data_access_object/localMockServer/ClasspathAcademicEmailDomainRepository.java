package com.scholarmatch.frameworks.data_access_object.localMockServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.IDN;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Classpath-backed catalog of recognized university email domains.
 */
public final class ClasspathAcademicEmailDomainRepository
        implements AcademicEmailDomainDataAccessInterface {

    private static final String RESOURCE_NAME = "academic-email-domains.csv";
    private final Set<String> domains;

    public ClasspathAcademicEmailDomainRepository() {
        this(ClasspathAcademicEmailDomainRepository.class
                .getClassLoader().getResourceAsStream(RESOURCE_NAME));
    }

    ClasspathAcademicEmailDomainRepository(final InputStream resourceStream) {
        this.domains = loadDomains(resourceStream);
    }

    @Override
    public boolean isAcademicEmail(final String email) {
        final String domain = extractDomain(email);
        if (domain == null) {
            return false;
        }
        return this.domains.stream().anyMatch(
                academicDomain -> domain.equals(academicDomain)
                        || domain.endsWith("." + academicDomain));
    }

    private Set<String> loadDomains(final InputStream stream) {
        if (stream == null) {
            throw new IllegalStateException(
                    "University email domain catalog not found: " + RESOURCE_NAME);
        }
        final Set<String> loaded = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            String line = reader.readLine();
            while (line != null) {
                final String trimmed = line.trim();
                if (!trimmed.isEmpty() && !trimmed.startsWith("#")) {
                    loaded.add(normalizeDomain(trimmed.split(",", 2)[0]));
                }
                line = reader.readLine();
            }
        } catch (final IOException exception) {
            throw new IllegalStateException(
                    "Unable to read university email domain catalog", exception);
        }
        return Set.copyOf(loaded);
    }

    private String extractDomain(final String email) {
        if (email == null) {
            return null;
        }
        final int separator = email.lastIndexOf('@');
        if (separator <= 0 || separator == email.length() - 1) {
            return null;
        }
        try {
            return normalizeDomain(email.substring(separator + 1));
        } catch (final IllegalArgumentException exception) {
            return null;
        }
    }

    private String normalizeDomain(final String domain) {
        final String trimmed = domain.trim();
        final String withoutTrailingDot = trimmed.endsWith(".")
                ? trimmed.substring(0, trimmed.length() - 1) : trimmed;
        return IDN.toASCII(withoutTrailingDot).toLowerCase(Locale.ROOT);
    }
}
