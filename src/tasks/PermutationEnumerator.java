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
package tasks;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Pete Cappello
 * @param <T> the type of objectList being permuted.
 */
public class PermutationEnumerator<T> 
{
    private PermutationEnumerator subPermutationEnumerator;
    private List<T> nextPermutation;
    private List<T> nextSubpermutation;
    private int nextIndex = 0;
    private T interleaveObject;
    
    final static Integer ONE = 1;
    final static Integer TWO = 2;
    
    /**
     *
     * @param objectList the objectList being permuted.
     * @throws java.lang.Exception
     */
    public PermutationEnumerator( final List<T> objectList ) throws Exception
    {
        if ( objectList == null )
        {
            throw new Exception();
        }
        List<T> objectSublist = new LinkedList<>( objectList );
        if ( objectList.isEmpty() )
        {
            nextPermutation = objectSublist;
        }
        else
        {
            interleaveObject = objectSublist.remove( 0 );
            subPermutationEnumerator = new PermutationEnumerator( objectSublist );
            nextSubpermutation = subPermutationEnumerator.next();
        }
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
         final List<List<Integer>> subPermutationList = enumerate(integerList );

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
     * Produce the nextPermutation permutation.
     * @return the nextPermutation permutation as a List.
     * @throws java.lang.Exception  nextPermutation() invoked when hasNext() is false.
     */
    public List<T> next() throws Exception
    {
        if ( ! hasNext() )
        {
            throw new Exception(); // make more specific
        }
        List<T> returnValue = new LinkedList<>( nextPermutation );
        
        // ?? Can I just compute returnValue as subPermutation.add( nextIndex, interleaveObject ) ? No.
        // compute new nextPermutation
        if ( nextPermutation.isEmpty() )
        {
            nextPermutation = null;
        }
        else if ( nextIndex <= nextSubpermutation.size() )
        {
            nextPermutation = new LinkedList( nextSubpermutation );
            nextPermutation.add( nextIndex++, interleaveObject );
        }
        else if ( subPermutationEnumerator.hasNext() )
        {
            nextSubpermutation = subPermutationEnumerator.next();
            nextIndex = 0;
            nextPermutation = new LinkedList( nextSubpermutation );
            nextPermutation.add( nextIndex++, interleaveObject) ;
        }
        else
        {
            nextSubpermutation = null;
        }
        return returnValue;
    }
    
    public boolean hasNext() { return nextPermutation != null; }
}
