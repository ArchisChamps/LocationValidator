package com.hackearth.germin8;

import java.util.List;

public interface LocationService {

    public String scrapeWebsite();

    public List<InvalidLocationDTO> getInvalidLocations();
    
}
