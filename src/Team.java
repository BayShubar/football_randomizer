public class Team {
	static int amount=12;
	  String name;
	  boolean[] havePeriod={false,false,false,false,false,false,false,false,false,false,false};//this indicate that he already play in particular period
	  int [] playAginstTeam={-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};//The set of teams that them already play
	  int[]  playAginstScore=new int[amount]; //tha amount of goals
	  int[]  periodPoint=new int[amount-1]; //the amont of points that team gain
	  int sumScore=0;
	  
	  
	  public Team(){};
	  public Team(String name){
		  this.name=name;
	  };
	  
	}
