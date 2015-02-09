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
    final private double lowerLeftX;
    final private double lowerLeftY;
    final private double edgeLength;
    final private int numPixels;
    final private int iterationLimit;
            
    public TaskMandelbrotSet( double lowerLeftX, double lowerLeftY, double edgeLength, int numPixels, int iterationLimit )
    {
        this.lowerLeftX = lowerLeftX;
        this.lowerLeftY = lowerLeftY;
        this.edgeLength = edgeLength;
        this.numPixels = numPixels;
        this.iterationLimit = iterationLimit;
    }
    
    /**
     * 
     * @return an Integer[row][col] array of iteration counts, where row and
     * col correspond to regions in the complex plane.
     */
    @Override
    public Integer[][] execute() 
    {
        final Integer[][] counts = new Integer[numPixels][numPixels];
        final double delta = edgeLength / numPixels;
        for ( int row = 0; row < numPixels; row++ )
            for ( int col = 0; col < numPixels; col++ )
            {
                counts[row][col] = getIterationCount( row, col, delta );
            }
        return counts;
    }
    
    @Override
    public String toString()
    {
        return String.format( "%s \n\t x: %e \n\t y: %e \n\t length: %e \n\t pixels: %d \n\t iteration limit: %d\n", 
                getClass(), lowerLeftX, lowerLeftY, edgeLength, numPixels, iterationLimit );
    }
    
    private int getIterationCount( int row, int col, double delta )
    {
        final double x0 = lowerLeftX + row * delta;
        final double y0 = lowerLeftY + col * delta;
        int iteration = 0;
        for ( double x = x0, y = y0; x*x + y*y <= 4.0 && iteration < iterationLimit; iteration++ )
        {
            double xtemp = x*x - y*y + x0;
            y = 2*x*y + y0;
            x = xtemp;
        }
        return iteration;
    }
}
