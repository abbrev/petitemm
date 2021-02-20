package com.googlecode.loveemu.petitemm;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

class Midi2MMLTrack {
	
	private static final String REGEX1 = "([<>]*[abcdefgr\\^][\\+\\-]*)";
	private static final String REGEX2 = "(\\s*[<>]*[abcdefgr\\^][\\+\\-]*)";
	
	/**
	 * Output MML text.
	 */
	private List<MMLEvent> mmlEventList = new LinkedList<>();
	
	/**
	 * Current position of conversion in tick.
	 */
	private long tick = 0L;
	
	/**
	 * Measure number of current position.
	 */
	private int measure = 0;
	
	/**
	 * Current octave.
	 */
	private int octave = 4;
	
	/**
	 * true if the first note is not processed yet.
	 */
	private boolean firstNote = true;
	
	/**
	 * Current note number. (for MIDI2MML conversion)
	 */
	private int noteNumber = MMLNoteConverter.KEY_REST;
	
	/**
	 * Current MIDI event index.
	 */
	private int midiEventIndex = 0;
	
	/**
	 * True if conversion is already finished.
	 */
	private boolean finished = false;
	
	/**
	 * True if use triplet syntax for some simple triplets.
	 */
	private boolean useTriplet = false;
	
	/**
	 * MML symbol set.
	 */
	private MMLSymbol mmlSymbol;
	
	private long currentNoteLastTick = 0;
	
	private int currentVolume = 127;
	private int currentVelocity = 127;
	private int currentExpression = 127;
	private int currentPan = 64;
	
	private boolean midNote = false;
	
	/**
	 * Construct new MML track conversion object.
	 * 
	 * @param mmlSymbol MML symbol set.
	 */
	Midi2MMLTrack(MMLSymbol mmlSymbol) {
		this.mmlSymbol = mmlSymbol;
	}
	
	public boolean getMidNote() {
		return midNote;
	}
	
	public void setMidNote(boolean midNote) {
		this.midNote = midNote;
	}
	
	/**
	 * Get current position of conversion in tick.
	 * 
	 * @return Current time in tick.
	 */
	public long getTick() {
		return tick;
	}
	
	/**
	 * Set current position of conversion in tick.
	 * 
	 * @param tick Current time in tick.
	 */
	public void setTick(long tick) {
		this.tick = tick;
	}
	
	/**
	 * Get measure number of current position.
	 * 
	 * @return Measure number of current position.
	 */
	public int getMeasure() {
		return measure;
	}
	
	/**
	 * Set measure number of current position.
	 * 
	 * @param measure Measure number of current position.
	 */
	public void setMeasure(int measure) {
		this.measure = measure;
	}
	
	/**
	 * Get current octave.
	 * 
	 * @return Current octave.
	 */
	public int getOctave() {
		return octave;
	}
	
	/**
	 * Set current octave.
	 * 
	 * @param octave Current octave.
	 */
	public void setOctave(int octave) {
		this.octave = octave;
	}
	
	/**
	 * Increase current octave.
	 */
	public void increaseOctave() {
		this.octave++;
	}
	
	/**
	 * Decrease current octave.
	 */
	public void decreaseOctave() {
		this.octave--;
	}
	
	/**
	 * Get current note number.
	 * 
	 * @return Current note number, MMLNoteConverter.KEY_REST if rest.
	 */
	public int getNoteNumber() {
		return noteNumber;
	}
	
	/**
	 * Set current note number.
	 * 
	 * @param noteNumber Current note number, MMLNoteConverter.KEY_REST if rest.
	 */
	public void setNoteNumber(int noteNumber) {
		this.noteNumber = noteNumber;
	}
	
	/**
	 * Get current MIDI event index.
	 * 
	 * @return Current MIDI event index.
	 */
	public int getMidiEventIndex() {
		return midiEventIndex;
	}
	
	/**
	 * Set current MIDI event index.
	 * 
	 * @param midiEventIndex Current MIDI event index.
	 */
	public void setMidiEventIndex(int midiEventIndex) {
		this.midiEventIndex = midiEventIndex;
	}
	
	/**
	 * Get if triplet is preferred.
	 * 
	 * @return true if triplet is preferred.
	 */
	public boolean getUseTriplet() {
		return useTriplet;
	}
	
	/**
	 * Set if triplet is preferred.
	 * 
	 * @param useTriplet true if triplet is preferred.
	 */
	public void setUseTriplet(boolean useTriplet) {
		this.useTriplet = useTriplet;
	}
	
	/**
	 * Clear the current MML text.
	 */
	void clear() {
		mmlEventList.clear();
	}
	
	/**
	 * Get if the first note is processed or not.
	 * 
	 * @return true if the first note is not processed yet.
	 */
	public boolean isFirstNote() {
		return firstNote;
	}
	
	/**
	 * Set if the first note is processed or not.
	 * 
	 * @param firstNote true if the first note is not processed yet.
	 */
	public void setFirstNote(boolean firstNote) {
		this.firstNote = firstNote;
	}
	
	/**
	 * Get if conversion is already finished.
	 * 
	 * @return True if conversion is already finished.
	 */
	public boolean isFinished() {
		return finished;
	}
	
	/**
	 * Set if conversion is already finished.
	 * 
	 * @return True if conversion is already finished.
	 */
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
	public long getCurrentNoteLastTick() {
		return currentNoteLastTick;
	}
	
	public void setCurrentNoteLastTick(long currentNoteLastTick) {
		this.currentNoteLastTick = currentNoteLastTick;
	}
	
	/**
	 * 
	 * @return the current track volume.
	 */
	public int getCurrentVolume() {
		return currentVolume;
	}
	
	/**
	 * Sets the current track volume.
	 * 
	 * @param currentVolume
	 */
	public void setCurrentVolume(int currentVolume) {
		this.currentVolume = currentVolume;
	}
	
	public int getCurrentVelocity() {
		return currentVelocity;
	}
	
	public void setCurrentVelocity(int currentVelocity) {
		this.currentVelocity = currentVelocity;
	}
	
	public int getCurrentExpression() {
		return currentExpression;
	}
	
	public void setCurrentExpression(int currentExpression) {
		this.currentExpression = currentExpression;
	}
	
	public int getCurrentPan() {
		return currentPan;
	}
	
	public void setCurrentPan(int currentPan) {
		this.currentPan = currentPan;
	}
	
	/**
	 * Appends the specified MML event.
	 * 
	 * @param str MML event.
	 */
	public void add(MMLEvent event) {
		mmlEventList.add(event);
	}
	
	/**
	 * Appends the specified MML events.
	 * 
	 * @param str Collection of MML events.
	 */
	public void addAll(Collection<MMLEvent> events) {
		mmlEventList.addAll(events);
	}
	
	/**
	 * Returns true if, and only if, length() is 0.
	 * 
	 * @return true if length() is 0, otherwise false
	 */
	public boolean isEmpty() {
		return mmlEventList.isEmpty();
	}
	
	/**
	 * Write the final MML.
	 * 
	 * @param writer Destination to write MML text.
	 */
	void writeMML(StringBuilder writer) {
		if(!mmlEventList.isEmpty()) {
			this.trimIfEmpty();
			
			StringBuilder mmlBuffer = new StringBuilder();
			
			// Satanic way to solve ties issues with commands inside notes
			boolean skip;
			boolean skipNext = false;
			for(int i = 0; i < mmlEventList.size(); i++) {
				if(skipNext) {
					skipNext = false;
					continue;
				}
				skip = false;
				MMLEvent event = mmlEventList.get(i);
				
				/*
				if(event.getCommand().equals(mmlSymbol.getTie())) {
					skip = false;
					
					// Check if we have a tie followed by a note command
					// If yes, remove the note name and skip it
					if(i < mmlEventList.size() - 1) {
						String nextCommand = mmlEventList.get(i+1).getCommand();
						if(mmlSymbol.isNoteOrRest(nextCommand)) {
							mmlBuffer.append(event.getCommand());
							mmlBuffer.append(nextCommand.replaceAll("[abcdefgr][+-]?", ""));
							skip = true;
							skipNext = true;
						} else if(nextCommand.equals(mmlSymbol.getOctaveDown())
								|| nextCommand.equals(mmlSymbol.getOctaveUp())) {
							skip = true;
						}
					}
				} else if(event.getCommand().equals(mmlSymbol.getVolumeMacro())) {
					// Optimize consecutive volume commands
					skip = checkIfCommandNext(mmlSymbol.getVolumeMacro(), i);
				} else if(event.getCommand().equals(mmlSymbol.getPanMacro())) {
					skip = checkIfCommandNext(mmlSymbol.getPanMacro(), i);
				}
				*/
				
				if(!skip) {
					// If not set to skip the current event, write it.
					mmlBuffer.append(event.toString());
				}
			}
			
			String mmlString = mmlBuffer.toString();
			if(useTriplet) {
				mmlString = convertToTriplet(mmlString);
			}
			
			writer.append(mmlString);
			
			if(!mmlString.endsWith(System.getProperty("line.separator"))) {
				writer.append(System.getProperty("line.separator"));
			}
		}
	}
	
	private boolean checkIfCommandNext(String command, int index) {
		for(int j = index + 1; j < mmlEventList.size(); j++) {
			String other = mmlEventList.get(j).getCommand();
			if(!other.equals(" ")) {
				if(other.equals(command)) {
					// If there's another volume command, we don't need to write the current one
					return true;
				} else if(mmlSymbol.isNoteOrRest(other) || other.matches("(o\\d+)|([<>]+)")) {
					// If there's a note (non-rest) next, we have to write the volume command
					return false;
				}
			}
		}
		return false;
	}
	
	private void trimIfEmpty() {
		boolean beginning = true;
		boolean empty = true;
		MMLEvent keepThis = new MMLEvent("");
		for(MMLEvent event : mmlEventList) {
			String command = event.getCommand();
			if(beginning) {
				if(command.equals(mmlSymbol.getTempo())) {
					keepThis = event;
				} else if(!command.equals(" ") && !command.equals("\n")) {
					if(mmlSymbol.isNote(command)) {
						empty = false;
						break;
					}
					beginning = false;
				}
			} else {
				if(mmlSymbol.isNote(command) || command.equals(mmlSymbol.getTempo())
						|| command.equals(mmlSymbol.getOctave())) {
					empty = false;
					break;
				}
			}
		}
		if(empty) {
			mmlEventList.clear();
			mmlEventList.add(keepThis);
		}
	}
	
	/**
	 * Use triplet rather than a simple note.
	 */
	private String convertToTriplet(String mmlString) {
		String converted = mmlString;
		for(int i = 0; i < 8; i++) {
			int noteLenTo = 1 << i;
			int noteLenFrom = noteLenTo * 3;
			converted = converted.replaceAll(
					REGEX1 + noteLenFrom + REGEX2 + noteLenFrom + REGEX2 + noteLenFrom,
					"\\" + mmlSymbol.getTripletStart(noteLenTo) + "$1"
							+ (mmlSymbol.shouldTripletHaveLengthInBracket() ? "\\" + noteLenTo : "") + "$2"
							+ (mmlSymbol.shouldTripletHaveLengthInBracket() ? "\\" + noteLenTo : "") + "$3"
							+ (mmlSymbol.shouldTripletHaveLengthInBracket() ? "\\" + noteLenTo : "") + "\\"
							+ mmlSymbol.getTripletEnd(noteLenTo)); // c12c12c12 -> {c4c4c4}
			if(mmlSymbol.shouldTripletHaveLengthInBracket()) {
				converted = converted.replaceAll(REGEX1 + (noteLenFrom * 2) + REGEX2 + noteLenFrom,
						"\\" + mmlSymbol.getTripletStart(noteLenTo) + "$1\\" + (noteLenTo * 2) + "$2\\" + noteLenTo
								+ "\\" + mmlSymbol.getTripletEnd(noteLenTo)); // c6c12 -> {c2c4}
				converted = converted.replaceAll(REGEX1 + noteLenFrom + REGEX2 + (noteLenFrom * 2),
						"\\" + mmlSymbol.getTripletStart(noteLenTo) + "$1\\" + noteLenTo + "$2\\" + (noteLenTo * 2)
								+ "\\" + mmlSymbol.getTripletEnd(noteLenTo)); // c12c6 -> {c4c2}
			} else {
				converted = converted.replaceAll(REGEX1 + (noteLenFrom * 2) + REGEX2 + noteLenFrom,
						"\\" + mmlSymbol.getTripletStart(noteLenTo) + "$1\\" + mmlSymbol.getTie() + "$2\\"
								+ mmlSymbol.getTripletEnd(noteLenTo)); // c6c12 -> {cc^}
				converted = converted.replaceAll(REGEX1 + noteLenFrom + REGEX2 + (noteLenFrom * 2),
						"\\" + mmlSymbol.getTripletStart(noteLenTo) + "$1$2\\" + mmlSymbol.getTie() + "\\"
								+ mmlSymbol.getTripletEnd(noteLenTo)); // c12c6 -> {c^c}
			}
		}
		return mmlString;
	}
}
