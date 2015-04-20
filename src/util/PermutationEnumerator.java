/*
 * The MIT License
 *
 * Copyright 2015 cappello.
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
package util;

import java.util.ArrayList;
import java.util.List;
//import org.paukov.combinatorics.Factory;
//import org.paukov.combinatorics.Generator;
//import org.paukov.combinatorics.ICombinatoricsVector;

/**
 *
 * @author Pete Cappello
 * @param <T> the type of objectList being permuted.
 */
public class PermutationEnumerator<T> 
{
    private PermutationEnumerator<T> subPermutationEnumerator;
    private List<T> permutation;
    private List<T> subpermutation;
    private int nextIndex = 0;
    private T interleaveObject;
    
    final static Integer ONE = 1;
    final static Integer TWO = 2;
    
    /**
     *
     * @param objectList the objectList being permuted is unmodified.
     * @throws java.lang.IllegalArgumentException when passed a null object list.
     */
    public PermutationEnumerator( final List<T> objectList ) throws IllegalArgumentException
    {
        if ( objectList == null )
        {
            throw new IllegalArgumentException();
        }
        permutation = new ArrayList<>( objectList );
        if ( permutation.isEmpty() )
        { 
            return; 
        }
        subpermutation = new ArrayList<>( permutation );
        interleaveObject = subpermutation.remove( 0 );
        subPermutationEnumerator = new PermutationEnumerator<>( subpermutation );
        subpermutation = subPermutationEnumerator.next();
    }
    
    /**
     * Enumerates the permutations of a List of Integer objectList.
     * Application: Guide the permutation a List or array of objectList.
     * @param integerList - the list of Integer objectList to be permuted.
     * @return List of permutations, each represented as a List of Integer.
     * If p is such a permutation, then reverse(p) is omitted from returned List.
     */
    public List<List<Integer>> enumerate( List<Integer> integerList )
    {
        List<List<Integer>> permutationList = new ArrayList<>();

         // Base case
        if( integerList.isEmpty() )
         {
             permutationList.add( new ArrayList<>() );
             return permutationList;
         }

         // Inductive case
         //  1. create subproblem
         final Integer n = integerList.remove( 0 );

         //  2. solve subproblem
         final List<List<Integer>> subPermutationList = enumerate( integerList );

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
    
    /**
     * Produce the permutation permutation.
     * @return the permutation permutation as a List.
     * If none, returns null.
     * @throws java.lang.IllegalArgumentException  permutation() invoked when hasNext() is false.
     */
    public List<T> next() throws IllegalArgumentException
    {
        if ( permutation == null )
        {
            return null;
        }
        List<T> returnValue = new ArrayList<>( permutation );
        if ( permutation.isEmpty() )
        {
            permutation = null;
        }
        else if ( nextIndex < permutation.size() - 1)
        {
            T temp = permutation.get( nextIndex + 1 );
            permutation.set( nextIndex + 1, permutation.get( nextIndex ) );
            permutation.set( nextIndex++, temp );
        }
        else
        {   
            subpermutation = subPermutationEnumerator.next();
            if ( subpermutation == null || subpermutation.isEmpty() )
            {
                permutation = null;
            }
            else
            {
                permutation = new ArrayList<>( subpermutation );
                permutation.add( 0, interleaveObject );                
                nextIndex = 0;
            }
        }
        return returnValue;
    }
}
