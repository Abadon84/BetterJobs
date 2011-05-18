package de.abadon.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * @author PBajoit
 * @version 1.1
 * <p>The <code>IniProperties</code> class is an accessor to the files in the INI file format 
 * often used on the Windows operating systems.<br>
 * The INI file format contains sections and keys:<br>
 * <ul>
 *  <li>the section pattern is : <code>[(section)]</code></li>
 *  <li>the key pattern is : <code>(key)=(value)</code></li>
 *  <li>the characters considered as starting a comment are: <code>; ' #</code></li>
 * </ul>
 * </p>
 * <p>It mainly allows to get entries values, add new entries and save the INI file.</p>
 * <p>In this version the reading of the comments is not supported, as a consequence 
 * the files opened then saved have their comments removed.</p>
 * <p>Optionnally it is possible to reload the INI file before to get an entry value in 
 * order to use an INI file for (CPU expensive) inter-process communication.</p>
 */
public class IniProperties {
	/** Common comment markers (value is <code>#';</code>). */
	public static final String EXTENDED_COMMENT_CHAR = "#';";
	/** Strict INI file format comment marker (value is <code>;</code>). */
	public static final String CLASSIC_COMMENT_CHAR = ";";

	/**
	 * Creates an empty list with no content
	 */
	public IniProperties() {
	}

	/**
	 * Creates a list with the content of the INI file <code>fileName</code>
	 * @param fileName the name of the INI file
	 */
	public IniProperties(String fileName) {
		iniFileName = fileName;
		loadFile();
	}

	/**
	 * Creates a list with the content of the INI file <code>fileName</code>
	 * @param fileName the name of the INI file
	 * @param reload should the file to be reloaded before to get a value (only useful if the INI file is used for inter-process communication) 
	 */
	public IniProperties(String fileName, boolean reload) {
		iniFileName = fileName;
		reloadOnGet = reload;
		loadFile();
	}

	/**
	 * Save the file with the original file name.
	 * @throws IOException if no file name is known 
	 * (ie the constructor of an empty list <code>IniProperties()</code> was used).
	 */
	public void save() throws IOException {
		if (iniFileName == null) {
			throw new IOException("INI File not defined");
		}

		saveAs(iniFileName);
	}

	/**
	 * Save the file with a specific file name
	 * @param fileName the name of the file to be saved
	 */
	public void saveAs(String fileName) {
        saveFile(fileName);
	}

	/**
	 * Get a property value
	 * @param section the name of the section
	 * @param key the name of the key
	 * @return the value of the key or <code>null</code> if not present
	 */
	public String getProperty(String sectionName, String key) {
		String rc = null;
		Map section = null;

		if (reloadOnGet) {
			loadFile();
		}

		if ((section = getSection(sectionName)) != null) {
			rc = (String) section.get(key);
		}

		return rc;
	}

	/**
	 * Get a list of the existing keys in the specified section
	 * @param section the name of the section
	 * @return The vector containing all the key names present in the section
	 */
	public List getProperties(String sectionName) {
		List rc = new Vector();
		Map section = getSection(sectionName);

		if (reloadOnGet) {
			loadFile();
		}

		if (section != null) {
			rc = new Vector(section.keySet());
		}

		return rc;
	}

	/**
	 * Get a list of the existing sections
	 * @return The vector containing all the section names present in the file
	 */
	public List getSections() {
		if (reloadOnGet) {
			loadFile();
		}

		return new Vector(sections.keySet());
	}

	/**
	 * Set a property in the given section
	 * @param section the name of the section
	 * @param key the name of the key
	 * @param value the value of the key to set
	 */
	public void setProperty(String sectionName, String key, String value) {
		Map section = getSection(sectionName);

		if (section == null) {
			section = new HashMap();
			sections.put(sectionName, section);
		}

		section.put(key, value);
	}

	/**
	 * Get the current characters defining a comment line or end of line
	 * @return The current comment characters
	 */
	public String getCommentChars() {
		return commentChar;
	}

	/**
	 * <p>Set the current characters defining a comment line or end of line.
	 * Setting the comment characters causes the reload of the file.</p>
	 * <p>Only single characters may be used as comment marker, for example
	 * the Java comment line marker <code>//</code> is not supported.</p>
	 * @param chars The comment characters to use
	 */
	public void setCommentChars(String chars) {
		commentChar = chars;
		
		if (iniFileName != null) {
			loadFile();
		}
	}

	private Map getSection(String section) {
		return (Map) sections.get(section);
	}

	private void loadFile() {
		if (iniFileName == null) {
			return;
		}

		String currentSection = null;
		sections = new HashMap();

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(iniFileName)));
			while (br.ready()) {
				String line = br.readLine();

				// find a comment at the beginning of the line
				if ((line != null)
					&& (line.length() > 0)
					&& (commentChar.indexOf(line.substring(0, 1)) == -1)) {
					currentSection = processLine(currentSection, chomp(line).trim());
				}
			}
			br.close();
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}

	private void saveFile(String fileName) {
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
            // the validated fileName becomes the default file name
            iniFileName = fileName;

			List scs = this.getSections();
			for (int s = 0; s < scs.size(); s++) {
				String section = (String) scs.get(s);
				bw.write("[" + section + "]\n");

				List keys = this.getProperties(section);
				for (int k = 0; k < keys.size(); k++) {
					String key = (String) keys.get(k);
					bw.write(key + "=" + this.getProperty(section, key) + "\n");
				}
			}
			bw.close();
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}

	private String processLine(String currentSection, String line) {
		String rc = currentSection;
		String usefulLine = line;
		int lineLen = usefulLine.length();

		// find a comment in the middle of the line
		for (int c = 0; c < commentChar.length(); c++) {
			int commentCharPos = -1;
			if ((commentCharPos = usefulLine.indexOf(commentChar.substring(c, c + 1))) > -1) {
				if (lineLen > commentCharPos) {
					lineLen = commentCharPos;
				}
			}
		}

		usefulLine = line.substring(0, lineLen).trim();
		lineLen = usefulLine.length();

		if (lineLen > 2) {
			if ((usefulLine.charAt(0) == '[') && (usefulLine.charAt(lineLen - 1) == ']')) {
				rc = usefulLine.substring(1, lineLen - 1);
			} else {
				int eqPos = usefulLine.indexOf("=");
				if (eqPos > 0) {
					String key = usefulLine.substring(0, eqPos);
					String value = usefulLine.substring(eqPos + 1, lineLen);
					this.setProperty(currentSection, key, value);
				}
			}
		}

		return rc;
	}

	/**
	 * <p>Remove one newline from end of a String if it's there,
	 * otherwise leave it alone.  A newline is "\n", "\r", or "\r\n".
	 * <p>
	 *
	 * @param str String to chomp a newline from
	 * @return String without newline
	 * @throws NullPointerException if str is <code>null</code>
	 * 
	 */
	private String chomp(String str) {
		if (str.length() == 0) {
			return str;
		}

		if (str.length() == 1) {
			if ("\r".equals(str) || "\n".equals(str)) {
				return "";
			} else {
				return str;
			}
		}

		int lastIdx = str.length() - 1;
		char last = str.charAt(lastIdx);

		if (last == '\n') {
			if (str.charAt(lastIdx - 1) == '\r') {
				lastIdx--;
			}
		} else if (last != '\r') {
			lastIdx++;
		}
		return str.substring(0, lastIdx);
	}

	private Map sections = new HashMap();
	private String iniFileName = null;
	private boolean reloadOnGet = false;
	private String commentChar = EXTENDED_COMMENT_CHAR;
}
