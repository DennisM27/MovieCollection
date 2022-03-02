import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MovieCollection
{
  private ArrayList<Movie> movies;
  private Scanner scanner;

  public MovieCollection(String fileName)
  {
    importMovieList(fileName);
    scanner = new Scanner(System.in);
  }

  public ArrayList<Movie> getMovies()
  {
    return movies;
  }
  
  public void menu()
  {
    String menuOption = "";
    
    System.out.println("Welcome to the movie collection!");
    System.out.println("Total: " + movies.size() + " movies");
    
    while (!menuOption.equals("q"))
    {
      System.out.println("------------ Main Menu ----------");
      System.out.println("- search (t)itles");
      System.out.println("- search (k)eywords");
      System.out.println("- search (c)ast");
      System.out.println("- see all movies of a (g)enre");
      System.out.println("- list top 50 (r)ated movies");
      System.out.println("- list top 50 (h)igest revenue movies");
      System.out.println("- (q)uit");
      System.out.print("Enter choice: ");
      menuOption = scanner.nextLine();
      
      if (!menuOption.equals("q"))
      {
        processOption(menuOption);
      }
    }
  }
  
  private void processOption(String option)
  {
    if (option.equals("t"))
    {
      searchTitles();
    }
    else if (option.equals("c"))
    {
      searchCast();
    }
    else if (option.equals("k"))
    {
      searchKeywords();
    }
    else if (option.equals("g"))
    {
      listGenres();
    }
    else if (option.equals("r"))
    {
      listHighestRated();
    }
    else if (option.equals("h"))
    {
      listHighestRevenue();
    }
    else
    {
      System.out.println("Invalid choice!");
    }
  }

  private void searchTitles()
  {
    System.out.print("Enter a title search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String movieTitle = movies.get(i).getTitle();
      movieTitle = movieTitle.toLowerCase();

      if (movieTitle.indexOf(searchTerm) != -1)
      {
        //add the Movie object to the results list
        results.add(movies.get(i));
      }
    }

    // sort the results by title
    sortResults(results);

    // now, display them all to the user
    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void sortResults(ArrayList<Movie> listToSort)
  {
    for (int j = 1; j < listToSort.size(); j++)
    {
      Movie temp = listToSort.get(j);
      String tempTitle = temp.getTitle();

      int possibleIndex = j;
      while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
      {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }
  
  private void displayMovieInfo(Movie movie)
  {
    System.out.println();
    System.out.println("Title: " + movie.getTitle());
    System.out.println("Tagline: " + movie.getTagline());
    System.out.println("Runtime: " + movie.getRuntime() + " minutes");
    System.out.println("Year: " + movie.getYear());
    System.out.println("Directed by: " + movie.getDirector());
    System.out.println("Cast: " + movie.getCast());
    System.out.println("Overview: " + movie.getOverview());
    System.out.println("User rating: " + movie.getUserRating());
    System.out.println("Box office revenue: " + movie.getRevenue());
  }
  
  private void searchCast()
  {
    System.out.print("Enter a search term here: ");
    String searchCast = scanner.nextLine();

    ArrayList<String> actors = new ArrayList<>();
    ArrayList<String> found = new ArrayList<>();

    for (Movie movie : movies) {
      String[] castMembers = movie.getCast().split("\\|");
      for (String cast : castMembers)
      {
        boolean isDup = false;
        for (String actor : actors)
        {
          if (actor.equals(cast))
          {
            isDup = true;
          }
        }
        if (!isDup)
        {
          actors.add(cast);
        }
      }
    }

    for (String actor : actors)
    {
      if (actor.toLowerCase().contains(searchCast))
      {
        found.add(actor);
      }
    }

    for (int i = 0; i < found.size(); i++)
    {
      java.util.Collections.sort(found);
      System.out.println(found.get(i));
    }

  }

  private void searchKeywords()
  {
    System.out.print("Enter a keyword search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String movieKeyword = movies.get(i).getKeywords();
      movieKeyword = movieKeyword.toLowerCase();

      if (movieKeyword.indexOf(searchTerm) != -1)
      {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    // sort the results by title
    sortResults(results);

    // now, display them all to the user
    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }
  
  private void listGenres()
  {
    ArrayList<String> allGenres = new ArrayList<>();

    for (int i = 0; i < movies.size(); i++) {
      String[] genres = movies.get(i).getGenres().split("\\|");
      for (String movieGenre : genres) {
        boolean isDuplicate = false;
        for (String genre : allGenres) {
          if (movieGenre.equals(genre)) {
            isDuplicate = true;
          }
        }
        if (!isDuplicate) {
          allGenres.add(movieGenre);
        }
      }
    }

    // sort list
    printList(allGenres);

    System.out.print("Enter a genre number: ");
    String searchTerm = scanner.nextLine().toLowerCase();

    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String movieGenre = movies.get(i).getGenres();
      movieGenre = movieGenre.toLowerCase();

      if (movieGenre.indexOf(searchTerm) != -1)
      {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    // sort the results by title
    sortResults(results);

    // now, display them all to the user
    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void listHighestRated()
  {
    ArrayList<Movie> movieList = new ArrayList<>();

    for (int i = 0; i < movies.size(); i++) {
      double rating = movies.get(i).getUserRating(); //
      boolean ratingIsBiggest = true;

      for (int k = i + 1; k < movies.size(); k++) {
        double anotherRating = movies.get(k).getUserRating(); //
        if (anotherRating > rating) {
          ratingIsBiggest = false;
        }
      }

      if (movieList.size() >= 50) {
        break;
      } else if (ratingIsBiggest) {
        movieList.add(movies.get(i));
      }
    }

    printMovieList(movieList);
  }

  private void listHighestRevenue()
  {
    ArrayList<Movie> movieList = new ArrayList<>();

    for (int i = 0; i < movies.size(); i++) {
      double revenue = movies.get(i).getRevenue(); //
      boolean biggest = true;

      for (int k = i + 1; k < movies.size(); k++) {
        double anotherRevenue = movies.get(k).getRevenue(); //
        if (anotherRevenue > revenue) {
          biggest = false;
        }
      }

      if (movieList.size() >= 50) {
        break;
      } else if (biggest) {
        movieList.add(movies.get(i));
      }
    }

    printMovieList(movieList);
  }

  private void importMovieList(String fileName)
  {
    try
    {
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line = bufferedReader.readLine();

      movies = new ArrayList<Movie>();

      while ((line = bufferedReader.readLine()) != null)
      {
        // import all cells for a single row as an array of Strings,
        // then convert to ints as needed
        String[] moviesFromCSV = line.split(",");

        // pull out the data for this cereal
        String title = moviesFromCSV[0];
        String cast = moviesFromCSV[1];
        String director = moviesFromCSV[2];
        String tagline = moviesFromCSV[3];
        String keywords = moviesFromCSV[4];
        String overview = moviesFromCSV[5];
        int runtime = Integer.parseInt(moviesFromCSV[6]);
        String genres = moviesFromCSV[7];
        double userRating = Double.parseDouble(moviesFromCSV[8]);
        int year = Integer.parseInt(moviesFromCSV[9]);
        int revenue = Integer.parseInt(moviesFromCSV[10]);

        // create Cereal object to store values
        Movie nextMovies = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);

        // adding Cereal object to the arraylist
        movies.add(nextMovies);
      }
      bufferedReader.close();
    }
    catch(IOException exception)
    {
      // Print out the exception that occurred
      System.out.println("Unable to access " + exception.getMessage());
    }
  }
  
  // ADD ANY ADDITIONAL PRIVATE HELPER METHODS you deem necessary

  private void printList(ArrayList<String> list) {
    for (int i = 0; i < list.size(); i++) {
      System.out.println(i + 1+". "+list.get(i));
    }
  }

  private void printMovieList(ArrayList<Movie> list) {
    for (int i = 0; i < list.size(); i++) {
      System.out.println(i + 1+". "+list.get(i).toString());
    }
  }
}