
package com.company;

        import javax.sound.sampled.*;
        import java.io.IOException;
        import java.net.URL;

public class ClipOutput {

    public static Mixer mixer;
    public static Clip clip;

    public static void main(String[] args) {
        Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
       /* DataLine.Info inf = new DataLine.Info(Clip.class, AudioFormat[]);
	for(Mixer.Info info : mixerInfos)
    {
        System.out.println(info.getName() + " ... " + info.getDescription());
    }*/
        mixer = AudioSystem.getMixer(mixerInfos[0]);

        DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
        try { clip = (Clip)mixer.getLine(dataInfo);}
        catch(LineUnavailableException lue) { lue.printStackTrace(); }

        try{
            URL soundURL = Main.class.getResource("win.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
            clip.open(audioStream);
        }
        catch(LineUnavailableException lue){lue.printStackTrace();}
        catch(UnsupportedAudioFileException uafe){uafe.printStackTrace();}
        catch(IOException ioe){ ioe.printStackTrace();}
        catch(NullPointerException npe){ npe.printStackTrace();}

        clip.start();

        do{
            try {Thread.sleep(50);}
            catch(InterruptedException ie){ ie.printStackTrace();}
        }   while(clip.isActive());
    }
}
