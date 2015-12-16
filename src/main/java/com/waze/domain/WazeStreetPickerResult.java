package com.waze.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Nimrod_Lahav on 5/22/15.
 */
public class WazeStreetPickerResult {

    private List<String> addressList;

    public WazeStreetPickerResult() {}

    public WazeStreetPickerResult(List<String> addressList) {
        this.addressList = addressList;
    }

    @JsonProperty
    public List<String> getAddressList() {
        return addressList;
    }
}
