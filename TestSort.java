import java.util.LinkedList;
import java.util.List;

import sort.Sort;
import sort.trace.MultipleWinTracer;

/*
 * Created on 24 oct. 2004
 *
 */

/**
 * @author baudon
 * 
 */

public class TestSort {

	private static Sort getSort(String name) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		return (Sort) Class.forName(name).newInstance();
	}

	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException {

		List<Sort> lsorts = new LinkedList<Sort>();
		try {
			for (String a : args) {
				lsorts.add(getSort(a));
			}
		} catch (ClassNotFoundException e) {
		}

		int nsorts = lsorts.size();
		int ndata = args.length - nsorts;
		String[] data = new String[ndata];
		System.arraycopy(args, nsorts, data, 0, ndata);

		new MultipleWinTracer<String>(lsorts.toArray(new Sort[0]), data)
				.start();
	}
}
