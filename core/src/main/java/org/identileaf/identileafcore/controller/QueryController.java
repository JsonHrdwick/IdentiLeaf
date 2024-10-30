package org.identileaf.identileafcore.controller;

import org.identileaf.identileafcore.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class QueryController {
    @Autowired
    private QueryService queryService;

    @GetMapping("/question")
    public ResponseEntity<Map<String, Object>> getQuestion() {
        Map<String, Object> response = new HashMap<>();
        response.put("question", queryService.promptQuestion());
        response.put("answers", queryService.getAnswers());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/answer")
    public ResponseEntity<String> getAnswer(@RequestBody String answer) {
        System.out.println(answer);
        String sql = queryService.resolveAnswer(answer);
        System.out.println(sql);

        return ResponseEntity.ok(sql);

    }
}

