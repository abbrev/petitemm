PetiteMM
========
[![Travis Build Status](https://travis-ci.org/loveemu/petitemm.svg?branch=master)](https://travis-ci.org/loveemu/petitemm)

PetiteMM is a SMF (MIDI) to MML converter.

Features:

- Supports triplets such as `c12d12e12`
- No polyphonic support, only one note will be converted
- Timings between tracks will never desync like some other converters
- Control changes are supported

How To Use
----------
### Easy Setup
With the current update, the best way to execute this code is to download the project as a .zip and run PetiteMM.java in the command line. If you just want to output an MML file without thinking about any of the commands below, then use one of these two options:

|Option            |Description                                                                                               |
|------------------|----------------------------------------------------------------------------------------------------------|
|--simple-output   |Returns an AMK-compatible MML file containing only the raw note data for each channel                     |
|--complex-output  |Returns an AMK-compatible MML file containing note data, panning values, instrument id's, and volume data |

Note that --complex-output will output a much larger MML file, and ideally should be optimized before being used.

As an example, to run simple-output on a MIDI file Test1, you would type `java PetiteMM --simple-output Test1.mid` into the command line. The same format goes for complex output as well.

The rest of the How to Use section is unchanged from the old version of PetiteMM, with some slight additions for the remaining options.

1. Install [Java](http://java.com/download/) Runtime Environment (if you do not have yet)
2. Drag and drop .mid files into PetiteMM.bat, and .mml files will be saved in the input directory

You can run PetiteMM manually with `java -jar PetiteMM.jar (options) input.mid [input2.mid...]` (list all midi files you want to convert last). Use `java -jar PetiteMM.jar (options) *.mid` to convert all midi files in the current folder.

### Options

|Option               |Arguments        |Description                                                                       |
|---------------------|-----------------|----------------------------------------------------------------------------------|
|-o                   |[string]filename |Specify the output MML filename.                                                  |
|--put-spaces         |n/a              |Put spaces between each note and command for readability.                         |
|--dots               |[int]count       |Maximum dot counts allowed for dotted-note, -1 for infinity. (default=-1)         |
|--middle-octave      |[int]octave      |Middle octave where A440 is found. (default=4)                                    |
|--timebase           |[int]TPQN        |Timebase of target MML, 0 to keep the input timebase. (default=0)                 |
|--input-timebase     |[int]TPQN        |Timebase of input sequence, 0 to keep the input timebase. (default=0)             |
|--quantize-precision |[int]length      |Specify the minimum note length for quantization. (example: 64 for 64th note)     |
|--no-quantize        |n/a              |Prevent adjusting note length. Result will be more accurate but more complicated. |
|--octave-reverse     |n/a              |Swap the octave symbol. (not recommended)                                         |
|--use-triplet        |n/a              |Use triplet syntax if possible. (really not so smart)                             |
|--no-control-changes |n/a              |Ignore control change messages (instrument, volume, panning).                     |
|--no-expression      |n/a              |Ignore Expression messages (Control Change message 11) for volume computation.    |
|--multiply-volumes   |[float]factor    |Multiply all output volumes by a constant factor.                                 |
|--no-pan-correction  |n/a              |Don't adjust the volume values based on the panning values, which is done to account for the fact that AMK's volumes get louder for pannings farther from the center.                                                                                          |
|--use-ticks          |n/a              |Convert note lengths to MML tick notation.                                        |

Special Thanks
--------------

- TinyMM: a similar converter, PetiteMM will never be created without it.
