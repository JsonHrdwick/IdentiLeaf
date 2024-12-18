package org.identileaf.identileafcore.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Contains the methods to handle the generation of questions/answer pairs as well as the SQL queries that go with the appropriate answers.
 * Questions are pulled from core/src/main/resources/questions.csv.
 * Illegal chars for question strings: "," "="
 */
@Service
public class QueryService {
    private final HashMap<Integer, HashMap<String, ArrayList<String>>> questionMap;
    private String question;
    private Integer questionNumber;
    private ArrayList<String> answers;

    QueryService() throws FileNotFoundException {
        questionMap = generateQuestions("core/src/main/resources/questions.csv");
        questionNumber = 0;
    }
    QueryService(String path) throws FileNotFoundException {
        questionMap = generateQuestions(path);
        questionNumber = 0;
    }

    /**
     * Initializes HashMap of questions which is pulled from the questions.csv file located in the main/resources directory.
     *
     * @return A Nested HashMap in format < Iterator, < Question, Answers >>. Questions have multiple answer mappings to support different answers.
     * Even indexed mappings are expected to be SQL query additions for WHERE clauses
     * (ex. Question: AnswerString, QueryConcat, AnswerString, QueryConcat...)
     * @throws FileNotFoundException
     */
    private HashMap<Integer, HashMap<String, ArrayList<String>>> generateQuestions(String filePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filePath));
        HashMap<Integer, HashMap<String, ArrayList<String>>> questionMap = new HashMap<>();
        int questionId = 0;
        while (scanner.hasNext()) {
            // Takes line from csv and splits it into an array
            String line = scanner.nextLine();
            String[] tokens = line.split(",");
            // Length of 3 or more denotes a question with atleast 1 answer and query statement
            // All other lines are trashed
            if (tokens.length >= 3 ) {
                questionMap.put(questionId, new HashMap<>());
                // Array for the answers
                ArrayList<String> answers = new ArrayList<>(tokens.length - 1);
                for (int i = 1; i < tokens.length; i++) {
                    answers.add(i - 1, tokens[i]);
                }
                // Adds Question, Answers to the ID Map
                questionMap.get(questionId).put(tokens[0], answers);
                questionId++;
            }
        }
        scanner.close();
        return questionMap;
    }

    /**
     * Pulls the question string from the Object's HashMap.
     *
     * @return Question to prompt to user
     */
    public String promptQuestion() {
        HashMap<String, ArrayList<String>> innerMap = questionMap.get(questionNumber);
        question = innerMap.keySet().toString().replace("[", "").replace("]", "");
        return question;
    }

    /**
     * Generates the answers that go with the current question
     *
     * @return ArrayList of possible expected answers
     */
    public ArrayList<String> getAnswers() {
        answers = questionMap.get(questionNumber).get(question);
        ArrayList<String> answersOnly = new ArrayList<>();
        for (int i = 0; i < answers.size(); i++) {
            if (i%2 == 0) {
                answersOnly.add(answers.get(i));
            }
        }
        return answersOnly;

    }

    /**
     * Takes user's answer from current question and generates the SQL concat string to append to the query
     *
     * @param answer Chosen answer from an answer set for the current question.
     *               Param is case-sensitive and has no error handling functionality
     * @return SQL string to append to query that relates to the given answer
     */
    public String resolveAnswer(String answer) {
        int SQLIndex = answers.indexOf(answer) + 1;
        questionNumber++;
        return answers.get(SQLIndex);
    }
    public void resolveAnswer() {
        questionNumber++;
    }

    public Boolean checkQuestionsLeft(){
        if (questionNumber >= questionMap.size()) {
            return true;
        }
        return false;
    }
    public void reset(){
        questionNumber = 0;
    }
}