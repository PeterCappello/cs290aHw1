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
package client;
import api.Computer;
import computer.ComputerImpl;
import tasks.TaskMandelbrotSet;

/**
 *
 * @author Peter Cappello
 */
public class ClientMandelbrotSet extends Client
{
    private static final double LOWER_LEFT_X = -2.0;
    private static final double LOWER_LEFT_Y = -2.0;
    private static final double EDGE_LENGTH = -2.0;
    private static final int N_PIXELS = 512;
    private static final int ITERATION_LIMIT = 128;
    
    public ClientMandelbrotSet() { super( "Mandelbrot Set Visualizer" ); }
    
    /**
     * Run the MandelbrotSet visualizer client.
     * @param args unused 
     * @throws java.rmi.RemoteException 
     */
    public static void main( String[] args ) throws Exception
    {  
        System.setSecurityManager( new SecurityManager() );
        
        final Client client = new ClientMandelbrotSet();
        
        final String domainName = "localhost";
        final String url = "rmi://" + domainName + ":" + Computer.PORT + "/" + Computer.SERVICE_NAME;
        final Computer computer = new ComputerImpl(); //(Computer) Naming.lookup( url );
        
        final TaskMandelbrotSet task = new TaskMandelbrotSet( LOWER_LEFT_X, LOWER_LEFT_Y, EDGE_LENGTH, N_PIXELS, ITERATION_LIMIT);
        
        // run task twice; report the 2nd execution time
        computer.execute( task );
        final long startTime = System.nanoTime();
        final int[][] counts = (int[][]) computer.execute( task );
        System.out.println( task + "\n\t runtime: " + ( ( System.nanoTime() - startTime ) / 1000000.0 ) + " ms.");
        
        client.add( task.getLabel( counts ) );
    }
}
