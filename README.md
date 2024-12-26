# Shakespeare Analytics

Shakespeare Analytics is a Java and Apache Spark-based application that analyzes William Shakespeare's play *All's Well That Ends Well*. The application performs text analytics, providing insights such as line, word, symbol counts, and offering an interactive search feature for exploring specific text elements.

---

## Features

- **Line Count**: Calculates the total number of lines in the play.
- **Word Analysis**:
  - Counts total words using regex-based filtering.
  - Counts the number of distinct (unique) words.
- **Symbol Analysis**:
  - Counts total symbols, including letters, spaces, and punctuation.
  - Calculates the number of distinct symbols.
- **Letter Analysis**:
  - Identifies and counts unique alphabetic characters (case-sensitive).
- **Interactive Search**:
  - Allows users to search for lines containing a specific word.
  - Displays all matching lines from the play.

---

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher.
- Apache Spark installed and configured.
- Text file: `AllsWellThatEndsWell.txt`.

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/shakespeare-analytics.git
   cd shakespeare-analytics
Ensure Apache Spark is installed and configured on your system.
Place the input file (AllsWellThatEndsWell.txt) in the project root directory.
Usage
Compile and Run
Compile the application using the Spark library:
bash
Copy code
javac -cp "path-to-spark/jars/*" org/example/ShakespeareAnalytics.java
Run the application:
bash
Copy code
java -cp ".:path-to-spark/jars/*" org.example.ShakespeareAnalytics
How It Works
Tasks Implemented
Line Count:

Uses Spark's count() method to determine the total lines in the text.
Word Processing:

Splits lines into words using regex.
Filters empty strings and computes the total count of words.
Identifies and counts unique words using Spark's distinct() method.
Symbol Processing:

Splits lines into individual characters, including spaces and punctuation.
Counts the total number of symbols and unique symbols.
Letter Analysis:

Filters alphabetic characters and counts unique letters.
Interactive Search:

Prompts the user to input a word.
Filters and displays lines containing the specified word.
