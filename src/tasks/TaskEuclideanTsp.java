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

/**
 *
 * @author Peter Cappello
 */
public class TaskEuclideanTsp implements Task<int[]>
{
    final private static int NUM_PIXELS = 600;
    final private double[][] cities;
    
    public TaskEuclideanTsp( double[][] cities ) { this.cities = cities; }
    
    @Override
    public int[] execute() 
    {
        return new int[]{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
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
}
