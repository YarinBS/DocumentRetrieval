import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public class CaseSensitiveIndex extends AbstractInvertedIndex {

    protected CaseSensitiveIndex(){ //Constructor
        this.hashMap = new HashMap<String, TreeSet<String>>();
    }

    @Override
    public File[] buildInvertedIndex(File[] files) {
        for (File file : files) {
            List<String> fileLines = Utils.readLines(file);
            for (String line : fileLines) {
                String[] lineWordsArray = Utils.splitBySpace(line);
            }
        }
        return null;
    }
}
