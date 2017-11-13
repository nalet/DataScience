/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.certe.bfh.datascience.exercise2;

import ch.certe.bfh.datascience.exercise2.stemmer.lovins.LovinsStemmer;
import ch.certe.bfh.datascience.exercise2.stemmer.porter.PorterStemmer;
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
        
        System.out.println("Wordlist Ranks: ");
        this.printStringArray(stopwordsRanksNl);
        System.out.println("Wordlist Snowball: ");
        this.printStringArray(stopwordsSnowball);
        System.out.println("Wordlist xpo6: ");
        this.printStringArray(stopwordsXpo6);
        
        String[] tokensRanksNL = this.getTokensByStopwords(book, stopwordsRanksNl);
        String[] tokensSnowball = this.getTokensByStopwords(book, stopwordsSnowball);
        String[] tokensXpo6 = this.getTokensByStopwords(book, stopwordsXpo6);
        
        System.out.println("Wordlist Ranks generated " + tokensRanksNL.length + " tokens.");
        this.printStringArray(tokensRanksNL);
        System.out.println("Wordlist Snowball generated " + tokensSnowball.length + " tokens.");
        this.printStringArray(tokensSnowball);
        System.out.println("Wordlist xpo6 generated " + tokensXpo6.length + " tokens.");
        this.printStringArray(tokensXpo6);
    }
    
    private void stemmer(String book) {
        String text = "Give me your pardon, sir: I have done you wrong: "
                + "But pardon't, as you are a gentleman. "
                + "This presence knows, and you must needs have heard, "
                + "How I am punish'd with sore distraction. "
                + "What I have done "
                + "That might your nature, honour, and exception ";
        System.out.println();
        System.out.println("Original Text:");
        System.out.println(text);
        System.out.println();
        System.out.println("Using PorterStemmer: ");
        PorterStemmer porterStemmer = new PorterStemmer();
        for(String s : text.split("[\\W]+")) {
            char[] c = s.toLowerCase().toCharArray(); 
            porterStemmer.add(c, c.length);
            porterStemmer.stem();
            System.out.print(porterStemmer.toString() + " ");
        }
        System.out.println();
        System.out.println("Using LovinsStemmer: ");

        LovinsStemmer lovinsStemmer = new LovinsStemmer();
        System.out.println(lovinsStemmer.stemString(text.toLowerCase()));
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
        return list.split("\\r?\\n");
        
    }
    
    private String[] getTokensByStopwords(String book, String[] stopwords) {
        ArrayList<String> output = new ArrayList<>();
        String[] words = book.split("[\\W]+");
        String token = "";
        
        for (String word : words) {
            token += word + " ";
            if (this.stringInStringArray(word, stopwords)) {
                output.add(token);
                token = "";
            }
        }
        
        return output.toArray(new String[output.size()]);
    }
    
    private boolean stringInStringArray(String string, String[] stringArray) {
        for (String s : stringArray) {
            if (s.equals(string)) {
                return true;
            }
        }
        
        return false;
    }
    
    private void printStringArray(String[] array) {
        StringBuilder output = new StringBuilder();
        output.append("{");
        boolean first = true;
        for (String string : array) {
            if (first) {
                output.append(string);
                first = false;
            } else {
                output.append(", ").append(string);
            }
        }
        output.append("}");
        System.out.println(output);
    }
    
}
