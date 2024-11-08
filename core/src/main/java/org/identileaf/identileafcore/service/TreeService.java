package org.identileaf.identileafcore.service;

import org.identileaf.identileafcore.model.Tree;
import org.identileaf.identileafcore.repository.TreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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

    public List<Tree> getSpecificTrees(int PlantTypeID){
        return treeRepository.findByPlantTypeID(PlantTypeID);
    }
    public List<Tree> getSpecificTrees(int PlantTypeID, int LeafTypeID){
        return treeRepository.findByPlantTypeIDAndLeafTypeID(PlantTypeID, LeafTypeID);
    }
    public List<Tree> getSpecificTrees(int PlantTypeID, int LeafType_ID, int TreeTypeID){
        return treeRepository.findSpecificTrees(PlantTypeID, LeafType_ID, TreeTypeID);
    }
}
