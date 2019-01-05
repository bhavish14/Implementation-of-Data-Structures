

import java.lang.reflect.Array;
import java.util.Arrays;

public class BoundedQueue <T> {

    //Loop Invarients
    int queueRearPosition, queueFrontPosition, queueSize;
    int queueCapacity;

    //Queue
    T[] boundedQueue;



    public BoundedQueue (int size)
    {

        boundedQueue = (T[]) new Object[size];
        queueRearPosition = 0;
        queueFrontPosition = 0;
        queueSize = 0;
        queueCapacity = size;

    }


    public boolean offer(T x) {
        /*
        //starts with index -1. so, qSize - 1
        if (queueRearPosition < queueSize - 1 || queueRearPosition < queueFrontPosition) {
            queueRearPosition++;
            boundedQueue[queueRearPosition] = x;
            return true;
        }
        else if(queueRearPosition == queueSize - 1 && queueFrontPosition != -1){
            queueRearPosition = 0;
            boundedQueue[queueRearPosition] = x;
            return true;
        }
        */

        /*
        * if qf > 0 and qR = qS, qR = 0
        * if qR < qS, add;
        *
        *
        * */

        if (queueRearPosition < queueCapacity){

            if(queueRearPosition != queueFrontPosition || queueRearPosition == 0){
                boundedQueue[queueRearPosition] = x;
                queueRearPosition++;
                queueSize++;
                return true;
            }
            System.out.print("Queue Full");
            return false;
        }
        //write the case where queue rear pointer is at max and front is not 0.
        else if (queueRearPosition == queueCapacity && queueFrontPosition > 0) {
            queueRearPosition = 0;
            boundedQueue[queueRearPosition] = x;
            queueRearPosition++;
            queueSize++;
            return true;
        }
        /*
        else if (queueFrontPosition > 0 && queueRearPosition != queueFrontPosition) {
            if (queueRearPosition == queueSize){
                queueRearPosition = 0;
            }
            boundedQueue[queueRearPosition] = x;
            queueRearPosition++;
            return true;
        }
        */
        return false;

    }

    public T poll() {
        //how to remove the element?
        //queue front == queue rear and pos = x. how to re-intiatate.
        /*

        if (queueFrontPosition < queueRearPosition) {
            T temp = boundedQueue[queueFrontPosition];
            queueFrontPosition++;
            //queueSize--;
            return temp;
        }
        else if (queueFrontPosition > queueRearPosition && queueFrontPosition < queueSize) {
            if (queueFrontPosition == queueSize) {
                queueFrontPosition = 0;
            }

            T temp = boundedQueue[queueFrontPosition];
            queueFrontPosition++;
            //queueSize--;
            return temp;
        }
        queueFrontPosition = 0;
        queueRearPosition = -1;
        return null;

        */
        if (queueFrontPosition < queueCapacity) {
            if (queueFrontPosition <= queueRearPosition) {
                T temp = boundedQueue[queueFrontPosition];
                queueFrontPosition++;
                queueSize--;
                return temp;
            }
        }

        else if (queueFrontPosition == queueCapacity && queueRearPosition < queueFrontPosition) {
            queueFrontPosition = 0;
            T temp = boundedQueue[queueFrontPosition];
            queueFrontPosition++;
            queueSize--;
            return temp;
        }
        return null;



         /*
            else if (queueFrontPosition == queueRearPosition) {
                T temp = boundedQueue[queueFrontPosition];
                queueFrontPosition = queueRearPosition = 0;
                return temp;
            }
            */
        /*
        if (queueFrontPosition != queueRearPosition){
            if (queueFrontPosition < queueRearPosition) {
                T temp = boundedQueue[queueFrontPosition];
                queueFrontPosition++;
                return temp;
            }

            else if (queueFrontPosition == queueSize) {
                queueFrontPosition = 0;
                T temp = boundedQueue[queueFrontPosition];
                queueFrontPosition++;
                return temp;
            }
            else if (queueFrontPosition == queueRearPosition) {
                T temp = boundedQueue[queueFrontPosition];
                queueFrontPosition = queueRearPosition = 0;
                return temp;
            }

        }*/


    }

    public T peek() {

        //if (queueFrontPosition != queueRearPosition) {
        if (queueSize > 0) {
            T temp = boundedQueue[queueFrontPosition];
            return temp;
        }
        return null;
    }

    //done
    public int size() {
        /*
        if (queueFrontPosition < queueRearPosition) {
            return (queueRearPosition - queueFrontPosition);
        }
        else if(queueFrontPosition > queueRearPosition){
            return ((queueSize - queueFrontPosition) + queueRearPosition );
        }
        return 0;
        */
        if (queueSize > 0){
            return queueSize;
        }
        return 0;

    }

    public boolean isEmpty() {
       return queueSize == 0;
    }

    public void printQueue() {
        for (T item: boundedQueue) {
            System.out.print(item);
        }
        System.out.println();
    }

    public void toArray(T[] a) {
        //local loop invarients
        int fp = queueFrontPosition, rp = queueRearPosition;
        int aCounter = 0;
        System.out.print("toArray");
        System.out.println(queueSize);

        if (queueSize > 0){

            if (fp >= rp) {
                while (fp < queueCapacity) {
                    System.out.println("if case");
                    a[aCounter] = boundedQueue[fp];
                    fp++;
                    aCounter++;
                }
                fp = 0;
            }

            while(fp < rp) {
                System.out.println("else case");
                a[aCounter] = boundedQueue[fp];
                fp++;
                aCounter++;
            }

        }



        /*

        if (queueFrontPosition < queueRearPosition) {
            while (fp < rp) {
                a[fp] = boundedQueue[fp];
            }
        }
        else if (fp > rp) {
            while
        }

        if (queueFrontPosition < queueSize) {
            if (queueFrontPosition <= queueRearPosition) {
                T temp = boundedQueue[queueFrontPosition];


            }
        }

        else if (queueFrontPosition == queueSize && queueRearPosition < queueFrontPosition) {
            queueFrontPosition = 0;
            T temp = boundedQueue[queueFrontPosition];

        }

        */


    }


    public static void main(String[] args) {
        BoundedQueue<Integer> obj = new BoundedQueue<>(1000);

        /*
        obj.offer(Integer.valueOf(30));
        obj.offer(Integer.valueOf(40));
        obj.offer(Integer.valueOf(50));
        */

        for (int i = 100; i <= 1100; i++) {
            obj.offer(Integer.valueOf(i));
        }



        Integer[] array = new Integer[obj.queueCapacity];

        obj.printQueue();
        obj.toArray(array);
        System.out.print("Array:");
        for (Integer item: array) {
            System.out.println(item + ',' );
        }
        System.out.println(array.length);

        /*
        System.out.println("isEmpty:" + obj.isEmpty());
        System.out.println(obj.poll());
        System.out.println(obj.poll());
        System.out.println("isEmpty:" + obj.isEmpty());
        System.out.println(obj.poll());
        obj.printQueue();
        System.out.println("isEmpty:" + obj.isEmpty());
        //System.out.println(obj.size());
        //System.out.println("Peek:" + obj.peek());
        //System.out.println("isEmpty:" + obj.isEmpty());
        */





    }

}