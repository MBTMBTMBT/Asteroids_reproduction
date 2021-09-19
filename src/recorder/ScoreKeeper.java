package recorder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class ScoreKeeper {
	public Score[] scores;

	public ScoreKeeper() {
		scores = new Score[10];
		for (int i = 0; i < scores.length; i++) {
			scores[i] = new Score("", 0);
		}
		loadFile();
	}

	private void sortScores() {
		this.quickSort(0, 9);
	}

	private void swap(int indexA, int indexB) {
		Score temp = this.scores[indexA];
		this.scores[indexA] = this.scores[indexB];
		this.scores[indexB] = temp;
	}

	private void quickSort(int begin, int end) {
		if (begin >= end || end - begin < 1) {
			return;
		}
		int pivot = this.scores[begin].value;
		int leftIndex = begin;
		int rightIndex = end;
		while (leftIndex < rightIndex) {
			if (this.scores[leftIndex].value == pivot && this.scores[rightIndex].value == pivot) {
				rightIndex--;
			}
			while (this.scores[leftIndex].value > pivot) {
				leftIndex++;
			}
			while (this.scores[rightIndex].value < pivot) {
				rightIndex--;
			}
			swap(leftIndex, rightIndex);
		}
		quickSort(begin, leftIndex);
		quickSort(rightIndex + 1, end);
	}

	public int getLowestScore() {
		sortScores();
		return scores[9].value;
	}

	public Score[] getScores() {
		this.sortScores();
		Score[] scs = new Score[10];
		System.arraycopy(scores, 0, scs, 0, scs.length);
		return scs;
	}

	public static class Score {
		private String name;
		private int value;

		public Score(String n, int s) {
			this.name = n;
			this.value = s;
		}

		public String getName() {
			return name;
		}

		public int getScore() {
			return value;
		}
	}

	public void addScore(String name, int s) {
		scores[9] = new Score(name, s);
		sortScores();
	}

	public void loadFile() {
		try (BufferedReader br = new BufferedReader(new FileReader("scores.txt"))) {
			String line = br.readLine();
			int i = 0;
			while(line != null && i < 10) {
				Scanner s = new Scanner(line);
				int score = s.nextInt();
				String name = s.nextLine().trim();
				scores[i++] = new Score(name, score);
				line = br.readLine();
				s.close();
			}
		} catch (IOException e) {
			System.err.println("Error reading score file");
		}
	}
	
	public void saveScores() {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("scores.txt"))) {
			for (Score score : scores) {
				bw.write(score.getScore() + " " + score.getName() + "\n");
			}
			bw.close();
		} catch (IOException e) {
			System.err.println("Error writing score file");
		}
	}
}
