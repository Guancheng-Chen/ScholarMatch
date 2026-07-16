package com.scholarmatch.usecase.data_access_interface;

import com.scholarmatch.usecase.connect.ConnectOutputData;

/**
 * Data access interface for the Connect use case.
 */
public interface ConnectDataAccessInterface {

    /**
     * Records a connection request for the selected scholar.
     *
     * @param connectedScholarId the ID of the scholar receiving the request
     * @return output data indicating whether a mutual match was created
     */
    ConnectOutputData connect(final String connectedScholarId);
}
