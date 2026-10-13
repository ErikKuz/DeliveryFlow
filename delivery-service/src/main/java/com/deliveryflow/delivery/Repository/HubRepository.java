package com.deliveryflow.delivery.Repository;

import java.util.UUID;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.deliveryflow.delivery.Hub.HubEntity;

@Repository
public interface HubRepository extends JpaRepository<HubEntity,UUID>{
    Optional<HubEntity> findByType(String type);

    @Query(value = """
        SELECT * FROM hubs h
        WHERE h.country_code = :countryCode
          AND replace(lower(btrim(h.city)), 'ё', 'е') =
              replace(lower(btrim(:city)), 'ё', 'е')
        """, nativeQuery = true)
    Optional<HubEntity> findByServedCity(
        @Param("countryCode") String countryCode,
        @Param("city") String city
    );
}
