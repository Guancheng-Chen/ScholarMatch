package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.logout.LogoutInputBoundary;
import com.scholarmatch.usecase.logout.LogoutInputData;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class LogoutControllerTest {

    @Test
    void testLogoutDelegatesToInteractor() {
        final LogoutInputBoundary interactor = mock(LogoutInputBoundary.class);

        new LogoutController(interactor).logout();

        verify(interactor).execute(any(LogoutInputData.class));
    }
}
