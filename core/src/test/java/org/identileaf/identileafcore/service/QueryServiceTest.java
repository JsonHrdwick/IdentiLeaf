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

        int questionNumber = -1;
        while (!queryService.checkQuestionsLeft()) {
            questionNumber++;
            String question = queryService.promptQuestion();
            assertEquals(expectedQuestions.get(questionNumber), question);

            List<String> answers = queryService.getAnswers();
            List<String> expectedAnswers = new ArrayList<>();
            switch (questionNumber) {
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

/*
// I was going to implement a pathing test here, but I realized that that would require loading all the resources in an
// integration test so this will probably be moved later, for now it lives here to make it easy to recall

    ArrayList<String> easternRedCedar = new ArrayList<>();
    easternRedCedar.add("yes");
    easternRedCedar.add("scale"); // final question

    ArrayList<String> loblollyPine = new ArrayList<>();
    loblollyPine.add("yes");
    loblollyPine.add("needle"); // final question

    ArrayList<String> whiteOak = new ArrayList<>();
    whiteOak.add("yes");
    whiteOak.add("broad");
    whiteOak.add("smooth edge");
    whiteOak.add("no");
    whiteOak.add("no");
    whiteOak.add("yes"); // final question

    ArrayList<String> blackjackOak = new ArrayList<>();
    blackjackOak.add("yes");
    blackjackOak.add("broad");
    blackjackOak.add("smooth edge");
    blackjackOak.add("no");
    blackjackOak.add("no");
    blackjackOak.add("no");
    blackjackOak.add("yes"); // final question

    ArrayList<String> pignutHickory = new ArrayList<>();
    pignutHickory.add("yes");
    pignutHickory.add("broad");
    pignutHickory.add("smooth edge");
    pignutHickory.add("no");
    pignutHickory.add("no");
    pignutHickory.add("no");
    pignutHickory.add("no");
    pignutHickory.add("compound"); // final question

    ArrayList<String> redMaple = new ArrayList<>();
    redMaple.add("yes");
    redMaple.add("broad");
    redMaple.add("teeth edge");
    redMaple.add("yes"); // final question

    ArrayList<String> sweetGum = new ArrayList<>();
    sweetGum.add("yes");
    sweetGum.add("broad");
    sweetGum.add("teeth edge");
    sweetGum.add("no");
    sweetGum.add("yes"); // final question

    ArrayList<String> blackGum = new ArrayList<>();
    blackGum.add("yes");
    blackGum.add("broad");
    blackGum.add("smooth edge");
    blackGum.add("no");
    blackGum.add("no");
    blackGum.add("no");
    blackGum.add("no");
    blackGum.add("simple");
    blackGum.add("no");
    blackGum.add("no");
    blackGum.add("yes"); // final question

    ArrayList<String> americanElm = new ArrayList<>();
    americanElm.add("yes");
    americanElm.add("broad");
    americanElm.add("smooth edge");
    americanElm.add("no");
    americanElm.add("no");
    americanElm.add("no");
    americanElm.add("no");
    americanElm.add("simple");
    americanElm.add("yes"); // final question

    ArrayList<String> hawthorn = new ArrayList<>();
    hawthorn.add("yes");
    hawthorn.add("broad");
    hawthorn.add("smooth edge");
    hawthorn.add("no");
    hawthorn.add("no");
    hawthorn.add("no");
    hawthorn.add("no");
    hawthorn.add("simple");
    hawthorn.add("no");
    hawthorn.add("yes"); // final question
*/