import java.util.List;

public class QuizActivities {
    private String question;
    private List<String> answers;

    QuizActivities(String question, List<String> answers) {
        this.question = question;
        this.answers = answers;
    }
    public String getQuestion() {
        return question;
    }
    public List<String> getAnswers() {
        return answers;
    }
}
