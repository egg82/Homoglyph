package ninja.egg82.homoglyph;

import it.unimi.dsi.fastutil.ints.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class HomoglyphHelper {
	public static HomoglyphHelper create() throws IOException { return create("char_codes.lst"); }

	public static HomoglyphHelper create(String resourceName) throws IOException { return create(new InputStreamReader(getResource(resourceName))); }

	public static HomoglyphHelper create(File file) throws IOException { return create(new FileReader(file)); }

	public static HomoglyphHelper create(Reader reader) throws IOException {
		List<IntSet> homoglyphs = new ArrayList<>();

		try (BufferedReader r = new BufferedReader(reader)) {
			String line;
			while ((line = r.readLine()) != null) {
				line = line.trim();
				if (line.isEmpty() || line.charAt(0) == '#') {
					// Don't want empty lines or comments
					continue;
				}

				IntSet set = new IntArraySet();
				for (String code : line.split(",")) {
					try {
						set.add((char) Integer.parseInt(code.trim(), 16));
					} catch (NumberFormatException ignored) {}
				}
				homoglyphs.add(set);
			}
		}

		return new HomoglyphHelper(homoglyphs);
	}

	private static InputStream getResource(String name) throws IOException {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("name cannot be null or empty.");
		}

		URL url = HomoglyphHelper.class.getClassLoader().getResource(name);
		if (url == null) {
			throw new IOException("url not found.");
		}
		URLConnection conn = url.openConnection();
		conn.setUseCaches(false);
		return conn.getInputStream();
	}

	/**
	 * ASCII cache where the key is an int character and the value is an ASCII Integer character that is similar to the key
	 */
	private Int2ObjectMap<Integer> asciiCache = new Int2ObjectOpenHashMap<>();
	/**
	 * Homoglyph cache where the key is an int character and the value is a set of int characters that are similar (but not equal to) the key
	 */
	private Int2ObjectMap<IntSet> homoglyphCache = new Int2ObjectOpenHashMap<>();

	private HomoglyphHelper(List<IntSet> homoglyphs) {
		if (homoglyphs == null) {
			throw new IllegalArgumentException("homoglyphs cannot be null.");
		}

		for (IntSet glyphs : homoglyphs) {
			// Each IntSet is a bunch of random unicode/ASCII chars that look alike
			// Try to find an ASCII char in each one
			for (int c : glyphs) {
				// Add char set to homoglyph cache (excluding the current char)
				IntSet modifiedSet = new IntArraySet(glyphs);
				modifiedSet.remove(c);
				homoglyphCache.put(c, modifiedSet);

				if (c >= 0 && c <= 255) {
					// ASCII char found
					for (int u : glyphs) {
						// Add all chars to ASCII cache that are NOT the current char
						if (u != c) {
							asciiCache.put(u, Integer.valueOf(c));
						}
					}
				}
			}
		}
	}

	/**
	 * Returns the string given, but with unicode homoglyphs converted
	 * into their ASCII counterparts.
	 * If there is no homoglyph for a given Unicode character, it  will
	 * not be transformed.
	 *
	 * @param unicode The unicode string to ASCII-ify
	 * @return The transformed result
	 */
	public String toASCII(String unicode) {
		if (unicode == null) {
			throw new IllegalArgumentException("unicode cannot be null.");
		}
		if (unicode.isEmpty()) {
			return "";
		}

		StringBuilder result = new StringBuilder();
		IntList chars = toChars(unicode);
		for (int c : chars) {
			Integer r = asciiCache.getOrDefault(c, Integer.valueOf(c));
			result.append(Character.toChars(r));
		}
		return result.toString();
	}

	/**
	 * Returns true if strings one and two are equal,
	 * given variance for homoglyphs.
	 *
	 * @param one The first string
	 * @param two The second string
	 * @return Whether or not the two strings are equal
	 */
	public boolean equals(String one, String two) {
		if (one == null && two == null) {
			return true;
		}
		if (one == null || two == null) {
			return false;
		}
		if (one.isEmpty() && two.isEmpty()) {
			return true;
		}

		IntList oneChars = toChars(one);
		IntList twoChars = toChars(two);

		if (oneChars.size() != twoChars.size()) {
			return false;
		}

		for (int i = 0; i < oneChars.size(); i++) {
			int o = oneChars.getInt(i);
			int t = twoChars.getInt(i);

			if (o == t) {
				continue;
			}

			IntSet similar = homoglyphCache.get(o);
			if (similar == null || !similar.contains(t)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns true if the prefix is found at the beginning
	 * of the haystack, given variance for homoglyphs.
	 *
	 * @param haystack The string to search in
	 * @param prefix The string to search for
	 * @return Whether or not the haystack starts with the prefix
	 */
	public boolean startsWith(String haystack, String prefix) {
		if (haystack == null || prefix == null) {
			return false;
		}
		if (prefix.isEmpty()) {
			return true;
		}
		if (haystack.isEmpty()) {
			return false;
		}

		IntList haystackChars = toChars(haystack);
		IntList prefixChars = toChars(prefix);

		if (prefixChars.size() > haystackChars.size()) {
			return false;
		}

		for (int i = 0; i < prefixChars.size(); i++) {
			int h = haystackChars.getInt(i);
			int p = prefixChars.getInt(i);

			if (h == p) {
				continue;
			}

			IntSet similar = homoglyphCache.get(h);
			if (similar == null || !similar.contains(p)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns true if the suffix is found at the end
	 * of the haystack, given variance for homoglyphs.
	 *
	 * @param haystack The string to search in
	 * @param suffix The string to search for
	 * @return Whether or not the haystack ends with the suffix
	 */
	public boolean endsWith(String haystack, String suffix) {
		if (haystack == null || suffix == null) {
			return false;
		}
		if (suffix.isEmpty()) {
			return true;
		}
		if (haystack.isEmpty()) {
			return false;
		}

		IntList haystackChars = toChars(haystack);
		IntList suffixChars = toChars(suffix);

		if (suffixChars.size() > haystackChars.size()) {
			return false;
		}

		for (int i = suffixChars.size() - 1; i >= 0; i--) {
			int h = haystackChars.getInt(haystackChars.size() - (suffixChars.size() - i));
			int s = suffixChars.getInt(i);

			if (h == s) {
				continue;
			}

			IntSet similar = homoglyphCache.get(h);
			if (similar == null || !similar.contains(s)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns the index of the needle in the haystack,
	 * given variance for homoglyphs.
	 *
	 * @param haystack The string to search in
	 * @param needle The string to search for
	 * @return The index of the needle, or -1 if not found
	 */
	public int indexOf(String haystack, String needle) { return indexOf(haystack, needle, 0); }

	/**
	 * Returns the index of the needle in the haystack
	 * starting at a specified index, given variance
	 * for homoglyphs.
	 *
	 * @param haystack The string to search in
	 * @param needle The string to search for
	 * @param fromIndex The index to start at
	 * @return The index of the needle, or -1 if not found
	 */
	public int indexOf(String haystack, String needle, int fromIndex) {
		if (haystack == null || needle == null) {
			return -1;
		}
		if (needle.isEmpty()) {
			return fromIndex;
		}
		if (haystack.isEmpty()) {
			return -1;
		}

		IntList haystackChars = toChars(haystack);
		IntList needleChars = toChars(needle);

		if (fromIndex < 0) {
			fromIndex += haystackChars.size() - 1;
		}
		if (fromIndex < 0) {
			return -1;
		}
		if (needleChars.size() + fromIndex > haystackChars.size()) {
			return -1;
		}

		// currentIndex holds the index of the current position for the needle.
		// When we search for a substring from beginning to end, we're always
		// moving the index through the haystack one character at a time, no
		// matter how far along we are on the needle. We can exploit this and
		// avoid iterating over two lists twice.
		//
		// Basically, if we find a match we increment the needle's index (currentIndex).
		// If we don't find a match, we reset the needle's index (currentIndex).
		// If the needle's index (currentIndex) is at the end of the needle, we've found
		// the needle in the haystack.
		int currentIndex = 0;
		for (int i = fromIndex; i < haystackChars.size(); i++) {
			int h = haystackChars.getInt(i);
			int p = needleChars.getInt(currentIndex);

			if (h == p) {
				if (currentIndex == needleChars.size() - 1) {
					return i - currentIndex;
				}
				currentIndex++;
				continue;
			}

			IntSet similar = homoglyphCache.get(h);
			if (similar != null && similar.contains(p)) {
				if (currentIndex == needleChars.size() - 1) {
					return i - currentIndex;
				}
				currentIndex++;
			} else {
				// Reset the main loop back in case we missed any potential matches
				i -= currentIndex;
				currentIndex = 0;
			}
		}

		return -1;
	}

	/**
	 * Returns the last index of the needle in the haystack,
	 * given variance for homoglyphs.
	 *
	 * @param haystack The string to search in
	 * @param needle The string to search for
	 * @return The last index of the needle, or -1 if not found
	 */
	public int lastIndexOf(String haystack, String needle) {
		if (haystack == null) {
			return -1;
		}

		return lastIndexOf(haystack, needle, haystack.length());
	}

	/**
	 * Returns the last index of the needle in the haystack
	 * starting at a specified index, given variance
	 * for homoglyphs.
	 *
	 * @param haystack The string to search in
	 * @param needle The string to search for
	 * @param fromIndex The index to start at
	 * @return The last index of the needle, or -1 if not found
	 */
	public int lastIndexOf(String haystack, String needle, int fromIndex) {
		if (haystack == null || needle == null) {
			return -1;
		}
		if (haystack.isEmpty()) {
			return -1;
		}

		IntList haystackChars = toChars(haystack);
		IntList needleChars = toChars(needle);

		if (fromIndex < 0) {
			fromIndex += haystackChars.size() - 1;
		}
		if (fromIndex < 0) {
			return -1;
		}
		if (fromIndex >= haystackChars.size()) {
			fromIndex = haystackChars.size() - 1;
		}

		if (needle.isEmpty()) {
			return fromIndex;
		}

		if (needleChars.size() > fromIndex) {
			return -1;
		}

		// currentIndex holds the index of the current position for the needle.
		// When we search for a substring from end to beginning, we're always
		// moving the index through the haystack one character at a time, no
		// matter how far along we are on the needle. We can exploit this and
		// avoid iterating over two lists twice.
		//
		// Basically, if we find a match we decrement the needle's index (currentIndex).
		// If we don't find a match, we reset the needle's index (currentIndex).
		// If the needle's index (currentIndex) is at 0, we've found the needle in the haystack.
		int currentIndex = needleChars.size() - 1;
		for (int i = fromIndex; i >= 0; i--) {
			int h = haystackChars.getInt(i);
			int p = needleChars.getInt(currentIndex);

			if (h == p) {
				if (currentIndex == 0) {
					return i;
				}
				currentIndex--;
				continue;
			}

			IntSet similar = homoglyphCache.get(h);
			if (similar != null && similar.contains(p)) {
				if (currentIndex == 0) {
					return i;
				}
				currentIndex--;
			} else {
				// Reset the main loop back in case we missed any potential matches
				i += needleChars.size() - currentIndex - 1;
				currentIndex = needleChars.size() - 1;
			}
		}

		return -1;
	}

	/**
	 * Returns true if the needle is found anywhere in
	 * the haystack, given variance for homoglyphs.
	 *
	 * @param haystack The string to search in
	 * @param needle The string to search for
	 * @return Whether or not the haystack contains the needle
	 */
	public boolean contains(String haystack, String needle) {
		return indexOf(haystack, needle) > -1;
	}

	private IntList toChars(String text) {
		IntList unicode = new IntArrayList(text.length());
		int i = 0;
		while (i < text.length()) {
			int c = text.codePointAt(i);
			unicode.add(c);
			i += Character.charCount(c);
		}
		return unicode;
	}
}
