package RPSLP;
/***
 * Author: @anandh_somu
 * Date: 24-Mar-2014
 * Rock-Paper-Scissor-lizard-Spock AI game
 * Ref: http://www.reddit.com/r/dailyprogrammer/comments/23qy19/4232014_challenge_159_intermediate_rock_paper/
 */

import java.util.*;

public class RPSLP_AI {

	//move - list of all possible moves in this game
	enum move{
		Rock,//0
		paper,//1
		scissors,//2
		lizard,//3
		Spock}

	//rules - all possible outcomes for the move selected
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

	static Scanner userInputScanner = new Scanner(System.in);
	static int totalGame=0,userWin=0,compWin=0,tie=0; //for recording stats
	static int[] userList=new int[]{0,0,0,0,0};		//to keep track of user selection: AI

	public static void main(String[] args) 
	{
		RPSLP_AI obj=new RPSLP_AI();
		int userPick = 0;

		String user = null;
		String computer = null;
		//UF Menu
		System.out.println("Select Game Mode - \n1.Human VS Noob Bot\n2.Human VS OK Bot\n3.Noob Bot VS OK Bot(100 games) - ");
		int userMenu=userInputScanner.nextInt();


		while(true)
		{
			if(userMenu==1)			
			{
				userPick=obj.userMove();
				userList[userPick-1]++;
				user = ""+move.values()[userPick-1];
				computer = ""+move.values()[obj.randomComputer(5)-1];		//just random numbers. no AI or processing
			}
			else if(userMenu==2)
			{
				userPick=obj.userMove();
				userList[userPick-1]++;
				user = ""+move.values()[userPick-1];
				computer= ""+move.values()[obj.AIComputer()-1];			// with AI processing
			}
			else if(userMenu==3)					// AI processing VS random numbers
			{
				System.out.println("-----------------------");
				userPick=obj.randomComputer(5);
				userList[userPick-1]++;
				user = ""+move.values()[userPick-1];				
				computer= ""+move.values()[obj.AIComputer()-1];
			}
			else
			{
				System.out.println("Wrong Selection .... Exiting ....");
				System.exit(0);
			}

			obj.findWinner(user, computer);				// to find the winner from the respective selection
			totalGame++;
			if(userMenu==3 && totalGame==100)		// limited to 100 games, and to print result at that point
			{
				obj.printStats();
			}
		}
	}

	int getMoveValue(String move)			// move to number conversion. The other way is provided by enum
	{
		switch(move.toLowerCase())
		{
		case "rock":
			return 0;
		case "paper":
			return 1;
		case "scissors":
			return 2;
		case "lizard":
			return 3;
		case "spock":
			return 4;
		}
		return -1;
	}

	int userMove()				//Get input from user
	{
		System.out.println("\n----------------\nSelect 1.Rock,2.paper,3.scissors,4.lizard,5.Spock,(6.Exit) - ");	
		int userInp=userInputScanner.nextInt();
		if(userInp==6)
		{
			printStats();
		}
		return userInp;
	}

	int randomComputer(int bound)		//simple random number generation with a bound
	{
		Random rand=new Random();
		return (1+rand.nextInt(bound));
	}

	/*AIComputer()
	 * The choice of what move the computer picks in future games will be based on taking the top picks so far 
	 * and picking from the counter picks. In the case of a tie for a move the computer will only random amongst 
	 * the counter moves of those choices and also eliminate from the potential pool of picks any moves it is trying 
	 * to counter to lessen the chance of a tie.
	 * 
	 * returns - after processing it selects a more efficient move
	 */
	int AIComputer()				
	{
		int max=-1;
		String maxNum="";		//keep track of all max selected move
		int finalPick;
		List<Integer> counterPicks = new ArrayList<Integer>();
		for(int i=0;i<userList.length;i++)		//loop to find the most selected move and also generates a string in CSV format with all max moves
		{										//Ex: if Rock and lizard are selected most, then maxNum=0,3,
			if(userList[i]>max)
			{
				max=userList[i];
				maxNum=i+",";
			}
			else if(userList[i]==max)
			{
				maxNum=maxNum+i+",";
			}
		}//end for

		for(int i=0;i<maxNum.split(",").length;i++)		//loop to find a counter pick for all the picks that are selected the most till now
		{
			if(max>=2)									//atleast the particular move should have been picked twice - to improve probability
			{
				String tmp=""+move.values()[Integer.parseInt(maxNum.split(",")[i])];				//get the name of the move from the number
				//System.out.println("max - "+move.values()[Integer.parseInt(maxNum.split(",")[i])]);		

				for(int cnt=0;cnt<rules.length;cnt++)								//search the move in all the rules and see if it is present in the end part of the rule 
				{																	
					if(rules[cnt].toLowerCase().contains(tmp.toLowerCase()) &&  rules[cnt].toLowerCase().indexOf(tmp.toLowerCase())!=0)
					{
						//System.out.println("counter -- "+rules[cnt].split(" ")[0]);
						counterPicks.add(getMoveValue(rules[cnt].split(" ")[0]));			//if the most picked move is in the end part of the rule then the move present in 
					}																		//- the first part of the rule is the counter. So split and grab the counter's name
				}																			//and put it in a list
			}
		}

		//Converting ArrayList to HashSet to remove duplicates
		HashSet<Integer> listToSet = new HashSet<Integer>(counterPicks);

		//Arraylist without duplicate values
		counterPicks = new ArrayList<Integer>(listToSet);

		for(int i=0;i<maxNum.split(",").length;i++)		//loop is to eliminate counters that match any of the top picks
		{
			if(counterPicks.indexOf(Integer.parseInt(maxNum.split(",")[i])) > -1)     //top picks in a string, so used it to remove those from the list
			{
				counterPicks.remove(counterPicks.indexOf(Integer.parseInt(maxNum.split(",")[i])));
			}
		}

		//System.out.println(counterPicks);
		if(counterPicks.size()>0)			
		{
			finalPick=counterPicks.get(randomComputer(counterPicks.size())-1)+1;   //if more than 1 counterpick then pick one randomly from them
			//System.out.println(finalPick);
			//System.out.println("Computers finalPick is - "+move.values()[finalPick]);
		}
		else
		{
			finalPick=randomComputer(5);			//if no counter pick then do a blind random
		}

		return finalPick;
	}

	void findWinner(String user,String computer)
	{
		System.out.println("Player Picks: "+user+"\nComputer Picks: "+ computer+"\n");
		for(int cnt=0;cnt<rules.length;cnt++)
		{
			if(user.equalsIgnoreCase(computer))			//is user and computer picks a same move then its a tie
			{
				System.out.println("Its a tie!");
				tie++;
				return;
			}																	//below line is to search if both the moves are present in any rule, if so that is the outcome
			else if(rules[cnt].toLowerCase().contains(user.toLowerCase()) && rules[cnt].toLowerCase().contains(computer.toLowerCase()))
			{
				System.out.print(rules[cnt]);
				if(rules[cnt].toLowerCase().indexOf(user.toLowerCase())==0)			//and if the move selected by user is in the front portion of the rule then user wins
				{
					System.out.println(". User Wins!");
					userWin++;
				}
				else			//if not above condition then computer wins
				{
					System.out.println(". Computer Wins!");
					compWin++;
				}
			}
		}
	}

	void printStats()			//print the stats with all those recorded values
	{
		System.out.println("-----------------Complete Stats--------------");
		System.out.println("Total games Played - "+totalGame);
		System.out.println("User Wins - "+userWin+"   :  Win percentage - "+(((float)userWin/(float)totalGame)*100));
		System.out.println("Computer Wins - "+compWin+"   :  Win percentage - "+(((float)compWin/(float)totalGame)*100));
		System.out.println("Ties - "+tie+"   :  Tie percentage - "+(((float)tie/(float)totalGame)*100));
		System.exit(0);
	}
}
