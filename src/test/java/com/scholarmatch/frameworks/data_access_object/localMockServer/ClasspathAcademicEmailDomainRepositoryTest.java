package com.scholarmatch.frameworks.data_access_object.localMockServer;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClasspathAcademicEmailDomainRepositoryTest {

    @Test
    void testProductionConstructorLoadsRealCatalogAndMatchesKnownDomain() {
        final ClasspathAcademicEmailDomainRepository repository =
                new ClasspathAcademicEmailDomainRepository();

        assertTrue(repository.isAcademicEmail("ada@mit.edu"));
        assertTrue(repository.isAcademicEmail("ada@cs.mit.edu"));
        assertFalse(repository.isAcademicEmail("ada@gmail.com"));
    }

    @Test
    void testMalformedOrMissingAtSignIsNotAcademic() {
        final ClasspathAcademicEmailDomainRepository repository =
                new ClasspathAcademicEmailDomainRepository(csvStream("mit.edu"));

        assertFalse(repository.isAcademicEmail(null));
        assertFalse(repository.isAcademicEmail("no-at-sign"));
        assertFalse(repository.isAcademicEmail("@mit.edu"));
        assertFalse(repository.isAcademicEmail("ada@"));
    }

    @Test
    void testUnparseableDomainIsNotAcademic() {
        final ClasspathAcademicEmailDomainRepository repository =
                new ClasspathAcademicEmailDomainRepository(csvStream("mit.edu"));

        assertFalse(repository.isAcademicEmail("ada@" + "x".repeat(70) + ".com"));
    }

    @Test
    void testCommentAndBlankLinesAreIgnored() {
        final ClasspathAcademicEmailDomainRepository repository =
                new ClasspathAcademicEmailDomainRepository(
                        csvStream("# a comment\n\nmit.edu\n"));

        assertTrue(repository.isAcademicEmail("ada@mit.edu"));
    }

    @Test
    void testMissingResourceStreamThrows() {
        final IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> new ClasspathAcademicEmailDomainRepository((InputStream) null));

        assertTrue(exception.getMessage().contains("not found"));
    }

    @Test
    void testUnreadableStreamThrows() {
        final InputStream failing = new InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException("disk error");
            }
        };

        final IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> new ClasspathAcademicEmailDomainRepository(failing));

        assertTrue(exception.getMessage().contains("Unable to read"));
    }

    private static InputStream csvStream(final String content) {
        return new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
    }
}
