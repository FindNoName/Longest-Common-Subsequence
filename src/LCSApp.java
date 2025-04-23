//时间复杂度：O(mn)，其中 m是str1的长度，n是str2的长度，遍历两个字符串所有字符
//空间复杂度：O(mn)，dp数组大小为m∗n

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Solution {
    // Function to find the longest common substrings
    public List<String> findLCS(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();

        // dp[i][j] represents the length of the common substring up to str1[i-1] and str2[j-1]
        int[][] dp = new int[m + 1][n + 1];
        int maxLen = 0; // Maximum length of the common substring
        List<String> longestSubstrings = new ArrayList<>(); // To store all longest common substrings

        // Fill the dp table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) { // When characters match
                    dp[i][j] = dp[i - 1][j - 1] + 1; // Increase the length of substring
                    // Check for a new maximum length
                    if (dp[i][j] > maxLen) {
                        maxLen = dp[i][j]; // Update maximum length
                        longestSubstrings.clear(); // Clear previous results
                        longestSubstrings.add(str1.substring(i - maxLen, i)); // Add the new longest substring
                    } else if (dp[i][j] == maxLen) {
                        longestSubstrings.add(str1.substring(i - maxLen, i)); // Add this substring as well
                    }
                } else {
                    dp[i][j] = 0; // Reset length if characters do not match
                }
            }
        }

        return longestSubstrings; // Return all longest common substrings
    }
}

public class LCSApp extends JFrame {
    private JTextField input1;
    private JTextField input2;
    private JTextArea resultArea;
    private JButton generateButton;
    private JButton manualInputButton;
    private JButton exitButton;

    public LCSApp() {
        setTitle("Longest Common Substring Finder");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set BoxLayout for better vertical alignment
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        input1 = new JTextField(15);
        input2 = new JTextField(15);
        resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);

        // 设置初始化提示信息
        resultArea.setText("提示:\n生成最长公共子序列，若有相同长度最长，则以空格隔开\n如：abcfdef abchdef 输出：abc def\n手动输入：在输入两个字符串后，点击“手动输入\"按钮运行");


        generateButton = new JButton("随机生成");
        manualInputButton = new JButton("手动输入");
        exitButton = new JButton("结束");
        JButton clearButton = new JButton("清除"); // 新增清除按钮

        // Add components with a vertical spacing
        add(new JLabel("输入字符串1"));
        add(input1);
        add(Box.createVerticalStrut(10)); // Add space between components
        add(new JLabel("输入字符串2"));
        add(input2);
        add(Box.createVerticalStrut(10)); // Add space between components
        add(new JLabel("结果"));
        add(new JScrollPane(resultArea));
        add(Box.createVerticalStrut(10)); // Add space between components

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout()); // Set FlowLayout for horizontal arrangement

        // Add buttons to the button panel
        buttonPanel.add(generateButton);
        buttonPanel.add(manualInputButton);
        buttonPanel.add(clearButton); // 添加清除按钮
        buttonPanel.add(exitButton);

        add(buttonPanel); // Add the button panel

        // Add action listeners
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateRandomStrings();
            }
        });

        manualInputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findLCS();
            }
        });

        // Clear button functionality
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear the input fields and result area
                input1.setText(""); // 清空输入字符串1
                input2.setText(""); // 清空输入字符串2
                resultArea.setText(""); // 清空结果区域
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }


    private void generateRandomStrings() {
        String str1 = generateRandomString(10);
        String str2 = generateRandomString(10);
        input1.setText(str1);
        input2.setText(str2);
        findLCS();
    }

    private String generateRandomString(int length) {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = (char) ('a' + rand.nextInt(26)); // Generate random lowercase letter
            sb.append(c);
        }
        return sb.toString();
    }

    private void findLCS() {
        String str1 = input1.getText();
        String str2 = input2.getText();
        Solution solution = new Solution();
        List<String> results = solution.findLCS(str1, str2);

        // Prepare the output with length information
        if (results.isEmpty()) {
            resultArea.setText("未找到公共子串。");
        } else {
            StringBuilder output = new StringBuilder("最长公共子串: ");
            for (String substring : results) {
                output.append(substring).append(" ");
            }
            output.append("\n长度: ").append(results.get(0).length());
            resultArea.setText(output.toString());
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LCSApp app = new LCSApp();
            app.setVisible(true);
        });
    }
}
