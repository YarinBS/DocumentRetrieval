public abstract class AbstractInvertedIndexFactory {
    public abstract AbstractInvertedIndex createInvertedIndex();
    /**
     * Creates a CaseSensitiveFactory / CaseInsensitiveFactory object using a Singleton design pattern
     * @return CaseSensitiveFactory / CaseInsensitiveFactory object
     */
}
