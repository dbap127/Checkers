/*
 * Denzel Baptiste
 * ICS3U
 * June 14th, 2017
 * 
 * Checkers Game For Summative
 * 
 * 
 */


import java.util.Random;

class Checkers
{
  
  // spaces[startLetter][startNumber] and spaces[endLetter][endNumber] mean the location of each input like spaces[row][column] 
  
  
  public static char[][] spaces = new char[8][8]; // 2d array for holding where the game pieces are
  public static String rowLegend = "abcdefgh"; // row number definitions
  public static String columnLegend = "12345678"; // column letter definitions
  public static String moveInput = ""; // gets player(s) move input
  public static int playerNum = 1; // player's turn
  public static int startLetter; // start row position for piece movement
  public static int startNumber; // start column position for piece movement
  public static int endLetter; // end row position for piece movement
  public static int endNumber; // end column position for piece movement
  public static boolean validMove = false; // checking for valid moves
  public static int[] pieceCount = new int[2]; // holds amount of pieces of each player
  public static String moveSeq = ""; // detecting what "multi" moves are happening
  public static String origI; // grabs original input
  public static boolean gameEnd = true; // checking if the game ended
  public static int menuChoice; // handles menu choices
  public static boolean aiEnable = false; // detects if its pvp or pvai
  public static String pieceLocationString = ""; // handles ai finding location of pieces
  public static String pieceTypesString = ""; // handles piece types of ai
  public static String possibleMoves = ""; // handles possible moves an ai can move too
  public static String aiChoice = ""; // where the ai choice is inputted
  public static boolean hopMoveFound = false;
  
  public static void main(String[] args) // main method handles other methods (menu choices, rules, gameplay, forfeiting, etc)
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
            if (moveInput.equals("forfeit"))
            {
              if (playerNum == 1)
              {
                for (int r = 0; r < 8; r++) // for loop for rows
                {
                  for (int c = 0; c < 8; c++) // for loop for columns
                  {
                    if (spaces[r][c] == 'o' || spaces[r][c] == 'k')
                    {
                      spaces[r][c] = 'f';
                    }
                  }
                }
                System.out.println("Player 1 Forfeits.");
                checkIfWinner();
              }
              else
              {
                for (int r = 0; r < 8; r++) // for loop for rows
                {
                  for (int c = 0; c < 8; c++) // for loop for columns
                  {
                    if (spaces[r][c] == 'o' || spaces[r][c] == 'k')
                    {
                      spaces[r][c] = 'f';
                    }
                  }
                } 
                System.out.println("Player 2 Forfeits.");
                checkIfWinner();
              }
            }
            
            
            else
            {
              sequenceCheck(moveInput);
              checkValidMove();
              checkKing();
              printBoard();
            }
          }
        }
        else if (menuChoice == 2)
        {
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
              if (moveInput.equals("forfeit"))
              {
                if (playerNum == 1)
                {
                  for (int r = 0; r < 8; r++) // for loop for rows
                  {
                    for (int c = 0; c < 8; c++) // for loop for columns
                    {
                      if (spaces[r][c] == 'o' || spaces[r][c] == 'k')
                      {
                        spaces[r][c] = 'f';
                      }
                    }
                  }
                  System.out.println("Player 1 Forfeits.");
                  checkIfWinner();
                }
              }
              else
              {
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
  
  
  public static void menuChoices() // method that displays the menu choices
  {
    System.out.println("Welcome to Checkers.");
    System.out.println("Press 1 for Player vs Player");
    System.out.println("Press 2 for Player vs AI");
    System.out.println("Press 3 for Checkers Rules (Highly Recommended)");
    System.out.println("Press 4 for Learning How To Move (Highly Recommended)");
  }
  
  public static void howToMove() // method that displays how to move a piece
  {
    System.out.println("In this version, you have to first type in the location of the");
    System.out.println("piece you want to move (ex: f1), then input the place of where");
    System.out.println("you want to go (ex: e2) so the full input would be \"f1e2\".");
    System.out.println("To hop, you have to input the place of where you want to hop after.");
    System.out.println("To multi-hop you have to input each location of where you hop in order.");
    System.out.println("For example \"f1d3b1\"");
    System.out.println("If you can no longer move, you have lost and \n you must type in \"Forfeit\" to end the game.\n\n");
  }
  
  public static void rules() // method that displays the rules of checkers
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
      System.out.print(columnLegend.charAt(c) + "  "); // prints number legend at top
    }
    
    System.out.println("");
    
    for (int r = 0; r < 8; r++) 
    {
      System.out.print(rowLegend.charAt(r) + " "); // prints letter legend at top
      
      for (int c = 0; c < 8; c++)
      {
        System.out.print("[" + spaces[r][c] + "]"); // prints piece with square brackets around
      }
      
      System.out.println("");
    }
  }
  
  public static void movePiece() // method that changes the positions of the pieces
  {
    spaces[endLetter][endNumber] = spaces[startLetter][startNumber]; 
    spaces[startLetter][startNumber] = ' ';
  }
  
  public static void checkValidMove() // checks if the move was valid then change the player number
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
    pieceLocationString = ""; // resets variable
    pieceTypesString = ""; // resets variable
    possibleMoves = ""; // resets variable
    
    for (int r = 0; r < 8; r++) // for loop for rows
    {
      for (int c = 0; c < 8; c++) // for loop for columns
      {
        if (spaces[r][c] == '0' || spaces[r][c] == 'K') // checks board which pieces are owned by ai
        {
          pieceLocationString = pieceLocationString.concat(Character.toString(rowLegend.charAt(r))); // adds row location of pieces
          pieceLocationString = pieceLocationString.concat(Character.toString(columnLegend.charAt(c))); // adds column locations of pieces
          pieceLocationString = pieceLocationString.concat(" "); // adds space to seperate locations 
          
          pieceTypesString = pieceTypesString.concat(Character.toString(spaces[r][c])); // gets the piece type 
          pieceTypesString = pieceTypesString.concat(" "); // adds space to seperate piece types
        }
      }
    }
    
    pieceLocationString = pieceLocationString.trim();
    pieceTypesString = pieceTypesString.trim();
    
    if (pieceLocationString.contains(" "))
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
    
    possibleMoves = possibleMoves.trim();
    
    if (possibleMoves.contains(" "))
    {
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
      String[] pieceTypesArray = pieceTypesString.split("\\s+");
      
      for (int i = 0; i < pieceLocationArray.length; i++)
      {
        startLetter = rowLegend.indexOf(pieceLocationArray[i].substring(0,1));
        startNumber = columnLegend.indexOf(pieceLocationArray[i].substring(1,2));
        
        if (pieceTypesArray[i].equals("0"))
        {
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
          
        }
        else
        {
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
        
        
      }
      else if (pieceTypesArray[0].equals("K"))
      {
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
        try
        {
          if (spaces[startLetter - 2][startNumber + 2] == ' ')
          {
            if (hoppingCheck(spaces[startLetter][startNumber], spaces[startLetter - 1][startNumber + 1]) == true)
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
  
  
  
  public static void checkersAi() // method that calls other methods to handle the ai movement
  {
    
    System.out.println("\nAI is thinking...\n");
    checkValidHops(); // checks for valid hops 
    if (hopMoveFound == false) // if no valid hops
    {
      validMovePlaces(); // check for valid places to move
    }
    printBoard();
    
  }
  
  
  public static void aiGetPositions(String i) // method that finds the start/end position the ai picked
  {
    try
    {
      
      while (i.length() > 3)
      {
        startLetter =  Character.getNumericValue(i.charAt(0)); // Character.getNumericaValue convert a char to integer
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
      if (i.matches("^[a-hA-H0-8]+$") && (i.length() % 2) == 0) // detects if input only contains a to h and 0 to 8
      {
        while (i.length() > 3) // for multi hops, keep checking inputs
        {
          startLetter = rowLegend.indexOf(i.charAt(0)); // converts letter to proper move input syntax
          startNumber = columnLegend.indexOf(i.charAt(1)); // converts number to proper move input syntax
          endLetter = rowLegend.indexOf(i.charAt(2)); // converts letter to proper move input syntax
          endNumber = columnLegend.indexOf(i.charAt(3)); // converts letter to proper move input syntax
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
  
  public static void sequenceCheck(String i) // method that detects if multi movements or single movements are valid
  {
    origI = i; // hold original input before i gets modified
    try
    {
      moveSeq = "";
      if (i.matches("^[a-hA-H0-8]+$") && (i.length() % 2) == 0)  // detects if input only contains a to h and 0 to 8
      {
        while (i.length() > 3) // for multi hops, keep checking inputs
        {
          startLetter = rowLegend.indexOf(i.charAt(0)); // converts letter to proper move input syntax
          startNumber = columnLegend.indexOf(i.charAt(1)); // converts number to proper move input syntax
          endLetter = rowLegend.indexOf(i.charAt(2)); // converts letter to proper move input syntax
          endNumber = columnLegend.indexOf(i.charAt(3)); // converts letter to proper move input syntax
          if (Math.abs(startLetter - endLetter) == 1 && Math.abs(startNumber - endNumber) == 1 && spaces[endLetter][endNumber] == ' ') // checking if its a single move
          {
            moveSeq = moveSeq.concat("1");
          }
          else if (Math.abs(startLetter - endLetter) == 2 && Math.abs(startNumber - endNumber) == 2 && spaces[endLetter][endNumber] == ' ') // checking if its a hopping move
          {
            moveSeq = moveSeq.concat("2");
          }
          else
          {
            moveSeq = moveSeq.concat("3"); // if its an invalid move
          }
          i = i.substring(2);
        }
        if (moveSeq.matches("^[1]{1}|[2]+[1]{0}$")) // checking if its valid, if its 1 "1" or only multiple "2"
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
  
  public static void checkMovement() // checks which player and whiche piece is being moved
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
    if (endLetter == (startLetter - 1) && endNumber == (startNumber + 1) && spaces[endLetter][endNumber] == ' ') // if placement is valid 
    {
      validMove = true;
      movePiece();
    }
    else if (endLetter == (startLetter - 1) && endNumber == (startNumber - 1) && spaces[endLetter][endNumber] == ' ') // if placement is valid 
    {
      validMove = true;
      movePiece();
    }
    else if (Math.abs(startLetter - endLetter) == 2 && Math.abs(startNumber - endNumber) == 2 && spaces[endLetter][endNumber] == ' ') // check if its a valid hop 
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
    if (endLetter == (startLetter + 1) && endNumber == (startNumber + 1) && spaces[endLetter][endNumber] == ' ') // if placement is valid  
    {
      validMove = true;
      movePiece();
    }
    else if (endLetter == (startLetter + 1) && endNumber == (startNumber - 1) && spaces[endLetter][endNumber] == ' ') // if placement is valid  
    {
      validMove = true;
      movePiece();
    }
    else if (Math.abs(startLetter - endLetter) == 2 && Math.abs(startNumber - endNumber) == 2 && spaces[endLetter][endNumber] == ' ') // check if its a valid hop  
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
    if (Math.abs(startLetter - endLetter) == 1 && Math.abs(startNumber - endNumber) == 1 && spaces[endLetter][endNumber] == ' ') // if placement is valid  
    {
      validMove = true;
      movePiece();
    }
    else if (Math.abs(startLetter - endLetter) == 2 && Math.abs(startNumber - endNumber) == 2 && spaces[endLetter][endNumber] == ' ') // check if its a valid hop  
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
    if (Math.abs(startLetter - endLetter) == 1 && Math.abs(startNumber - endNumber) == 1 && spaces[endLetter][endNumber] == ' ') // if placement is valid 
    {
      validMove = true;
      movePiece();
    }
    else if (Math.abs(startLetter - endLetter) == 2 && Math.abs(startNumber - endNumber) == 2 && spaces[endLetter][endNumber] == ' ') // check if its a valid hop  
    {
      checkBunnyHop();
    }
    else
    {
      System.out.println("Invalid Move");
    }
  }
  
  public static void checkBunnyHop() // method that checks if the hop movement is valid
  {
    if (endLetter == (startLetter - 2) && endNumber == (startNumber + 2))
    {
      if (hoppingCheck(spaces[startLetter][startNumber],spaces[startLetter - 1][startNumber + 1]) == true) // if right pieces are hop/being hopped
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
      if (hoppingCheck(spaces[startLetter][startNumber],spaces[startLetter - 1][startNumber - 1]) == true) // if right pieces are hop/being hopped 
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
      if (hoppingCheck(spaces[startLetter][startNumber],spaces[startLetter + 1][startNumber - 1]) == true) // if right pieces are hop/being hopped 
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
      if (hoppingCheck(spaces[startLetter][startNumber],spaces[startLetter + 1][startNumber + 1]) == true) // if right pieces are hop/being hopped
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
  
  public static boolean hoppingCheck(char hopper, char hopped) // method that checks if the piece is hopping the correct piece(s)
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
  
  public static void checkIfWinner() // counts the pieces of each players and see if one has none 
  {
    pieceCount[0] = 0;
    pieceCount[1] = 0;
    
    for (int r = 0; r < 8; r++) // for loop for rows
    {
      for (int c = 0; c < 8; c++) // for loop for columns
      {
        if (spaces[r][c] == 'o' || spaces[r][c] == 'k') // adds player 1 pieces to count
        {
          ++pieceCount[0];
        }
        else if (spaces[r][c] == '0' || spaces[r][c] == 'K') // adds player 2 pieces to count
        {
          ++pieceCount[1];
        }
      }
    }
    
    if (pieceCount[0] == 0) // if player 1 has no pieces 
    {
      // player 2 wins ! :D
      if (aiEnable == true) // if the ai was enabled
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
    else if (pieceCount[1] == 0) // if player 2 has no pieces 
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
