package org.identileaf.identileafcore.repository;

import org.identileaf.identileafcore.model.LeafType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeafTypeRepository extends JpaRepository<LeafType, Integer> {
    LeafType findByType(String type);
}
