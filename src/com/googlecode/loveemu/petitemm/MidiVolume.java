package com.googlecode.loveemu.petitemm;

public class MidiVolume {
	
	int volume;
	int quantization;
	int expression;
	int pan;
	
	public MidiVolume(int volume, int quantization, int expression, int pan) {
		super();
		this.volume = volume;
		this.quantization = quantization;
		this.expression = expression;
		this.pan = pan;
	}
	
	@Override
	public String toString() {
		return "MidiVolume [volume=" + volume + ", quantization=" + quantization + ", expression=" + expression
				+ ", pan=" + pan + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + expression;
		result = prime * result + pan;
		result = prime * result + quantization;
		result = prime * result + volume;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		MidiVolume other = (MidiVolume) obj;
		if(expression != other.expression)
			return false;
		if(pan != other.pan)
			return false;
		if(quantization != other.quantization)
			return false;
		if(volume != other.volume)
			return false;
		return true;
	}
	
}
