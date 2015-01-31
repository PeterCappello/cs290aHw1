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
package api;
import computer.ComputerImpl;
import java.awt.BorderLayout;
import java.awt.Container;
import java.rmi.RemoteException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

/**
 *
 * @author Peter Cappello
 * @param <T> return type of runTask method
 */
public class Client<T> extends JFrame
{
    final protected Task task;
    final private   Computer computer;
          protected T taskReturnValue;
    
    public Client( String title, Task task ) throws RemoteException
    {     
        this.task = task;
        setTitle( title );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        final String domainName = "localhost";
        final String url = "rmi://" + domainName + ":" + Computer.PORT + "/" + Computer.SERVICE_NAME;
        computer = new ComputerImpl(); //(Computer) Naming.lookup( url );
    }
    
    public void add( JLabel jLabel )
    {
        Container container = getContentPane();
        container.setLayout( new BorderLayout() );
        container.add( new JScrollPane( jLabel ), BorderLayout.CENTER );
        pack();
        setVisible( true );
    }
    
    public  T runTask() throws RemoteException
    {
        computer.execute( task );
        final long startTime = System.nanoTime();
        final T value = (T) computer.execute( task );
        final long runTime = ( System.nanoTime() - startTime ) / 1000000;
        System.out.println( task + "\n\t runtime: " + runTime + " ms.");
        return value;
    }
}
