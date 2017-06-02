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
  
  public static void main(String[] args)
  {
    setupBoard();
    printBoard();
    while (!(true == false))
    {
      System.out.println("Player " + playerNum + "'s turn");
      moveInput = In.getString();
      moveInput = moveInput.replaceAll("\\s+","");
      getPositions(moveInput);
      checkKing();
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
      spaces[endLetter][endNumber] = spaces[startLetter][startNumber];
      spaces[startLetter][startNumber] = ' ';
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
          System.out.println(startLetter);
          System.out.println(startNumber);
          System.out.println(endLetter);
          System.out.println(endNumber);
          checkMovement();
          i = i.substring(2); 
        }
      }
      else
      {
        System.out.println("Invalid Move");
      }
    }
    catch (Exception e)
    {
      System.out.println("Invalid Move");
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
    if (validMove == true)
    {
      validMove = false;
    }
    else
    {
      System.out.println("Invalid Move");
    }
  }
  
  public static void checkNormalOne() // checking player one's regular piece
  {
    if (startNumber == 0) // detecting if the piece selected is on the "1" column
    {
      if (endLetter == (startLetter - 1) && endNumber == (startNumber + 1) && spaces[endLetter][endNumber] == ' ')
      {
        validMove = true;
        movePiece();
        playerNum = 2;
      }
    }  
    else if (startNumber == 7) // detecting if the piece selected is on the "8" column
    {
      if (endLetter == (startLetter - 1) && endNumber == (startNumber - 1) && spaces[endLetter][endNumber] == ' ')
      {
        validMove = true;
        movePiece();
        playerNum = 2;
      }
    }  
    else // detecting if the piece is between 2 - 7
    {
      if (endLetter == (startLetter - 1) && endNumber == (startNumber - 1) && spaces[endLetter][endNumber] == ' ')
      {
        validMove = true;
        movePiece();
        playerNum = 2;
      }
      else if (endLetter == (startLetter - 1) && endNumber == (startNumber + 1) && spaces[endLetter][endNumber] == ' ')
      {
        validMove = true;
        movePiece();
        playerNum = 2;
      }
    }
  }
  
  public static void checkNormalTwo() // checking player two's regular piece
  {
    if (startNumber == 0) // detecting if piece is on the "1" column
    {
      if (endLetter == (startLetter + 1) && endNumber == (startNumber + 1) && spaces[endLetter][endNumber] == ' ')
      {
        validMove = true;
        movePiece();
        playerNum = 1;
      }
    }  
    
    
    else if (startNumber == 7) // detecting if the piece selected is on the "8" column
    {
      if (endLetter == (startLetter + 1) && endNumber == (startNumber - 1) && spaces[endLetter][endNumber] == ' ')
      {
        validMove = true;
        movePiece();
        playerNum = 1;
      }
    }
    else // detecting if the piece is between 2 - 7
    {
      if (endLetter == (startLetter + 1) && endNumber == (startNumber - 1) && spaces[endLetter][endNumber] == ' ')
      {
        validMove = true;
        movePiece();
        playerNum = 1;
      }
      else if (endLetter == (startLetter + 1) && endNumber == (startNumber + 1) && spaces[endLetter][endNumber] == ' ')
      {
        validMove = true;
        movePiece();
        playerNum = 1;
      }
    }
  }
  
  public static void checkKingOne() // checking player one's king piece
  {
  
  }
  
  public static void checkKingTwo() // checking player two's regular piece
  {
  
  }
 
  public static void checkBunnyHop()
  {
    
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
        else
        {
          // carry on : ^ )
        }
      }
    }
    if (pieceCount[0] == 0)
    {
      // player 2 wins ! :D
    }
    else if (pieceCount[1] == 0)
    {
      // player 1 wins ! :D
    }
    else
    {
      // carry on : ^ ) 
    }
  }
  
}
