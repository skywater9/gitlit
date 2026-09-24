import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.HexFormat;

public class Git {
    
    public static void main(String[] args) {
        
        boolean existed = false;
        if (new File("/git").exists() && new File("/git/objects").exists() && new File("/git/index").exists() && new File("/git/HEAD").exists()) {
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
            System.out.println("Git directory already exists. ");
        } else {
            System.out.println("Git Repository Created");
        }

        File test = new File("test.txt");
        try {
            test.createNewFile();
            hashFile(test.getPath());
        } catch (Exception e) {
            System.out.println(e);
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

        //add to objects folder
        try {
            File fileTitle = new File("./git/objects/" + hexString);
            
            try {
                fileTitle.createNewFile();
                String file1content = Files.readString(Paths.get("./git/objects/" + hexString));
                System.out.println(file1content);
            } catch (Exception e) {
                System.out.println("Failed to create a new file: " + e);
            }

            FileWriter writer1 = new FileWriter("./git/objects/" + hexString);
            writer1.write(hexString);
            writer1.close();

        } catch (Exception e) {
            System.out.println("This didn't work because " + e);
        }

        //add to index 
        //need to add index, how is index stored 

        try {
            
            File fileTitle = new File("./git/objects/" + hexString);
            //ArrayLis<String, String> indexContent = Files.read
            


            


        } catch (Exception e) {

        }

        return hexString;
    }

}
