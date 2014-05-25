package RPSLP;
/***
 * Author: @anandh_somu
 * Date: 23-Mar-2014
 * Rock-Paper-Scissor-lizard-Spock simulator
 * Ref: http://www.reddit.com/r/dailyprogrammer/comments/23lfrf/4212014_challenge_159_easy_rock_paper_scissors
 */

import java.util.Scanner;
import java.util.*;

public class RPSLP {

	enum move{
		Rock,
		paper,
		scissors,
		lizard,
		Spock}

	static String rules[]={"Scissors cut paper",
		"Paper covers rock",
		"Rock crushes lizard",
		"Lizard poisons Spock",
		"Spock smashes scissors",
		"Scissors decapitate lizard",
		"Lizard eats paper",
		"Paper disproves Spock",
		"Spock vaporizes rock",
	"Rock crushes scissors"};

	Scanner userInputScanner = new Scanner(System.in);
	static int totalGame=0,userWin=0,compWin=0,tie=0;

	public static void main(String[] args) 
	{
		RPSLP obj=new RPSLP();
		String user;
		String computer;

		while(true)
		{
			user = ""+move.values()[obj.userMove()-1];
			computer = ""+move.values()[obj.computerMove()-1];
			obj.findWinner(user, computer);
			totalGame++;
		}
	}

	int userMove()
	{
		System.out.println("\n----------------\nSelect 1.Rock,2.paper,3.scissors,4.lizard,5.Spock,(6.Exit) - ");	
		int userInp=userInputScanner.nextInt();
		if(userInp==6)
		{
			System.out.println("-----------------Complete Stats--------------");
			System.out.println("Total games Played - "+totalGame);
			System.out.println("User Wins - "+userWin+"   :  Win percentage - "+(((float)userWin/(float)totalGame)*100));
			System.out.println("Computer Wins - "+compWin+"   :  Win percentage - "+(((float)compWin/(float)totalGame)*100));
			System.out.println("Ties - "+tie+"   :  Tie percentage - "+(((float)tie/(float)totalGame)*100));
			System.exit(0);
		}
		return userInp;
	}

	int computerMove()
	{
		Random rand=new Random();
		return (1+rand.nextInt(5));
	}

	void findWinner(String user,String computer)
	{
		System.out.println("Player Picks: "+user+"\nComputer Picks: "+ computer+"\n");
		for(int cnt=0;cnt<rules.length;cnt++)
		{
			if(user.equalsIgnoreCase(computer))
			{
				System.out.println("Its a tie!");
				tie++;
				return;
			}
			else if(rules[cnt].toLowerCase().contains(user.toLowerCase()) && rules[cnt].toLowerCase().contains(computer.toLowerCase()))
			{
				System.out.print(rules[cnt]);
				if(rules[cnt].toLowerCase().indexOf(user.toLowerCase())==0)
				{
					System.out.println(". User Wins!");
					userWin++;
				}
				else
				{
					System.out.println(". Computer Wins!");
					compWin++;
				}
			}
		}
	}

}
