package FalloutWordGuessgame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/***
 * Author: @anandh_somu
 * Date: 22-May-2014
 * Fallout's Hacking Game
 * Ref: http://www.reddit.com/r/dailyprogrammer/comments/263dp1/5212014_challenge_163_intermediate_fallouts/
 */

public class hackingGame {

	Scanner userInputScanner = new Scanner(System.in);
	int wordLength=0;
	int totalWords=0;
	static int totalGame=0,totalWin=0,totalGuess=0;
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {

		hackingGame hg=new hackingGame();
		Random rand = new Random();
		Scanner userInputScanner = new Scanner(System.in);
		do{
		totalGame++;
		hg.fixHardness();
		List<String> wordList = new ArrayList<String>(hg.getRandomWords(hg.readFile()));
		String secretWord =  wordList.get(rand.nextInt(wordList.size()));				//select a random secret word from retrieved list 
		for(String object : wordList) {	
			System.out.println(object.toUpperCase());
		}

		hg.game(secretWord,4);
		System.out.print("===--------------------------------------===\n Play another(1/0) - ");
		}while(userInputScanner.nextInt()==1);
		
		System.out.println("-----------------Complete Stats--------------");
		System.out.println("Total games Played - "+totalGame);
		System.out.println("User Wins - "+totalWin+"   :  Win percentage - "+(((float)totalWin/(float)totalGame)*100));
		System.out.println("Total Guesses - " + totalGuess + "   :  Correct Guess percentage - " +(((float)totalWin/(float)totalGuess)*100));
	}

	void game(String secretWord,int guess)
	{
		String guessWord;
		int correct;
		while(guess > 0)
		{
			totalGuess++;
			System.out.print("Guess ("+guess+" left)? ");
			guessWord = userInputScanner.next();
			correct = chkGuess(guessWord, secretWord);
			System.out.println(correct + "/" + wordLength + " correct");
			if(correct == wordLength){
				System.out.println("!-!-!-!-  You won!! -!-!-!-!");
				totalWin++;
				return;
			}	
			guess--;
		}	
		System.out.println("!-!-!-!- You Lost. No more guesses available -!-!-!-! Answer is - "+ secretWord);
	}
	
	int chkGuess(String guessWord, String secretWord)		//chk if char are at correct position
	{
		int correctPosition = 0 ;
		for(int i=0;i<wordLength;i++)
		{
			if(guessWord.toUpperCase().charAt(i) == secretWord.toUpperCase().charAt(i))
			{
				correctPosition++;
			}
		}
		return correctPosition;
	}

	void fixHardness()
	{
		int difficulty[] = {9,14,20,26,30};			//predefined sets of difficulty
		System.out.print("Difficulty (1-5)? ");
		int diff = userInputScanner.nextInt();
		wordLength = (int)difficulty[diff-1]/2;
		totalWords = wordLength;
	}

	List<String> readFile() throws IOException
	{
		List<String> totalWordList = new ArrayList<String>();
		FileReader f=new FileReader("C:\\Users\\Anandh\\workspace\\RedditChallenge\\src\\FalloutWordGuessgame\\enable1.txt");

		BufferedReader bf =new BufferedReader(f);
		String line;
		while ((line = bf.readLine()) != null) 
		{
			totalWordList.add(line);
		}
		return totalWordList;
	}

	Set<String> getRandomWords(List<String> totalWordList)			//retrieves random string from a file
	{																//retrieves the string that are of given length from a file and from that it randomly picks a set of words
		int n=0;
		List<String> wordList = listWithSameLength(totalWordList);		
		Set<String> selectWord=new HashSet<String>();
		while(selectWord.size() < totalWords)					//continue this till desired word list is attained
		{
			Random rand = new Random();
			String word = wordList.get(rand.nextInt(wordList.size()));					//rand.nextInt(num) will give a random number <= num
			selectWord.add(word);														//appending word in set to avoid dupicates 
		}
		return selectWord;
	}

	List<String> listWithSameLength(List<String> totalWordList)			//retrieves the string that are of given length from a file
	{
		List<String> sameLengthWords = new ArrayList<String>();
		for(String word: totalWordList)
		{
			if(word.length() == wordLength)
			{
				sameLengthWords.add(word);
			}
		}
		return sameLengthWords;
	}

}
