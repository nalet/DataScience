/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.certe.bfh.datascience.exercise1;

import ch.certe.bfh.datascience.common.Gnuplot;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
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

        String[] words = book.split("[\\W]+");
        HashMap<String, Integer> wordMap = new HashMap<String, Integer>();

        for (String word : words) {
            wordMap.put(word, wordMap.getOrDefault(word, 0) + 1);
        }
        
        Gnuplot.showStringIntegerMap(wordMap, true, "Hamlet");
    }

}
