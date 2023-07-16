package com.hackearth.germin8;

import lombok.Data;

@Data
public class InvalidLocationDTO {
    
    public InvalidLocationDTO(String name, String latitude, String longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private String name;

    private String latitude;

    private String longitude;

}
