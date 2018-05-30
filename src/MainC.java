/**
 * Duisebay Yerkebulan 1603 CSSE
 */
import java.util.*;
import java.io.*;
public class MainC {
static int amount=12;
static Team[] team=new Team[amount];
static PrintWriter fo=null;
//this indicate that in particular period does the Strong teams play
static boolean[] firstStrongTeam={false,false,false,false,false,false,false,false,false,false,false};
	public static void main(String[] args) throws IOException {
while(true){
		Scanner in=new Scanner(System.in);
		System.out.println("enter Y or y to play");
		String answer=in.next(), get="y";
		answer=answer.toLowerCase();
		if(!answer.equals(get))break;
     boolean stop=true;
     while(stop){
		Scanner fr=new Scanner(new FileReader("input.txt"));
		fo= new PrintWriter(new FileWriter("output.txt", true));
		fo.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");            
		fo.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		fo.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		
		for(int i=0;i<amount;i++)
        	team[i]=new Team(fr.nextLine());
        fr.close();
        
        
        //first 5 stri=ong team
        for(int i=0; i<5;i++) //team
        	for(int j=0;j<4-i;j++)
        		optFirstStrongTeams(i);
        
        //to match rest teams
        for(int i=0;i<amount;i++){//this opt a team
        	for(int j=0;j<amount-1;j++){//this is show weeks or period
        		if(team[i].havePeriod[j]) continue;
        			rivalOpt(i,j);
        		for(int k=0;k<amount-1;k++)
        			firstStrongTeam[k]=false;	
            }
        }      

        
      //this is output a table
        for(int i=0;i<amount;i++){
        	fo.print("                                                       ");
        	String nameTeam=i+" "+team[i].name+" :";
        	fo.print(nameTeam);
        	for(int t=0;t<24-nameTeam.length();t++)
        		fo.print("-");
        	for(int j=0;j<amount-1;j++){
                 String playAgainstString=String.valueOf(team[i].playAginstTeam[j]);
               if(playAgainstString.length()==1) playAgainstString+=' ';
        		fo.print(playAgainstString+" | ");
        	}
        	fo.println();
        }
    stop=errorHandler();
      }
     for(int i=0; i<firstStrongTeam.length;i++)
    	firstStrongTeam[i]=false;
     outputResult();
     toSumScore();
     outputWiner(); 
   fo.close();
 }
}
	
//this code design to match strong teams
static void  optFirstStrongTeams(int id){
	ArrayList<Integer> ranWeekOptimization=new ArrayList();
	for(int i=0;i<firstStrongTeam.length;i++)
		if(!firstStrongTeam[i])ranWeekOptimization.add(i);
	
if(ranWeekOptimization.size()!=0){
	int ranWeek=(int)Math.round(Math.random()*(ranWeekOptimization.size()-1));
	ArrayList<Integer> ranStrongOptimization=new ArrayList();
	for(int i=0; i<5;i++){
		if(id==i)continue;
		if(alreadyHavePlayed(id,i))continue;
		if(team[i].havePeriod[ranWeekOptimization.get(ranWeek)])continue;
		ranStrongOptimization.add(i);
	}
	if(ranStrongOptimization.size()!=0){
		int ranRival=(int)Math.round(Math.random()*(ranStrongOptimization.size()-1));
		theyPlay(ranWeekOptimization.get(ranWeek),id,ranStrongOptimization.get(ranRival));
		firstStrongTeam[ranWeekOptimization.get(ranWeek)]=true;
		
	}
 }
}
	



	
//this a take particular period and team  to choose a rivals
static void rivalOpt(int id, int period){
	while(true){
		if(team[id].playAginstTeam[period]!=-1)break;
		int randomIdRival=randomGenerator(id,period);
		if(randomIdRival==-1)break;
		theyPlay(period,id,randomIdRival);
		break;
	}
}

//this method record data regarding game
static void theyPlay(int period, int id, int randomIdRival){
    team[id].havePeriod[period]=true;
    team[randomIdRival].havePeriod[period]=true;
    team[id].playAginstTeam[period]=randomIdRival;
    team[randomIdRival].playAginstTeam[period]=id; 
    team[id].playAginstScore[period]=score();
    team[randomIdRival].playAginstScore[period]=score();
    
    if(team[randomIdRival].playAginstScore[period]==team[id].playAginstScore[period]){
		team[randomIdRival].periodPoint[period]=1;
		team[id].periodPoint[period]=1;
	     }
	else if(team[randomIdRival].playAginstScore[period]>team[id].playAginstScore[period]){
		team[randomIdRival].periodPoint[period]=3;
		team[id].periodPoint[period]=0;
	    }
	else{
		team[randomIdRival].periodPoint[period]=0;
		team[id].periodPoint[period]=3;
	    }
}

//this is Random id Generator
static int randomGenerator(int id, int period){
   int res=0,index=0;
   ArrayList<Integer> ranRivalOptimization=new ArrayList();
   for(int i=0;i<amount;i++){
	   if(team[i].havePeriod[period])continue;
	   if(alreadyHavePlayed(id,i))continue;
       if(id<5 && i<5)continue;
	   if(id==i)continue;
	   ranRivalOptimization.add(i);
   }
   if(ranRivalOptimization.size()==0)return -1;
   int randomIndex=(int)Math.round(Math.random()*(ranRivalOptimization.size()-1));
   res=ranRivalOptimization.get(randomIndex);
   return res;
}


//this method check in this HOLE game have a -1 value in order to avoid from errors
static boolean errorHandler(){
	boolean local=false;
	for(int i=0;i<amount;i++){
		for(int j=0;j<amount-1;j++){
			if(team[i].playAginstTeam[j]==-1)
				local=true;
		}
	}
	return local;
}


//alreadyHavePlayed
static boolean alreadyHavePlayed(int currentId, int cheked){
   boolean tem=false;
    for(int i=0; i<amount-1; i++)
      if(team[currentId].playAginstTeam[i]==cheked)tem=true;
   return tem;
}

//score
static int score(){
	int[] a={0,0,1,1,1,2,2,2,2,3,3,3,4,4,5,5,6,7};
    int tem=(int)Math.round(Math.random()*17);
    return a[tem];
}


static void outputResult(){
	for(int i=0;i<amount-1;i++){
		//this a check if a particular has been shown or not
		boolean alreadyShow[]={false,false,false,false,false,false,false,false,false,false,false,false};
		fo.println("                                                                          "+(i+1)+"-Week: ");
		for(int j=0;j<amount/2;j++){
		 String tem = toShow(alreadyShow,i);
		 fo.println("                                                                          "+tem);		
		 }
		fo.println();
		fo.println();
	}
}


//to randomly opt teams to output
static String toShow(boolean[] a, int period){
	int ran;
 ArrayList<Integer> toRanOptimisation=new ArrayList();
	for(int i=0; i<a.length;i++)
		if(!a[i])toRanOptimisation.add(i);
	
      ran=(int)Math.round(Math.random()*(toRanOptimisation.size()-1));
	int oposite=team[toRanOptimisation.get(ran)].playAginstTeam[period];
	String res="   (H)"+team[toRanOptimisation.get(ran)].name+"["+team[toRanOptimisation.get(ran)].playAginstScore[period]+"]-"+team[oposite].name+"["+team[oposite].playAginstScore[period]+"]";
	a[toRanOptimisation.get(ran)]=true;
	a[oposite]=true;
	return res;
}


//----------------------------this part output statistics--------------------------------------------------
static void outputWiner(){
	int top=10000,id=0, temMax=-1, gfMax=-1;
	boolean[] showed={false,false,false,false,false,false,false,false,false,false,false,false};
	fo.print("                                                                     ");
	fo.println(beautiful("#",5)+beautiful("Name",15)+beautiful("PTS ",6)+beautiful("W", 6)+beautiful("D", 6)+
			beautiful("L", 6)+beautiful("GF", 6)+beautiful("GA", 6)+beautiful("GD", 6));
for(int i=0; i<amount;i++){
	for(int j=0; j<amount;j++){
	    if(team[j].sumScore>=temMax&&team[j].sumScore<=top && !showed[j]){
	    	if(team[j].sumScore==temMax){
	    	 if(gfAmount(j)>gfMax){
				temMax=team[j].sumScore;  id=j; gfMax=gfAmount(j);} 
	    	 }else{
				temMax=team[j].sumScore;  id=j; gfMax=gfAmount(j);}
			}
	}
	fo.print("                                                                     ");
	fo.println(beautiful((String.valueOf(i+1)), 5)+beautiful(team[id].name,15)+beautiful(String.valueOf(team[id].sumScore),6)
	+beautiful(String.valueOf(winsAmount(id)),6)+beautiful(String.valueOf(11-winsAmount(id)-loseAmount(id)),6)
	+beautiful(String.valueOf(loseAmount(id)),6)+beautiful(String.valueOf(gfAmount(id)),6)
	+beautiful(String.valueOf(gaAmount(id)),6)+beautiful(String.valueOf(gfAmount(id)-gaAmount(id)),6));
	showed[id]=true;
	top=temMax;
	temMax=-1;
	id=0;
  }
}
//this method show total GF
static int gfAmount(int id){
	int res=0;
	for(int i=0; i<11;i++)
		res+=team[id].playAginstScore[i];
	return res;
}

//this method show total GAgainst
static int gaAmount(int id){
	int res=0;
	for(int i=0; i<11;i++)
		res+=team[team[id].playAginstTeam[i]].playAginstScore[i];
	return res;
}

//this method show how many times the team wins
static int loseAmount(int id){
	int res=0;
	for(int i=0; i<11;i++)
		if(team[id].periodPoint[i]==0)res++;
	return res;
}

//this method show how many times the team wins
static int winsAmount(int id){
	int res=0;
	for(int i=0; i<11;i++)
		if(team[id].periodPoint[i]==3)res++;
	return res;
}


//Only in decerative purposes in order to avoid ugly veiw
static String beautiful(String str, int l){
	String res=str;
	for(int i=str.length(); i<l;i++)
		res+=' ';
	return res;
}

//--------------------------------------------------------------
  static void toSumScore(){
	  for(int i=0; i<amount;i++)
			for(int j=0; j<amount-1;j++)
				team[i].sumScore+=team[i].periodPoint[j];
   }
}
