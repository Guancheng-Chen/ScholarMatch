package com.scholarmatch.frameworks.data_access_object;

import com.scholarmatch.entity.Institution;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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
        assertEquals("Mid, Quoted",
                repository.findById("mid_quote").getDisplayName());
        assertEquals(4, repository.getAllInstitutions().size());
    }

    @Test
    void testBlankOrNullInstitutionIdReturnsOther() {
        final ClasspathInstitutionCatalogRepository repository =
                new ClasspathInstitutionCatalogRepository("institutions-test.csv");

        assertEquals(Institution.OTHER, repository.findById(null));
        assertEquals(Institution.OTHER, repository.findById("   "));
    }

    @Test
    void testMalformedRowWithoutSecondColumnIsSkipped() {
        final InputStream stream = new ByteArrayInputStream(
                ("id,name\nno-comma-row\nexample,Example University\n")
                        .getBytes(StandardCharsets.UTF_8));

        final ClasspathInstitutionCatalogRepository repository =
                new ClasspathInstitutionCatalogRepository(stream);

        assertEquals("Example University",
                repository.findById("example").getDisplayName());
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

    @Test
    void testCatalogCanLoadFromStream() {
        final InputStream stream = new ByteArrayInputStream(
                "id,name\nexample,Example University\n"
                        .getBytes(StandardCharsets.UTF_8));

        final ClasspathInstitutionCatalogRepository repository =
                new ClasspathInstitutionCatalogRepository(stream);

        assertEquals("Example University",
                repository.findById("example").getDisplayName());
    }

}
