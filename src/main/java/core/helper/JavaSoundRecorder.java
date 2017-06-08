package core.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.*;
import java.io.*;

public class JavaSoundRecorder {

	protected static final Logger LOG = LoggerFactory.getLogger(JavaSoundRecorder.class);
	static final long RECORD_TIME = 60000;	// 1 minute
	File wavFile;
	AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
	TargetDataLine line;

	public JavaSoundRecorder(String fileName) {
		wavFile = new File(System.getProperty("user.dir") + "\\target\\" + fileName + ".wav");
	}

	AudioFormat getAudioFormat() {
		return new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
	}

	void start() {
		try {
			AudioFormat format = getAudioFormat();
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
			// checks if system supports the data line
			if (!AudioSystem.isLineSupported(info)) {
				LOG.error("Line not supported. Try to change mixerInfo value");
				System.exit(0);
			}
			Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
			LOG.debug(" mixerInfo[].length: " + mixerInfo.length);
			//!Mixer soundOut = AudioSystem.getMixer(mixerInfo[3]);
			Mixer soundOut = AudioSystem.getMixer(mixerInfo[2]);
			line = (TargetDataLine) soundOut.getLine(info);
	    	line.open(format);
	    	LOG.debug("Start recording");
			line.start();
			AudioInputStream ais = new AudioInputStream(line);
			AudioSystem.write(ais, fileType, wavFile);

		} catch (LineUnavailableException ex) {
			ex.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	void finish() {
		line.stop();
		line.close();
		LOG.debug(" >> Finished recording");
	}

	public static void grepSound(String fileName) {
		LOG.debug("  >>>> GREP SOUND for " + fileName);
		final JavaSoundRecorder recorder = new JavaSoundRecorder(fileName);
		Thread stopper = new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(RECORD_TIME);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				recorder.finish();
			}
		});
		stopper.start();
		recorder.start();
	}
}