import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DevOpsCloudQuiz {
    private JFrame frame;
    private JLabel questionLabel, timerLabel, scoreLabel;
    private JButton nextButton, optionA, optionB, optionC, optionD;
    private JTextField nameField;
    private JPanel startPanel, quizPanel, resultPanel;
    private Timer timer;
    private int timeLeft = 120; // 2 minutes = 120 seconds
    private int currentQuestionIndex = 0;
    private int score = 0;
    private String candidateName = "";
    
    private final int QUESTIONS_PER_ROUND = 60;
    private final int PASS_MARK = 15;
    private List<Question> questions;
    private Question currentQuestion;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DevOpsCloudQuiz::new);
    }

    public DevOpsCloudQuiz() {
        frame = new JFrame("DevOps & Cloud Engineer Quiz - INDIAN AI");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startPanel = new JPanel(new GridLayout(3, 1));
        JLabel welcome = new JLabel("Enter Your Name to Start the Quiz", JLabel.CENTER);
        nameField = new JTextField();
        JButton startButton = new JButton("Start Quiz");
        startButton.addActionListener(e -> startQuiz());

        startPanel.add(welcome);
        startPanel.add(nameField);
        startPanel.add(startButton);

        frame.add(startPanel);
        frame.setVisible(true);
    }

    private void startQuiz() {
        candidateName = nameField.getText().trim();
        if (candidateName.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter your name!");
            return;
        }

        // Load questions
        loadQuestions();
        Collections.shuffle(questions); // shuffle for randomness
        questions = questions.subList(0, Math.min(QUESTIONS_PER_ROUND, questions.size()));

        // Setup quiz panel
        quizPanel = new JPanel(new BorderLayout());
        questionLabel = new JLabel("", JLabel.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel optionsPanel = new JPanel(new GridLayout(2, 2));
        optionA = new JButton();
        optionB = new JButton();
        optionC = new JButton();
        optionD = new JButton();

        ActionListener optionListener = e -> checkAnswer(((JButton) e.getSource()).getText());
        optionA.addActionListener(optionListener);
        optionB.addActionListener(optionListener);
        optionC.addActionListener(optionListener);
        optionD.addActionListener(optionListener);

        optionsPanel.add(optionA);
        optionsPanel.add(optionB);
        optionsPanel.add(optionC);
        optionsPanel.add(optionD);

        timerLabel = new JLabel("Time: 120", JLabel.CENTER);
        scoreLabel = new JLabel("Score: 0", JLabel.CENTER);
        nextButton = new JButton("Next Question");
        nextButton.addActionListener(e -> nextQuestion());

        JPanel bottomPanel = new JPanel(new GridLayout(1, 3));
        bottomPanel.add(timerLabel);
        bottomPanel.add(scoreLabel);
        bottomPanel.add(nextButton);

        quizPanel.add(questionLabel, BorderLayout.NORTH);
        quizPanel.add(optionsPanel, BorderLayout.CENTER);
        quizPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.remove(startPanel);
        frame.add(quizPanel);
        frame.revalidate();
        frame.repaint();

        nextQuestion();
    }

    private void loadQuestions() {
        questions = new ArrayList<>();
        questions.add(new Question("What is Infrastructure as Code (IaC)?", "Managing infrastructure with code", "A deployment script", "Only a DevOps tool", "Manual process", "Managing infrastructure with code"));
        questions.add(new Question("Which tool is used for container orchestration?", "Terraform", "Kubernetes", "Jenkins", "Ansible", "Kubernetes"));
        questions.add(new Question("What does CI/CD stand for?", "Continuous Integration/Continuous Deployment", "Cloud Integration/Cloud Development", "Code Integration/Code Delivery", "Continuous Improvement/Continuous Delivery", "Continuous Integration/Continuous Deployment"));
        questions.add(new Question("Which AWS service is serverless compute?", "EC2", "ECS", "Lambda", "CloudWatch", "Lambda"));
        questions.add(new Question("Which tool is widely used for configuration management?", "Docker", "Kubernetes", "Ansible", "Prometheus", "Ansible"));
        // 👉 You can add hundreds more DevOps/Cloud interview questions here
    }

    private void nextQuestion() {
        if (currentQuestionIndex >= questions.size()) {
            showResult();
            return;
        }

        currentQuestion = questions.get(currentQuestionIndex++);
        questionLabel.setText("Q" + currentQuestionIndex + ": " + currentQuestion.question);
        optionA.setText(currentQuestion.optionA);
        optionB.setText(currentQuestion.optionB);
        optionC.setText(currentQuestion.optionC);
        optionD.setText(currentQuestion.optionD);

        timeLeft = 120;
        if (timer != null) timer.stop();
        timer = new Timer(1000, e -> updateTimer());
        timer.start();
    }

    private void updateTimer() {
        timeLeft--;
        timerLabel.setText("Time: " + timeLeft);
        if (timeLeft <= 0) {
            timer.stop();
            nextQuestion();
        }
    }

    private void checkAnswer(String selectedOption) {
        if (selectedOption.equals(currentQuestion.correctAnswer)) {
            score += 2;
            scoreLabel.setText("Score: " + score);
        }
        timer.stop();
        nextQuestion();
    }

    private void showResult() {
        frame.remove(quizPanel);

        resultPanel = new JPanel(new GridLayout(3, 1));
        JLabel resultLabel = new JLabel("Candidate: " + candidateName, JLabel.CENTER);
        JLabel scoreResult = new JLabel("Final Score: " + score, JLabel.CENTER);
        JLabel status = new JLabel(score >= PASS_MARK * 2 ? "Status: PASS ✅" : "Status: FAIL ❌", JLabel.CENTER);

        resultPanel.add(resultLabel);
        resultPanel.add(scoreResult);
        resultPanel.add(status);

        frame.add(resultPanel);
        frame.revalidate();
        frame.repaint();
    }

    // Question Class
    static class Question {
        String question, optionA, optionB, optionC, optionD, correctAnswer;

        public Question(String question, String optionA, String optionB, String optionC, String optionD, String correctAnswer) {
            this.question = question;
            this.optionA = optionA;
            this.optionB = optionB;
            this.optionC = optionC;
            this.optionD = optionD;
            this.correctAnswer = correctAnswer;
        }
    }
}
