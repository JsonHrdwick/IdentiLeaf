package org.identileaf.identileafcore.repository;

import org.identileaf.identileafcore.model.BarkType;
import org.identileaf.identileafcore.model.LeafType;
import org.identileaf.identileafcore.model.PlantType;
import org.identileaf.identileafcore.model.Tree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TreeRepository extends JpaRepository<Tree, Integer>, JpaSpecificationExecutor<Tree> {
    Optional<Tree> findById(Integer id);

    Optional<List<Tree>> findByIdIn(List<Integer> ids);




}

