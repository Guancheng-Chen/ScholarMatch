package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.apply_to_posting.ApplyToPostingInputBoundary;
import com.scholarmatch.usecase.apply_to_posting.ApplyToPostingInputData;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

class ApplyToPostingControllerTest {

    @Test
    void testExecuteForwardsPostingIdAndMessage() {
        final ApplyToPostingInputBoundary interactor = mock(ApplyToPostingInputBoundary.class);
        final ApplyToPostingController controller = new ApplyToPostingController(interactor);

        controller.execute("posting-1", "Please consider me");

        final ArgumentCaptor<ApplyToPostingInputData> captor =
                ArgumentCaptor.forClass(ApplyToPostingInputData.class);
        verify(interactor).execute(captor.capture());
        assertEquals("posting-1", captor.getValue().postingId());
        assertEquals("Please consider me", captor.getValue().message());
    }

    @Test
    void testApplyDelegatesToExecute() {
        final ApplyToPostingInputBoundary interactor = mock(ApplyToPostingInputBoundary.class);
        final ApplyToPostingController controller = new ApplyToPostingController(interactor);

        controller.apply("posting-1", "Please consider me");

        verify(interactor, times(1)).execute(
                new ApplyToPostingInputData("posting-1", "Please consider me"));
    }
}
