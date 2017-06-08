# YouTube preroll ads test

Requirements: JDK 8 + maven

For run test:

 ### mvn clean test -Dsearch.request="California dreamin'"

 All 'wave' files stored in ./target/ folder

 and look result here: ./target/result.csv

 Log file: ./trget/testrun.log

Note: in case of fails, try to change  'MixerInfo' line out
JavaSoundRecorder: line 33
		Mixer soundOut = AudioSystem.getMixer(mixerInfo[2]);