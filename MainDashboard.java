package com.simulator;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainDashboard extends JFrame {
    JPanel readyQueuePanel, cpuPanel, ganttChartPanel;
    JComboBox<String> algoMenu;

    // Logic Data
    ArrayList<Process> masterList = new ArrayList<>();
    ArrayList<Process> processList = new ArrayList<>();
    ArrayList<Process> finishedList = new ArrayList<>();
    HashMap<String, Color> processColors = new HashMap<>();

    int processCount = 0;
    int currentTime = 0;
    Timer simulationTimer;
    Process currentRunningProcess = null;
    int timeQuantum = 2;
    int quantumCounter = 0;

    // --- COLOR THEME ---
    Color bgDark = new Color(18, 18, 18);
    Color panelDark = new Color(30, 30, 30);
    Color textWhite = new Color(240, 240, 240);
    Color pastelBlue = new Color(174, 214, 241);
    Color pastelGreen = new Color(171, 235, 198);
    Color pastelRed = new Color(241, 148, 138);

    public MainDashboard() {
        setTitle("CPU Scheduling Simulator - Research Edition");
        setSize(1200, 850);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(bgDark);
        setLayout(new BorderLayout());

        // --- HEADER ---
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(bgDark);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        JLabel mainTitle = new JLabel("CPU Scheduling Simulator");
        mainTitle.setForeground(textWhite);
        mainTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        mainTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(Box.createVerticalStrut(20));
        headerPanel.add(mainTitle);

        JPanel topBar = new JPanel();
        topBar.setBackground(bgDark);
        JLabel algoLbl = new JLabel("Algorithm: ");
        algoLbl.setForeground(textWhite);
        topBar.add(algoLbl);

        String[] algos = {"FCFS", "Priority Scheduling", "SJF", "Round Robin"};
        algoMenu = new JComboBox<>(algos);
        topBar.add(algoMenu);

        JButton addBtn = createStyledButton("Add Manual", pastelBlue);
        addBtn.addActionListener(e -> {
            processCount++;
            Process p = new Process("P" + processCount);
            processColors.put(p.pid, generateRandomPastel());
            masterList.add(p);
            updateReadyQueueUI(masterList);
        });
        topBar.add(addBtn);

        JButton startBtn = createStyledButton("Start", pastelGreen);
        startBtn.addActionListener(e -> startSimulation());
        topBar.add(startBtn);

        JButton resetBtn = createStyledButton("Reset", pastelRed);
        resetBtn.addActionListener(e -> resetSimulation());
        topBar.add(resetBtn);

        headerPanel.add(topBar);
        add(headerPanel, BorderLayout.NORTH);

        // --- CENTER ---
        JPanel centerArea = new JPanel(new GridLayout(2, 1, 15, 15));
        centerArea.setBackground(bgDark);
        centerArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        readyQueuePanel = createStyledPanel("Ready Queue (Waiting Room)");
        cpuPanel = createStyledPanel("CPU (Executing)");
        centerArea.add(readyQueuePanel);
        centerArea.add(cpuPanel);
        add(centerArea, BorderLayout.CENTER);

        // --- GANTT ---
        ganttChartPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 5));
        ganttChartPanel.setBackground(panelDark);
        JScrollPane ganttScroll = new JScrollPane(ganttChartPanel);
        ganttScroll.setPreferredSize(new Dimension(1100, 180));
        ganttScroll.getViewport().setBackground(panelDark);
        TitledBorder ganttBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(60, 60, 60)), "Gantt Chart Timeline");
        ganttBorder.setTitleColor(new Color(0, 255, 255));
        ganttScroll.setBorder(ganttBorder);
        add(ganttScroll, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void prepareProcessList() {
        processList.clear();
        for (Process p : masterList) {
            Process copy = new Process(p.pid);
            copy.burstTime = p.burstTime;
            copy.remainingTime = p.burstTime;
            copy.priority = p.priority;
            copy.arrivalTime = 0;
            processList.add(copy);
        }
    }

    public void startSimulation() {
        if (masterList.isEmpty()) return;
        if (simulationTimer != null && simulationTimer.isRunning()) simulationTimer.stop();

        prepareProcessList();
        currentTime = 0;
        quantumCounter = 0;
        finishedList.clear();
        ganttChartPanel.removeAll();
        currentRunningProcess = null;

        simulationTimer = new Timer(500, e -> {
            if (processList.isEmpty() && currentRunningProcess == null) {
                simulationTimer.stop();
                showResultsTable();
                updateReadyQueueUI(masterList);
                return;
            }

            String selectedAlgo = (String) algoMenu.getSelectedItem();

            if (currentRunningProcess == null && !processList.isEmpty()) {
                if (selectedAlgo.equals("Priority Scheduling")) {
                    processList.sort((p1, p2) -> Integer.compare(p1.priority, p2.priority));
                } else if (selectedAlgo.equals("SJF")) {
                    processList.sort((p1, p2) -> Integer.compare(p1.burstTime, p2.burstTime));
                }
                currentRunningProcess = processList.remove(0);
                updateReadyQueueUI(processList);
                cpuPanel.removeAll();
                cpuPanel.add(createProcessCard(currentRunningProcess, processColors.get(currentRunningProcess.pid)));
            }

            if (currentRunningProcess != null) {
                currentRunningProcess.remainingTime--;
                quantumCounter++;

                JLabel block = new JLabel(currentRunningProcess.pid, SwingConstants.CENTER);
                block.setOpaque(true);
                block.setBackground(processColors.get(currentRunningProcess.pid));
                block.setForeground(new Color(30, 30, 30));
                block.setPreferredSize(new Dimension(50, 50));
                block.setBorder(BorderFactory.createLineBorder(bgDark));
                ganttChartPanel.add(block);

                if (currentRunningProcess.remainingTime <= 0) {
                    currentRunningProcess.finishTime = currentTime + 1;
                    finishedList.add(currentRunningProcess);
                    currentRunningProcess = null;
                    quantumCounter = 0;
                    cpuPanel.removeAll();
                } else if (selectedAlgo.equals("Round Robin") && quantumCounter >= timeQuantum) {
                    processList.add(currentRunningProcess);
                    currentRunningProcess = null;
                    quantumCounter = 0;
                    cpuPanel.removeAll();
                    updateReadyQueueUI(processList);
                }
            }
            currentTime++;
            ganttChartPanel.revalidate();
            repaint();
        });
        simulationTimer.start();
    }

    // --- HELPER METHODS ---
    private Color generateRandomPastel() {
        Random r = new Random();
        return new Color(r.nextInt(105) + 150, r.nextInt(105) + 150, r.nextInt(105) + 150);
    }

    private JButton createStyledButton(String text, Color pastelColor) {
        JButton btn = new JButton(text);
        btn.setBackground(pastelColor);
        btn.setForeground(new Color(40, 40, 40));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        return btn;
    }

    private JPanel createStyledPanel(String title) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.setBackground(panelDark);
        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(80, 80, 80)), title);
        border.setTitleColor(new Color(0, 255, 255));
        p.setBorder(border);
        return p;
    }

    public void updateReadyQueueUI(ArrayList<Process> listToDisplay) {
        readyQueuePanel.removeAll();
        for (Process p : listToDisplay) {
            readyQueuePanel.add(createProcessCard(p, processColors.get(p.pid)));
        }
        readyQueuePanel.revalidate();
        readyQueuePanel.repaint();
    }

    public JPanel createProcessCard(Process p, Color color) {
        JPanel card = new JPanel(new GridLayout(4, 1));
        card.setPreferredSize(new Dimension(100, 110));
        card.setBackground(color);
        card.setBorder(BorderFactory.createLineBorder(bgDark, 2));
        JLabel id = new JLabel(p.pid, SwingConstants.CENTER);
        id.setFont(new Font("SansSerif", Font.BOLD, 16));
        card.add(id);
        card.add(new JLabel("BT: " + p.burstTime, SwingConstants.CENTER));
        card.add(new JLabel("Pri: " + p.priority, SwingConstants.CENTER));
        card.add(new JLabel("Rem: " + p.remainingTime, SwingConstants.CENTER));
        return card;
    }

    public void showResultsTable() {
        String[] columns = {"PID", "Burst", "Priority", "Wait Time", "Turnaround"};
        Object[][] data = new Object[finishedList.size() + 1][5];
        double totalWait = 0, totalTat = 0;
        for (int i = 0; i < finishedList.size(); i++) {
            Process p = finishedList.get(i);
            int tat = p.finishTime;
            int wait = tat - p.burstTime;
            data[i][0] = p.pid; data[i][1] = p.burstTime; data[i][2] = p.priority;
            data[i][3] = wait; data[i][4] = tat;
            totalWait += wait; totalTat += tat;
        }
        data[finishedList.size()][0] = "AVERAGE";
        data[finishedList.size()][3] = String.format("%.2f", totalWait/finishedList.size());
        data[finishedList.size()][4] = String.format("%.2f", totalTat/finishedList.size());
        JOptionPane.showMessageDialog(this, new JScrollPane(new JTable(data, columns)), "Metrics", JOptionPane.INFORMATION_MESSAGE);
    }

    public void resetSimulation() {
        if (simulationTimer != null) simulationTimer.stop();
        masterList.clear(); processList.clear(); finishedList.clear();
        processColors.clear(); processCount = 0; currentRunningProcess = null;
        readyQueuePanel.removeAll(); cpuPanel.removeAll(); ganttChartPanel.removeAll();
        revalidate(); repaint();
    }

    public static void main(String[] args) { new MainDashboard(); }
}