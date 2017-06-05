class Checkers2
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
      if (playerNum == 1)
      {
        playerNum = 2;
      }
      else
      {
        playerNum = 1;
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
          // System.out.println(startLetter);
          // System.out.println(startNumber);
          // System.out.println(endLetter);
          // System.out.println(endNumber);
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
    }
    else if (endLetter == (startLetter - 2) && endNumber == (startNumber - 2))
    {
      if (hoppingCheck(spaces[startLetter][startNumber],spaces[startLetter - 1][startNumber - 1]) == true)
      {
        validMove = true;
        spaces[startLetter - 1][startNumber - 1] = ' ';
        movePiece();
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
    }
    else if (endLetter == (startLetter + 2) && endNumber == (startNumber + 2))
    {
      if (hoppingCheck(spaces[startLetter][startNumber],spaces[startLetter + 1][startNumber + 1]) == true)
      {
        validMove = true;
        spaces[startLetter + 1][startNumber + 1] = ' ';
        movePiece();
      }
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
    }
    else if (pieceCount[1] == 0)
    {
      // player 1 wins ! :D
    }
    else
    {
      
    }
  }
 
}
