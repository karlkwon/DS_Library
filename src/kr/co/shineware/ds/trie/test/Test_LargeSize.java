package kr.co.shineware.ds.trie.test;

import kr.co.shineware.ds.trie.TrieDictionary;

public class Test_LargeSize {

	public void makeData(TrieDictionary<String> dic, int maxDepth) {
		makeDataSub(dic, "", 0, maxDepth);
	}

	private boolean makeDataSub(TrieDictionary<String> dic, String prefix,
			int depth, int maxDepth) {
		if (depth > maxDepth)
			return false;

		for (int i = 0; i < 26; i++) {
			String tmpStr = prefix + (char) ('A' + i);

			dic.put(tmpStr, "[" + tmpStr + "]");
			if (!makeDataSub(dic, tmpStr, depth + 1, maxDepth))
				break;
		}

		return true;
	}

	public static void main(String[] args) {
		String testFileName = "trie_Large.db";
		Test_LargeSize inst = new Test_LargeSize();

		MeasureResource measure = new MeasureResource();

		measure.update(true, false);
		System.out.println(measure.toString());

		// //////////////////////////////////////////////////////
		// make test data.
		{
			MeasureResource measureBackup = new MeasureResource(measure);

			System.out.println("\r\n### make test data.");

			TrieDictionary<String> trieDic = new TrieDictionary<String>();
			inst.makeData(trieDic, 3);

			// avoid memory usage from makeData()
			System.out.println("- remove temporary");
			measure.update(true, false);

			trieDic.save(testFileName);

			System.out.println("- after save data to file");
			measure.update(false, false);
			System.out.println("Used change: " + (measure.used- measureBackup.used));

			//	remove temporary memory
			System.out.println("- remove temporary");
			measure.update(true, false);
			System.out.println("Used change: " + (measure.used- measureBackup.used));
		}

		// //////////////////////////////////////////////////////
		// reload test data from file.
		{
			System.out.println("\r\n### reload test data from file.");

			measure.update(true, false);
			MeasureResource measureBackup = new MeasureResource(measure);

			TrieDictionary<String> trieDic = new TrieDictionary<String>();
			trieDic.load(testFileName);

			System.out.println("- after load data from file");
			measure.update(false, false);
			System.out.println("Used change: " + (measure.used- measureBackup.used));

			//	remove temporary memory
			System.out.println("- remove temporary");
			measure.update(true, false);
			System.out.println("Used change: " + (measure.used- measureBackup.used));

			
			// Verify
			System.out.println("\r\n### Verify");
			
			String[] verifyData = { "", "A", "AT", "AAG", "a", "Aa", "AAAR",
					"ZAAR" };

			for (String s : verifyData) {
				System.out.println(s + " - " + trieDic.get(s));
				System.out.println(trieDic.hasChildren());
			}
		}
	}
}
