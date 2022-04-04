package com.shramko;

import org.apache.commons.text.similarity.JaroWinklerDistance;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


/**
 * @author Boris Shramko
 */

public class DataConverter {

    private DataConverter() {
    }

    public static void saveResultToFile(Map<String, String> map, String output) {
        File file = new File(output);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                bufferedWriter.write(entry.getKey() + ":" + entry.getValue());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> getOutputMap(List<String> datas) {

        int firstPartCount = Integer.parseInt(datas.get(0));
        int secondPartCount = Integer.parseInt(datas.get(firstPartCount + 1));

        List<String> subList1 = new ArrayList<>(datas.subList(1, firstPartCount + 1));
        List<String> subList2 = new ArrayList<>(datas.subList(firstPartCount + 2, firstPartCount + secondPartCount + 2));

        List<String> minList;
        List<String> maxList;

        if (subList1.size() >= subList2.size()) {
            maxList = subList1;
            minList = subList2;
        } else {
            maxList = subList2;
            minList = subList1;
        }

        Map<String, String> map = new HashMap<>();
        for (String str : maxList) {
            map.put(str, "?");
        }

        JaroWinklerDistance jaroWinklerDistance = new JaroWinklerDistance();

        Iterator<String> iterator = minList.iterator();

        while (iterator.hasNext()) {
            String next = iterator.next();
            int best = 0;
            double distance = 0;
            double maxDistance = 0;

            for (int i = 0; i < maxList.size(); i++) {
                distance = jaroWinklerDistance.apply(next, maxList.get(i));
                if (distance > maxDistance) {
                    maxDistance = distance;
                    best = i;
                }
            }
            map.put(maxList.get(best), next);
            iterator.remove();
        }

        return map;
    }
}
