package org.identileaf.identileaf.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

@Service
public class QueryService {
    private final HashMap<Integer,HashMap<String,ArrayList<String>>> questionMap;
    private String question;
    public Integer questionNumber;

    QueryService() throws FileNotFoundException {
        questionMap = generateQuestions();
        questionNumber = 0;
    }

    /**
     * Initializes HashMap of questions which is pulled from the questions.csv file located in the root directory.
     * @return A Nested HashMap in format < Iterator, < Question, Answers >>. Questions have multiple answer mappings to support different answers.
     * Even indexed mappings are expected to be SQL query additions for WHERE clauses
     * (ex. Question: AnswerString, QueryConcat, AnswerString, QueryConcat...)
     * @throws FileNotFoundException
     */
    private HashMap<Integer,HashMap<String,ArrayList<String>>> generateQuestions() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("questions.csv"));
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

    // Implement question pull
    public String promptQuestion(){
        HashMap<String,ArrayList<String>> innerMap = questionMap.get(questionNumber);
        question = innerMap.keySet().toString().replace("[", "").replace("]", "");
        return question;
    }

    // Implement answer adjustment
    public String resolveAnswer(String answer){
        HashMap<String,ArrayList<String>> innerMap = questionMap.get(questionNumber);
        ArrayList<String> answers = innerMap.get(question);
        int SQLIndex = answers.indexOf(answer) + 1;
        questionNumber++;
        return answers.get(SQLIndex);
    }
    public ArrayList<String> getAnswers(){
        ArrayList<String> answersOnly = questionMap.get(questionNumber).get(question);
        System.out.println(answersOnly);
        answersOnly.removeIf(i -> i.contains("="));
        System.out.println(answersOnly);
        return answersOnly;

    }

    public static String userAnswer(String question,String answers) {
        System.out.println(question);
        System.out.println(answers);
        Scanner scanner = new Scanner(System.in);
        String userInputAnswer = "";
        userInputAnswer = scanner.nextLine();
        return userInputAnswer;
    }

}