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
public class TaskMandelbrotSet implements Task<Integer[][]>
{
    private final double lowerLeftX;
    private final double lowerLeftY;
    private final double edgeLength;
    private final int numPixels;
    private final int iterationLimit;
            
    public TaskMandelbrotSet( double lowerLeftX, double lowerLeftY, double edgeLength, int numPixels, int iterationLimit )
    {
        this.lowerLeftX = lowerLeftX;
        this.lowerLeftY = lowerLeftY;
        this.edgeLength = edgeLength;
        this.numPixels = numPixels;
        this.iterationLimit = iterationLimit;
    }
    
    @Override
    public Integer[][] execute() 
    {
        final Integer[][] counts = new Integer[numPixels][numPixels];
        for ( int row = 0; row < numPixels; row++ )
            for ( int col = 0; col < numPixels; col++ )
            {
                counts[row][col] = ( row + col ) % ( iterationLimit + 1 );
            }
        return counts;
    }
    
    @Override
    public String toString()
    {
        return String.format( "%s \n\t x: %e \n\t y: %e \n\t length: %e \n\t pixels: %d \n\t iteration limit: %d", 
                getClass(), lowerLeftX, lowerLeftY, edgeLength, numPixels, iterationLimit );
    }
}
