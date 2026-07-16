package com.scholarmatch.usecase.data_access_interface;

import com.scholarmatch.usecase.connect.ConnectOutputData;

public interface ConnectDataAccessInterface {

    ConnectOutputData connect(String connectedScholarId);
}
