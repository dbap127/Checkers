/**
 * Created by Moocow on 6/6/2017.
 */
import java.util.Random;

class Checkers
{
  public static char[][] spaces = new char[8][8]; // 2d array for holding where the game pieces are
  public static String rowLegend = "abcdefgh"; // row number definitions
  public static String columnLegend = "12345678"; // column letter definitions
  public static String moveInput = "";
  public static int playerNum = 1;
  public static int startLetter;
  public static int startNumber;
  public static int endLetter;
  public static int endNumber;
  public static boolean validMove = false;
  public static int[] pieceCount = new int[2];
  public static String moveSeq = "";
  public static String origI;
  public static boolean gameEnd = true;
  public static int menuChoice;
  public static boolean aiEnable = false;
  public static String pieceLocationString = "";
  public static String pieceTypesString = "";
  public static String possibleMoves = "";
  public static String aiChoice = "";
  public static boolean hopMoveFound = false;
  
  public static void main(String[] args)
  {
    while (!(true == false))
    {
      menuChoices();
      try
      {
        System.out.print("Input choice: ");
        menuChoice = Integer.parseInt(In.getString());
        if (menuChoice == 1)
        {
          aiEnable = false;
          gameEnd = false;
          setupBoard();
          printBoard();
          while (gameEnd == false)
          { 
            System.out.println("Player " + playerNum + "'s turn");
            moveInput = In.getString();
            moveInput = moveInput.replaceAll("\\s+","");
            sequenceCheck(moveInput);
            checkValidMove();
            checkKing();
            printBoard();
          }
        }
        else if (menuChoice == 2)
        {
          System.out.println("Sorry, Currently in Development.\n");
          aiEnable = true;
          gameEnd = false;
          setupBoard();
          printBoard();
          while (gameEnd == false)
          { 
            if (playerNum == 1)
            {
              System.out.println("Your turn");
              moveInput = In.getString();
              moveInput = moveInput.replaceAll("\\s+","");
              sequenceCheck(moveInput);
              checkValidMove();
              checkKing();
              printBoard();
            }
            if (playerNum == 2)
            {
              checkersAi();
            }
          }
        }
        else if (menuChoice == 3)
        {
          rules();
        }
        else if (menuChoice == 4)
        {
          howToMove();
        }
        else
        {
          System.out.println("Invalid Input\n");
        }
      }
      catch (Exception e)
      {
        System.out.println("Invalid Input\n");
      }
    }
  }
  
  public static void menuChoices()
  {
    System.out.println("Welcome to Checkers.");
    System.out.println("Press 1 for Player vs Player");
    System.out.println("Press 2 for Player vs AI");
    System.out.println("Press 3 for Checkers Rules (Highly Recommended)");
    System.out.println("Press 4 for Learning How To Move (Highly Recommended)");
  }
  
  public static void howToMove()
  {
    System.out.println("In this version, you have to first type in the location of the");
    System.out.println("piece you want to move (ex: f1), then input the place of where");
    System.out.println("you want to go (ex: e2) so the full input would be \"f1e2\".");
    System.out.println("To hop, you have to input the place of where you want to hop after.");
    System.out.println("To multi-hop you have to input each location of where you hop in order.");
    System.out.println("For example \"f1e2\" \n\n");
  }
  
  public static void rules()
  {
    System.out.println("Welcome to Checkers");
    System.out.println("This is how to play:");
    System.out.println("Each player begins the game with 12 pieces, or checkers,");
    System.out.println("placed in the three rows closest to him or her. The object");
    System.out.println("of the game is to capture all of your opponent's checkers");
    System.out.println("or position your pieces so that your opponent has no available moves.");
    System.out.println("Basic movement is to move a checker one space diagonally forward.");
    System.out.println("To capture an opponent piece, you have to diagonally hop over it.");
    System.out.println("In this version, you are allowed to choose if you want to hop over");
    System.out.println("and you are also allowed to choose how many pieces you want to hop over.\n\n");
  }
  
  public static void setupBoard() // method to place the game pieces at the start of the game
  {
    for (int r = 0; r < 8; r++) // for loop for rows
    {
      for (int c = 0; c < 8; c++) // for loop for columns
      {
        if (r == 0) // placing the pieces based on first row
        {
          if ((c % 2) == 1)
          {
            spaces[r][c] = '0';
          }
          else
          {
            spaces[r][c] = ' ';
          }
        }
        else if (r == 1) // placing the pieces based on second row
        {
          if ((c % 2) == 0)
          {
            spaces[r][c] = '0';
          }
          else
          {
            spaces[r][c] = ' ';
          }
        }
        else if (r == 2) // placing the pieces based on third row
        {
          if ((c % 2) == 1)
          {
            spaces[r][c] = '0';
          }
          else
          {
            spaces[r][c] = ' ';
          }
        }
        else if (r == 5) // placing the pieces based on sixth row
        {
          if ((c % 2) == 0)
          {
            spaces[r][c] = 'o';
          }
          else
          {
            spaces[r][c] = ' ';
          }
        }
        else if (r == 6) // placing the pieces based on seventh row
        {
          if ((c % 2) == 1)
          {
            spaces[r][c] = 'o';
          }
          else
          {
            spaces[r][c] = ' ';
          }
        }
        else if (r == 7) // placing the pieces based on eighth row
        {
          if ((c % 2) == 0)
          {
            spaces[r][c] = 'o';
          }
          else
          {
            spaces[r][c] = ' ';
          }
        }
        else
        {
          spaces[r][c] = ' ';
        }
      }
    }
  }
  
  
  public static void printBoard() // prints the board with pieces placed
  {
    System.out.print("   ");
    for (int c = 0; c < 8; c++)
    {
      System.out.print(columnLegend.charAt(c) + "  ");
    }
    
    System.out.println("");
    
    for (int r = 0; r < 8; r++)
    {
      System.out.print(rowLegend.charAt(r) + " ");
      
      for (int c = 0; c < 8; c++)
      {
        System.out.print("[" + spaces[r][c] + "]");
      }
      
      System.out.println("");
    }
  }
  
  public static void movePiece() // method that changes the positions of the pieces
  {
    spaces[endLetter][endNumber] = spaces[startLetter][startNumber];
    spaces[startLetter][startNumber] = ' ';
  }
  
  public static void checkValidMove()
  {
    if (validMove == true)
    {
      
      if (playerNum == 1)
      {
        playerNum = 2;
      }
      else
      {
        playerNum = 1;
      }
      
      validMove = false;
    }
  }
  
  public static void validMovePlaces()
  {
    pieceLocationString = "";
    pieceTypesString = "";
    possibleMoves = ""; 
    
    for (int r = 0; r < 8; r++) // for loop for rows
    {
      for (int c = 0; c < 8; c++) // for loop for columns
      {
        if (spaces[r][c] == '0' || spaces[r][c] == 'K')
        {
          pieceLocationString = pieceLocationString.concat(Character.toString(rowLegend.charAt(r)));
          pieceLocationString = pieceLocationString.concat(Character.toString(columnLegend.charAt(c)));
          pieceLocationString = pieceLocationString.concat(" ");
          
          pieceTypesString = pieceTypesString.concat(Character.toString(spaces[r][c]));
          pieceTypesString = pieceTypesString.concat(" ");
        }
      }
    }
    
    pieceLocationString = pieceLocationString.trim();
    pieceTypesString = pieceTypesString.trim();
    
    if (pieceLocationString.length() > 6)
    {
      String[] pieceLocationArray = pieceLocationString.split("\\s+"); 
      String[] pieceTypesArray = pieceTypesString.split("\\s+"); 
      
      for (int i = 0; i < pieceLocationArray.length; i++)
      {
        startLetter = rowLegend.indexOf(pieceLocationArray[i].substring(0,1));
        startNumber = columnLegend.indexOf(pieceLocationArray[i].substring(1,2));
        
        if (pieceTypesArray[i].equals("0"))
        {
          try
          {
            if (spaces[startLetter + 1][startNumber + 1] == ' ')
            {
              possibleMoves = possibleMoves.concat(Integer.toString(startLetter));
              possibleMoves = possibleMoves.concat(Integer.toString(startNumber));
              possibleMoves = possibleMoves.concat(Integer.toString(startLetter + 1));
              possibleMoves = possibleMoves.concat(Integer.toString(startNumber + 1));
              possibleMoves = possibleMoves.concat(" ");
            }
          }
          catch (Exception e)
          {
            
          }
          
          try
          {
            if (spaces[startLetter + 1][startNumber - 1] == ' ')
            {
              possibleMoves = possibleMoves.concat(Integer.toString(startLetter));
              possibleMoves = possibleMoves.concat(Integer.toString(startNumber));
              possibleMoves = possibleMoves.concat(Integer.toString(startLetter + 1));
              possibleMoves = possibleMoves.concat(Integer.toString(startNumber - 1));
              possibleMoves = possibleMoves.concat(" ");
            }
          }
          catch (Exception e)
          {
            
          }
          
        }
        
        else if (pieceTypesArray[i].equals("K"))
        {
          try
          {
            if (spaces[startLetter + 1][startNumber + 1] == ' ')
            {
              possibleMoves = possibleMoves.concat(Integer.toString(startLetter));
              possibleMoves = possibleMoves.concat(Integer.toString(startNumber));
              possibleMoves = possibleMoves.concat(Integer.toString(startLetter + 1));
              possibleMoves = possibleMoves.concat(Integer.toString(startNumber + 1));
              possibleMoves = possibleMoves.concat(" ");
            }
          }
          catch (Exception e)
          {
            
          }
          
          try
          {
            if (spaces[startLetter + 1][startNumber - 1] == ' ')
            {
              possibleMoves = possibleMoves.concat(Integer.toString(startLetter));
              possibleMoves = possibleMoves.concat(Integer.toString(startNumber));
              possibleMoves = possibleMoves.concat(Integer.toString(startLetter + 1));
              possibleMoves = possibleMoves.concat(Integer.toString(startNumber - 1));
              possibleMoves = possibleMoves.concat(" ");
            }
          }
          catch (Exception e)
          {
            
          }
          
          try
          {
            if (spaces[startLetter - 1][startNumber - 1] == ' ')
            {
              possibleMoves = possibleMoves.concat(Integer.toString(startLetter));
              possibleMoves = possibleMoves.concat(Integer.toString(startNumber));
              possibleMoves = possibleMoves.concat(Integer.toString(startLetter - 1));
              possibleMoves = possibleMoves.concat(Integer.toString(startNumber - 1));
              possibleMoves = possibleMoves.concat(" ");
            }
          }
          catch (Exception e)
          {
            
          }
          
          try
          {
            if (spaces[startLetter - 1][startNumber + 1] == ' ')
            {
              possibleMoves = possibleMoves.concat(Integer.toString(startLetter));
              possibleMoves = possibleMoves.concat(Integer.toString(startNumber));
              possibleMoves = possibleMoves.concat(Integer.toString(startLetter - 1));
              possibleMoves = possibleMoves.concat(Integer.toString(startNumber + 1));
              possibleMoves = possibleMoves.concat(" ");
            }
          }
          catch (Exception e)
          {
            
          }
        }
      }
    }
    else
    {
      String[] pieceLocationArray = {pieceLocationString, " "}; 
      String[] pieceTypesArray = {pieceTypesString, " "};
      
      startLetter = rowLegend.indexOf(pieceLocationArray[0].substring(0,1));
      startNumber = columnLegend.indexOf(pieceLocationArray[0].substring(1,2));
      
      if (pieceTypesArray[0].equals("0"))
      {
        try
        {
          if (spaces[startLetter + 1][startNumber + 1] == ' ')
          {
            possibleMoves = possibleMoves.concat(Integer.toString(startLetter));
            possibleMoves = possibleMoves.concat(Integer.toString(startNumber));
            possibleMoves = possibleMoves.concat(Integer.toString(startLetter + 1));
            possibleMoves = possibleMoves.concat(Integer.toString(startNumber + 1));
            possibleMoves = possibleMoves.concat(" ");
          }
        }
        catch (Exception e1)
        {
          
        }
        
        try
        {
          if (spaces[startLetter + 1][startNumber - 1] == ' ')
          {
            possibleMoves = possibleMoves.concat(Integer.toString(startLetter));
            possibleMoves = possibleMoves.concat(Integer.toString(startNumber));
            possibleMoves = possibleMoves.concat(Integer.toString(startLetter + 1));
            possibleMoves = possibleMoves.concat(Integer.toString(startNumber - 1));
            possibleMoves = possibleMoves.concat(" ");
          }
        }
        catch (Exception e1)
        {
          
        }
        
      }
      
      else if (pieceTypesArray[0].equals("K"))
      {
        try
        {
          if (spaces[startLetter + 1][startNumber + 1] == ' ')
          {
            possibleMoves = possibleMoves.concat(Integer.toString(startLetter));
            possibleMoves = possibleMoves.concat(Integer.toString(startNumber));
            possibleMoves = possibleMoves.concat(Integer.toString(startLetter + 1));
            possibleMoves = possibleMoves.concat(Integer.toString(startNumber + 1));
            possibleMoves = possibleMoves.concat(" ");
          }
        }
        catch (Exception e1)
        {
          
        }
        
        try
        {
          if (spaces[startLetter + 1][startNumber - 1] == ' ')
          {
            possibleMoves = possibleMoves.concat(Integer.toString(startLetter));
            possibleMoves = possibleMoves.concat(Integer.toString(startNumber));
            possibleMoves = possibleMoves.concat(Integer.toString(startLetter + 1));
            possibleMoves = possibleMoves.concat(Integer.toString(startNumber - 1));
            possibleMoves = possibleMoves.concat(" ");
          }
        }
        catch (Exception e1)
        {
          
        }
        
        try
        {
          if (spaces[startLetter - 1][startNumber - 1] == ' ')
          {
            possibleMoves = possibleMoves.concat(Integer.toString(startLetter));
            possibleMoves = possibleMoves.concat(Integer.toString(startNumber));
            possibleMoves = possibleMoves.concat(Integer.toString(startLetter - 1));
            possibleMoves = possibleMoves.concat(Integer.toString(startNumber - 1));
            possibleMoves = possibleMoves.concat(" ");
          }
        }
        catch (Exception e1)
        {
          
        }
        
        try
        {
          if (spaces[startLetter - 1][startNumber + 1] == ' ')
          {
            possibleMoves = possibleMoves.concat(Integer.toString(startLetter));
            possibleMoves = possibleMoves.concat(Integer.toString(startNumber));
            possibleMoves = possibleMoves.concat(Integer.toString(startLetter - 1));
            possibleMoves = possibleMoves.concat(Integer.toString(startNumber + 1));
            possibleMoves = possibleMoves.concat(" ");
          }
        }
        catch (Exception e1)
        {
          
        }
      }
      
    }
    
    if (possibleMoves.length() > 6)
    {
      possibleMoves = possibleMoves.trim();
      String[] possibleMovesArray = possibleMoves.split("\\s+");
      
      
      /* for (int i = 0; i < possibleMovesArray.length; i++) for debugging
       {
       System.out.println(possibleMovesArray[i]);
       }
       */
      
      
      Random randGen = new Random();
      
      aiChoice = possibleMovesArray[randGen.nextInt(possibleMovesArray.length - 1)];
      
      aiGetPositions(aiChoice);
      checkValidMove();
      checkKing();
    }
    else if (possibleMoves.length() > 3 && possibleMoves.length() < 5)
    {
      aiChoice = possibleMoves;
      // System.out.println(aiChoice);
      aiGetPositions(aiChoice);
      checkValidMove();
      checkKing();
    }
    else
    {
      for (int r = 0; r < 8; r++) // for loop for rows
      {
        for (int c = 0; c < 8; c++) // for loop for columns
        {
          if (spaces[r][c] == '0' || spaces[r][c] == 'K')
          {
            spaces[r][c] = 'F';
          }
        }
      }
      System.out.println("The AI Forfeits.");
      checkIfWinner();
    }
    
    
  }
  
  public static void checkValidHops()
  {
    pieceLocationString = "";
    pieceTypesString = "";
    possibleMoves = ""; 
    
    for (int r = 0; r < 8; r++) // for loop for rows
    {
      for (int c = 0; c < 8; c++) // for loop for columns
      {
        if (spaces[r][c] == '0' || spaces[r][c] == 'K')
        {
          pieceLocationString = pieceLocationString.concat(Character.toString(rowLegend.charAt(r)));
          pieceLocationString = pieceLocationString.concat(Character.toString(columnLegend.charAt(c)));
          pieceLocationString = pieceLocationString.concat(" ");
          
          pieceTypesString = pieceTypesString.concat(Character.toString(spaces[r][c]));
          pieceTypesString = pieceTypesString.concat(" ");
        }
      }
    }
    
    pieceLocationString = pieceLocationString.trim();
    pieceTypesString = pieceTypesString.trim();
    
    if (pieceLocationString.contains(" "))
    {
      String[] pieceLocationArray = pieceLocationString.split("\\s+"); 
      
      for (int i = 0; i < pieceLocationArray.length; i++)
      {
        startLetter = rowLegend.indexOf(pieceLocationArray[i].substring(0,1));
        startNumber = columnLegend.indexOf(pieceLocationArray[i].substring(1,2));
        
        try
        {
          if (spaces[startLetter + 2][startNumber + 2] == ' ')
          {
            if (hoppingCheck(spaces[startLetter][startNumber], spaces[startLetter + 1][startNumber + 1]) == true)
            {
              possibleMoves = possibleMoves.concat(Integer.toString(startLetter));
              possibleMoves = possibleMoves.concat(Integer.toString(startNumber));
              possibleMoves = possibleMoves.concat(Integer.toString(startLetter + 2));
              possibleMoves = possibleMoves.concat(Integer.toString(startNumber + 2));
              possibleMoves = possibleMoves.concat(" ");
            }
          }
        }
        catch (Exception e)
        {
          
        }
        
        try
        {
          if (spaces[startLetter + 2][startNumber - 2] == ' ')
          {
            if (hoppingCheck(spaces[startLetter][startNumber], spaces[startLetter + 1][startNumber - 1]) == true)
            {
              possibleMoves = possibleMoves.concat(Integer.toString(startLetter));
              possibleMoves = possibleMoves.concat(Integer.toString(startNumber));
              possibleMoves = possibleMoves.concat(Integer.toString(startLetter + 2));
              possibleMoves = possibleMoves.concat(Integer.toString(startNumber - 2));
              possibleMoves = possibleMoves.concat(" ");
            }
          }
        }
        catch (Exception e)
        {
          
        }
        
        try
        {
          if (spaces[startLetter - 2][startNumber - 2] == ' ')
          {
            if (hoppingCheck(spaces[startLetter][startNumber], spaces[startLetter - 1][startNumber - 1]) == true)
            {
              possibleMoves = possibleMoves.concat(Integer.toString(startLetter));
              possibleMoves = possibleMoves.concat(Integer.toString(startNumber));
              possibleMoves = possibleMoves.concat(Integer.toString(startLetter - 2));
              possibleMoves = possibleMoves.concat(Integer.toString(startNumber - 2));
              possibleMoves = possibleMoves.concat(" ");
            }
          }
        }
        catch (Exception e)
        {
          
        }
        
      }
      
      try
      {
        if (spaces[startLetter - 2][startNumber + 2] == ' ')
        {
          if (hoppingCheck(spaces[startLetter][startNumber], spaces[startLetter - 1][startNumber + 1]) == true)
          {
            possibleMoves = possibleMoves.concat(Integer.toString(startLetter));
            possibleMoves = possibleMoves.concat(Integer.toString(startNumber));
            possibleMoves = possibleMoves.concat(Integer.toString(startLetter - 2));
            possibleMoves = possibleMoves.concat(Integer.toString(startNumber + 2));
            possibleMoves = possibleMoves.concat(" ");
          }
        }
      }
      catch (Exception e)
      {
        
      }
    }
    else
    {
      String[] pieceLocationArray = {pieceLocationString, " "}; 
      String[] pieceTypesArray = {pieceTypesString, " "};
      
      startLetter = rowLegend.indexOf(pieceLocationArray[0].substring(0,1));
      startNumber = columnLegend.indexOf(pieceLocationArray[0].substring(1,2));
      
      try
      {
        if (spaces[startLetter + 2][startNumber + 2] == ' ')
        {
          if (hoppingCheck(spaces[startLetter][startNumber], spaces[startLetter + 1][startNumber + 1]) == true)
          {
            possibleMoves = possibleMoves.concat(Integer.toString(startLetter));
            possibleMoves = possibleMoves.concat(Integer.toString(startNumber));
            possibleMoves = possibleMoves.concat(Integer.toString(startLetter + 2));
            possibleMoves = possibleMoves.concat(Integer.toString(startNumber + 2));
            possibleMoves = possibleMoves.concat(" ");
          }
        }
      }
      catch (Exception e1)
      {
        
      }
      
      try
      {
        if (spaces[startLetter + 2][startNumber - 2] == ' ')
        {
          if (hoppingCheck(spaces[startLetter][startNumber], spaces[startLetter + 1][startNumber - 1]) == true)
          {
            possibleMoves = possibleMoves.concat(Integer.toString(startLetter));
            possibleMoves = possibleMoves.concat(Integer.toString(startNumber));
            possibleMoves = possibleMoves.concat(Integer.toString(startLetter + 2));
            possibleMoves = possibleMoves.concat(Integer.toString(startNumber - 2));
            possibleMoves = possibleMoves.concat(" ");
          }
        }
      }
      catch (Exception e1)
      {
        
      }
      
      try
      {
        if (spaces[startLetter - 2][startNumber - 2] == ' ')
        {
          if (hoppingCheck(spaces[startLetter][startNumber], spaces[startLetter - 1][startNumber - 1]) == true)
          {
            possibleMoves = possibleMoves.concat(Integer.toString(startLetter));
            possibleMoves = possibleMoves.concat(Integer.toString(startNumber));
            possibleMoves = possibleMoves.concat(Integer.toString(startLetter - 2));
            possibleMoves = possibleMoves.concat(Integer.toString(startNumber - 2));
            possibleMoves = possibleMoves.concat(" ");
          }
        }
      }
      catch (Exception e1)
      {
        
      }
      
    }
    
    try
    {
      if (spaces[startLetter - 2][startNumber + 2] == ' ')
      {
        if (hoppingCheck(spaces[startLetter][startNumber], spaces[startLetter - 1][startNumber + 1]) == true)
        {
          possibleMoves = possibleMoves.concat(Integer.toString(startLetter));
          possibleMoves = possibleMoves.concat(Integer.toString(startNumber));
          possibleMoves = possibleMoves.concat(Integer.toString(startLetter - 2));
          possibleMoves = possibleMoves.concat(Integer.toString(startNumber + 2));
          possibleMoves = possibleMoves.concat(" ");
        }
      }
    }
    catch (Exception e1)
    {
      
    }
    
    possibleMoves = possibleMoves.trim();
    
    if (possibleMoves.contains(" "))
    {
      hopMoveFound = true;
      String[] possibleMovesArray = possibleMoves.split("\\s+");
      Random randGen = new Random();
      
      aiChoice = possibleMovesArray[randGen.nextInt(possibleMovesArray.length - 1)];
      
      aiGetPositions(aiChoice);
      checkValidMove();
      checkKing();
    }
    else if (possibleMoves.length() > 3)
    {
      aiChoice = possibleMoves;
      
      // System.out.println(aiChoice);
      
      aiGetPositions(aiChoice);
      checkValidMove();
      checkKing();
    }
    else
    {
      hopMoveFound = false;
    }
  }
  
  
  
  public static void checkersAi()
  {
    
    System.out.println("\nAI is thinking...\n");
    checkValidHops();
    if (hopMoveFound == false)
    {
      validMovePlaces();
    }
    printBoard();
    
  }
  
  
  public static void aiGetPositions(String i) // method that finds the start/end position they picked
  {
    try
    {
      
      while (i.length() > 3)
      {
        startLetter =  Character.getNumericValue(i.charAt(0));
        startNumber =  Character.getNumericValue(i.charAt(1));
        endLetter =  Character.getNumericValue(i.charAt(2));
        endNumber =  Character.getNumericValue(i.charAt(3));
        checkMovement();
        i = i.substring(2);
      }
      
    }
    catch (Exception e)
    {
      System.out.println("Invalid Move Sequence");
    }
  }
  
  public static void getPositions(String i) // method that finds the start/end position they picked
  {
    try
    {
      if (i.matches("^[a-hA-H0-8]+$") && (i.length() % 2) == 0)
      {
        while (i.length() > 3)
        {
          startLetter = rowLegend.indexOf(i.charAt(0));
          startNumber = columnLegend.indexOf(i.charAt(1));
          endLetter = rowLegend.indexOf(i.charAt(2));
          endNumber = columnLegend.indexOf(i.charAt(3));
          checkMovement();
          i = i.substring(2);
        }
      }
      else
      {
        System.out.println("Invalid Move Sequence");
      }
    }
    catch (Exception e)
    {
      System.out.println("Invalid Move Sequence");
    }
  }
  
  public static void sequenceCheck(String i)
  {
    origI = i;
    try
    {
      moveSeq = "";
      if (i.matches("^[a-hA-H0-8]+$") && (i.length() % 2) == 0)
      {
        while (i.length() > 3)
        {
          startLetter = rowLegend.indexOf(i.charAt(0));
          startNumber = columnLegend.indexOf(i.charAt(1));
          endLetter = rowLegend.indexOf(i.charAt(2));
          endNumber = columnLegend.indexOf(i.charAt(3));
          if (Math.abs(startLetter - endLetter) == 1 && Math.abs(startNumber - endNumber) == 1 && spaces[endLetter][endNumber] == ' ')
          {
            moveSeq = moveSeq.concat("1");
          }
          else if (Math.abs(startLetter - endLetter) == 2 && Math.abs(startNumber - endNumber) == 2 && spaces[endLetter][endNumber] == ' ')
          {
            moveSeq = moveSeq.concat("2");
          }
          else
          {
            moveSeq = moveSeq.concat("3");
          }
          i = i.substring(2);
        }
        if (moveSeq.matches("^[1]{1}|[2]+[1]{0}$"))
        {
          getPositions(origI);
        }
        else
        {
          System.out.println("Invalid Move");
        }
      }
    }
    catch (Exception e)
    {
      System.out.println("Invalid Move Sequence");
    }
  }
  
  public static void checkMovement()
  {
    if (playerNum == 1)
    {
      if (spaces[startLetter][startNumber] == 'o')
      {
        checkNormalOne();
      }
      else if (spaces[startLetter][startNumber] == 'k')
      {
        checkKingOne();
      }
    }
    else
    {
      if (spaces[startLetter][startNumber] == '0')
      {
        checkNormalTwo();
      }
      else if (spaces[startLetter][startNumber] == 'K')
      {
        checkKingTwo();
      }
    }
  }
  
  public static void checkNormalOne() // checking player one's regular piece
  {
    if (endLetter == (startLetter - 1) && endNumber == (startNumber + 1) && spaces[endLetter][endNumber] == ' ')
    {
      validMove = true;
      movePiece();
    }
    else if (endLetter == (startLetter - 1) && endNumber == (startNumber - 1) && spaces[endLetter][endNumber] == ' ')
    {
      validMove = true;
      movePiece();
    }
    else if (Math.abs(startLetter - endLetter) == 2 && Math.abs(startNumber - endNumber) == 2 && spaces[endLetter][endNumber] == ' ')
    {
      checkBunnyHop();
    }
    else
    {
      System.out.println("Invalid Move");
    }
  }
  
  public static void checkNormalTwo() // checking player two's regular piece
  {
    if (endLetter == (startLetter + 1) && endNumber == (startNumber + 1) && spaces[endLetter][endNumber] == ' ')
    {
      validMove = true;
      movePiece();
    }
    else if (endLetter == (startLetter + 1) && endNumber == (startNumber - 1) && spaces[endLetter][endNumber] == ' ')
    {
      validMove = true;
      movePiece();
    }
    else if (Math.abs(startLetter - endLetter) == 2 && Math.abs(startNumber - endNumber) == 2 && spaces[endLetter][endNumber] == ' ')
    {
      checkBunnyHop();
    }
    else
    {
      System.out.println("Invalid Move");
    }
  }
  
  public static void checkKingOne() // checking player one's king piece
  {
    if (Math.abs(startLetter - endLetter) == 1 && Math.abs(startNumber - endNumber) == 1 && spaces[endLetter][endNumber] == ' ')
    {
      validMove = true;
      movePiece();
    }
    else if (Math.abs(startLetter - endLetter) == 2 && Math.abs(startNumber - endNumber) == 2 && spaces[endLetter][endNumber] == ' ')
    {
      checkBunnyHop();
    }
    else
    {
      System.out.println("Invalid Move");
    }
  }
  
  public static void checkKingTwo() // checking player two's regular piece
  {
    if (Math.abs(startLetter - endLetter) == 1 && Math.abs(startNumber - endNumber) == 1 && spaces[endLetter][endNumber] == ' ')
    {
      validMove = true;
      movePiece();
    }
    else if (Math.abs(startLetter - endLetter) == 2 && Math.abs(startNumber - endNumber) == 2 && spaces[endLetter][endNumber] == ' ')
    {
      checkBunnyHop();
    }
    else
    {
      System.out.println("Invalid Move");
    }
  }
  
  public static void checkBunnyHop()
  {
    if (endLetter == (startLetter - 2) && endNumber == (startNumber + 2))
    {
      if (hoppingCheck(spaces[startLetter][startNumber],spaces[startLetter - 1][startNumber + 1]) == true)
      {
        validMove = true;
        spaces[startLetter - 1][startNumber + 1] = ' ';
        movePiece();
      }
      else
      {
        System.out.println("Invalid Move");
      } 
    }
    else if (endLetter == (startLetter - 2) && endNumber == (startNumber - 2))
    {
      if (hoppingCheck(spaces[startLetter][startNumber],spaces[startLetter - 1][startNumber - 1]) == true)
      {
        validMove = true;
        spaces[startLetter - 1][startNumber - 1] = ' ';
        movePiece();
      }
      else
      {
        System.out.println("Invalid Move");
      } 
    }
    else if (endLetter == (startLetter + 2) && endNumber == (startNumber - 2))
    {
      if (hoppingCheck(spaces[startLetter][startNumber],spaces[startLetter + 1][startNumber - 1]) == true)
      {
        validMove = true;
        spaces[startLetter + 1][startNumber - 1] = ' ';
        movePiece();
      }
      else
      {
        System.out.println("Invalid Move");
      } 
    }
    else if (endLetter == (startLetter + 2) && endNumber == (startNumber + 2))
    {
      if (hoppingCheck(spaces[startLetter][startNumber],spaces[startLetter + 1][startNumber + 1]) == true)
      {
        validMove = true;
        spaces[startLetter + 1][startNumber + 1] = ' ';
        movePiece();
      }
      else
      {
        System.out.println("Invalid Move");
      } 
    }
    else
    {
      System.out.println("Invalid Move");
    } 
  }
  
  public static boolean hoppingCheck(char hopper, char hopped)
  {
    if (hopper == 'o' && hopped == '0')
    {
      return true;
    }
    else if (hopper == 'o' && hopped == 'K')
    {
      return true;
    }
    else if (hopper == '0' && hopped == 'o')
    {
      return true;
    }
    else if (hopper == '0' && hopped == 'k')
    {
      return true;
    }
    else if (hopper == 'k' && hopped == 'K')
    {
      return true;
    }
    else if (hopper == 'k' && hopped == '0')
    {
      return true;
    }
    else if (hopper == 'K' && hopped == 'k')
    {
      return true;
    }
    else if (hopper == 'K' && hopped == 'o')
    {
      return true;
    }
    else
    {
      return false;
    }
  }
  
  
  public static void checkKing() // checks if player's pieces reached the end, then turns them into king pieces
  {
    for (int c = 0; c < 8; c++)
    {
      if (spaces[0][c] == 'o')
      {
        spaces[0][c] = 'k';
      }
    }
    
    for (int c = 0; c < 8; c++)
    {
      if (spaces[7][c] == '0')
      {
        spaces[7][c] = 'K';
      }
    }
  }
  
  public static void checkIfWinner()
  {
    pieceCount[0] = 0;
    pieceCount[1] = 0;
    
    for (int r = 0; r < 8; r++) // for loop for rows
    {
      for (int c = 0; c < 8; c++) // for loop for columns
      {
        if (spaces[r][c] == 'o' || spaces[r][c] == 'k')
        {
          ++pieceCount[0];
        }
        else if (spaces[r][c] == '0' || spaces[r][c] == 'K')
        {
          ++pieceCount[1];
        }
      }
    }
    
    if (pieceCount[0] == 0)
    {
      // player 2 wins ! :D
      if (aiEnable == true)
      {
        gameEnd = true;
        System.out.println("The AI Wins!");
      }
      else
      {
        gameEnd = true;
        System.out.println("Player 2 Wins!\n");
      }
    }
    else if (pieceCount[1] == 0)
    {
      // player 1 wins ! :D
      gameEnd = true;
      System.out.println("Player 1 Wins!");
    }
    else
    {
      
    }
  }
}
