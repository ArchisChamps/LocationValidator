package com.archi.locval;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class LocationDao {
    
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void saveLocation(Location location){
        entityManager.merge(location);
    }

    public List<Location> getInvalidLocations() {
        return entityManager.createQuery("from Location where isValid=0", Location.class).getResultList();
    }
}
