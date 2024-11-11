package org.identileaf.identileafcore.controller;

import org.identileaf.identileafcore.model.Tree;
import org.identileaf.identileafcore.service.QueryService;
import org.identileaf.identileafcore.service.TreeService;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Controller
public class QueryController {

    private final QueryService queryService;
    private TreeService treeService;
    private Optional<List<Tree>> treeList;
    private final OpenAiChatModel chatModel;

    private int barkType;
    private int leafType;
    private int plantType;

    private boolean finalQuestion = false;

    @Autowired
    public QueryController(TreeService treeService , QueryService queryService, OpenAiChatModel chatModel) {
        this.treeService = treeService;
        this.queryService = queryService;
        this.chatModel = chatModel;
        treeList = Optional.ofNullable(treeService.getAllTrees());
    }

    @GetMapping("/question")
    public ResponseEntity<Map<String, Object>> getQuestion() {
        Map<String, Object> response = new HashMap<>();
        if (!finalQuestion) {
            response.put("question", queryService.promptQuestion());
            response.put("answers", queryService.getAnswers());
        } else {
            response.put("question", "Is " + treeList.toString().split("=")[3].split(",")[0] + " your tree?");
            ArrayList<String> answers = new ArrayList<>();
            answers.add("Yes"); answers.add("No");
            response.put("answers", answers);
        }
        return ResponseEntity.ok(response);

    }

    @PostMapping("/answer")
    public ResponseEntity<?> getAnswer(@RequestBody String answer) {

        if (treeList.isPresent() && treeList.get().size() > 1) {
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
            if (treeList.get().size() == 1) {finalQuestion = true;}
            else if (treeList.get().isEmpty()) {restartQuery();}
            System.out.println(treeList);
        } else if (finalQuestion) {
            if ("Yes".equals(answer)) {
                return ResponseEntity.ok().body(Map.of("redirectUrl", "/final-tree"));
            } else{
                restartQuery();
            }
        } else if (treeList.isEmpty()) {
            restartQuery();
        }
        return ResponseEntity.ok(Map.of("status", "continue")); // Use a JSON object for consistent structure
    }
    private void restartQuery() {
        treeList = Optional.ofNullable(treeService.getAllTrees());
        leafType = 0;
        plantType = 0;
        barkType = 0;
        queryService.questionNumber=0;
        finalQuestion = false;
    }

    @GetMapping("/treeDetail")
    public ResponseEntity<Map<String, String>> treeDetail() {
        if (treeList.get().size() == 1){
            String finalTree = treeList.toString().split("=")[3].split(",")[0];
            String prompt = "Provide 200 word or less detailed information about the " + finalTree + " tree.";
            return ResponseEntity.ok(Map.of( "treename", finalTree,
                    "treedetail", chatModel.call(prompt)));
        } else {
            // Catch if page is reached without a proper query. Could also do a redirect but whatever
            return ResponseEntity.badRequest().body(Map.of("error", "You've reached this page in error. " +
                    "Please return to the tree query and make sure you have answered the questions properly"));
        }
    }

}

