package com.scholarmatch.usecase.load_profile;

import com.scholarmatch.entity.User;
import com.scholarmatch.usecase.data_access_interface.LoadProfileDataAccessInterface;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LoadProfileInteractorTest {

    @Test
    void testSuccessIsPresented() {
        final LoadProfileDataAccessInterface dao =
                mock(LoadProfileDataAccessInterface.class);
        final LoadProfileOutputBoundary output = mock(LoadProfileOutputBoundary.class);
        when(dao.getProfile()).thenReturn(mock(User.class));

        new LoadProfileInteractor(dao, output).execute();

        verify(output).prepareSuccessView(any(LoadProfileOutputData.class));
    }

    @Test
    void testFailureIsPresented() {
        final LoadProfileDataAccessInterface dao =
                mock(LoadProfileDataAccessInterface.class);
        final LoadProfileOutputBoundary output = mock(LoadProfileOutputBoundary.class);
        when(dao.getProfile()).thenThrow(new InvalidRequestException("cannot load"));

        new LoadProfileInteractor(dao, output).execute();

        verify(output).prepareFailView("cannot load");
    }
}
