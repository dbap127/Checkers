class Checkers
{
  public static char[][] spaces = new char[8][8]; // 2d array for holding where the game pieces are
  public static String rowLegend = "12345678"; // row number definitions
  public static String columnLegend = "abcdefgh"; // column letter definitions
  
  public static void main(String[] args)
  {
    setupBoard();
    printBoard();
  }
  
  public static void setupBoard() // method to place the game pieces at the start of the game
  {
    for (int i = 0; i < 8; i++) // for loop for rows
    {
      for (int c = 0; c < 8; c++) // for loop for columns 
      {
        if (i == 0) // placing the pieces based on first row
        {
          if ((c % 2) == 1)
          {
            spaces[i][c] = '0';
          }
          else
          {
            spaces[i][c] = ' ';
          }
        }
        else if (i == 1) // placing the pieces based on second row
        {
          if ((c % 2) == 0)
          {
            spaces[i][c] = '0';
          }
          else
          {
            spaces[i][c] = ' ';
          }
        }
        else if (i == 2) // placing the pieces based on third row
        {
          if ((c % 2) == 1)
          {
            spaces[i][c] = '0';
          }
          else
          {
            spaces[i][c] = ' ';
          }
        }
        else if (i == 5) // placing the pieces based on sixth row
        {
          if ((c % 2) == 0)
          {
            spaces[i][c] = 'o';
          }
          else
          {
            spaces[i][c] = ' ';
          }
        }
        else if (i == 6) // placing the pieces based on seventh row
        {
          if ((c % 2) == 1)
          {
            spaces[i][c] = 'o';
          }
          else
          {
            spaces[i][c] = ' ';
          }
        }
        else if (i == 7) // placing the pieces based on eighth row
        {
          if ((c % 2) == 0)
          {
            spaces[i][c] = 'o';
          }
          else
          {
            spaces[i][c] = ' ';
          }
        }
        else
        {
          spaces[i][c] = ' ';
        }
        
      }
    }
  }
  
  
  public static void printBoard()
  {
    System.out.print("   ");
    for (int b = 0; b < 8; b++)
    {
      System.out.print(columnLegend.charAt(b) + "  ");
    }
    
    System.out.println("");
    
    for (int i = 0; i < 8; i++)
    {
      System.out.print(rowLegend.charAt(i) + " ");
      
      for (int c = 0; c < 8; c++)
      {
        System.out.print("[" + spaces[i][c] + "]");
      }
      
      System.out.println("");
    }
    
  }

}