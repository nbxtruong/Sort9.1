package sort.trace;

import java.util.Observable;
import java.util.Observer;

import sort.ObservableSortableData;

public class AsciiTracer implements Observer {

    public void update(Observable o, Object arg) {
        ObservableSortableData<?>.Operation op = (ObservableSortableData<?>.Operation) arg;
        System.out.print(op.name() + " ");
        for (int i = 0; i < op.indices().length; ++i) {
            System.out.print(op.indices()[i] + " ");
        }
        System.out.println();
    }

} 