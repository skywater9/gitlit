import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.ArrayList;

public class Git {
    
    public static void main(String[] args) {
        //Test with two files: test and hello
        initialize();
        File test = new File("test.txt");
        File hello = new File("hello.txt");
        try {
            test.createNewFile();
            String hashTest = hashFile(test.getPath());
            addToObjects(hashTest, test.getPath());
            addToIndex(hashTest, test.getPath());

            hello.createNewFile();
            String hashHello = hashFile(hello.getPath());
            addToObjects(hashHello, hello.getPath());
            addToIndex(hashHello, hello.getPath());
        } catch (Exception e) {
            System.out.println(e);
        }  
    }

    public static void initialize() {
        boolean existed = false;
        if (new File("./git").exists() && new File("./git/objects").exists() && new File("./git/index").exists() && new File("./git/HEAD").exists()) {
            existed = true;
        }

        File gitDirectory = new File("./git");
        if (!gitDirectory.exists()) {
            gitDirectory.mkdir();
        }

        File objectsDirectory = new File("./git/objects");
        if (!objectsDirectory.exists()) {
            objectsDirectory.mkdir();
        }

        File indexFile = new File("./git/index");
        if (!indexFile.exists()) {
            try {
                indexFile.createNewFile();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        File headFile = new File("./git/HEAD");
        if (!headFile.exists()) {
            try {
                headFile.createNewFile();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        if (existed == true) {
            System.out.println("Git Repository Already Exists");
        } else {
            System.out.println("Git Repository Created");
        }
    }


    //Copied hashFile from fileHasher.java
    public static String hashFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        byte[] fileBytes = Files.readAllBytes(path);
        MessageDigest digest;
    
        try {
            digest = MessageDigest.getInstance("SHA-1");
        } catch (Exception e) {
            throw new RuntimeException("Something's not working in MessageDigest.");
        }
        
        //digest.update(fileBytes);
        byte[] encodedHash = digest.digest(fileBytes);
        String hexString = HexFormat.of().formatHex(encodedHash);
        System.out.println(hexString);
        return hexString;
    }

    public static void addToObjects(String hexString, String filePath) {
        //Make new file in objects folder titled the hexString hash
        File newFile = new File("./git/objects/" + hexString);

        //If the file already exists, meaning same contents already saved, do nothing
        if (newFile.exists()) {
            return;
        }
        
        try {
            newFile.createNewFile();

            //Copy contents from original file and write them into the object 
            String content = Files.readString(Paths.get(filePath));
            FileWriter writer1 = new FileWriter("./git/objects/" + hexString);
            writer1.write(content);
            writer1.close();

            //Use below to test if works:
            // String file1content = Files.readString(Paths.get("./git/objects/" + hexString));
            // System.out.println(file1content);
        } catch (Exception e) {
            System.out.println("Failed to create a new file: " + e);
        }
    }

    public static void addToIndex(String hexString, String filePath) {
        Path indexPath = Paths.get("./git/index");
        try {
            //Reads all contents of index file into an arraylist of arraylists
            boolean exists = false;
            ArrayList<ArrayList<String>> indexContents = new ArrayList<>();
            for (String line : Files.readAllLines(indexPath)) {
                String[] partsOfLine = line.split(" ");
                ArrayList<String> lineInIndex = new ArrayList<>(); //ArrayList of each line in index
                lineInIndex.add(partsOfLine[0]);
                lineInIndex.add(partsOfLine[1]);
                indexContents.add(lineInIndex);
            }

            //Checks if already exists by going through each arraylist containing each line of index file
            for (ArrayList<String> lineInIndex : indexContents) {
                if (lineInIndex.get(1).equals(filePath)) {
                    lineInIndex.set(0, hexString);
                    exists = true;
                    break;
                } 
                // else if (lineInIndex.get(0).equals(hexString)) {
                //     lineInIndex.set(1, filePath);
                //     exists = true;
                //     break;
                // }
            }

            //If the arraylist containing the file name doesn't already exist, add to arraylist of lines called indexContents
            if (exists == false) {
                ArrayList<String> lineInIndex = new ArrayList<>();
                lineInIndex.add(hexString);
                lineInIndex.add(filePath);
                indexContents.add(lineInIndex);
            }
            
            //Add indexContents back to actual file
            ArrayList<String> fileLines = new ArrayList<>();
            for (ArrayList<String> lineInIndex : indexContents) {
                fileLines.add(lineInIndex.get(0) + " " + lineInIndex.get(1));
            }
            Files.writeString(indexPath, String.join("\n", fileLines));
            
        } catch (Exception e) {
            System.out.println("This didn't work because " + e);
        }

    }

}