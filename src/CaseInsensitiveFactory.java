public class CaseInsensitiveFactory extends AbstractInvertedIndexFactory {
    private static CaseInsensitiveIndex caseInsensitiveIndex;

    @Override
    public AbstractInvertedIndex createInvertedIndex() {
        /**
         * Implementation for createInvertedIndex
         */
        if (caseInsensitiveIndex == null) {
            caseInsensitiveIndex = new CaseInsensitiveIndex();
            System.out.println("New CaseInsensitive index is created");
        } else {
            System.out.println("You already have a CaseInsensitive index");
        }
        return caseInsensitiveIndex;
    }
}
