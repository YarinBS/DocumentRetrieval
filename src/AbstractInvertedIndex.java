import java.io.File;
import java.util.*;

/**
 * The abstract class for index systems
 */
public abstract class AbstractInvertedIndex {
    HashMap<String, TreeSet<String>> hashMap;

    /**
     * builds a dictionary that contains all words in the given documents list as keys 
     * and the document names set that shoes where they were taken from as a value
     * @param : lst- File[]  documents list
     */
    public abstract void buildInvertedIndex(File[] lst);

    /**
     * gets the query and builds the output
     * @param : str- the given query
     * @return : a TreeSet contains the output for the current query
     */
    public TreeSet runQuery(String str) {
        TreeSet<String> returnedValue = new TreeSet<String>();

        String[] lineWords = str.split("\\W+");
        List<String> operands = Arrays.asList("OR", "AND", "NOT");
        Stack<String> stack = new Stack<String>();

        for (String word : lineWords) {
            // if in s=case insensitive will set to lowercase
            if(this.getClass().getSimpleName()=="CaseInsensitiveIndex" && !operands.contains(word))
            { word=word.toLowerCase();}
            if (!operands.contains(word)) {
                stack.push(word);
            } else {
                String a = stack.pop();
                String b = stack.pop();
                int index = operands.indexOf(word);
                switch (index) {
                    case 0://OR
                        returnedValue= OR_op(a, b);
                        stack.push(a+"or"+b+'*');
                        this.hashMap.put(a+"or"+b+'*',returnedValue);
                        break;
                    case 1://AND
                        returnedValue = AND_op(a, b);
                        stack.push(a+"and"+b+'*');
                        this.hashMap.put(a+"and"+b+'*',returnedValue);
                        break;
                    case 2://NOT
                        returnedValue=NOT_op(a, b);
                        stack.push(a+"NOT"+b+'*');
                        this.hashMap.put(a+"NOT"+b+'*',returnedValue);
                        break;
                }
            }
        }

        while (!stack.empty()){// deals with "word without operator " situations, adds all documents in such case
            returnedValue.addAll(this.hashMap.get(stack.pop()));
        }

        return returnedValue;
    }
    /**
     * @return : a TreeSet Contains all documents that contains a and all documents that contains b
     */
    public TreeSet<String> OR_op(String a, String b) {
        TreeSet<String> relevant_docs = new TreeSet<String>();
        if (this.hashMap.containsKey(a)) {
            relevant_docs.addAll((Collection) this.hashMap.get(a));
        }
        if (this.hashMap.containsKey(b)) {
            relevant_docs.addAll((Collection) this.hashMap.get(b));
        }
        return relevant_docs;
    }
    /**
     * @return : a TreeSet Contains  all documents that contains a and b
     */
    public TreeSet<String> AND_op(String a, String b) {
        TreeSet<String> relevant_docs = new TreeSet<String>();
        TreeSet<String> tree_a =  this.hashMap.get(a);
        TreeSet<String> tree_b =  this.hashMap.get(b);
        for (String doc: tree_a ) {
            if(tree_b.contains(doc)){
                relevant_docs.add(doc);
            }
        }
        return relevant_docs;
    }
    /**
     * @return : a TreeSet Contains  all documents that contains a and NOT b
     */
    public TreeSet<String> NOT_op(String a, String b) {
        TreeSet<String> relevant_docs = new TreeSet<String>();
        TreeSet<String> tree_a = (TreeSet<String>) this.hashMap.get(a);
        TreeSet<String> tree_b = (TreeSet<String>) this.hashMap.get(b);
        for (String doc: tree_b) {
            if(!tree_a.contains(doc)){
                relevant_docs.add(doc);
            }
        }
        return relevant_docs;
    }


}
