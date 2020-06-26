import java.io.File;
import java.util.*;

public abstract class AbstractInvertedIndex {
    HashMap<String, TreeSet<String>> hashMap;

    public abstract void buildInvertedIndex(File[] lst);

    public TreeSet runQuery(String str) {
        TreeSet<String> returnedValue = new TreeSet<String>();

//        int returnValue = 0;
        String[] lineWords = str.split("\\W+");
        List<String> operands = Arrays.asList("OR", "AND", "NOT");
        Stack<String> stack = new Stack<String>();

        for (String t : lineWords) {
            if(this.getClass().getSimpleName()=="CaseInsensitiveIndex" && !operands.contains(t))
            { t=t.toLowerCase();}
            if (!operands.contains(t)) {
                stack.push(t);
            } else {
                String a = stack.pop();
                String b = stack.pop();
                int index = operands.indexOf(t);
                switch (index) {
                    case 0:
                        returnedValue= OR_op(a, b);
                        stack.push(a+"or"+b+'*');
                        this.hashMap.put(a+"or"+b+'*',returnedValue);
                        break;
                    case 1:
                        returnedValue = AND_op(a, b);
                        stack.push(a+"and"+b+'*');
                        this.hashMap.put(a+"and"+b+'*',returnedValue);
                        break;
                    case 2:
                        returnedValue=NOT_op(a, b);
                        stack.push(a+"NOT"+b+'*');
                        this.hashMap.put(a+"NOT"+b+'*',returnedValue);
                        break;
                }
            }
        }

//        returnValue = Integer.valueOf(stack.pop());

        return returnedValue;
    }

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
    public TreeSet<String> NOT_op(String a, String b) {
        TreeSet<String> relevant_docs = new TreeSet<String>();
        TreeSet<String> tree_a = (TreeSet<String>) this.hashMap.get(a);
        TreeSet<String> tree_b = (TreeSet<String>) this.hashMap.get(b);
        for (String doc: tree_a ) {
            if(!tree_b.contains(doc)){
                relevant_docs.add(doc);
            }
        }
        return relevant_docs;
    }


}
