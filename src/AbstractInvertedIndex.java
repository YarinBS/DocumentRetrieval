import java.io.File;
import java.util.HashMap;
import java.util.TreeSet;

public abstract class AbstractInvertedIndex {
    HashMap hashMap;

    public abstract File[] buildInvertedIndex(File[] lst);

    public static TreeSet runQuery(String str){
        return null;
    }
}
