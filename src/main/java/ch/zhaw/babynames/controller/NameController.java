package ch.zhaw.babynames.controller;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.opencsv.CSVReader;
import ch.zhaw.babynames.model.Name;
@RestController
public class NameController {
 private ArrayList<Name> listOfNames;
 @GetMapping("/names")
 public ArrayList<Name> getNames() {
 return listOfNames;
 }
 @EventListener(ApplicationReadyEvent.class)
 public void runAfterStartup() throws Exception {
 listOfNames = new ArrayList<>();
 Path path = Paths.get(ClassLoader.getSystemResource("data/babynames.csv").toURI());
 System.out.println("Read from: " + path);
 try (Reader reader = Files.newBufferedReader(path)) {
 try (CSVReader csvReader = new CSVReader(reader)) {
 String[] line;
 while ((line = csvReader.readNext()) != null) {
 listOfNames.add(new Name(line[0], line[1], Integer.parseInt(line[2])));
 }
 System.out.println("Read " + listOfNames.size() + " names");
 }
 }
 }
}