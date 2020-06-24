import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public class CaseInsensitiveIndex extends AbstractInvertedIndex {

    protected CaseInsensitiveIndex() { //Constructor
        this.hashMap = new HashMap<String, TreeSet<String>>();
    }

    @Override
    public File[] buildInvertedIndex(File[] files) {
        for (File file : files) {
            List<String> fileLines = Utils.readLines(file);
            for (String line : fileLines) {
                String fileName = Utils.substringBetween(fileLines.get(1), "<DOCNO> ", " </DOCNO>");
                String[] lineWordsArray = Utils.splitBySpace(line);
                for (String word : lineWordsArray){
                    if (!hashMap.containsKey(word)) {
                        hashMap.put(word, new TreeSet<String>());
                    }
                    hashMap.computeIfAbsent(word, k -> new TreeSet<String>().add(fileName));
                }
            }
        }
        return null;
    }
}
