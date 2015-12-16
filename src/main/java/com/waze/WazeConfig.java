package com.waze;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Nimrod_Lahav on 5/17/15.
 */
public class WazeConfig extends Configuration {

    @NotEmpty
    private String modelFile = "hi.html";

    public WazeConfig() {}

    @JsonProperty
    public String getModelFile() {
        return modelFile;
    }

    @JsonProperty
    public void setModelFile(String modelFile) {
        this.modelFile = modelFile;
    }
}
