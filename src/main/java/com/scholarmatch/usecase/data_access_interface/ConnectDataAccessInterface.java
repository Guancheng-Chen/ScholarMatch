package com.scholarmatch.usecase.connect;

/**
 * Data access interface for the connect use case.
 */
public interface ConnectDataAccessInterface {

    /**
     * Records a connection request for the selected scholar.
     *
     * @param connectedScholarId the ID of the scholar receiving the request
     * @return output data indicating whether a mutual match was created
     */
    ConnectOutputData connect(String connectedScholarId);
}
