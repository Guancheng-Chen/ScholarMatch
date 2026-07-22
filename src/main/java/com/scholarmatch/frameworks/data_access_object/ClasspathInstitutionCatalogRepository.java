package com.scholarmatch.frameworks.data_access_object;

import com.scholarmatch.entity.Institution;
import com.scholarmatch.usecase.data_access_interface.InstitutionCatalogDataAccessInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Classpath-backed institution reference catalog.
 */
public final class ClasspathInstitutionCatalogRepository
        implements InstitutionCatalogDataAccessInterface {

    private static final String DEFAULT_RESOURCE = "institutions.csv";
    private final Map<String, Institution> institutionsById;

    /**
     * Loads the default institution catalog.
     */
    public ClasspathInstitutionCatalogRepository() {
        this(DEFAULT_RESOURCE);
    }

    /**
     * Loads an institution catalog from a classpath resource.
     *
     * @param resourceName classpath resource name
     */
    public ClasspathInstitutionCatalogRepository(final String resourceName) {
        this.institutionsById = load(resourceName);
    }

    @Override
    public List<Institution> getAllInstitutions() {
        return List.copyOf(this.institutionsById.values());
    }

    @Override
    public Institution findById(final String institutionId) {
        if (institutionId == null || institutionId.isBlank()) {
            return Institution.OTHER;
        }
        return this.institutionsById.getOrDefault(
                normalizeId(institutionId), Institution.OTHER);
    }

    private Map<String, Institution> load(final String resourceName) {
        final InputStream stream = ClasspathInstitutionCatalogRepository.class
                .getClassLoader().getResourceAsStream(resourceName);
        if (stream == null) {
            throw new IllegalStateException("Institution catalog not found: " + resourceName);
        }
        final Map<String, Institution> result = new LinkedHashMap<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                final List<String> columns = parseCsvLine(line);
                if (columns.size() >= 2) {
                    final Institution institution = new Institution(
                            normalizeId(columns.get(0)), columns.get(1));
                    result.put(institution.getInstitutionId(), institution);
                }
            }
        } catch (final IOException exception) {
            throw new IllegalStateException("Unable to read institution catalog", exception);
        }
        result.putIfAbsent(Institution.OTHER.getInstitutionId(), Institution.OTHER);
        return Map.copyOf(result);
    }

    private List<String> parseCsvLine(final String line) {
        final List<String> columns = new ArrayList<>();
        final StringBuilder value = new StringBuilder();
        boolean quoted = false;
        boolean escapedQuote = false;
        for (int index = 0; index < line.length(); index++) {
            final char character = line.charAt(index);
            if (character == '"') {
                if (escapedQuote) {
                    escapedQuote = false;
                } else if (quoted && index + 1 < line.length() && line.charAt(index + 1) == '"') {
                    value.append('"');
                    escapedQuote = true;
                } else {
                    quoted = !quoted;
                }
            } else if (character == ',' && !quoted) {
                columns.add(value.toString());
                value.setLength(0);
            } else {
                value.append(character);
            }
        }
        columns.add(value.toString());
        return columns;
    }

    private String normalizeId(final String institutionId) {
        return institutionId.trim().toUpperCase(Locale.ROOT);
    }
}
