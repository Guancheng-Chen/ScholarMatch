package com.scholarmatch.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class PostingApplicationStatusTest {

    @Test
    void testContainsExactlyThreeStates() {
        assertArrayEquals(
                new PostingApplicationStatus[] {
                    PostingApplicationStatus.PENDING,
                    PostingApplicationStatus.ACCEPTED,
                    PostingApplicationStatus.REJECTED,
                },
                PostingApplicationStatus.values());
    }
}
