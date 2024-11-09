package org.identileaf.identileafcore.controller;

import org.identileaf.identileafcore.model.Tree;
import org.identileaf.identileafcore.service.QueryService;
import org.identileaf.identileafcore.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class QueryController {

    private final QueryService queryService;
    private TreeService treeService;
    private Optional<List<Tree>> treeList;

    private int barkType;
    private int leafType;
    private int plantType;

    @Autowired
    public QueryController(TreeService treeService , QueryService queryService) {
        this.treeService = treeService;
        this.queryService = queryService;
        treeList = Optional.ofNullable(treeService.getAllTrees());
    }

    @GetMapping("/question")
    public ResponseEntity<Map<String, Object>> getQuestion() {
        Map<String, Object> response = new HashMap<>();
        response.put("question", queryService.promptQuestion());
        response.put("answers", queryService.getAnswers());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/answer")
    public ResponseEntity<String> getAnswer(@RequestBody String answer) {

        while (treeList.isPresent() && treeList.get().size() > 1) {
            String sql = queryService.resolveAnswer(answer);

            // Only format of Tree.ID= should have / to delimit multiple possible ids
            if (sql.contains("/")) {
                List<String> stringIDList = List.of(sql.split("/"));
                List<Integer> idList = new ArrayList<>();
                stringIDList.forEach(i -> idList.add(Integer.parseInt(i.replace("Tree.ID=", ""))));

                treeList = treeService.getTreesByIds(idList);
            }

            if (sql.contains("PlantType_ID=")) {
                plantType = Integer.parseInt(sql.split("PlantType_ID=")[1]);
                treeList = Optional.ofNullable(treeService.findTrees(plantType, leafType, barkType));
            } else if (sql.contains("BarkType_ID=")) {
                barkType = Integer.parseInt(sql.split("BarkType_ID=")[1]);
                treeList = Optional.ofNullable(treeService.findTrees(plantType, leafType, barkType));
            } else if (sql.contains("LeafType_ID=")) {
                leafType = Integer.parseInt(sql.split("LeafType_ID=")[1]);
                treeList = Optional.ofNullable(treeService.findTrees(plantType, leafType, barkType));
            }
            System.out.println(treeList);
            return ResponseEntity.ok(sql);
        }
        if (treeList.isEmpty()) {
            treeList = Optional.ofNullable(treeService.getAllTrees());
            return ResponseEntity.ok("Restarting"); // Make the page redirect here
        } else {
            return ResponseEntity.ok("/finalQuestion"); // On to new page
        }
    }

}

