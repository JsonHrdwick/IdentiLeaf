package org.identileaf.identileafcore.repository;

import org.identileaf.identileafcore.model.Tree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TreeRepository extends JpaRepository<Tree, Integer> {
    Optional<Tree> findById(Integer id);
}
