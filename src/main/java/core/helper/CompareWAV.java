package core.helper;

import com.bitsinharmony.recognito.MatchResult;
import com.bitsinharmony.recognito.Recognito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class CompareWAV {

    protected static final Logger LOG = LoggerFactory.getLogger(CompareWAV.class);
    private static final String PATH = System.getProperty("user.dir") + "\\target\\";

/*
    public static void main(String[] args) throws IOException, UnsupportedAudioFileException {

//        String clipid1 = "N-aK6JnyFmk", clipid2 = "0TFmncOtzcE", clipid3 = "Oe3VBoE3g4k", clipid4 = "RNt4kQGnk48",
//                clipid5 = "N-aK6JnyFmk&list", clipid6 = "CHaGFE0szJk", clipid7 = "y8faJ1uWuNY", clipid8 = "IlcFAsJ8zb4",
//                clipid9 = "14zIhlfeUjE", clipid10 = "";
        String clipid1 = "qeMFqkcPYcg", clipid2 = "QUvVdTlA23w", clipid3 = "p9j8RGTqju0", clipid4 = "cxdwEOpGknk",
                clipid5 = "N-aK6JnyFmk&list", clipid6 = "CHaGFE0szJk", clipid7 = "y8faJ1uWuNY", clipid8 = "IlcFAsJ8zb4",
                clipid9 = "14zIhlfeUjE", clipid10 = "";
        Recognito<String> recognito = new Recognito(44100.0f);

        recognito.createVoicePrint(clipid1, new File(clipid1 +"_adblock-off.wav"));
        recognito.createVoicePrint(clipid2, new File(clipid2 +"_adblock-off.wav"));
        recognito.createVoicePrint(clipid3, new File(clipid3 +"_adblock-off.wav"));
        recognito.createVoicePrint(clipid4, new File(clipid4 +"_adblock-off.wav"));
//        recognito.createVoicePrint(clipid5, new File(clipid5 +"_adblock-off.wav"));
//        recognito.createVoicePrint(clipid6, new File(clipid6 +"_adblock-off.wav"));
//        recognito.createVoicePrint(clipid7, new File(clipid7 +"_adblock-off.wav"));
//        recognito.createVoicePrint(clipid8, new File(clipid8 +"_adblock-off.wav"));
//        recognito.createVoicePrint(clipid9, new File(clipid9 +"_adblock-off.wav"));
        //recognito.createVoicePrint(clipid10, new File(clipid10 +"_adblock-off.wav"));

        String compreWith = clipid4;
        System.out.println(" <<<< Compare with: " + compreWith + "_ablock-on.wav >>>> \n");
        List<MatchResult<String>> matches = recognito.identify(new File(compreWith + "_adblock-on.wav"));

        matches.forEach(m -> System.out.println("Similrity for file: " + m.getKey() + " is about " + m.getLikelihoodRatio() + "% positive about it..."));
        System.out.println("=======================");
        matches.stream().
                filter(m -> m.getKey().equals(compreWith)).collect(Collectors.toList()).
                forEach(m -> System.out.println("Similrity for file: " + m.getKey() + " is about " + m.getLikelihoodRatio() + "% positive about it..."));

    }
*/
    public static String getSimilarity(String clipId, List<String> trainingSample) throws IOException, UnsupportedAudioFileException {
        LOG.debug("invoke getSimilarity from CompareWAV");
        Recognito<String> recognito = new Recognito(44100.0f);
        trainingSample.forEach(record -> { try {
                recognito.createVoicePrint(record, new File(PATH + record + "_adblock-off.wav"));
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        List<MatchResult<String>> matches = recognito.identify(new File(PATH + clipId + "_adblock-on.wav"));

        List<Integer> res = matches.stream().
                filter(m -> m.getKey().equals(clipId)).limit(1).map(m -> m.getLikelihoodRatio()).collect(Collectors.toList());
        LOG.debug(" Similarity for " + clipId + " = " + res.get(0) + " %");
        return clipId + "_adblock-off.wav, " + convertSimilarity2bool(res.get(0));
    }

    private static String convertSimilarity2bool(int similarity) {
        if (similarity < 95) {
            return "true";
        } else {
            return "false";
        }
    }
}