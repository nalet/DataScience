/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.certe.bfh.datascience.exercise2;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nalet
 */
public class StartApp {

    public static void main(String[] args) {
        new StartApp().run();
    }

    private void run() {
        String book = null;

        try {
            book = new String(Files.readAllBytes(
                    Paths.get(getClass().getResource("pg1524.txt")
                            .toURI())));
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(StartApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        book = book.toLowerCase();

        this.standardJavaStringTokenizer(book);
        this.stopwords(book);
        this.stemmer(book);
    }

    private void standardJavaStringTokenizer(String book) {
        StringTokenizer st = new StringTokenizer(book);
        int count = 0;
        while (st.hasMoreTokens()) {
            st.nextToken();
            count++;
        }
        System.out.println("The Standard Java String Tokenizer generated: " + count + " Tokens");
    }

    private void stopwords(String book) {
        String[] stopwordsRanksNl = this.getStopwords("ranks.nl.txt");
        String[] stopwordsSnowball = this.getStopwords("snowball.tartarus.org.txt");
        String[] stopwordsXpo6 = this.getStopwords("xpo6.com.txt");        
        
        String[] tokensRanksNL = this.getTokensByStopwords(book, stopwordsRanksNl);
        String[] tokensSnowball = this.getTokensByStopwords(book, stopwordsSnowball);
        String[] tokensXpo6 = this.getTokensByStopwords(book, stopwordsXpo6);
        
        System.out.println("Wordlist Ranks generated " + tokensRanksNL.length + " tokens.");
        System.out.println("Wordlist Snowball generated " + tokensSnowball.length + " tokens.");
        System.out.println("Wordlist xpo6 generated " + tokensXpo6.length + " tokens.");
    }

    private void stemmer(String book) {

    }

    private String[] getStopwords(String fileName) {
        String list = "";
        try {
            list = new String(Files.readAllBytes(
                    Paths.get(getClass().getResource(fileName)
                            .toURI())));
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(StartApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        list = list.toLowerCase();
        return list.split("\n");
        
    }

    private String[] getTokensByStopwords(String book, String[] stopwords) {
        ArrayList<String> output = new ArrayList<>();
        String[] words = book.split("[\\W]+");
        String token = "";
        
        for (String word : words) {
            token += word + " ";
            if(this.stringInStringArray(word, stopwords)) {
                output.add(token);
                token = "";
            }
        }
        
        return output.toArray(new String[output.size()]);
    }
    
    private boolean stringInStringArray(String string, String[] stringArray) {
        for (String s : stringArray) {
            if(s.equals(string)) {
                return true;
            }
        }

        return false;
    }

}
