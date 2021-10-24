package v2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;

public class UsernameGenerator {
    private static final String animalPath = "v2/dict/animals.txt";
    private static final String adjectivesPath = "v2/dict/english-adjectives.txt";

    public String generateUsername() {
        InputStream animals = this.getClass().getClassLoader().getResourceAsStream(animalPath);
        InputStream adjectives = this.getClass().getClassLoader().getResourceAsStream(adjectivesPath);

        String output = "";
        try{
            output = reservoirSelect(adjectives) + " " + reservoirSelect(animals);
        } catch (Exception e){
            System.out.println("Couldn't find a text file!");
        }
        return output;
    }

    private static String reservoirSelect(InputStream f) throws FileNotFoundException {
        String result = "";
        Random random = new Random();
        int n = 0;
        for (Scanner scanner = new Scanner(f); scanner.hasNext();){
            n++;
            String line = scanner.nextLine();
            if(random.nextInt(n) == 0){
                result = line;
            }
        }
        result = result.substring(0,1).toUpperCase() + result.substring(1);
        return result;
    }
}
