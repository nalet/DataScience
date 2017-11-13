/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.certe.bfh.datascience.exercise3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderEvaluator;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;

/**
 *
 * @author meinenn_adm
 */
public class StartApp {

    public static void main(String[] args) {
        new StartApp().run();
    }

    private void run() {
        TokenNameFinderModel model = null;
        try (InputStream modelIn = new FileInputStream(getClass().getResource("en-ner-person.bin").getPath())) {
            model = new TokenNameFinderModel(modelIn);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StartApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StartApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        NameFinderME nameFinder = new NameFinderME(model);
        {
            String text[] = this.getText().split("[\\W]+");

            Span nameSpans[] = nameFinder.find(text);

            System.out.println(Arrays.toString(nameSpans));

        }

        {
            String sentence[] = this.getText().split("[\\W]+");
            Span nameSpans[] = nameFinder.find(sentence);
            System.out.println(Arrays.toString(Span.spansToStrings(nameSpans, sentence)));

        }
    }

    private String getText() {
        String text = null;

        try {
            text = new String(Files.readAllBytes(
                    Paths.get(getClass().getResource("nlp.stanford.edu.txt")
                            .toURI())));
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(StartApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return text;
    }

    private String getShakespeare() {
        String text = null;

        try {
            text = new String(Files.readAllBytes(
                    Paths.get(getClass().getResource("pg1524.txt")
                            .toURI())));
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(StartApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return text;
    }

}
