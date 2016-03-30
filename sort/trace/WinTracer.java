package sort.trace;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import sort.ObservableSortableData;

public class WinTracer extends JPanel implements Observer {

	JLabel[] labels;

	JSlider slider;

	ObservableSortableData<?> data;

	private static final Color END_COLOR = Color.cyan;

	private static class Action {
		String name;

		Color color;

		Action(String name, Color c) {
			this.name = name;
			this.color = c;
		}
	}

	private static final Action[] ACTIONS = {
			new Action(ObservableSortableData.COMPARE_NAME, Color.red),
			new Action(ObservableSortableData.SWAP_NAME, Color.blue),
			new Action(ObservableSortableData.GET_NAME, Color.green) };

	private static final Color NORMAL_COLOR = Color.black;

	private static final Color LABELS_BG = Color.white;

	public WinTracer(ObservableSortableData<?> data, JSlider slider) {
		super();
		this.slider = slider;
		this.data = data;
		setLayout(new FlowLayout(5));
		setBackground(LABELS_BG);
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		labels = new JLabel[data.size()];
		for (int i = 0; i < data.size(); ++i) {
			labels[i] = new JLabel();
			labels[i].setForeground(NORMAL_COLOR);
			labels[i].setText(data.get(i).toString());
			add(labels[i]);
		}
	}

	public void plugout() {
		data.deleteObserver(this); // Plug-out the
		// observer
	}

	public void update(Observable o, Object arg) {
		ObservableSortableData<?>.Operation op = (ObservableSortableData<?>.Operation) arg;
		int[] indices = op.indices();
		for (Action a : ACTIONS) {
			if (op.name().equals(a.name)) {
				for (int i : indices) {
					labels[i].setForeground(a.color);
				}
				break;
			}
		}
		if (op.name().equals(ObservableSortableData.SWAP_NAME)) {
			String tmp = labels[indices[0]].getText();
			labels[indices[0]].setText(labels[indices[1]].getText());
			labels[indices[1]].setText(tmp);
		}
		try {
			Thread.sleep(20 * slider.getValue());
		} catch (InterruptedException e) {
		}

		if (op.name().equals(ObservableSortableData.END_NAME)) {
			for (JLabel l : labels) {
				l.setForeground(END_COLOR);
			}
		} else {
			for (int i : indices) {
				labels[i].setForeground(NORMAL_COLOR);
			}
		}
	}
}