package org.identileaf.identileafcore.service;

import jakarta.transaction.Transactional;
import org.identileaf.identileafcore.model.Tree;
import org.identileaf.identileafcore.repository.TreeRepository;
import org.identileaf.identileafcore.repository.TreeSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;


import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TreeService {
    private final TreeRepository treeRepository;

    @Autowired
    public TreeService(TreeRepository treeRepository) {
        this.treeRepository = treeRepository;
    }

    /**
     * Generates a list of all trees in the database
     * @return A List of all trees
     */
    public List<Tree> getAllTrees() {
        return treeRepository.findAll();
    }

    /**
     * Query the trees by specific IDs
     * @param ids List of IDs that will be returned as trees
     * @return A list of trees with the correct IDs
     */
    public Optional<List<Tree>> getTreesByIds(List<Integer> ids) {
        return treeRepository.findByIdIn(ids);
    }

    /**
     * Queries the database for specific parameters. IDs can be null if not supplied. Must be in the correct order.
     * @param plantTypeId PlantType_ID defined in database
     * @param leafTypeId LeafType_ID defined in database
     * @param barkTypeId BarkType_ID defined in database
     * @return List of trees that meet the critera of this query
     */
    public List<Tree> findTrees(Integer plantTypeId, Integer leafTypeId, Integer barkTypeId) {
        Specification<Tree> spec = Specification.where(TreeSpecifications.hasPlantType(plantTypeId==0 ? null : plantTypeId))
                .and(TreeSpecifications.hasLeafType(leafTypeId==0 ? null : leafTypeId))
                .and(TreeSpecifications.hasBarkType(barkTypeId==0 ? null : barkTypeId));


        return treeRepository.findAll(spec);
    }

}
