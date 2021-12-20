package com.googlecode.loveemu.petitemm;

public class MMLSymbol {
	
	/**
	 * MML note name table.
	 */
	private String[] notes = {"c", "c+", "d", "d+", "e", "f", "f+", "g", "g+", "a", "a+", "b"};
	
	/**
	 * MML text for rest.
	 */
	private String rest = "r";
	
	/**
	 * MML text for tie.
	 */
	private String tie = "^";
	
	/**
	 * MML text for setting octave.
	 */
	private String octave = "o";
	
	/**
	 * MML text for tempo.
	 */
	private String tempo = "t";
	
	/**
	 * MML text for instrument.
	 */
	private String instrument = "@";
	
	/**
	 * MML text for pan.
	 */
	private String pan = "y";
	
	/**
	 * MML text for volume.
	 */
	private String volume = "v";
	
	/**
	 * MML text for increasing octave.
	 */
	private String octaveUp = ">";
	
	/**
	 * MML text for decreasing octave.
	 */
	private String octaveDown = "<";
	
	/**
	 * MML text for end of track. [OBSOLETED]
	 */
	//private String trackEnd = "#_ ;[REPLACE _ WITH CHANNEL NUMBER]";
	
	/**
	 * MML text for triplet start.
	 */
	private String tripletStart = "{";
	
	/**
	 * MML text for triplet end.
	 */
	private String tripletEnd = "}";
	
	/**
	 * true if triplet should have length in bracket. ({c4c4c4} or {ccc})
	 */
	private boolean tripletHaveLengthInBracket = true;
	
	/**
	 * MML text for instrument macro.
	 */
	private String instrumentMacro = "I";
	
	/**
	 * MML text for instrument macro.
	 */
	private String volumeMacro = "V";
	
	/**
	 * MML text for instrument macro.
	 */
	private String panMacro = "Y";
	
	/**
	 * MML text for tick length.
	 */
	private String ticks = "=";
	
	/**
	 * Construct a new MML symbol set.
	 */
	public MMLSymbol() {
		super();
	}
	
	/**
	 * Construct a new MML symbol set.
	 * 
	 * @param obj
	 */
	public MMLSymbol(MMLSymbol obj) {
		notes = obj.notes;
		rest = obj.rest;
		tie = obj.tie;
		octave = obj.octave;
		tempo = obj.tempo;
		instrument = obj.instrument;
		octaveUp = obj.octaveUp;
		octaveDown = obj.octaveDown;
		//trackEnd = obj.trackEnd;
		tripletStart = obj.tripletStart;
		tripletEnd = obj.tripletEnd;
		instrumentMacro = obj.instrumentMacro;
		volumeMacro = obj.volumeMacro;
		panMacro = obj.panMacro;
		ticks = obj.ticks;
	}
	
	public String getNote(int index) {
		return notes[index];
	}
	
	public String[] getNotes() {
		return notes;
	}
	
	public void setNotes(String[] notes) {
		this.notes = notes;
	}
	
	public String getRest() {
		return rest;
	}
	
	public void setRest(String rest) {
		this.rest = rest;
	}
	
	public String getTie() {
		return tie;
	}
	
	public void setTie(String tie) {
		this.tie = tie;
	}
	
	public String getOctave() {
		return octave;
	}
	
	public void setOctave(String octave) {
		this.octave = octave;
	}
	
	public String getTempo() {
		return tempo;
	}
	
	public void setTempo(String tempo) {
		this.tempo = tempo;
	}
	
	public String getInstrument() {
		return instrument;
	}
	
	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}
	
	public String getOctaveUp() {
		return octaveUp;
	}
	
	public void setOctaveUp(String octaveUp) {
		this.octaveUp = octaveUp;
	}
	
	public String getOctaveDown() {
		return octaveDown;
	}
	
	public void setOctaveDown(String octaveDown) {
		this.octaveDown = octaveDown;
	}
	
	/*public String getTrackEnd() {
		return trackEnd;
	}*/
	
	/*public void setTrackEnd(String trackEnd) {
		this.trackEnd = trackEnd;
	}*/
	
	public String getTripletStart(int totalLength) {
		return tripletStart;
	}
	
	public void setTripletStart(String tripletStart) {
		this.tripletStart = tripletStart;
	}
	
	public String getTripletEnd(int totalLength) {
		return tripletEnd;
	}
	
	public void setTripletEnd(String tripletEnd) {
		this.tripletEnd = tripletEnd;
	}
	
	public boolean shouldTripletHaveLengthInBracket() {
		return tripletHaveLengthInBracket;
	}
	
	public void setTripletHaveLengthInBracket(boolean tripletHaveLengthInBracket) {
		this.tripletHaveLengthInBracket = tripletHaveLengthInBracket;
	}
	
	public String getInstrumentMacro() {
		return instrumentMacro;
	}
	
	public void setInstrumentMacro(String instrumentMacro) {
		this.instrumentMacro = instrumentMacro;
	}
	
	public String getVolumeMacro() {
		return volumeMacro;
	}
	
	public void setVolumeMacro(String volumeMacro) {
		this.volumeMacro = volumeMacro;
	}
	
	public String getPanMacro() {
		return panMacro;
	}
	
	public void setPanMacro(String panMacro) {
		this.panMacro = panMacro;
	}
	
	public String getPan() {
		return pan;
	}
	
	public void setPan(String pan) {
		this.pan = pan;
	}
	
	public String getVolume() {
		return volume;
	}
	
	public void setVolume(String volume) {
		this.volume = volume;
	}
	
	public String getTicks() {
		return ticks;
	}
	
	public void setTicks(String ticks) {
		this.ticks = ticks;
	}
	
	public boolean isRest(String command) {
		return command.startsWith(rest);
	}
	
	public boolean isNote(String command) {
		for(String note : notes) {
			if(command.startsWith(note)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isTie(String command) {
		return command.startsWith(tie);
	}
	
	public boolean isNoteOrTie(String command) {
		return isNote(command) || isTie(command);
	}
	
	public boolean isOctaveChange(String command) {
		return command.matches("o[-]?\\d+") || command.matches("<+|>+");
	}
	
}
