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

import api.Task;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Peter Cappello
 */
public class TaskEuclideanTsp implements Task<List<Integer>>
{
    final private double[][] cities;
    
    public TaskEuclideanTsp( double[][] cities ) { this.cities = cities; }
    
    @Override
    public List<Integer> execute() 
    {
        final int TOUR_SIZE = cities.length;
        List<Integer> intList = new ArrayList<>();
        for ( int i = 1; i < TOUR_SIZE; i++ )
        {
            intList.add( i );
        }
        
        // permutations of [1, n - 1]
        List<List<Integer>> tours = enumPermutations( intList );
        
        // cyclic permutations of  [0, n - 1]
        tours.stream().forEach( tour -> { tour.add( 0, 0 ); } );

        List<Integer> shortestTour = tours.get( 0 );
        double shortestTourDistance = tourDistance( shortestTour );
        for ( List<Integer> tour : tours )
        {
            if ( tourDistance( tour ) < shortestTourDistance )
            {
                shortestTour = tour;
                shortestTourDistance = tourDistance( tour );
            }
        }
        return shortestTour;
    }
    
    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( getClass() );
        stringBuilder.append( "\t Cities:\n\t" );
        for ( int city = 0; city < cities.length; city++ )
        {
            stringBuilder.append( city ).append( ": ");
            stringBuilder.append( cities[ city ][ 0 ] ).append(' ');
            stringBuilder.append( cities[ city ][ 1 ] ).append("\n\t");
        }
        return stringBuilder.toString();
    }
    
   private static List<List<Integer>> enumPermutations( List<Integer> numberList )
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
        Integer n = numberList.remove( 0 );
        
        //  2. solve subproblem
        List<List<Integer>> subPermutationList = enumPermutations( numberList );
        
        //  3. solve problem using subproblem solution
//        subPermutationList.stream().forEach( subPermutation -> 
//        {
//            for( int index = 0; index <= subPermutation.size(); index++ )
//            {
//                ArrayList<Integer> permutation = new ArrayList<>( subPermutation );
//                permutation.add( index, n ); 
//                permutationList.add( permutation );
//            } 
//        });
        //  include only those cyclic permutations where 1 is before 2.
        subPermutationList.stream().forEach( subPermutation -> 
        {
            if ( n != 1 )
                for( int index = 0; index <= subPermutation.size(); index++ )
                {
                    ArrayList<Integer> permutation = new ArrayList<>( subPermutation );
                    permutation.add( index, n ); 
                    permutationList.add( permutation );
                } 
            else 
               for( int index = 0; index < subPermutation.indexOf( 2 ); index++ )
                {
                    ArrayList<Integer> permutation = new ArrayList<>( subPermutation );
                    permutation.add( index, n ); 
                    permutationList.add( permutation );
                } 
        });   
        return permutationList;
    }
   
   private double tourDistance( List<Integer> tour )
   {
       double cost = 0.0;
       for ( int city = 0; city < tour.size() - 1; city ++ )
       {
           cost += distance( cities[ tour.get( city ) ], cities[ tour.get( city + 1 ) ] );
       }
       return cost + distance( cities[ tour.get( tour.size() - 1 ) ], cities[ tour.get( 0 ) ] );
   }
   
   private static double distance( double[] city1, double[] city2 )
   {
       double deltaX = city1[ 0 ] - city2[ 0 ];
       double deltaY = city1[ 1 ] - city2[ 1 ];
       return Math.sqrt( deltaX * deltaX + deltaY * deltaY );
   }
   
   private void printPermutations( List<List<Integer>> permutations )
   {
       int num = 0;
        for ( List<Integer> permutation : permutations )
        {
            System.out.println( ++num + ": " + permutation + " tourDistance: " + tourDistance( permutation ) );
        }
   }
}
