/********************************************************************
Class:     CSCI 470-1
Program:   Assignment 2
Author:    Nick Inserra
Z-number:  z1749082
Date Due:  2/24/16

Purpose:   This program performs the Pearson R correlation on a series
*          of movies and movie ratings.  It reads in data from two files.
* 	       One file contains a list of movie names, the other file
* 		   contains a 2d sparce matrix sperated by semi colons.

Execution:  java hw02.java

Notes:     sparce data as treated as no rating

*********************************************************************/

import java.util.Scanner;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import java.util.List;

public class hw02 {
	
  //wrapper function to close stream
    public static void closeStream(Closeable stream) {
		try 
		{
			if (stream != null) stream.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
   //function to extract non zero reviews
    public static void getReviews(ArrayList<ArrayList<Integer>> movieMatrix, int movieNumber, ArrayList<Integer> movieRev) {
	    for (int i = 0; i < movieMatrix.get(movieNumber).size(); i++)
		   {
		      if (movieMatrix.get(movieNumber).get(i) != 0)   //if it got reviewed
				{
				 movieRev.add(i);
				}
		    }
	}		
	
	
	public static void main (String args[]) {
		
		boolean valid = false;
		boolean quit = false;
		String movieChoice = "";
		String buffer;
		int movieCount = 0;
		
		InputStream inputStream = null;
		InputStreamReader inputReader = null;
		BufferedReader bufferedReader = null;
				
		ArrayList<String> movieNames = new ArrayList<String>();
		ArrayList<ArrayList<Integer>> movieMatrix = new ArrayList<ArrayList<Integer>>();	
		
		
		ArrayList<Integer> firstMovie = new ArrayList<Integer>();
		ArrayList<Integer> secondMovie = new ArrayList<Integer>();
		
		ArrayList<Double> piersonCorrelationR = new ArrayList<Double>();
		//hold common reviews
		ArrayList<Integer> allCommonReviews = new ArrayList<Integer>();
		
			
		//Open movie names file			
			try
			{
			   //open input stream
			   inputStream = new FileInputStream("/home/turing/t90rkf1/d470/dhw/hw2-movies/movie-names.txt");
			
			   //create input stream reader
			   inputReader = new InputStreamReader(inputStream);
			
			   //create new buffered reader
			    bufferedReader = new BufferedReader(inputReader);
			    
			    while ((buffer = bufferedReader.readLine()) != null)
			    {
					movieNames.add(buffer);
				}
		    }
		    
		    catch (IOException e)
			{
				e.printStackTrace();
			}


			 closeStream(inputStream);
			 closeStream(inputReader);
			 closeStream(bufferedReader);

			
		//open matrix file
			try
			{
			   //open input stream
			   inputStream = new FileInputStream("/home/turing/t90rkf1/d470/dhw/hw2-movies/movie-matrix.txt");
			   inputReader = new InputStreamReader(inputStream);
			   bufferedReader = new BufferedReader(inputReader);
			    
			    
			    int count = 0;
			    
			    while ((buffer = bufferedReader.readLine()) != null)
			    {
					
					ArrayList<Integer> tempRow = new ArrayList<Integer>();
					
					 String[] rating = buffer.split(";", -1);  //-1 negative limit argument for trailing delimters
				
				
				  if (count < 3)
	                  {
	                  System.out.println("The size BEFOFE 0000 inserts array is " + rating.length);
	                  System.out.println("The size of the arrayLIST is " + tempRow.size());
				      }
				      
				
				
				//replace nulls with a zero	
					for (int i = 0; i < rating.length; i++)
		              {
		               if (rating[i].isEmpty()) rating[i] = "0";
		               
		               tempRow.add(Integer.parseInt(rating[i]));	
	                  }
	                  
	                  if (count < 3)
	                  {
	                  System.out.println("The size of the array is " + rating.length);
	                  System.out.println("The size of the arrayLIST is " + tempRow.size());
				      }
				      
	                  movieMatrix.add(tempRow);
	                  
	                  
				if (count < 3)
				  {
				   System.out.println("rating 1 = " + rating[0] + " more2 " + rating[1] + " more3 " + rating[2] + " more4 " +rating[3] + "more5 " + rating[4]);
				   System.out.println(rating.length);
			      }
			      count++; 
				}
		    }
		    
		    catch (IOException e)
			{
				e.printStackTrace();
			}

		     

			 closeStream(inputStream);
			 closeStream(inputReader);
			 closeStream(bufferedReader);
			
	while(!quit) 
	{
			valid = false;
			movieCount = 0;
			System.out.println("Please enter a movie number to compare, or q to quit");				
			
			Scanner input = new Scanner(System.in);
		while (valid == false)
		   {
				movieChoice = input.next();
				if(movieChoice.contains("q")) break;
				
				  try {
					    int i = Integer.parseInt(movieChoice);
						if(i > 0 && i < movieNames.size())
						  {
						   valid = true;
						  }
						else
						  {
						System.out.println("Integer not in range must be between 1 and " + movieNames.size());
						valid = false;
						  }
					  }
				   catch (NumberFormatException n)
						{
							System.out.println("NumberFormatException: You must enter an integer");
						}
			}
		     
			int movieNumber = Integer.parseInt(movieChoice);
			movieNumber--;
		
		
		    System.out.println("The first Movie " + movieNames.get(movieNumber));
		
		    System.out.println("movieMatrix stats plain size num of rows " + movieMatrix.size());
		
		    System.out.println("Reviews for movie 1 " + movieMatrix.get(movieNumber).size());
		    

            
		    
		    System.out.println("The ACTUAL number of reviews is " + firstMovie.size());
		    
		    //movieMatrix.get(movieNumber).size()  to do whole file
		for (int movieNumber2 = 0; movieNumber2 < movieMatrix.get(movieNumber).size(); movieNumber2++)
		  {
			int realMovieNumber2 = movieNumber2 + 1;  
			getReviews(movieMatrix, movieNumber, firstMovie); 
		    getReviews(movieMatrix, movieNumber2, secondMovie);
		    System.out.println( "And for comparison movie " + realMovieNumber2 + ": "+ secondMovie.size() );
		    
		    List<Integer> common = new ArrayList<Integer>(firstMovie);
		    common.retainAll(secondMovie); //find all common
		    System.out.println("Number of common reviews " + common.size());
		    allCommonReviews.add(common.size());
     //if not 10 common reviews break loop
     if (common.size() >= 10)
       {
		   movieCount++;   //movieCount must get to 20
		   
		 //common hold subscripts for movieMatrix[Movienumber][common.get()]
		    for (int i = 0; i < common.size(); i++)
		        {   
		            System.out.print(common.get(i) + "  ");
		            if ( i % 8 == 0 ) System.out.println("");
				}
			
			int sum = 0; int sum2 = 0;
			double sd = 0; double sd2 = 0;
			
			//get sume for movie 1 //should make function later	
		    for (int i = 0; i < common.size(); i++)
		       {
					sum += movieMatrix.get(movieNumber).get(common.get(i));
			   }
			 double average =  ( (double)sum / common.size() );
		   for (int i = 0; i < common.size(); i++)
		       {
					sd += Math.pow(movieMatrix.get(movieNumber).get(common.get(i)) - average, 2);
			   }
			sd = Math.sqrt(sd * ( (double)1 / (common.size() - 1)) );
			
	       //get sume for movie 2 //should make function later	
		    for (int i = 0; i < common.size(); i++)
		       {
					sum2 += movieMatrix.get(movieNumber2).get(common.get(i));
			   }
			 double average2 =  ( (double)sum2 / common.size() );
		   for (int i = 0; i < common.size(); i++)
		       {
					sd2 += Math.pow(movieMatrix.get(movieNumber2).get(common.get(i)) - average2, 2);
			   }
			sd2 = Math.sqrt(sd2 * ( (double)1 / (common.size() - 1)) );
			
			double pierR = 0;
			//going for correlation///
			for (int i = 0; i < common.size(); i++)
				{
				 pierR += ( ((movieMatrix.get(movieNumber2).get(common.get(i)) - average2) / sd2) * 
				           ((movieMatrix.get(movieNumber).get(common.get(i))  -  average) /  sd) );
				}
			pierR = ((double)1 / (common.size() - 1) ) * pierR;
			

			piersonCorrelationR.add(pierR);
		}
		else
			{
				piersonCorrelationR.add(-.9999);
			}
			  
		    firstMovie.clear(); secondMovie.clear(); common.clear(); //clear for next time
		  }
		  //end main for loop
		  
		  if (movieCount > 20)
		    {
		   
		  for (int i = 0; i < 20; i++)
			 System.out.print(" PCR " + piersonCorrelationR.get(i));
		  
		  //sort and print to find best movies
		  List<Double> tempR = new ArrayList<Double>(piersonCorrelationR);
		
		System.out.println("Printing the tempR");
		  
		for (int i = 0; i < 20; i++)
		    System.out.print(tempR.get(i) + " ");
		  
		System.out.println("Printing the tempR");
		  
		ArrayList<Integer> top20Index = new ArrayList<Integer>();
		
		//sort the temp and find top 20
			Collections.sort(tempR);
			Collections.reverse(tempR);
		
		for (int i = 0; i < 20; i++)
		    System.out.print(tempR.get(i) + " ");
		
		
		
		for (int i = 0; i < 20; i++)
				top20Index.add(piersonCorrelationR.indexOf(tempR.get(i)));
		   
		System.out.println(" The top 20 indexs are: " );
		for (int i = 0; i < 20; i++)
			{
				System.out.print((top20Index.get(i)+ 1) + " ");
				if (i % 5 == 0) System.out.println();
			}
			
	//print header	
	System.out.println("");
	//System.out.println("No.		R		No.		Reviews			Name ");
	System.out.format("%-8s%14s%8s%15s%40s\n", "Rank", "R value" , "No." , "Reviews", "Movie Name");
	for (int i = 0; i < 20; i++)
	   {
		   System.out.format("%-8d%14f%8d%10d%60s\n", (i+1), piersonCorrelationR.get(top20Index.get(i)), 
		                     top20Index.get(i), allCommonReviews.get(top20Index.get(i)), movieNames.get(top20Index.get(i)));
		   //System.out.print(" " + (i + 1) + ".   			" + piersonCorrelationR.get(top20Index.get(i)) + "	 	" + top20Index.get(i) + "	 " + allCommonReviews.get(top20Index.get(i)) + "  		" + movieNames.get(top20Index.get(i)) + "\n"); 
	   }
	   top20Index.clear(); tempR.clear();
	}  //end if 20 loop
	else System.out.println("Sorry there was not enough movies to compare ");
	
	//clear arrays before doing another
	piersonCorrelationR.clear();
	allCommonReviews.clear();
	
	
			if (movieChoice.contains("quit")) quit = true;
			else System.out.println("the movie choice was " + movieChoice);
		}
		
	}
}

