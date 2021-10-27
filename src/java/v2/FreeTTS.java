package v2;

import java.beans.PropertyVetoException;
import java.util.Locale;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.EngineListener;
import javax.speech.synthesis.SpeakableEvent;
import javax.speech.synthesis.SpeakableListener;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

public class FreeTTS implements Runnable {

    public static Synthesizer synthesizer;

    String text;
    public FreeTTS(String text){
        this.text = text;
    }
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */

    public static void handleShutdown() {
        try {
            synthesizer.deallocate();
        } catch (EngineException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            // Set property as Kevin Dictionary
            System.setProperty(
                    "freetts.voices",
                    "com.sun.speech.freetts.en.us"
                            + ".cmu_us_kal.KevinVoiceDirectory");

            // Register Engine
            Central.registerEngineCentral(
                    "com.sun.speech.freetts"
                            + ".jsapi.FreeTTSEngineCentral");

            // Create a Synthesizer
            synthesizer
                    = Central.createSynthesizer(
                    new SynthesizerModeDesc(Locale.US));

            // Allocate synthesizer
            synthesizer.allocate();

//            synthesizer.getSynthesizerProperties().setSpeakingRate(100);
//            synthesizer.getSynthesizerProperties().setPitch(100);
//            synthesizer.getSynthesizerProperties().setVolume(200);

            // Resume Synthesizer
            synthesizer.resume();

            synthesizer.addSpeakableListener(new SpeakableListener() {
                @Override
                public void markerReached(SpeakableEvent speakableEvent) {

                }

                @Override
                public void speakableCancelled(SpeakableEvent speakableEvent) {

                }

                @Override
                public void speakableEnded(SpeakableEvent speakableEvent) {

                }

                @Override
                public void speakablePaused(SpeakableEvent speakableEvent) {

                }

                @Override
                public void speakableResumed(SpeakableEvent speakableEvent) {

                }

                @Override
                public void speakableStarted(SpeakableEvent speakableEvent) {
                    if (speakableEvent.getText() != null){
                        String messageText = speakableEvent.getText();
                        String data = "";
                        if (messageText.contains(":")){
                            data = messageText.substring(messageText.indexOf(":") + 2);
                        }
                        System.out.println(data);

                        if (data.equalsIgnoreCase("balls") || data.equalsIgnoreCase("sus"));
                        try {
                            synthesizer.getSynthesizerProperties().setSpeakingRate(20);
                            synthesizer.getSynthesizerProperties().setPitch(10);
                        } catch (PropertyVetoException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void topOfQueue(SpeakableEvent speakableEvent) {

                }

                @Override
                public void wordStarted(SpeakableEvent speakableEvent) {
                }
            });
            // Speaks the given text
            // until the queue is empty.
            synthesizer.speakPlainText(
                    text, null);
            synthesizer.waitEngineState(
                    Synthesizer.DEALLOCATED);
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
