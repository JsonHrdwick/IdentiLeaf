package org.identileaf.identileafcore.config;

import jakarta.annotation.PostConstruct;
import org.identileaf.identileafcore.model.BarkType;
import org.identileaf.identileafcore.model.LeafType;
import org.identileaf.identileafcore.model.PlantType;
import org.identileaf.identileafcore.model.Tree;
import org.identileaf.identileafcore.repository.BarkTypeRepository;
import org.identileaf.identileafcore.repository.LeafTypeRepository;
import org.identileaf.identileafcore.repository.PlantTypeRepository;
import org.identileaf.identileafcore.repository.TreeRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader {

    private final TreeRepository treeRepository;
    private final PlantTypeRepository plantTypeRepository;
    private final LeafTypeRepository leafTypeRepository;
    private final BarkTypeRepository barkTypeRepository;

    public DataLoader(TreeRepository treeRepository,
                      PlantTypeRepository plantTypeRepository,
                      LeafTypeRepository leafTypeRepository,
                      BarkTypeRepository barkTypeRepository) {
        this.treeRepository = treeRepository;
        this.plantTypeRepository = plantTypeRepository;
        this.leafTypeRepository = leafTypeRepository;
        this.barkTypeRepository = barkTypeRepository;
    }

    @PostConstruct
    public void init() {
        if (treeRepository.count() == 0) {
            if (plantTypeRepository.count() == 0) {

                plantTypeRepository.saveAll(List.of(
                        new PlantType(1, "Tree"),
                        new PlantType(2, "Snake")
                ));
            }
            if (leafTypeRepository.count() == 0) {

                leafTypeRepository.saveAll(List.of(
                        new LeafType(1, "Scale"),
                        new LeafType(2, "Needle"),
                        new LeafType(3, "Broad")
                ));
            }
            if (barkTypeRepository.count() == 0) {

                barkTypeRepository.saveAll(List.of(
                        new BarkType(0, "None"),
                        new BarkType(1, "Peeling"),
                        new BarkType(2, "Scaly"),
                        new BarkType(3, "Irregularly Plated")
                ));
            }

            treeRepository.save(new Tree(1,"Juniperus virginiana", "Eastern Red Cedar",
                    plantTypeRepository.findByType("Tree"),
                    leafTypeRepository.findByType("Scale"),
                    barkTypeRepository.findByType("Peeling")));

            treeRepository.save(new Tree(2,"Pinus taeda","Loblolly Pine",
                    plantTypeRepository.findByType("Tree"),
                    leafTypeRepository.findByType("Needle"),
                    barkTypeRepository.findByType("Scaly")));

            treeRepository.save(new Tree(3,"Acer rubrum","Red Maple",
                    plantTypeRepository.findByType("Tree"),
                    leafTypeRepository.findByType("Broad"),
                    barkTypeRepository.findByType("None")));

            treeRepository.save(new Tree(4,"Liquidambar styraiflua","Sweetgum",
                    plantTypeRepository.findByType("Tree"),
                    leafTypeRepository.findByType("Broad"),
                    barkTypeRepository.findByType("None")));

            treeRepository.save(new Tree(5,"Quercus alba","White Oak",
                    plantTypeRepository.findByType("Tree"),
                    leafTypeRepository.findByType("Broad"),
                    barkTypeRepository.findByType("Irregularly Plated")));

            treeRepository.save(new Tree(6,"Quercus marilandica","Blackjack Oak",
                    plantTypeRepository.findByType("Tree"),
                    leafTypeRepository.findByType("Broad"),
                    barkTypeRepository.findByType("None")));

            treeRepository.save(new Tree(7,"Ulmus americana","American Elm",
                    plantTypeRepository.findByType("Tree"),
                    leafTypeRepository.findByType("Broad"),
                    barkTypeRepository.findByType("None")));

            treeRepository.save(new Tree(8,"Crataegus phaenopyrum","Washington Hawthorn",
                    plantTypeRepository.findByType("Tree"),
                    leafTypeRepository.findByType("Broad"),
                    barkTypeRepository.findByType("None")));

            treeRepository.save(new Tree(9,"Carya glabra","Pignut Hickory",
                    plantTypeRepository.findByType("Tree"),
                    leafTypeRepository.findByType("Broad"),
                    barkTypeRepository.findByType("None")));

            treeRepository.save(new Tree(10,"Nyssa sylvatica","Blackgum",
                    plantTypeRepository.findByType("Tree"),
                    leafTypeRepository.findByType("Broad"),
                    barkTypeRepository.findByType("None")));
        }


    }
}
