package org.identileaf.identileafcore.repository;

import org.identileaf.identileafcore.model.BarkType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarkTypeRepository extends JpaRepository<BarkType, Integer> {
    BarkType findByType(String type);
}
