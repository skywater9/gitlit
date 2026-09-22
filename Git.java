import java.io.File;

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
        
    }
}
