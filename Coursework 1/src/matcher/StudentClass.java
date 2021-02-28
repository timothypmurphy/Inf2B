package matcher;

public class StudentClass {

	public KMPMatcher kmpMatcher;

	public StudentClass(String text, String pattern) {
		kmpMatcher = new KMPMatcher(text, pattern);
	}

	public void buildPrefixFunction() {
		kmpMatcher.setPrefixFunction(computePrefixFunction(kmpMatcher.getPattern()));
	}

	
	public static void main(String[] args) {
		//String pat = "bab";
		//String txt = "babbacababa";
		//System.out.println(Matcher.testPrefixFunction(pat));
		//int out[] = computePrefixFunction(pat);
		//for (int i = 0; i < pat.length(); i++) {
		//	System.out.print(out[i]);
		//}
		//System.out.println();
		//KMPMatcher test = new KMPMatcher(txt, pat);
		//test.setPrefixFunction(computePrefixFunction(pat));
		//test.search();
		//System.out.println(test.getMatchIndices().toString());
		//Matcher.testKMPMatcher(1, 3);
		Matcher.getRuntimes(10, 100, "matcherTimes.txt");
		//Matcher.getRatios(10, 100, 70000, "test2.txt");
		//Matcher.plotRuntimes(0.006403, 0.005099, "matcherTimes.txt");
	}

	public static int[] computePrefixFunction(String pattern) {
		int m = pattern.length();
		int pi[] = new int[m];
		// The pi array will be used to store the output of the function.
		pi[0] = 0;
		// pi[0] will always equal 0, regardless of the pattern.
		int k = 0;
		for (int q = 1; q < m; q++) {
			// This for loop will run through the length of the pattern
			while (k > 0 && pattern.charAt(k) != pattern.charAt(q)) {
				// If the current loop pattern character does not match the initial sequence of
				// characters in the loop, then we decrease k until we find a character that
				// matches or until k = 0
				k = pi[k - 1];
			}
			if (pattern.charAt(k) == pattern.charAt(q)) {
				// If the current loop character matches the next character in the initial
				// sequence of the pattern then we increment k by 1 to show this
				k++;
			}
			// We assign the value of k to the output array
			pi[q] = k;
		}
		return pi;
	}

	public static class KMPMatcher {

		private String text;
		private String pattern;
		private int textLen;
		private int patternLen;
		private int[] prefixFunction;
		private Queue matchIndices;

		public KMPMatcher(String text, String pattern) {
			this.text = text;
			this.pattern = pattern;
			this.textLen = text.length();
			this.patternLen = pattern.length();
			this.prefixFunction = new int[patternLen + 1];
			this.matchIndices = new Queue();
		}

		public void setPrefixFunction(int[] prefixFunction) {
			this.prefixFunction = prefixFunction;
		}

		public int[] getPrefixFunction() {
			return prefixFunction;
		}

		public String getPattern() {
			return pattern;
		}

		public Queue getMatchIndices() {
			return matchIndices;
		}

		public void search() {
			int q = 0;
			// If the pattern is longer than the text then there are no possible matches of
			// the pattern in the text
			if (textLen >= patternLen) {
				// We are going to go through each character of the text
				for (int i = 0; i < textLen; i++) {
					// If q = 0 then we are at the start of the pattern.
					while (q > 0 && pattern.charAt(q) != text.charAt(i)) {
						q = prefixFunction[q - 1];
					}
					if (pattern.charAt(q) == text.charAt(i)) {
						// If the char at q in the pattern = the current loop character in the text then
						// we add 1 to q to show how far into the pattern we are.
						q++;
					}
					if (q == patternLen) {
						// If q equals the pattern length then we have found the pattern inside the text
						// and we add the location of the start of the pattern to the queue.
						matchIndices.enqueue(i - patternLen + 1);
						q = prefixFunction[q - 1];
					}
				}
			}
		}
	}
}