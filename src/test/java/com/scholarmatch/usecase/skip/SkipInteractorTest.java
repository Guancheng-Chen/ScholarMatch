package com.scholarmatch.usecase.skip;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SkipInteractorTest {

    @Test
    void testExecutePresentsSuccess() {
        final SkipOutputBoundary output = mock(SkipOutputBoundary.class);

        new SkipInteractor(output).execute(new SkipInputData("user-2"));

        verify(output).prepareSuccessView();
    }
}
