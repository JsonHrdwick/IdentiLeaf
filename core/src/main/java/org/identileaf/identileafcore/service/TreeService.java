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

    public List<Tree> getAllTrees() {
        return treeRepository.findAll();
    }

    public Optional<Tree> getTreeById(int id) {
        return treeRepository.findById(id);
    }

    public Optional<List<Tree>> getTreesByIds(List<Integer> ids) {
        return treeRepository.findByIdIn(ids);
    }

    public List<Tree> getTreeByPlantTypeID(int PlantTypeID) { return treeRepository.findByPlantTypeID(PlantTypeID); }

    public List<Tree> getTreeByLeafTypeID(int LeafTypeID) { return treeRepository.findByLeafTypeID(LeafTypeID); }

    public List<Tree> getTreeByBarkTypeID(int BarkTypeID) { return treeRepository.findByBarkTypeID(BarkTypeID); }

    public List<Tree> findTrees(Integer plantTypeId, Integer leafTypeId, Integer barkTypeId) {
        Specification<Tree> spec = Specification.where(TreeSpecifications.hasPlantType(plantTypeId))
                .and(TreeSpecifications.hasLeafType(leafTypeId))
                .and(TreeSpecifications.hasBarkType(barkTypeId));

        return treeRepository.findAll(spec);
    }

    public List<Tree> getSpecificTrees(int PlantTypeID){
        return treeRepository.findByPlantTypeID(PlantTypeID);
    }

    public List<Tree> getSpecificTrees(int PlantTypeID, int LeafTypeID){
        return treeRepository.findByPlantTypeIDAndLeafTypeID(PlantTypeID, LeafTypeID);
    }
    public List<Tree> getSpecificTrees(int PlantTypeID, int LeafType_ID, int BarkTypeID){
        return treeRepository.findSpecificTrees(PlantTypeID, LeafType_ID, BarkTypeID);
    }
}
