package org.identileaf.identileafcore.repository;

import org.identileaf.identileafcore.model.PlantType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantTypeRepository extends JpaRepository<PlantType, Integer> {
    PlantType findByType(String type);
}
