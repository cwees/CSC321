public class QuickDriver {

    public static void main(String[] args) {
        BrailleTree tree = new BrailleTree("alphabet.txt");
        tree.translateFile("PrideBraille.txt", "test.txt");
    }
}
