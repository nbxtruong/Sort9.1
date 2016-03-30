package sort.trace;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

import sort.ObservableSortableData;
import sort.Sort;
import sort.SortableComparableData;
import sort.SwapableArray;

public class MultipleWinTracer<T> extends JFrame {

	static class SortThread extends Thread {
		private Sort sort;

		private ObservableSortableData<?> data;

		public SortThread(Sort s, ObservableSortableData<?> sd) {
			data = sd;
			sort = s;
		}

		public void run() {
			sort.doSort(data);
			data.end();
		}
	}

	SortThread[] threads;

	public MultipleWinTracer(Sort[] sorts, T[] data) {
		super("Sort");

		JPanel mainPane = new JPanel(new BorderLayout());
		JPanel tracersPane = new JPanel();
		tracersPane.setLayout(new BoxLayout(tracersPane, BoxLayout.Y_AXIS));
		JSlider slider = new JSlider();
		
		int nsorts = sorts.length;
		final WinTracer[] wtracers = new WinTracer[nsorts];
		threads = new SortThread[nsorts];

		for (int i = 0; i < nsorts; i++) {
			String[] copyData = new String[data.length];
			System.arraycopy(data, 0, copyData, 0, data.length);
			ObservableSortableData<String> observable = new ObservableSortableData<String>(
					new SortableComparableData<String>(
							new SwapableArray<String>(copyData)));
			wtracers[i] = new WinTracer(observable, slider);
			observable.addObserver(wtracers[i]);
			threads[i] = new SortThread(sorts[i], observable);
		}

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				for (WinTracer wt : wtracers) {
					wt.plugout();
				}
			}
		});
		
		for (WinTracer wt : wtracers) {
			tracersPane.add(wt);
		}
		mainPane.add(tracersPane, BorderLayout.NORTH);
		mainPane.add(slider, BorderLayout.SOUTH);
		setContentPane(mainPane);
		pack();
		setVisible(true);
	}

	public void start() {
		for (SortThread st : threads) {
			st.start();
		}
	}

}