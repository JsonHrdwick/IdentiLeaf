package org.identileaf.identileafcore.repository;

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


    @Query("SELECT t FROM Tree t WHERE t.PlantType_ID = :plantTypeID")
    List<Tree> findByPlantTypeID(@Param("plantTypeID") int plantTypeID);

    @Query("SELECT t FROM Tree t WHERE t.LeafType_ID = :leafTypeID")
    List<Tree> findByLeafTypeID(@Param("leafTypeID") int leafTypeID);

    @Query("SELECT t FROM Tree t WHERE t.BarkType_ID = :barkTypeID")
    List<Tree> findByBarkTypeID(@Param("barkTypeID") int barkTypeID);


    @Query("SELECT t FROM Tree t WHERE t.PlantType_ID = :plantTypeID AND t.LeafType_ID = :leafTypeID")
    List<Tree> findByPlantTypeIDAndLeafTypeID(@Param("plantTypeID") int plantTypeID, @Param("leafTypeID") int leafTypeID);

    @Query("SELECT t FROM Tree t WHERE t.PlantType_ID = :plantTypeID AND t.LeafType_ID = :leafTypeID AND t.BarkType_ID = :barkTypeID")
    List<Tree> findSpecificTrees(@Param("plantTypeID") int plantTypeID, @Param("leafTypeID") int leafTypeID, @Param("barkTypeID") int barkTypeID);

}


