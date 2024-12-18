package org.identileaf.identileafcore.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileNotFoundException;
import java.lang.runtime.SwitchBootstraps;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class QueryServiceTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testQueryService() throws FileNotFoundException {
        List<String> expectedQuestions = new ArrayList<>();
        expectedQuestions.add("Is PlantType 1?");
        expectedQuestions.add("Is the LeafType 1 or 2 or 3?");
        expectedQuestions.add("It could be Tree 3 or 4 or it could be Tree 5 or 6?");
        expectedQuestions.add("No SQL?");
        expectedQuestions.add("What happens when we have no answers?");

        QueryService queryService = new QueryService("../core/src/test/resources/testQuestions.csv");

        int questionNumber=-1;
        while (!queryService.checkQuestionsLeft()) {
            questionNumber++;
            String question = queryService.promptQuestion();
            assertEquals(expectedQuestions.get(questionNumber), question);

            List<String> answers = queryService.getAnswers();
            List<String> expectedAnswers = new ArrayList<>();
            switch(questionNumber){
                case 0:
                case 3:
                    expectedAnswers.add("Yes");
                    expectedAnswers.add("No");
                    assertEquals(expectedAnswers, answers);
                    expectedAnswers.clear();
                    break;
                case 1:
                    expectedAnswers.add("1");
                    expectedAnswers.add("2");
                    expectedAnswers.add("3");
                    assertEquals(expectedAnswers, answers);
                    expectedAnswers.clear();
                    break;
                case 2:
                    expectedAnswers.add("The first set");
                    expectedAnswers.add("The second set");
                    assertEquals(expectedAnswers, answers);
                    expectedAnswers.clear();
                    break;
                case 4:
                    // Test should not reach this statement or QueryService.checkQuestionsLeft has failed
                    fail();
                    break;
            }
            try {
                queryService.resolveAnswer(answers.getFirst());
            } catch (Exception e) {
                queryService.resolveAnswer();
            }
        }



    }
}