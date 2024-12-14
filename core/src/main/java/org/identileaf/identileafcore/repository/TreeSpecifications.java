package org.identileaf.identileafcore.repository;

import org.identileaf.identileafcore.model.Tree;
import org.springframework.data.jpa.domain.Specification;

public class TreeSpecifications {
    public static Specification<Tree> hasPlantType(Integer plantTypeId) {
        return (root, query, cb) -> plantTypeId == null ? cb.conjunction() : cb.equal(root.get("plantType").get("id"), plantTypeId);
    }

    public static Specification<Tree> hasLeafType(Integer leafTypeId) {
        return (root, query, cb) -> leafTypeId == null ? cb.conjunction() : cb.equal(root.get("leafType").get("id"), leafTypeId);
    }

    public static Specification<Tree> hasBarkType(Integer barkTypeId) {
        return (root, query, cb) -> barkTypeId == null ? cb.conjunction() : cb.equal(root.get("barkType").get("id"), barkTypeId);
    }
}
