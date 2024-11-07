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
    private final HashMap<Integer,HashMap<String,ArrayList<String>>> questionMap;
    private String question;
    public Integer questionNumber;
    private ArrayList<String> answers;

    QueryService() throws FileNotFoundException {
        questionMap = generateQuestions();
        questionNumber = 0;
    }

    /**
     * Initializes HashMap of questions which is pulled from the questions.csv file located in the main/resources directory.
     * @return A Nested HashMap in format < Iterator, < Question, Answers >>. Questions have multiple answer mappings to support different answers.
     * Even indexed mappings are expected to be SQL query additions for WHERE clauses
     * (ex. Question: AnswerString, QueryConcat, AnswerString, QueryConcat...)
     * @throws FileNotFoundException
     */
    private HashMap<Integer,HashMap<String,ArrayList<String>>> generateQuestions() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("core/src/main/resources/questions.csv"));
        HashMap<Integer,HashMap<String,ArrayList<String>>> questionMap = new HashMap<>();
        int questionId = 0;
        while(scanner.hasNext()){
            // Takes line from csv and splits it into an array
            String line = scanner.nextLine();
            String[] tokens = line.split(",");
            // Adds ID to Map
            questionMap.put(questionId, new HashMap<>());
            // Array for the answers
            ArrayList<String> answers = new ArrayList<>(tokens.length - 1);
            for(int i = 1; i < tokens.length; i++){
                answers.add(i - 1, tokens[i]);
            }
            // Adds Question, Answers to the ID Map
            questionMap.get(questionId).put(tokens[0], answers);
            questionId++;
        }
        scanner.close();
        return questionMap;
    }

    /**
     * Pulls the question string from the Object's HashMap.
     * @return Question to prompt to user
     */
    public String promptQuestion(){
        HashMap<String,ArrayList<String>> innerMap = questionMap.get(questionNumber);
        question = innerMap.keySet().toString().replace("[", "").replace("]", "");
        return question;
    }

    /**
     * Takes user's answer from current question and generates the SQL concat string to append to the query
     * @param answer Chosen answer from an answer set for the current question.
     *               Param is case-sensitive and has no error handling functionality
     * @return SQL string to append to query that relates to the given answer
     */
    public String resolveAnswer(String answer){
        int SQLIndex = answers.indexOf(answer) + 1;
        questionNumber++;
        return answers.get(SQLIndex);
    }

    /**
     * Generates the answers that go with the current question
     * @return ArrayList of possible expected answers
     */
    public ArrayList<String> getAnswers(){
        answers = questionMap.get(questionNumber).get(question);
        ArrayList<String> answersOnly = answers;
        answersOnly.removeIf(i -> i.contains("="));// Remove entries that contain = as those are most likely SQL queries
        return answersOnly;

    }

    /**
     * Deprecated method for parsing answers via terminal line input
     * @param question
     * @param answers
     * @return
     */
    public static String userAnswer(String question,String answers) {
        System.out.println(question);
        System.out.println(answers);
        Scanner scanner = new Scanner(System.in);
        String userInputAnswer = "";
        userInputAnswer = scanner.nextLine();
        return userInputAnswer;
    }

}