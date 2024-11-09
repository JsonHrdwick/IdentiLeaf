package org.identileaf.identileafcore.controller;

import org.identileaf.identileafcore.model.Tree;
import org.identileaf.identileafcore.service.QueryService;
import org.identileaf.identileafcore.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class QueryController {

    private final QueryService queryService;
    private TreeService treeService;
    private List<Tree> treeList;

    @Autowired
    public QueryController(TreeService treeService , QueryService queryService) {
        this.treeService = treeService;
        this.queryService = queryService;
        treeList = treeService.getAllTrees();
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
        String[] newAnswer = answer.split("\"");
        String trueAnswer = newAnswer[3];
        String sql = queryService.resolveAnswer(trueAnswer);
        System.out.println(sql);
        if (sql.contains("PlantType_ID=")){
            treeList = treeService.getSpecificTrees(Integer.parseInt(sql.replace("PlantType_ID=", "")));
        }
        return ResponseEntity.ok(sql);

    }
}

