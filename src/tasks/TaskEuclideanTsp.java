/*
 * The MIT License
 *
 * Copyright 2015 peter.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package tasks;

import util.PermutationEnumerator;
import api.Task;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

/**
 *
 * @author Peter Cappello
 */
public class TaskEuclideanTsp implements Task<List<Integer>>
{
    final static Integer ONE = 1;
    final static Integer TWO = 2;
    
    final private double[][] cities;
    
    public TaskEuclideanTsp( double[][] cities ) { this.cities = cities; }
    
    @Override
    public List<Integer> call() 
    {
        // initial value for shortestTour and its distance.
        List<Integer> partialCityList = initialTour();
        List<Integer> shortestTour = new LinkedList<>( partialCityList );
        shortestTour.add( 0, 0 );
        double shortestTourDistance = tourDistance( cities, shortestTour );
        
        // Use Combinatoricslib-2.1 to generate tour suffixes
//        ICombinatoricsVector<Integer> initialVector = Factory.createVector( partialCityList );
//        Generator<Integer> generator = Factory.createPermutationGenerator(initialVector);
//        for ( ICombinatoricsVector<Integer> tourSuffix : generator ) 
//        {
//            List<Integer> tour = tourSuffix.getVector();
//            tour.add( 0, 0 );
//            if ( tour.indexOf( ONE ) >  tour.indexOf( TWO ) )
//            {
//                continue; // skip tour; it is the reverse of another.
//            }
//            double tourDistance = tourDistance( cities, tour );
//            if ( tourDistance < shortestTourDistance )
//            {
//                shortestTour = tour;
//                shortestTourDistance = tourDistance;
//            }
//        }
        
        // Use my permutation enumerator
        PermutationEnumerator<Integer> permutationEnumerator = new PermutationEnumerator<>( partialCityList );
        for ( List<Integer> subtour = permutationEnumerator.next(); subtour != null; subtour = permutationEnumerator.next() ) 
        {
            List<Integer> tour = new ArrayList<>( subtour );
            tour.add( 0, 0 );
            if ( tour.indexOf( ONE ) >  tour.indexOf( TWO ) )
            {
                continue; // skip tour; it is the reverse of another.
            }
            double tourDistance = tourDistance( cities, tour );
            if ( tourDistance < shortestTourDistance )
            {
                shortestTour = tour;
                shortestTourDistance = tourDistance;
            }
        }
        return shortestTour;
    }
    
    List<Integer> initialTour()
    {
        List<Integer> tour = new LinkedList<>();
        for ( int city = 1; city < cities.length; city++ )
        {
            tour.add( city );
        }
        return tour;
    }
    
    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( getClass() );
        stringBuilder.append( "\n\tCities:\n\t" );
        for ( int city = 0; city < cities.length; city++ )
        {
            stringBuilder.append( city ).append( ": ");
            stringBuilder.append( cities[ city ][ 0 ] ).append(' ');
            stringBuilder.append( cities[ city ][ 1 ] ).append("\n\t");
        }
        return stringBuilder.toString();
    }
    
   private double tourDistance( double[][] cities, final List<Integer> tour  )
   {
       double cost = 0.0;
       for ( int city = 0; city < tour.size() - 1; city ++ )
       {
           cost += distance( cities[ tour.get( city ) ], cities[ tour.get( city + 1 ) ] );
       }
       return cost + distance( cities[ tour.get( tour.size() - 1 ) ], cities[ tour.get( 0 ) ] );
   }
   
   private static double distance( final double[] city1, final double[] city2 )
   {
       final double deltaX = city1[ 0 ] - city2[ 0 ];
       final double deltaY = city1[ 1 ] - city2[ 1 ];
       return Math.sqrt( deltaX * deltaX + deltaY * deltaY );
   }
   
   private static List<List<Integer>> enumeratePermutations( List<Integer> numberList )
  {
      List<List<Integer>> permutationList = new ArrayList<>();
      
       // Base case
      if( numberList.isEmpty() )
       {
           permutationList.add( new ArrayList<>() );
           return permutationList;
       }
       
       // Inductive case
       //  1. create subproblem
       final Integer n = numberList.remove( 0 );
       
       //  2. solve subproblem
       final List<List<Integer>> subPermutationList = enumeratePermutations( numberList );
       
       //  3. solve problem using subproblem solution
       subPermutationList.stream().forEach( subPermutation -> 
       {            
           //  if p is a cyclic permutation, omit reverse(p): 1 always occurs before 2 in p.
           if ( ! n.equals( ONE ) )
               for( int index = 0; index <= subPermutation.size(); index++ )
                   permutationList.add( addElement( subPermutation, index, n ) );
           else 
              for( int index = 0; index < subPermutation.indexOf( TWO ); index++ )
                   permutationList.add( addElement( subPermutation, index, n ) );
       });   
       return permutationList;
   }
  
  private static List<Integer> addElement( final List<Integer> subPermutation, final int index, final Integer n )
  {
       List<Integer> permutation = new ArrayList<>( subPermutation );
       permutation.add( index, n );
       return permutation;
  }
}
