import java.util.*;
import java.io.PrintWriter;
import java.io.File;
//import java.swing.*;

public class Baseball{
    //Static only class
    public Baseball(){}

    private static player[] players = new player[2];

    private static int currentPlayer = 0;

    //Inning number < 9
    private static int inning = 0;

    public static int getInnings(){
        return inning;
    }

    public static void addInning(){
        inning++;
    }
    
    public static String printScore(){
        String retval = "Scoreboard\n";
        for(int i = 0; i < 2; i++){
            retval += players[i].getName() + " : " + players[i].getPoints() + "\n";     
        }
        return retval;
    }

    public static String printCurrent(player cp){
        return "Pitching: " + cp.getName() + "\n" + 
               "Balls: " + cp.getBalls() + "\n" + 
               "Strikes: " + cp.getStrikes() + "\n" +
               "Outs: " + cp.getOuts() + "\n" + 
               "Bases: " + cp.getBases() + "\n";
    }

    public static String generateHTML(){
        player cp = players[currentPlayer];
        String baseImage = "res/Bases" + cp.getBases() + ".png";
        String ballImage = "res/Balls" + cp.getBalls() + ".png";
        String outImage = "res/Outs" + cp.getOuts() + ".png";
        String strikeImage = "res/Strikes" + cp.getStrikes() + ".png";
        String pitcherName = cp.getName();
        String player1 = players[0].getName();
        String player2 = players[1].getName();
        int[] p1Points = players[1].getPoints();
        int[] p2Points = players[0].getPoints();
        int p1Total = players[1].getTotalPoints();
        int p2Total = players[0].getTotalPoints();

        String HTML = "<html><link rel = \"stylesheet\" type = \"text/css\" href = \"Game.css\" /><script type=\"text/javascript\" src=\"Game.js\"></script><table id=\"buttontable\"><tr><td id=\"red\"><button id=\"red\" onclick=\"send('Single')\">Single</button></td><td id=\"red\"><button id=\"red\" onclick=\"send('Double')\">Double</button></td><td id=\"red\"><button id=\"red\" onclick=\"send('Triple')\">Triple</button></td><td id=\"red\"><button id=\"red\" onclick=\"send('Home Run')\">Home Run</button></td></tr><tr><td id=\"green\"><button id=\"green\" onclick=\"send('Double Play')\">Double Play</button></td><td id=\"red\"><button id=\"red\" onclick=\"send('Stolen Base')\">Stolen Base</button></td><td id=\"red\"><button id=\"red\" onclick=\"send('Safe Hit')\">Safe Hit</button></td><td id=\"red\"><button id=\"red\" onclick=\"send('Sacrifice')\">Sacrifice</button></td></tr></table><table id=\"buttontable2\"><tr><td id=\"yellow\"><button id=\"yellow\" onclick=\"send('Ball')\">Ball</button></td><td id=\"yellow\"><button id=\"yellow\" onclick=\"send('Foul')\">Foul</button></td><td id=\"yellow\"><button id=\"yellow\" onclick=\"send('Strike')\">Strike</button></td><td id=\"yellow\"><button id=\"yellow\" onclick=\"send('Out')\">Out</button></td></tr></table>"
                    + "<img src=\"" + baseImage + "\" id=\"image1\"></img>"
                    + "<p id=\"pitcher\">Current Pitcher: " + pitcherName + "</p>"
                    + "<p id=\"balls\">Balls: </p>"
                    + "<img src=\"" + ballImage + "\" id=\"image2\"></img>"
                    + "<p id=\"strikes\">Strikes: </p>"
                    + "<img src=\"" + strikeImage + "\" id=\"image3\"></img>"
                    + "<p id=\"outs\">Outs: </p>"
                    + "<img src=\""+ outImage + "\" id=\"image4\"></img>"
                    + "<table id=\"table2\"><tr><td id=\"empty\"></td>";
                    
        for(int i = 1; i <= 9; i++){
            if(i == (inning + 1)){
                HTML += "<td id=\"current\">" + i + "</td>";
            }else{
                HTML += "<td>" + i + "</td>";
            }
        }            
        HTML += "<td>Total</td></tr><tr><td>" + player1 + "</td>";

        for(int i = 0; i <= inning; i++){
            HTML += "<td>" + p1Points[i] + "</td>";
        }
        for(int i = inning+1; i < 9; i++){
            HTML += "<td></td>";
        }
        HTML += "<td>" + p1Total + "</td></tr><tr>"
             + "<td>" + player2 + "</td>";

        for(int i = 0; i <= inning; i++){
            HTML += "<td>" + p2Points[i] + "</td>";
        }
        for(int i = inning + 1; i < 9; i++){
            HTML += "<td></td>";
        }
        HTML +="<td>" + p2Total + "</td></tr></table></html>";

        return HTML;
    }

    public static void printHTML(String filename, String HTML){
        try{
            PrintWriter p = new PrintWriter(new File(filename));
            p.write(HTML);
            p.close();
        }catch(Exception e){
            System.err.println("Failed to write data to file: " + filename);
        }
    }

    public static void setup(String name1, String name2){
        players[0] = new player(name1); players[1] = new player(name2);
    }

    public static player getCurrentPlayer(){
        return players[currentPlayer];
    }

    public static void switchCurrentPlayer(){
        currentPlayer = (currentPlayer == 0)? 1 : 0;
    }

    public static void callCommand(String command){
        System.out.println(command);
        switch(command){
            case "Single": players[currentPlayer].Single(); break;
            case "Double" : players[currentPlayer].Double(); break;
            case "Triple" :  players[currentPlayer].Triple();break;
            case "Home Run" : players[currentPlayer].HomeRun(); break;
            case "Foul" : players[currentPlayer].Foul(); break;
            case "Strike" : players[currentPlayer].Strike(); break;
            case "Ball" : players[currentPlayer].Ball(); break;
            case "Out" : players[currentPlayer].Out(); break;
            case "Safe Hit" : players[currentPlayer].SafeHit(); break;
            case "Double Play" : players[currentPlayer].DoublePlay(); break;
            case "Sacrifice" : players[currentPlayer].Sacrifice(); break;
            case "Stolen base" : players[currentPlayer].StolenBase(); break;
            default : System.out.println("Invalid Entry");
        }
    }
}


class player{
    //count holds four feilds [totals points, outs < 2, balls < 4, strike < 3]
    private int balls;
    private int outs;
    private int strikes;
    private int inning;
    private int[] points;
    private String name;
    private ArrayList<Integer> bases;
    private boolean turnOver;

    public int[] getPoints(){
        return points;
    }
    public int getTotalPoints(){
        int retval = 0;
        for(int i = 0; i < 9; i++){
            retval += points[i];
        }
        return retval;
    }
    public String getName(){return name;}
    public int getBalls(){ return balls; }
    public int getOuts(){ return outs; }
    public int getStrikes(){ return strikes; }
    public String getBases(){
        if(bases.size() == 0){
            return "";
        }else if(bases.size() == 1){
            return "" + bases.get(0);
        }else{
            
            String retval = "" + bases.get(0);
            for(int i = 1; i < bases.size(); i++){
                retval += "," + bases.get(i);
            }
            return retval;
        }
    }

    public player(String name){
        this.name = name;
        balls = outs = strikes = inning = 0;
        points = new int[]{0,0,0,0,0,0,0,0,0};
        bases = new ArrayList<Integer>();
    }

    public void reset(){
        balls = outs = strikes = inning = 0;
        bases = new ArrayList<Integer>();
    }

    public boolean isOut(){
        return outs > 2;
    }

    public void countRuns(){
        for(int i = bases.size() - 1; i >= 0; i--){
            if(bases.get(i) > 3){
                bases.remove(i);
                points[Baseball.getInnings()]++;
            }
        }
    }

    public void resetCount(){
        balls = strikes = 0;
    }

    public void hit(int base){
        for(int i = 0; i < bases.size(); i++){
            bases.set(i, bases.get(i) + base);
        }
        bases.add(base);
        resetCount();
    }
    
    public void Single(){
        hit(1);
    }
    

    public void Double(){
        hit(2);
    }

    public void Triple(){
        hit(3);
    }

    public void HomeRun(){
        ArrayList<Integer> newBases = new ArrayList<Integer>();
        for(int b : bases){
            newBases.add(4);
        }
        newBases.add(4);
        this.bases = newBases;
        resetCount();
    }

    public void Foul(){
        if(strikes < 2){
            strikes++;
        }
    }

    public void Strike(){
        if(++strikes > 2){
            Out();
        }
    }

    public void Ball(){
        if(++balls > 3){
            Single();
            balls = 0;
        }
    }

    public void Out(){
        outs++; resetCount();
    }

    public void SafeHit(){
        Single();
    }

    private boolean onBase(int base){
        for(int b : bases){
            if(b == base){ return true; }
        }
        return false;
    }

    //Rules for a Double Play:
    //Guy on 1st
    //> 2 Outs
    //How it works:
    //if the rules are true
    //then a single is played
    //and the closest two people to home
    //are out.
    //Exception:
    //if there is a guy on
    //1st and 3rd and a double play is hit.
    //The guys on 2nd and 1st would be out leaving
    //on guy on 3rd.
    //Reasoning:
    //In real baseball the guy on 3rd is not forced
    //to run therefor he would stay. But a double play is still
    //inorder so we get rid of the other two people
    public void DoublePlay(){
        if(outs > 2 || !onBase(1)){
            System.out.println("Test");
            Ball(); return;
        }
        if(onBase(3)){
            bases.clear(); bases.add(3);
        }else{
            Single();
            bases.remove(0); bases.remove(0);
        }
    }

    //Rules:
    //Guy on 3rd
    //>2 outs
    //How it works
    //Everyone advances 1 plate
    //and the guy hitting the ball gets out
    public void Sacrifice(){
        if(!onBase(3) || outs > 2){
            Ball(); return;
        }
        StolenBase();
        Out();
    }
    
    //Everyone on base moves up one position
    public void StolenBase(){
        ArrayList<Integer> newBases = new ArrayList<Integer>();
        for(int b: bases){
            newBases.add(++b);
        }  
        bases = newBases;
    }
}
