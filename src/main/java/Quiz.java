import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Quiz {

    private Scanner input = new Scanner(System.in);
    private Scanner scanner = new Scanner(System.in);
    private Map<String, String> categories = new HashMap<>();
    private static List<QuizActivities> questions = new ArrayList<>();
    private Random random = new Random();
    private List<QuizActivities> newQuestionsList;

    public void quizStart() throws InterruptedException {
            addCategory();
            System.out.println("All categories ");
            System.out.println(categories.keySet());
            String category;
            category = input.nextLine();
            if (categories.containsKey(category)) {
                {
                    try {
                        newQuestionsList = Quiz.proccessFile(categories.get(category));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                runThisQuiz();
            } else System.out.println("There is no such category ... ");

    }
        private void runThisQuiz() throws InterruptedException {
            int resault = 0;
            System.out.println("Quiz time !!!");
            System.out.println("Amount of questions :  " + questions.size());
            List<Integer> generatedRandomQuestions = drawNumbers();
            for (int i = 0; i < 10; i++) {
                String answer;
                QuizActivities tempQuestion = newQuestionsList.get(generatedRandomQuestions.get(i));
                System.out.println("\n\n\n\nQuestion no " + (i + 1) + " :");
                System.out.println(tempQuestion.getQuestion());
                System.out.println();
                System.out.println("Possible answer : ");
                Map<String, Boolean> tempAnswersMap = answersMap(tempQuestion.getAnswers());
                List<String> mixedAnswers = mixAnswers(tempAnswersMap.keySet());
                for (String s : mixedAnswers) {
                    System.out.println(s);
                }
                answer = "";

                FutureTask<String> readNextLine = new FutureTask<>(() -> scanner.nextLine());
                ExecutorService executor = Executors.newFixedThreadPool(2);
                executor.execute(readNextLine);
                try {
                    String token = readNextLine.get(5000, TimeUnit.MILLISECONDS);
                } catch (TimeoutException | InterruptedException | ExecutionException e) {
                    String awaitingAnswer = "";
                }
                try {
                    answer = readNextLine.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
//                answer=timerNextLine();
                Thread.sleep(100);
                if (tempAnswersMap.containsKey(answer)) {
                    if (tempAnswersMap.get(answer)) {
                        resault++;
                    }
                }
            }
            System.out.println("Your resault is : " + resault);
            System.exit(0);
        }
    private List<Integer> drawNumbers() {
            List<Integer> tempList = new ArrayList<>();
            int i = 0;
            while (i < 10) {
                Integer tempInt = random.nextInt(newQuestionsList.size());
                if (!tempList.contains(tempInt)) {
                    tempList.add(tempInt);
                    i++;
                }
            }
            return tempList;
        }
        private Map<String, Boolean> answersMap(List<String> answers) {
            Map<String, Boolean> answersMap = new HashMap<>();
            String correctAnswer = answers.get(0);
            answersMap.put(correctAnswer, true);
            for (int i = 1; i < answers.size(); i++) {
                String wrongAnswer = answers.get(i);
                answersMap.put(wrongAnswer, false);
            }
            return answersMap;
        }
        static List<QuizActivities> proccessFile(String categoryPath) throws FileNotFoundException {
            if (categoryPath.equals("")) {
                return readWholeFolder();
            } else {
                File folder = new File("src/main/resources/" + categoryPath);
                return readWholeFile(folder);
            }
        }
        private static List<QuizActivities> readWholeFolder() throws FileNotFoundException {
            List<QuizActivities> returnedList = new ArrayList<>();
            File folder = new File("src/main/resources/");
            File[] files = folder.listFiles();
            for (File file : files) {
                List<QuizActivities> tempCategory = readWholeFile(file);
                returnedList.addAll(tempCategory);
            }
            return returnedList;
        }
        private static List<QuizActivities> readWholeFile(File file) throws FileNotFoundException {
            Scanner scanner = new Scanner(file);
            String tempQuestion;
            int tempSize;
            while (scanner.hasNextLine()) {
                List<String> tempAnswers = new ArrayList<>();
                tempQuestion = scanner.nextLine();
                tempSize = Integer.parseInt(scanner.nextLine());
                for (int i = 0; i < tempSize; i++) {
                    tempAnswers.add(scanner.nextLine());
                }
                QuizActivities zq = new QuizActivities(tempQuestion, tempAnswers);
                questions.add(zq);
            }
            return questions;
        }
        private List<String> mixAnswers(Set<String> strings) {
            List<String> notMixedList = new ArrayList<>(strings);
            List<String> mixedList = new ArrayList<>();
            while (!notMixedList.isEmpty()) {
                int randomNumber = random.nextInt(notMixedList.size());
                mixedList.add(notMixedList.get(randomNumber));
                notMixedList.remove(randomNumber);
            }
            return mixedList;
        }
        private void addCategory() {
            categories.put("Animals", "Animals.txt");
            categories.put("Board games", "Entertainment_Board_Games.txt");
            categories.put("Books", "Entertainment_Books.txt");
            categories.put("Cartoon Animations", "Entertainment_Cartoon__Animations.txt");
            categories.put("Film", "Entertainment_Film.txt");
            categories.put("Manga/Anime", "Entertainment_Japanese_Anime__Manga.txt");
            categories.put("Music", "Entertainment_Music.txt");
            categories.put("TV", "Entertainment_Television.txt");
            categories.put("Video Games", "Entertainment_Video_Games.txt");
            categories.put("General Knowledge", "General_Knowledge.txt");
            categories.put("Geography", "Geography.txt");
            categories.put("History", "History.txt");
            categories.put("Science/Nature", "Science__Nature.txt");
            categories.put("Computers", "Science_Computers.txt");
            categories.put("Sport", "Sports.txt");
            categories.put("Vehicles", "Vehicles.txt");
            categories.put("All", "");
        }
    }