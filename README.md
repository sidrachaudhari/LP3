# LP3 — Laboratory Practice III

**SPPU 2019 Pattern — BE Computer Engineering**
Savitribai Phule Pune University

---

## 📁 Repository Structure

```
LP3/
├── DAA/                          # Design & Analysis of Algorithms
│   ├── daa1/DAA_1.java           # Fibonacci (Iterative & Recursive)
│   ├── daa2/DAA_2.java           # Huffman Encoding (Greedy)
│   ├── daa3/DAA_3.java           # Fractional Knapsack (Greedy)
│   ├── daa4/DAA_4.java           # 0-1 Knapsack (DP & Branch and Bound)
│   ├── daa5/DAA_5.java           # N-Queens (Backtracking)
│   └── DAA_mini_project/         # Merge Sort vs Multithreaded Merge Sort
│
├── ML/                           # Machine Learning
│   ├── Prac_1_uber-fare-price-prediction.ipynb
│   ├── Prac_2_email-spam-detection.ipynb
│   └── Prac_3_bank-customer-churn-modeling.ipynb
│
└── README.md
```

---

## 📝 DAA Assignments

| # | Assignment | Algorithm | Time Complexity | Space Complexity |
|---|-----------|-----------|-----------------|------------------|
| 1 | Fibonacci Numbers | Iterative: Loop / Recursive | O(n) / O(2ⁿ) | O(1) / O(n) |
| 2 | Huffman Encoding | Greedy (Min-Heap) | O(n log n) | O(n) |
| 3 | Fractional Knapsack | Greedy (Sort by P/W ratio) | O(n log n) | O(n) |
| 4 | 0-1 Knapsack | DP / Branch and Bound | O(n×W) / O(2ⁿ) | O(n×W) / O(2ⁿ) |
| 5 | N-Queens Problem | Backtracking | O(n!) | O(n²) |
| Mini | Merge Sort Benchmark | Divide & Conquer (Multithreaded) | O(n log n) | O(n) |

---

## 📝 ML Assignments

| # | Assignment | Description |
|---|-----------|-------------|
| 1 | Uber Fare Prediction | Price prediction using regression on the Uber dataset |
| 2 | Email Spam Detection | Spam classification using ML (98% accuracy) |
| 3 | Bank Customer Churn | Churn prediction modeling for bank customers |

---

## ▶️ How to Run

### DAA (Java)
```bash
# Compile and run any assignment
cd DAA/daa1
javac DAA_1.java
java DAA_1
```

### ML (Python / Jupyter)
```bash
cd ML
jupyter notebook Prac_1_uber-fare-price-prediction.ipynb
```

---

## 🛠️ Tech Stack

- **DAA**: Java (JDK 11+)
- **ML**: Python, Jupyter Notebook, Pandas, Scikit-learn, Matplotlib

---
