package com.scholarmatch.frameworks.data_access_object;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClasspathInstitutionCatalogRepositoryTest {

    @Test
    void testMissingCatalogFailsClearly() {
        assertThrows(IllegalStateException.class,
                () -> new ClasspathInstitutionCatalogRepository("missing-catalog.csv"));
    }

    @Test
    void testQuotedCsvValuesAreParsed() {
        final ClasspathInstitutionCatalogRepository repository =
                new ClasspathInstitutionCatalogRepository("institutions-test.csv");

        assertEquals("University, Example",
                repository.findById("comma_university").getDisplayName());
        assertEquals("Quote \"Academy\"",
                repository.findById("quote_academy").getDisplayName());
        assertEquals(3, repository.getAllInstitutions().size());
    }

    @Test
    void testUnreadableCatalogFailsClearly() {
        final InputStream unreadableStream = new InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException("Cannot read catalog");
            }
        };

        final IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> new ClasspathInstitutionCatalogRepository(unreadableStream));

        assertEquals(IOException.class, exception.getCause().getClass());
    }

}
