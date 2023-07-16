package com.archi.locval;

import java.util.List;

public interface LocationService {

    public String scrapeWebsite();

    public List<InvalidLocationDTO> getInvalidLocations();
    
}
