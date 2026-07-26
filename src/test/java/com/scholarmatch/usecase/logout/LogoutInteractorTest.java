package com.scholarmatch.usecase.logout;

import com.scholarmatch.usecase.data_access_interface.SessionClearerInterface;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class LogoutInteractorTest {

    @Test
    void testExecuteClearsSessionAndPresentsSuccess() {
        final SessionClearerInterface session = mock(SessionClearerInterface.class);
        final LogoutOutputBoundary output = mock(LogoutOutputBoundary.class);

        new LogoutInteractor(session, output).execute(new LogoutInputData());

        verify(session).clearSession();
        verify(output).prepareSuccessView(any(LogoutOutputData.class));
    }
}
