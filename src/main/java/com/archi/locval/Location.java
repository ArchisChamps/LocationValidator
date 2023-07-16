package com.archi.locval;

import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "location", schema = "location_schema")
@Where(clause = "is_deleted = 0")
public class Location {

    @Column(name = "pk_location_id")
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long pkLocationId;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "name")
    private String name;
    
    @Column(name = "is_valid")
    private boolean isValid;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}
