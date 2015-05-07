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

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author Pete Cappello
 * @param <T> the type of objectList being permuted.
 */
public class Permutation<T> 
{
    /**
     * Permute the elements of the List of Integers, starting from element k.
     * @param permutation the List of Integers to be permuted.
     * @param k index of element of the sublist to be permuted.
     * @param consumer a Consumer that takes the List of Integers, permutation, as its parameter.
     */
    final static public void iterate( final List<Integer> permutation, final int k, final Consumer<List<Integer>> consumer )
    {
        for( int i = k; i < permutation.size(); i++ )
        {
            Collections.swap( permutation, i, k );
            iterate( permutation, k + 1 , consumer );
            Collections.swap( permutation, k, i );
        }
        if ( k == permutation.size() - 1 )
        {
            consumer.accept( permutation );
        }
    }
}
