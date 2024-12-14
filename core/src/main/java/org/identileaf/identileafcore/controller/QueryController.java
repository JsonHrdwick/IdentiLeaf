package org.identileaf.identileafcore.controller;

import org.identileaf.identileafcore.model.Tree;
import org.identileaf.identileafcore.service.QueryService;
import org.identileaf.identileafcore.service.TreeService;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Controller
public class QueryController {

    private final QueryService queryService;
    private final TreeService treeService;
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

    // @PostMapping("/login")
    // The /login PostMapping is handled natively by Spring Security and therefore does not need to be defined here.
    // The form parameters and definitions are defined in the SecurityConfig class.
    // Error handler method is also defined in this class which handles the user account locked out use-case.

    /**
     * Handles the processing of moving forward through the question pool. Breaks the encoded questions/answer pairs into
     * json objects to pass to frontend. There is currently no handling for reaching the end of the question pool.
     * @return json response consisting of question:String question & answers:ArrayList<String> answers
     */
    @GetMapping("/question")
    public ResponseEntity<Map<String, Object>> getQuestion() {
        Map<String, Object> response = new HashMap<>();
        int questionNumber = queryService.getQuestionNumber();
        System.out.println(questionNumber);
        if (questionNumber == 11) {
            restartQuery();
        }
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

    /**
     * Processes the answer returned from the /question arraylist of possible answers.
     * A mismatching answer (such as from a manual user entry answer) will likely prompt an exception as this PostMapping
     * does not support any case matching.
     * @param answer A single string answer picked from the list of possible answers supplied by the /question GetMapping
     * @return If the list of possible trees is greater than one, this PostMapping will return a status:continue json
     * which should prompt the query to continue to the next question. If the possible trees is exactly equal to one then
     * the method will return a redirect:/final-tree json. Should neither of these criteria be met, the question mapping will
     * be completely restarted.
     */
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
        } else if (finalQuestion) {
            if ("Yes".equals(answer)) {
                return ResponseEntity.ok().body(Map.of("redirectUrl", "/final-tree"));
            } else{
                restartQuery();
            }
        } else if (treeList.get().isEmpty()) {
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

    /**
     * Handles the request for AI to supply additional details on a tree. The method first checks to see if the list
     * of possible trees is exactly one as it will query the first tree in the list. This also ensures that the query has
     * been successfully completed and the user has not navigated to this mapping out of order.
     * @return If the treeList size is exactly one tree, returns an AI generated detail on the tree in the json form of
     * treename: String tree-name & treeDetail: String tree-detail. Should this condition not be met, the method returns
     * a json of error: String error-message
     */
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

