package com.company;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by ciochonm on 07.12.2016.
 */
public class TargetInput {


    public static void main(String[] args) {
        try {
            System.out.println("Starting the recording");
            AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);

            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            if (!AudioSystem.isLineSupported(info)) {
                System.err.println("Line not supported");
            }

            final TargetDataLine targetLine = (TargetDataLine) AudioSystem.getLine(info);
            targetLine.open();
            targetLine.start();
            System.out.println("Starting the recording");
            Thread thread = new Thread() {
                @Override
                public void run() {
                    AudioInputStream audioStream = new AudioInputStream(targetLine);
                    System.out.println("Creating a .wav file");
                    File audioFile = new File("out/production/untitled/com/company/recording.wav");
                    try {
                        AudioSystem.write(audioStream, AudioFileFormat.Type.WAVE, audioFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
            Thread.sleep(5000);
            System.out.println("Stopping the recording");
            targetLine.stop();
            targetLine.close();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        } catch (LineUnavailableException lue) {
            lue.printStackTrace();
        }
    }
}
