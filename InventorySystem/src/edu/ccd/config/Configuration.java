package edu.ccd.config;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Configuration {
    //TODO: There should be some kind of mechanism by which we could assure that more than one read/write is going on at a time...

    //We would like to use a path, so we can stay somewhat platform independent
    public Path configPath = FileSystems.getDefault().getPath("config","config.txt");

    public void writeConfig (String out) throws IOException {
        FileOutputStream fos = new FileOutputStream(configPath.toFile());

        /*We will use a Channel to track locations within a file. A file is really an array of bytes, and a channel is
        where we are currently looking in the file.  Because our config is really only one line it is not necessary
        but if you can imagine how big a typical config is, you can see the power of using a channel.*/
        fos.getChannel().write(ByteBuffer.wrap(out.getBytes()));
        fos.getChannel().close();
        fos.close();
    }

    public String readConfig() throws IOException {
        String myReturn;
        FileReader fr = new FileReader(configPath.toFile());
        BufferedReader br = new BufferedReader(fr);
        myReturn = br.readLine();
        br.close();
        fr.close();
        return myReturn;
    }
}
