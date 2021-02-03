import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;

import com.googlecode.loveemu.petitemm.Midi2MML;

public class PetiteMM {
	
	// list of available option switches
	private static final String[] argsAvail = {
			"-o", "<filename>", "Specify the output MML filename.",
			"--dots", "<count>", "Maximum dot counts allowed for dotted-note, -1 for infinity. (default=" + Midi2MML.DEFAULT_MAX_DOT_COUNT + ")",
			"--timebase", "<TPQN>", "Timebase of target MML, " + Midi2MML.RESOLUTION_AS_IS + " to keep the input timebase. (default=" + Midi2MML.DEFAULT_RESOLUTION + ")",
			"--input-timebase", "<TPQN>", "Timebase of input sequence, " + Midi2MML.RESOLUTION_AS_IS + " to keep the input timebase. (default=" + Midi2MML.RESOLUTION_AS_IS + ")",
			"--quantize-precision", "<length>", "Specify the minimum note length for quantization.",
			"--no-quantize", "", "Prevent adjusting note length. Result will be more accurate but more complicated.",
			"--octave-reverse", "", "Swap the octave symbol. (not recommended)",
			"--use-triplet", "", "Use triplet syntax if possible. (really not so smart)",
			"--use-spaces", "", "Put a space after each note/octave/instrument change.",
			"--no-expression", "", "Ignore Expression messages (Control Change message 11).",
			"--multiply-volumes", "<factor>", "Multiply all the volumes by a given amount."};

	/**
	 * Removes the extension from a filename.
	 * 
	 * @param filename
	 *            the filename to query, null returns null
	 * @return the filename minus the extension
	 */
	public static String removeExtension(String filename) {
		if (filename == null)
			return null;

		int extensionIndex = filename.lastIndexOf('.');
		if (extensionIndex == -1)
			return filename;

		String separator = System.getProperty("file.separator");
		int lastSeparatorIndex = filename.lastIndexOf(separator);
		if (extensionIndex > lastSeparatorIndex)
			return filename.substring(0, extensionIndex);
		else
			return filename;
	}

	/**
	 * Convert the given MIDI file into MML.
	 * 
	 * @param args
	 *            Parameters, specify the empty array for details.
	 */
	public static void main(String[] args) { // NOSONAR
		boolean showAbout = false;
		Midi2MML opt = new Midi2MML();
		String mmlFileName = null;

		int argi = 0;

		//args = new String[]{"--no-quantize", "--put-spaces", "Untitled.mid"};
		
		// dispatch option switches
		while (argi < args.length && args[argi].startsWith("-")) {
			switch(args[argi]) {
			case "-o":
				checkArgumentCount(args, argi);
				mmlFileName = args[++argi];
				break;
			case "--dots":
				checkArgumentCount(args, argi);
				opt.setMaxDots(Integer.parseInt(args[++argi]));
				break;
			case "--timebase":
				checkArgumentCount(args, argi);
				opt.setTargetResolution(Integer.parseInt(args[++argi]));
				break;
			case "--input-timebase":
				checkArgumentCount(args, argi);
				opt.setInputResolution(Integer.parseInt(args[++argi]));
				break;
			case "--quantize-precision":
				checkArgumentCount(args, argi);
				opt.setQuantizePrecision(Integer.parseInt(args[++argi]));
				break;
			case "--no-quantize":
				opt.setQuantizationEnabled(false);
				break;
			case "--octave-reverse":
				opt.setOctaveReversed(false);
				break;
			case "--use-triplet":
				opt.setTripletPreference(true);
				break;
			case "--put-spaces":
				opt.setPutSpaces(true);
				break;
			case "--no-expression":
				opt.setNoExpression(true);
				break;
			case "--multiply-volumes":
				checkArgumentCount(args, argi);
				opt.setMultiplyVolumes(Double.parseDouble(args[++argi]));
				break;
			default:
				throw new IllegalArgumentException("Unsupported option [" + args[argi] + "]");
			}
			argi++;
		}

		// show about the program and exit, if needed
		if (argi >= args.length || showAbout) {
			System.out.println(Midi2MML.NAME + " " + Midi2MML.VERSION + " by " + Midi2MML.AUTHOR);
			System.out.println(Midi2MML.WEBSITE);
			System.out.println();

			System.out.println("Syntax: PetiteMM <options> input.mid");
			if (argsAvail.length > 0)
				System.out.println("Options:");
			for (int i = 0; i < argsAvail.length / 3; i++) {
				System.out.format("%-20s %-9s %s%n", argsAvail[i * 3], argsAvail[i * 3 + 1], argsAvail[i * 3 + 2]);
			}

			System.exit(1);
		}
		
		if(mmlFileName != null && args.length - argi > 1) {
			throw new IllegalArgumentException("The -o option can only be used with a single input file!");
		}
		
		boolean success = true;
		
		while(argi < args.length) {
			String midiFileName = args[argi];
			String currentFileName = mmlFileName;
			if(mmlFileName == null) {
				currentFileName = removeExtension(args[argi]) + ".txt";
			}
			boolean fileSuccess = convert(midiFileName, currentFileName, opt);
			if(!fileSuccess) {
				success = false;
			}
			argi++;
		}

		System.exit(success ? 0 : 1);
	}
	
	private static boolean convert(String midiFileName, String mmlFileName, Midi2MML options) {
		// convert the given file
		File midiFile = new File(midiFileName);
		File mmlFile = new File(mmlFileName);

		Midi2MML converter = new Midi2MML(options);
		boolean success = false;

		try (FileWriter fileWriter = new FileWriter(mmlFile)){
			if (!midiFile.exists()) {
				throw new FileNotFoundException(midiFile.getName() + " (The system cannot find the file specified)");
			}

			StringBuilder writer = new StringBuilder();
			converter.writeMML(MidiSystem.getSequence(midiFile), writer);
			StringBuilder mml = converter.writeMacros();
			mml.append(writer.toString());
			fileWriter.write(postProcess(mml));
			success = true;
		} catch (InvalidMidiDataException | IOException e) {
			e.printStackTrace();
		}
		
		return success;
	}
	
	private static void checkArgumentCount(String[] args, int argi) {
		if (argi + 1 >= args.length) {
			throw new IllegalArgumentException("Too few arguments for " + args[argi]);
		}
	}

	private static String postProcess(StringBuilder mml) {
		String output = mml.toString();

		// If there's some unused macro left, remove it
		List<String> matches = new ArrayList<>();
		Matcher matcher = Pattern.compile("\".*=").matcher(output);
		while (matcher.find()) {
			String match = matcher.group().replaceAll("\\s+", "");
			match = match.substring(1, match.length() - 1);
			int count = output.split(match, -1).length - 1;
			if (count <= 1 && !matches.contains(match)) {
				matches.add(match);
			}
		}
		for (String match : matches) {
			output = output.replaceAll("\"" + match + ".*=.*\"\\n", "");
		}

		// If all expression values are the same, just remove them from macro names
		matches.clear();
		matcher = Pattern.compile("V..Q..E..").matcher(output);
		while (matcher.find()) {
			String match = matcher.group().substring(6);
			if (!matches.contains(match)) {
				matches.add(match);
			}
		}
		if (matches.size() <= 1) {
			output = output.replaceAll("(V..)(Q..)(E..)", "$1$2");
		}

		// If all velocity values are the same, just remove them from macro names
		matches.clear();
		matcher = Pattern.compile("V..Q..").matcher(output);
		while (matcher.find()) {
			String match = matcher.group().substring(3);
			if (!matches.contains(match)) {
				matches.add(match);
			}
		}
		if (matches.size() <= 1) {
			output = output.replaceAll("(V..)(Q..)", "$1");
		}

		return output;
	}

}
