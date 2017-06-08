package core.helper;

import com.musicg.fingerprint.*;
import com.musicg.wave.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.*;

public class CompareWaveFiles {

    protected static final Logger LOG = LoggerFactory.getLogger(CompareWaveFiles.class);

    public static String getSimilarity(String clipId) {
        String path = System.getProperty("user.dir") + "\\target\\";
        Wave adblockOn = readWaveFile(path + clipId + "_adblock-on.wav", 0);
        Wave adblockOff = readWaveFile(path + clipId + "_adblock-off.wav", 0);
        FingerprintSimilarity similarity = adblockOn.getFingerprintSimilarity(adblockOff);
        float similar = similarity.getSimilarity();
        LOG.debug(" Similarity for " + clipId + " = " + (100 * similar) + " %");
        return clipId + "_adblock-off.wav, " + convertSimilarity2bool(similar);
    }

    private static String convertSimilarity2bool(float similarity) {
        if (similarity < 0.98) {
            return "true";
        } else {
            return "false";
        }
    }

    private static Wave readWaveFile(String filePathString, int cnt) {
        Path path = Paths.get(filePathString);
        Wave file = null;
        if (Files.exists(path) && cnt < 10) {
            file = new Wave(filePathString);
        } else {
            LOG.warn("Wait untill file will be save, cnt=" + cnt);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            readWaveFile(filePathString, cnt + 1);
        }
        return file;
    }
}
