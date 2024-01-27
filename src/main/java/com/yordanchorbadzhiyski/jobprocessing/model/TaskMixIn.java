package com.yordanchorbadzhiyski.jobprocessing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public abstract class TaskMixIn {
    @JsonIgnore
    abstract List<Integer> getRequires();

    @JsonIgnore
    abstract int getId();

    @JsonIgnore
    abstract List<Task> getDependencies();
}
