package com.deutschebahn.deutschebahntest.service;

import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.*;

@Service
public class TrainService {
    public Map<String, List<String>>  getTrainSections(String ril100, String trainNumber, String waggon) {
        List<String> sections = null;
        try {
            File file = new File("/data/"+ril100+".xml"); // Hier soll die Erweiterung der XML-Datei eingetragen werden

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            sections = queryXML(document, ril100, trainNumber, waggon);

        } catch (Exception e) {
            e.printStackTrace();
        }

       for (String section : sections) {
            System.out.println("Section: " + section);
        }
        Map<String, List<String>> response = new HashMap<>();
        response.put("sections", sections);
        return response;
        
    }

    public static List<String> queryXML(Document document, String shortcode, String trainNumber, String waggon) {
        List<String> sectionsList = new ArrayList<>();
        try {
            NodeList trainList = document.getElementsByTagName("train");
            for (int i = 0; i < trainList.getLength(); i++) {
                Node trainNode = trainList.item(i);
                if (trainNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element trainElement = (Element) trainNode;

                    String currentTrainNumber = trainElement.getElementsByTagName("trainNumber")
                            .item(0).getTextContent();

                    if (currentTrainNumber.equals(trainNumber)) {
                        NodeList waggonList = trainElement.getElementsByTagName("waggon");
                        for (int j = 0; j < waggonList.getLength(); j++) {
                            Node waggonNode = waggonList.item(j);
                            if (waggonNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element waggonElement = (Element) waggonNode;

                                String currentWaggonNumber = waggonElement.getElementsByTagName("number")
                                        .item(0).getTextContent();

                                String currentShortcode = document.getElementsByTagName("shortcode")
                                        .item(0).getTextContent();

                                if (currentWaggonNumber.equals(waggon) && currentShortcode.equals(shortcode)) {
                                    NodeList sectionsListXML = waggonElement.getElementsByTagName("sections");
                                    for (int k = 0; k < sectionsListXML.getLength(); k++) {
                                        Node sectionNode = sectionsListXML.item(k);
                                        if (sectionNode.getNodeType() == Node.ELEMENT_NODE) {
                                            Element sectionElement = (Element) sectionNode;
                                            String section = sectionElement.getTextContent().trim();
                                            sectionsList.add(section);

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sectionsList;
    }
}
