/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.certe.bfh.datascience.common;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Map;

/**
 *
 * @author nalet
 */
public class Gnuplot {

    public static void showStringIntegerMap(HashMap<String, Integer> wordMap, boolean sorted, String title) {
        ArrayList lines = new ArrayList(wordMap.entrySet());

        if (sorted) {
            // Defined Custom Comparator here
            Collections.sort(lines, new Comparator() {
                public int compare(Object o1, Object o2) {
                    return ((Comparable) ((Map.Entry) (o2)).getValue())
                            .compareTo(((Map.Entry) (o1)).getValue());
                }
            });
        }

        File outputPlt = null;
        try {
            File outputData = File.createTempFile("temp-gnuplot-data", ".dat");
            try (PrintWriter printWriter = new PrintWriter(outputData)) {
                for (int i = 0; i < lines.size(); i++) {
                    Map.Entry entry = (Map.Entry) lines.get(i);
                    printWriter.append((i + 1) + " " + entry.getKey() + " " + entry.getValue() + "\n");
                }
            }
            outputPlt = File.createTempFile("temp-gnuplot", ".plt");
            try (PrintWriter printWriter = new PrintWriter(outputPlt)) {
                printWriter.append("plot \"" + outputData.getName() + "\" every ::0::10 using 1:3:xtic(2) with boxes fill solid 0.5 title '" + title + "'");
            }
        } catch (IOException ex) {
            Logger.getLogger(Gnuplot.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("File created: " + outputPlt.getAbsolutePath());

    }

}
