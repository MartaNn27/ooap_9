import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

// Клас для представлення стану розмови
class ConversationState {
    private List<String> words;

    public ConversationState(List<String> words) {
        this.words = new ArrayList<>(words);
    }

    public List<String> getWords() {
        return words;
    }
}

// Клас, що представляє імітацію генератора випадкових слів
class RandomWordGenerator {
    private static final String[] WORDS = {"hello", "world", "java", "random", "chat"};

    public String generateRandomWord() {
        Random random = new Random();
        return WORDS[random.nextInt(WORDS.length)];
    }
}

// Клас Originator, який зберігає та відтворює стан розмови
class Originator {
    private List<String> words;

    public Originator() {
        words = new ArrayList<>();
    }

    public void addWord(String word) {
        words.add(word);
    }

    public ConversationState saveState() {
        return new ConversationState(words);
    }

    public void restoreState(ConversationState state) {
        words = state.getWords();
    }

    public void printCurrentState() {
        System.out.println("Current conversation state:");
        for (String word : words) {
            System.out.print(word + " ");
        }
        System.out.println();
    }
}

// Клас Caretaker, який зберігає та відновлює стани розмови
class Caretaker {
    private Stack<ConversationState> conversationHistory = new Stack<>();

    public void saveState(Originator originator) {
        conversationHistory.push(originator.saveState());
    }

    public void restoreState(Originator originator) {
        if (!conversationHistory.isEmpty()) {
            originator.restoreState(conversationHistory.pop());
        } else {
            System.out.println("No conversation history to restore.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Originator originator = new Originator();
        Caretaker caretaker = new Caretaker();
        RandomWordGenerator wordGenerator = new RandomWordGenerator();

        // Додамо кілька слів до розмови
        originator.addWord(wordGenerator.generateRandomWord());
        originator.addWord(wordGenerator.generateRandomWord());
        originator.addWord(wordGenerator.generateRandomWord());

        // Збережемо поточний стан розмови
        caretaker.saveState(originator);
        originator.printCurrentState();

        // Додамо ще кілька слів до розмови
        originator.addWord(wordGenerator.generateRandomWord());
        originator.addWord(wordGenerator.generateRandomWord());

        // Збережемо новий стан розмови
        caretaker.saveState(originator);
        originator.printCurrentState();

        // Відновимо попередній стан розмови
        caretaker.restoreState(originator);
        originator.printCurrentState();
    }
}
