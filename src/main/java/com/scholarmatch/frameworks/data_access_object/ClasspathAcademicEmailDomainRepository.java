package com.scholarmatch.frameworks.data_access_object;

import com.scholarmatch.entity.Institution;
import com.scholarmatch.usecase.data_access_interface.AcademicEmailDomainDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.InstitutionCatalogDataAccessInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.IDN;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * Classpath-backed catalog of recognized university email domains.
 */
public final class ClasspathAcademicEmailDomainRepository
        implements AcademicEmailDomainDataAccessInterface {

    private static final String DEFAULT_RESOURCE = "academic-email-domains.csv";
    private final Map<String, Institution> institutionsByDomain;
    private final InstitutionCatalogDataAccessInterface institutionCatalog;

    /**
     * Loads the default university domain catalog.
     */
    public ClasspathAcademicEmailDomainRepository() {
        this(DEFAULT_RESOURCE, new ClasspathInstitutionCatalogRepository());
    }

    public ClasspathAcademicEmailDomainRepository(
            final InstitutionCatalogDataAccessInterface institutionCatalog) {
        this(DEFAULT_RESOURCE, institutionCatalog);
    }

    /**
     * Loads a university domain catalog from the classpath.
     *
     * @param resourceName classpath resource containing one domain per row
     */
    public ClasspathAcademicEmailDomainRepository(final String resourceName) {
        this(resourceName, new ClasspathInstitutionCatalogRepository());
    }

    public ClasspathAcademicEmailDomainRepository(
            final String resourceName,
            final InstitutionCatalogDataAccessInterface institutionCatalog) {
        this.institutionCatalog = institutionCatalog;
        this.institutionsByDomain = loadDomains(resourceName);
    }

    @Override
    public boolean isAcademicEmail(final String email) {
        return findInstitutionByEmail(email).isPresent();
    }

    @Override
    public Optional<Institution> findInstitutionByEmail(final String email) {
        final String domain = extractDomain(email);
        if (domain == null) {
            return Optional.empty();
        }
        for (final Map.Entry<String, Institution> entry : this.institutionsByDomain.entrySet()) {
            final String academicDomain = entry.getKey();
            if (domain.equals(academicDomain) || domain.endsWith("." + academicDomain)) {
                return Optional.of(entry.getValue());
            }
        }
        return Optional.empty();
    }

    private Map<String, Institution> loadDomains(final String resourceName) {
        final InputStream stream = ClasspathAcademicEmailDomainRepository.class
                .getClassLoader().getResourceAsStream(resourceName);
        if (stream == null) {
            throw new IllegalStateException("University email domain catalog not found: " + resourceName);
        }

        final Map<String, Institution> domains = new LinkedHashMap<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            String line = reader.readLine();
            while (line != null) {
                final String trimmed = line.trim();
                if (!trimmed.isEmpty() && !trimmed.startsWith("#")) {
                    final String[] columns = trimmed.split(",", 2);
                    final String domain = normalizeDomain(columns[0].trim());
                    final Institution institution = columns.length == 2
                            ? this.institutionCatalog.findById(columns[1].trim())
                            : Institution.OTHER;
                    domains.put(domain, institution);
                }
                line = reader.readLine();
            }
        } catch (final IOException exception) {
            throw new IllegalStateException("Unable to read university email domain catalog", exception);
        }
        return Map.copyOf(domains);
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
        final String withoutTrailingDot = domain.endsWith(".")
                ? domain.substring(0, domain.length() - 1) : domain;
        return IDN.toASCII(withoutTrailingDot.trim()).toLowerCase(Locale.ROOT);
    }
}
