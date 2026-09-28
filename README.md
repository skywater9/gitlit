initialize(): This method checks to see if a directory ./git, directory objects, file HEAD, and file index, exists. If not, it creates them. In this order:
    ./git
    ./git/objects
    ./git/index
    ./git/HEAD


hashFile(String filePath): This method takes in a filePath as a paramater and reads its content as bytes. It then uses MessageDigest to determine the file's unique SHA-1 hash. The result is converted into a hexadecimal String.


addToObjects(Strign hexString, String filePath): This method creates a file in ./git/objects named the file's SHA-1 hexidecimal hash and then puts the file's original contents into it. This is called a blob because the file has no specific format. If a blob with that hash already exists, it returns the method early since it means the same content hash already been saved. 


addToIndex(String hexString, String filePath): This method reads every line of ./git/index into an ArrayList of ArrayLists, where each ArrayList holds [hash, path]. If the file's path is already in the index, it updates the hash with the new content. If the path isn't there, it adds a new entry into the indexContents ArrayList. It then reads through the indexContents and joins everything together as a string to be added to the index file. 