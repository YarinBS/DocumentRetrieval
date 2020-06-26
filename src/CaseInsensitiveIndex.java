import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public class CaseInsensitiveIndex extends AbstractInvertedIndex {

    protected CaseInsensitiveIndex() { //Constructor

        this.hashMap = new HashMap<String, TreeSet<String>>();
    }

    @Override
    public void buildInvertedIndex(File[] files) {
        for (File file : files) {
            List<String> fileLines = Utils.readLines(file);
            for (String line : fileLines) {
                String fileName = Utils.substringBetween(fileLines.get(1), "<DOCNO> ", " </DOCNO>");
                String[] lineWordsArray = Utils.splitBySpace(line);
                for (String word : lineWordsArray){
                    word= word.toLowerCase();
                    if (!this.hashMap.containsKey(word)) {
                        TreeSet<String> ts1 = new TreeSet<String>();
                        ts1.add(fileName);
                        this.hashMap.put(word, ts1);
                    }else{
                        this.hashMap.get(word).add(fileName);
                    }

                }
            }
        }
    }
}
