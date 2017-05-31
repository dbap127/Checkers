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
  
  
  public static void main(String[] args)
  {
    setupBoard();
    printBoard();
    while (!(true == false))
    {
      System.out.println("Player " + playerNum + "'s turn");
      moveInput = In.getString();
      getPositions(moveInput);
      movePiece();
      printBoard();
    }
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
    if (playerNum == 1)
    {   
      if (spaces[startLetter][startNumber] == '0')
      {
        // check what row (0, 7 or 2 - 6)
        // if 0
        
        
        
        
        spaces[startLetter][startNumber] = ' ';
        spaces[endLetter][endNumber] = '0';
        playerNum = 2;
      }
    }
    else
    {
      if (spaces[startLetter][startNumber] == 'o')
      {
        spaces[startLetter][startNumber] = ' ';
        spaces[endLetter][endNumber] = 'o';
        playerNum = 1;
      }
    }
    
  }
  
  
  public static void getPositions(String i) // method that finds the start/end position they picked
  {
    
    String[] inputSplit = i.split("\\s+");
    startLetter = rowLegend.indexOf(inputSplit[0].charAt(0));
    startNumber = columnLegend.indexOf(inputSplit[0].charAt(1));
    endLetter = rowLegend.indexOf(inputSplit[1].charAt(0));
    endNumber = columnLegend.indexOf(inputSplit[1].charAt(1));
    
  }
  
  
  
}
